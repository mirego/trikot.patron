package com.trikot.sample.models

import kotlinx.serialization.Serializable

@Serializable
data class Quote(val text: String, val author: String? = null)
