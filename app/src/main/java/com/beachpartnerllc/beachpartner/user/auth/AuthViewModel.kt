package com.beachpartnerllc.beachpartner.user.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.*
import androidx.lifecycle.ViewModel
import com.beachpartnerllc.beachpartner.etc.base.BaseErrorEvent
import com.beachpartnerllc.beachpartner.etc.common.SingleLiveEvent
import com.beachpartnerllc.beachpartner.etc.model.rest.isError
import com.beachpartnerllc.beachpartner.etc.model.rest.isLoading
import com.beachpartnerllc.beachpartner.etc.model.rest.isSuccess
import com.beachpartnerllc.beachpartner.user.Profile
import com.beachpartnerllc.beachpartner.user.auth.AuthState.*
import com.beachpartnerllc.beachpartner.user.state.State
import javax.inject.Inject

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 04 Jun 2018 at 2:44 PM
 */
class AuthViewModel @Inject constructor(private val repo: AuthRepository) : ViewModel() {
    
    val loading = MutableLiveData<Boolean>()
    val loginLoading = MutableLiveData<Boolean>()
    val auth = MutableLiveData<Auth>()
    val profile = MutableLiveData<Profile>()

    
    val nameError = MutableLiveData<BaseErrorEvent>()
    val state = MutableLiveData<AuthState>()
    val selectedStatePosition = MutableLiveData<Int>()
    private lateinit var stateList: List<State>

    val signInValidate = MutableLiveData<Boolean>()
    val signUpValidate = MutableLiveData<Boolean>()
    val signUp2Validate = MutableLiveData<Boolean>()
    val event = SingleLiveEvent<String>()

    fun signIn() = map(repo.signIn(auth.value!!)) {
        loginLoading.value = it.isLoading()
        when {
            it.isSuccess() -> {
                event.value = it.message
                state.value = AUTHENTICATED
            }

            it.isError() -> event.value = it.message
        }
    }!!

    fun register() = map(repo.register(profile.value!!)) {
        loading.value = it.isLoading()
        when {
            it.isSuccess() -> {
                autoSetEmail(it.data!!.email!!)
                state.value = REGISTERED
                profile.value = Profile()
            }

            it.isError() -> event.value = it.message
        }
    }!!

    fun signInSkipInitCount(): Long = if (signInValidate.value == true) 0 else 1

    fun signUpSkipInitCount(): Long = if (signUpValidate.value == true) 0 else 1

    fun signUp2SkipInitCount(): Long = if (signUp2Validate.value == true) 0 else 1

    private fun autoSetEmail(email: String) {
        val auth = auth.value!!
        auth.email = email
        this.auth.value = auth
    }

    fun setStatePosition(position: Int) {
        val user = profile.value!!
        user.stateId = stateList[position].stateId
        profile.value = user
    }

    fun getStates() = map(repo.getStateList()) {
        loading.value = it.isLoading()

        if (it.isSuccess()) {
            stateList = it.data!!
            selectedStatePosition.value = selectedStatePosition.value
        }
        it
    }!!

    init {
        auth.value = Auth()
        profile.value = Profile()
    }
}