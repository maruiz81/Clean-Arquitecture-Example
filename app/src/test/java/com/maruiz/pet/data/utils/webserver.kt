package com.maruiz.pet.data.utils

import com.squareup.moshi.Moshi
import okhttp3.mockwebserver.RecordedRequest
import org.amshove.kluent.shouldBeEqualTo

inline fun <reified T> RecordedRequest.getRequest(moshi: Moshi): T? =
    moshi.adapter(T::class.java).fromJson(this.body.readUtf8())

fun RecordedRequest.checkMethodAndPath(method: Method, path: String) {
    this.method shouldBeEqualTo method.name
    this.path shouldBeEqualTo path
}

enum class Method {
    POST, GET, PUT
}