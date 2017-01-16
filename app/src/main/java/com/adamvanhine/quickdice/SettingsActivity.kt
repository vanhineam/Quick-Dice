package com.adamvanhine.quickdice

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.NumberPicker

class SettingsActivity : AppCompatActivity(), NumberPicker.OnValueChangeListener,
        View.OnClickListener {
    private var PREFS_KEY = "QUICK_DICE_PREFERENCES"
    private var SIDES_KEY = "DiceSides"

    var sharedPreferences: SharedPreferences? = null
    var confirmButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

        val currentValue = getCurrentValue()
        val numberPicker: NumberPicker = findViewById(R.id.sides_picker) as NumberPicker
        numberPicker.maxValue = 99
        numberPicker.value = currentValue

        numberPicker.setOnValueChangedListener(this)
        confirmButton = findViewById(R.id.confirm) as Button
        confirmButton?.setOnClickListener(this)
    }

    fun getCurrentValue(): Int {
        val result: Int = sharedPreferences?.getInt(SIDES_KEY, 6) ?: 6
        return result
    }

    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
        sharedPreferences?.edit()?.putInt(SIDES_KEY, newVal)?.apply()
    }

    override fun onClick(v: View?) {
        val vID = v?.id ?: -1
        if (vID == confirmButton?.id) {
            finish()
        }
    }

}
