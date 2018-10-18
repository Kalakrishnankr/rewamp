package com.beachpartnerllc.beachpartner.user.profile

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.beachpartnerllc.beachpartner.etc.common.isEmail
import com.beachpartnerllc.beachpartner.etc.common.isMobile
import com.beachpartnerllc.beachpartner.etc.common.isName
import com.beachpartnerllc.beachpartner.etc.common.isPassword
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "Connections")
open class Profile(
    @PrimaryKey var userId: Int = -1,
    var firstName: String? = null,
    var lastName: String? = null,
    var stateId: Int? = null,
    var gender: Gender? = null,
    var userType: UserType? = null,
    var email: String? = null,
    var mobile: String? = null,
    var password: String? = null,
    var dob: String? = null,
    var avatarUrl: String? = null,
    var videoUrl: String? = null,
    var age: Int? = null,
    var status: String? = null) {
    @Ignore var dateOfBirth: Date? = null
    val fullName: String? get() = "$firstName $lastName"

    fun isFirstNameValid() = firstName.isName()

    fun isLastNameValid() = lastName.isName()

    fun isStateValid() = stateId != null

    fun isGenderValid() = gender != null

    fun isUserTypeValid() = userType != null

    fun isValid() = isFirstNameValid() && isLastNameValid() && isStateValid()
        && isGenderValid() && isUserTypeValid()

    fun isEmailValid() = email.isEmail()

    fun isMobileValid() = mobile.isMobile()

    fun isPasswordValid() = password.isPassword()

    fun isDobValid() = try {
        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        sdf.isLenient = false
        dateOfBirth = sdf.parse(dob)
        true
    } catch (e: ParseException) {
        false
    } catch (e: NullPointerException) {
        false
    }

    fun isValid2() = isEmailValid() && isMobileValid() && isPasswordValid() && isDobValid()

    fun isAthlete() = userType == UserType.ATHLETE

    fun isMale() = gender == Gender.MALE

    fun isFemale() = gender == Gender.FEMALE

    fun isCoach() = userType == UserType.COACH

    override fun equals(other: Any?): Boolean {
        if (other !is Profile) return false

        return firstName == other.fullName && lastName == other.lastName &&
            stateId == other.stateId && gender == other.gender &&
            userType == other.userType && email == other.email &&
            mobile == other.mobile && password == other.password &&
            dob == other.dob && avatarUrl == other.avatarUrl &&
            videoUrl == other.videoUrl && age == other.age &&
            status == other.status
    }
}

data class Session(val profile: Profile, val sessionId: String)