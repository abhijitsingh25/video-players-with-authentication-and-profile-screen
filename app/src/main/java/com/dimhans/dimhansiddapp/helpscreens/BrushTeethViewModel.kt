package com.dimhans.dimhansiddapp.helpscreens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BrushTeethViewModel: ViewModel() {
    private val _toothpasteClickCount = MutableStateFlow(0)
    val toothpasteClickCount = _toothpasteClickCount.asStateFlow()

    fun incrementToothpasteCount() {
        _toothpasteClickCount.value = _toothpasteClickCount.value + 1
    }
}