package com.trikot.sample.repositories.impl

import com.mirego.trikot.http.RequestBuilder
import com.mirego.trikot.http.requestPublisher.DeserializableHttpRequestPublisher
import com.mirego.trikot.http.requestPublisher.HttpRequestPublisher
import com.mirego.trikot.streams.reactive.ColdPublisher
import com.mirego.trikot.streams.reactive.map
import com.trikot.sample.models.Quote
import com.trikot.sample.repositories.QuoteRepository
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import org.reactivestreams.Publisher

class QuoteRepositoryImpl() : QuoteRepository {
    override fun getQuotes(): Publisher<List<Quote>> {
        return ColdPublisher { cancellableManager ->
            val request = RequestBuilder().also {
                it.baseUrl = "https://breaking-bad-quotes.herokuapp.com"
                it.path = "/v1/quotes/5"
            }

            DeserializableHttpRequestPublisher(ListSerializer(String.serializer()), request).also {
                it.execute()
                cancellableManager.add(it)
            }
        }.map {
            it.map {  Quote(it, it) }
        }
    }
}
