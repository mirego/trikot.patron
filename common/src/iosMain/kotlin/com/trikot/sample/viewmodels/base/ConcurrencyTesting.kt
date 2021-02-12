package com.trikot.sample.viewmodels.base

import com.trikot.sample.domain.impl.SomeMutableData
import kotlin.native.concurrent.TransferMode
import kotlin.native.concurrent.Worker
import kotlin.native.concurrent.freeze

private val worker = Worker.start()

fun background(block: () -> Unit) {
    worker.execute(TransferMode.SAFE, { block.freeze() }) {
        it()
    }
}

class ConcurrenyTesting {

}




val smd = SomeMutableData(3)
smd.i++
println("smd: $smd")

MrFreeze.freeze(smd)
smd.i++