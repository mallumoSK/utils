@file:Suppress("unused")

package tk.mallumo.utils

import androidx.compose.runtime.*
import kotlinx.coroutines.flow.*

sealed class DataState<T>(val entry: T?) {
    class Idle<T>(entry: T? = null) : DataState<T>(entry)
    class Loading<T>(entry: T? = null) : DataState<T>(entry)
    class Result<T>(entry: T) : DataState<T>(entry) {
        @Suppress("unused")
        val nnvl
            get() = entry!!
    }

    class Error<T>(
        val message: String,
        val extraMessage: String? = null,
        val throwable: Throwable? = null,
        entry: T? = null
    ) : DataState<T>(entry)
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> mutableDataStateFlow(state: DataState<T>? = null): MutableStateFlow<DataState<T>> =
    MutableStateFlow(state ?: DataState.Idle())

@Suppress("NOTHING_TO_INLINE")
inline fun <T> mutableDataState(state: DataState<T>? = null): MutableState<DataState<T>> =
    mutableStateOf(state ?: DataState.Idle())

@Suppress("NOTHING_TO_INLINE")
inline fun <T> mutableStateFlowOf(default: T) = MutableStateFlow(default)
