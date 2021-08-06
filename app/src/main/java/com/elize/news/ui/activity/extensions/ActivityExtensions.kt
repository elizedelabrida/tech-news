package com.elize.news.ui.activity.extensions

import android.app.Activity
import android.widget.Toast

fun Activity.displayError(message: String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_LONG
    ).show()
}