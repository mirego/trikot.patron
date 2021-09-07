package com.trikot.sample

import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.promise.Promise
import com.trikot.sample.models.Quote
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

object Api {
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        allowSpecialFloatingPointValues = true
        useArrayPolymorphism = true
        useAlternativeNames = false
    }

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
    }

    fun getQuotes(cancellableManager: CancellableManager?) =
        Promise.create<List<Quote>>(cancellableManager) { resolve, _ ->
            GlobalScope.launch(Dispatchers.Default) {
                val quotes: List<Quote> = client.get {
                    url("https://breaking-bad-quotes.herokuapp.com/v1/quotes/5")
                }
                resolve(quotes)
            }
        }
}
