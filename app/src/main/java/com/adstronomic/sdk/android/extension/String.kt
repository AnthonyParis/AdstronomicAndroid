package com.adstronomic.sdk.extension

val String.hasHttpSign: Boolean
    get() {
        return this.startsWith("http")
    }