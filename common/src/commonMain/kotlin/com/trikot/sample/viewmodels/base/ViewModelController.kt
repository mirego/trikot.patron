package com.trikot.sample.viewmodels.base

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect abstract class ViewModelController() {
    protected open fun onCleared()

    protected open fun startConcurrencyTest(freezeTest:Boolean, threadTest:Boolean)
}
