package com.yasaremre.gitusersearch.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.yasaremre.gitusersearch.R
import com.yasaremre.gitusersearch.databinding.ViewLabelValueBinding
import com.yasaremre.gitusersearch.extension.layoutInflater

class LabelValueView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewLabelValueBinding.inflate(context.layoutInflater, this, true)

    var label: String?
        get() = binding.viewLabelText.text.toString()
        set(value) {
            binding.viewLabelText.text = value
            requestLayout()
        }
    var value: String?
        get() = binding.viewValueText.text.toString()
        set(value) {
            binding.viewValueText.text = value
            visibility = if (value.isNullOrEmpty()) View.GONE else View.VISIBLE
            requestLayout()
        }

    init {
        obtainParameters(attrs)
    }

    private fun obtainParameters(attrs: AttributeSet?){
        if (attrs != null) {
            val a = context!!.obtainStyledAttributes(attrs, R.styleable.LabelValueView)
            val label = a.getString(R.styleable.LabelValueView_label)
            val value = a.getString(R.styleable.LabelValueView_value)
            this.label = label
            this.value = value
            a.recycle()
        }
    }

}