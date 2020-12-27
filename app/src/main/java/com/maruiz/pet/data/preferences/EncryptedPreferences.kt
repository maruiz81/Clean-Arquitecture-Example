package com.maruiz.pet.data.preferences

import android.content.SharedPreferences
import hu.autsoft.krate.Krate

open class EncryptedPreferences(encryptedSharedPreferences: SharedPreferences) : Krate {
    override val sharedPreferences: SharedPreferences = encryptedSharedPreferences
}