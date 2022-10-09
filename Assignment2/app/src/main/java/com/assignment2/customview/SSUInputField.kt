package com.assignment2.customview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import androidx.core.view.isVisible
import com.assignment2.R
import com.assignment2.databinding.SsuInputFieldBinding
import com.assignment2.util.toDp


class SSUInputField @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : LinearLayout(context, attributeSet,defStyleAttr, defStyleRes) {
    private val binding: SsuInputFieldBinding
    var getEditText : EditText? = null

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = SsuInputFieldBinding.inflate(layoutInflater,this,true)
        getEditText = binding.ssuEt

        context.withStyledAttributes(
            attributeSet,
            R.styleable.SSUComponentTextField,defStyleAttr, defStyleRes){

            val state = getString(R.styleable.SSUComponentTextField_ssu_state)
            val colorId = when(state){
                "error" -> R.color.text_warned
                "success"-> R.color.text_pointed
                else -> R.color.text_default
            }
            val tint =ContextCompat.getColor(context,colorId)

            /** 도움말 **/
            val helper = getString(R.styleable.SSUComponentTextField_ssu_helper_message)

            if(!helper.isNullOrBlank()){
                binding.txtHelper.isVisible = true
                binding.txtHelper.text = helper
            }

            /** input 옵션 **/
            val option = getString(R.styleable.SSUComponentTextField_ssu_option)
            when(option){
                "email" -> {
                    binding.ssuEtEmailTv.isVisible = true
                    binding.ssuEtDeleteIv.visibility = View.GONE
                }
                "password"->{
                    binding.ssuEtEmailTv.visibility = View.GONE
                    binding.ssuEtDeleteIv.apply {
                        isVisible = true
                        setOnClickListener {
                            Log.e("option","click!")
                            binding.ssuEt.text?.clear()
                        }
                    }
                }
                else ->{
                    binding.ssuEtDeleteIv.visibility = View.GONE
                    binding.ssuEtEmailTv.visibility = View.GONE
                }
            }
            /** 상태 ui **/
            state?.let{ setState(it) }
        }
    }

    fun setState(state : String){
        with(binding){
            val stateColor = when(state){
                "error" -> R.color.text_warned
                "success"-> R.color.text_pointed
                else -> R.color.transparent
            }
            val tint =ContextCompat.getColor(context,stateColor)
            txtHelper.setTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(context,
                        when(state){
                            "error" -> R.color.text_warned
                            else -> R.color.text_tertiary
                        }
                    )
                ))
            val borderColor = ColorStateList.valueOf(tint)
            val backgroundColor = ColorStateList.valueOf(ContextCompat.getColor(context,R.color.bg_input))
            val gradientDrawable = GradientDrawable().apply {
                setStroke(1.toDp(), borderColor)
                color = backgroundColor
                cornerRadius = 4.toDp().toFloat()
            }
            ssuEtLayout.background = gradientDrawable
        }
    }
}