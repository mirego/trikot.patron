package com.trikot.sample.repositories.impl

import com.mirego.trikot.streams.reactive.ColdPublisher
import com.trikot.sample.Api
import com.trikot.sample.models.Quote
import com.trikot.sample.repositories.QuoteRepository
import org.reactivestreams.Publisher

class QuoteRepositoryImpl() : QuoteRepository {
    override fun getQuotes(): Publisher<List<Quote>> {
        return ColdPublisher(Api::getQuotes)
    }
}
