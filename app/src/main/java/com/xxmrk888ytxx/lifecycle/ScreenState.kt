package com.xxmrk888ytxx.lifecycle

sealed class ScreenState(open val count:Int)  {

    data class Counter(override val count: Int) : ScreenState(count)

    data class UpdateCounter(override val count: Int) : ScreenState(count)
}