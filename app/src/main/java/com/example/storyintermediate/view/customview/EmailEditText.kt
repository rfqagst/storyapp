package com.example.storyintermediate.view.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class EmailEditText : AppCompatEditText {
    private val validationDelay = 1000L
    private var validationJob: Job? = null
    private val emailPattern = Pattern.compile(
        "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    )

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validationJob?.cancel()
                validationJob = CoroutineScope(Dispatchers.Main).launch {
                    delay(validationDelay)
                    validateInput()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun validateInput() {
        val text = this.text.toString()
        if (!emailPattern.matcher(text).matches()) {
            setError("Format email tidak valid", null)
        } else {
            error = null
        }
    }
}