package com.aptopayments.sdk.pci

import android.content.Context
import android.content.res.TypedArray
import android.util.TypedValue

class ColorHelper {
    fun getColorAccent(context: Context): Int {
        val typedValue = TypedValue()
        val a: TypedArray = context.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorAccent))
        val color = a.getColor(0, 0)
        a.recycle()
        return color
    }
}
