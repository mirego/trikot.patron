package com.trikot.sample.viewmodels.base

import kotlin.native.concurrent.DetachedObjectGraph
import kotlin.native.concurrent.TransferMode
import kotlin.native.concurrent.Worker
import kotlin.native.concurrent.freeze

private val worker = Worker.start()

fun background(block: () -> Unit) {
    worker.execute(TransferMode.UNSAFE, { block }) {
        it()
    }
}

data class SomeMutableData(var i: Int)

class ConcurrencyTesting {

    // should crash
    fun testFreeze() {
        val smd = SomeMutableData(3)
        smd.i++
        println("testFreeze before freeze smd: $smd")
        smd.freeze()

        smd.i++
        println("testFreeze after freeze smd: $smd")
    }

    //should crash
    fun testNotFrozenOnOtherThread() {
        val smd = SomeMutableData(7)
        smd.i++
        println("testNotFrozenOnOtherThread before moving thread smd: $smd")
        background { incrementMutableData(smd) }
    }

    private fun incrementMutableData(smd: SomeMutableData) {
        println("testNotFrozenOnOtherThread before use smd: $smd")
        smd.i++
        println("testNotFrozenOnOtherThread after use smd: $smd")
    }
}




