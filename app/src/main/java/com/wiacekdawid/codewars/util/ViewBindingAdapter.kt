package com.wiacekdawid.codewars.util

import android.databinding.BindingAdapter
import android.view.View
import android.support.design.widget.BottomNavigationView



/**
 * Created by dawidwiacek on 04/05/2018.
 */


@BindingAdapter("onNavigationItemSelected")
fun setOnNavigationItemSelectedListener(
        view: BottomNavigationView, listener: BottomNavigationView.OnNavigationItemSelectedListener) {
    view.setOnNavigationItemSelectedListener(listener)
}

@BindingAdapter("android:visibility")
fun setViewVisibilityByBoolean(view: View,
                               value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}

@BindingAdapter("android:visibility")
fun setViewVisibilityByString(view: View,
                              text: String?) {
    view.visibility = if (text == null || text.isEmpty()) View.GONE else View.VISIBLE
}