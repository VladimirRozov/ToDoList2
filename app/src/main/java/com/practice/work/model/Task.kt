package com.practice.work.model


import android.widget.EditText

class Task(key: String){
    var text: String? = null
    lateinit var view: EditText
    var keyText = key
}

