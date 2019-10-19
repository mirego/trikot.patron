package com.trikot.sample

import com.mirego.trikot.http.HttpConfiguration
import com.mirego.trikot.foundation.concurrent.AtomicReference

object Environment {
    private val internalFlavor = AtomicReference(Environment.Flavor.RELEASE)

    var flavor: Flavor
        get() = internalFlavor.value
        set(value) {
            internalFlavor.setOrThrow(internalFlavor.value, value)
            HttpConfiguration.baseUrl = value.baseUrl
        }

    enum class Flavor(
        val baseUrl: String
    ) {
        DEBUG("https://trikot.dev.mirego.com"),
        QA("https://trikot.qa.mirego.com"),
        STAGING("https://trikot.staging.mirego.com"),
        RELEASE("https://trikot.prod.mirego.com")
    }
}
