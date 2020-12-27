package com.maruiz.pet.data.repository

import com.maruiz.pet.data.preferences.authentication.AuthenticationHelper

interface AuthenticationRepository {
    fun getToken(): String?

    fun setToken(token: String)

    fun logout()

    class Disk(private val authentication: AuthenticationHelper) : AuthenticationRepository {
        override fun getToken(): String? = authentication.token

        override fun setToken(token: String) {
            authentication.token = token
        }

        override fun logout() {
            authentication.token = null
        }
    }
}