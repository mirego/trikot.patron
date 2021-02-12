package com.trikot.sample.viewmodels.base

actual abstract class ViewModelController {
    lateinit var testingInstance: ConcurrencyTesting
    protected actual open fun onCleared() {
        // NO-OP
    }

    protected actual open fun startConcurrencyTest(
        freezeTest: Boolean,
        threadTest: Boolean
    ) {
        testingInstance = ConcurrencyTesting()

        if (freezeTest) {
            testingInstance.testFreeze()
        }

        if (threadTest) {
            testingInstance.testNotFrozenOnOtherThread()
        }
    }
}
