package com.helpers.base.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Created by Tejas Deshmukh on 28/09/24.
 */

abstract class BaseViewModel<State : IViewModel.State, Event : IViewModel.Event, Effect : IViewModel.Effect> :
    ViewModel() {

    abstract val initialState: State

    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State> get() = _state.asStateFlow()

    private val _effects = Channel<Effect>(1)
    val effect = _effects.receiveAsFlow()

    protected fun render(newState: State) {
        _state.tryEmit(newState)
    }

    protected fun sendEffect(newEffect: Effect) {
        _effects.trySend(newEffect)
    }

    abstract fun onEvent(event: Event)
}