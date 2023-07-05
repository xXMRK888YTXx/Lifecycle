package com.xxmrk888ytxx.lifecycle

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ShareViewModel : ViewModel() {

    private val _state:MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.Counter(0))

    val state = _state.asStateFlow()

    private var isUserInApp = true

    private var userLeaveTime:Long? = null

    fun onUserLeaveFromApp() {
        if(!isUserInApp) return
        isUserInApp = false
        userLeaveTime = System.currentTimeMillis()
        updateCount(5)
    }

    fun onUserReturnInApp() {
        if(!isUserInApp) {
            isUserInApp = true
            updateCount(2)

            userLeaveTime?.let {
                val timeInOutApp = System.currentTimeMillis() - it

                updateCount(-((timeInOutApp / 60_000L).toInt() * 2))
                userLeaveTime = null
            }
        }
    }

    fun toUpdateCounterScreen() {
        _state.update { ScreenState.UpdateCounter(minOf(it.count,100)) }
    }

    fun toCounterScreen() {
        _state.update { ScreenState.Counter(minOf(it.count,100)) }
    }

    fun updateCount(value:Int) {
        _state.update {
            when(it) {
                is ScreenState.Counter -> it.copy( it.count + value)
                is ScreenState.UpdateCounter -> it.copy( it.count + value)
            }
        }
    }
}