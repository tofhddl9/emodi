package com.lgtm.emoji_diary.utils

import android.app.Activity
import com.google.android.material.snackbar.Snackbar

fun Activity.showSnackBar(msg:String){
    Snackbar.make(this.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()
}