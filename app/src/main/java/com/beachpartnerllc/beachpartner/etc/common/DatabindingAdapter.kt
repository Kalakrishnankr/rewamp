package com.beachpartnerllc.beachpartner.etc.common

import android.databinding.BindingAdapter
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.wang.avi.AVLoadingIndicatorView

/**
 * @author Samuel Robert <samuel.robert@seqato.com>
 * @created on 04 Jun 2018 at 9:44 AM
 */
@BindingAdapter("error")
fun setError(view: TextInputLayout, error: String?) {
    view.error = error
}

@BindingAdapter("isLoading")
fun setStateLoading(view: AVLoadingIndicatorView, isLoading: Boolean) {
    if (isLoading) view.smoothToShow()
    else view.smoothToHide()
}

@BindingAdapter("url")
fun setUrl(imageView: ImageView, url: String?) {
    /*Picasso.with(imageView.context)
            .load(url)
            .fit()
            .centerCrop()
            .into(imageView)*/
}

@BindingAdapter("nestedScrollingEnabled")
fun setNestedScrollingEnabled(view: RecyclerView, nestedScrollingEnabled: Boolean) {
    view.isNestedScrollingEnabled = nestedScrollingEnabled
}

@BindingAdapter("foregroundColorSpan", "start", "end", requireAll = false)
fun setForegroundColorSpan(view: TextView, color: Int, startIndex: Int = 0, endIndex: Int = view.text.length - 1) {
    val spanBuilder = SpannableStringBuilder(view.text)
    spanBuilder.setSpan(ForegroundColorSpan(color), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    view.text = spanBuilder
}

@BindingAdapter("disableShift")
fun disableShiftMode(view: BottomNavigationView, disableShift: Boolean) {
    if (!disableShift) return
    val menuView = view.getChildAt(0) as BottomNavigationMenuView
    try {
        val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
        shiftingMode.isAccessible = true
        shiftingMode.setBoolean(menuView, false)
        shiftingMode.isAccessible = false
        for (i in 0 until menuView.childCount) {
            val item = menuView.getChildAt(i) as BottomNavigationItemView

            item.setShiftingMode(false)
            // set once again checked value, so view will be updated

            item.setChecked(item.itemData.isChecked)
        }
    } catch (e: NoSuchFieldException) {
        Log.e("BNVHelper", "Unable to get shift mode field", e)
    } catch (e: IllegalAccessException) {
        Log.e("BNVHelper", "Unable to change value of shift mode", e)
    }
}