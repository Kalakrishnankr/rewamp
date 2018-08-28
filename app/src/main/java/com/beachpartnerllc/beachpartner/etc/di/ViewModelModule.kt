package com.beachpartnerllc.beachpartner.etc.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.beachpartnerllc.beachpartner.user.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
	@Binds
	@IntoMap
	@ViewModelKey(AuthViewModel::class)
	fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel
	
	@Binds
	fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
