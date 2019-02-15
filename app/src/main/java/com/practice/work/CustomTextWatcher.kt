package com.practice.work

import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.text.Editable
import android.view.View


class CustomTextWatcher(internal var edList: Array<EditText>, v: Button) : TextWatcher {

    internal var v: View

    init {
        this.v = v
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
        for (editText in edList) {
            if (editText.text.toString().trim { it <= ' ' }.length <= 0) {
                v.setEnabled(false)
                break
            } else
                v.setEnabled(true)
        }
    }
}