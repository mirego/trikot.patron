package com.trikot.sample.viewmodels.home.impl

import com.mirego.trikot.foundation.date.Date
import com.mirego.trikot.foundation.timers.TimerFactory
import com.mirego.trikot.kword.I18N
import com.mirego.trikot.streams.cancellable.CancellableManager
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.RefreshablePublisher
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.viewmodels.mutable.MutableButtonViewModel
import com.mirego.trikot.viewmodels.mutable.MutableLabelViewModel
import com.mirego.trikot.viewmodels.mutable.MutableViewModel
import com.mirego.trikot.viewmodels.properties.ViewModelAction
import com.trikot.sample.localization.KWordTranslation
import com.trikot.sample.viewmodels.home.HomeViewModel
import kotlin.time.Duration

class HomeViewModelImpl(
    cancellableManager: CancellableManager,
    refreshableQuotePublisher: RefreshablePublisher<String>,
    i18N: I18N,
    timerFactory: TimerFactory,
) :
    HomeViewModel, MutableViewModel() {

    private var mutation: Long = 0L
    private val publisher = Publishers.behaviorSubject(mutation.toString())

    init {
        val timer = timerFactory.repeatable(Duration.Companion.seconds(1)) {
            mutation++
            publisher.value = mutation.toString()
        }
        cancellableManager.add { timer.cancel() }
    }

    override val quoteLabel = MutableLabelViewModel().also {
        it.text = publisher
    }

    override val refreshButton = MutableButtonViewModel().also {
        it.text = i18N[KWordTranslation.REFRESH_BUTTON].just()
        it.action = ViewModelAction {
            refreshableQuotePublisher.refresh()
        }.just()
    }
}
