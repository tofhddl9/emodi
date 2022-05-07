package com.lgtm.emodi.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar

/**
 * 출처 : https://pluu.github.io/blog/android/2020/05/04/fragment-result/
 * */

// Fragment 에서 childFragmentManager 를 사용한 FragmentResultListener Extension
fun Fragment.setChildFragmentResultListener(
    requestKey: String,
    listener: ((resultKey: String, bundle: Bundle) -> Unit)
) {
    childFragmentManager.setFragmentResultListener(requestKey, this, listener)
}

// FragmentActivity 에서 FragmentResultListener Extension
fun FragmentActivity.setFragmentResultListener(
    requestKey: String,
    listener: ((resultKey: String, bundle: Bundle) -> Unit)
) {
    supportFragmentManager.setFragmentResultListener(requestKey, this, listener)
}

fun Fragment.showSnackBar(msg:String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(requireActivity().findViewById(android.R.id.content), msg, duration).show()
}