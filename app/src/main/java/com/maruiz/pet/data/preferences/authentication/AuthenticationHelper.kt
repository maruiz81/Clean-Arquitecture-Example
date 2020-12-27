package com.maruiz.pet.data.preferences.authentication

import android.content.SharedPreferences
import com.maruiz.pet.data.preferences.EncryptedPreferences
import hu.autsoft.krate.stringPref

class AuthenticationHelper(sharedPreferences: SharedPreferences) :
    EncryptedPreferences(sharedPreferences) {

    var token by stringPref(TOKEN_KEY)

    companion object {
        private const val TOKEN_KEY = "TOKEN"
    }
}