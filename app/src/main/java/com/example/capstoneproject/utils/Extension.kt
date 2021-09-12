package com.example.capstoneproject.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.example.capstoneproject.ui.detail.cardetail.ParkingPositionAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

fun EditText.textChanges(): Flow<CharSequence> {
    return callbackFlow<CharSequence> {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    trySend(it)
                }
            }

        }
        addTextChangedListener(textWatcher)
        awaitClose { removeTextChangedListener(textWatcher) }
    }.onStart {
        if (text.isNotEmpty()) {
            emit(text)
        }
    }
}

fun EditText.initNotEmptyTextField(
    flows: ArrayList<Flow<Boolean>>,
    scope: CoroutineScope
): Flow<Boolean> =
    textChanges().debounce(100).map {
        it.isNotEmpty()
    }.also {
        it.launchIn(scope)
        flows.add(it)
    }

fun ParkingPositionAdapter.positionChanges(): Flow<Int>{
    return callbackFlow {
        val selectedPositionListener = ParkingPositionAdapter.SelectedPositionListener { position ->
            trySend(position)
        }
        addSelectedPositionListener(selectedPositionListener)
        awaitClose { removeSelectedPositionListener() }
    }
}