package edu.skillbox.layoutapp

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import edu.skillbox.layoutapp.databinding.MyCustomButtonBinding

class CustomButton
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    val bining = MyCustomButtonBinding.inflate(LayoutInflater.from(context))

    init {
        addView(bining.root)
    }

    fun setTextOne(text: String) {
        bining.textOne.text = text
    }
    fun setTextTwo(text: String){
        bining.textTwo.text = text
    }
}