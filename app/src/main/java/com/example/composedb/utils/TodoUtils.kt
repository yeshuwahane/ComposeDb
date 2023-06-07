package com.example.composedb.utils

import android.content.Context
import android.widget.Toast

class TodoUtils {
    fun makeToast(context: Context, msg: String){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

}