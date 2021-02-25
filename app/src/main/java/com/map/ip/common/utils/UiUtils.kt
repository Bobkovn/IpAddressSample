package com.map.ip.common.utils

import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout


fun Boolean.visibleOrGone() = if (this) {
    View.VISIBLE
} else {
    View.GONE
}

fun Boolean.visibleOrInvisible() = if (this) {
    View.VISIBLE
} else {
    View.INVISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}

fun View.toInvisible() {
    visibility = View.INVISIBLE
}

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun TextInputLayout.clearErrorAfterTextChanged() = editText?.let { editText ->
    editText.doAfterTextChanged {
        if (it?.isNotEmpty() == true && error != null) {
            error = null
        }
    }
}