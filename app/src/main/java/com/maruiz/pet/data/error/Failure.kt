package com.maruiz.pet.data.error

sealed class Failure {
    object GenericError : Failure()

    /** * Extend this class for feature specific failures.*/
}