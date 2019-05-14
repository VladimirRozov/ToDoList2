package com.practice.work.model

import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.text.Editable
import android.view.View


class CustomTextWatcher(private var edList: Array<EditText>, v: Button) : TextWatcher {

    private var v: View = v

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
        for (editText in edList) {
            if (editText.text.toString().trim { it <= ' ' }.isEmpty()) {
                v.isEnabled = false
                break
            } else
                v.isEnabled = true
        }
    }
}