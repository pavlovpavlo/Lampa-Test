package com.pavlov.lampatest.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.pavlov.lampatest.R

open class BaseActivity(contentLayoutId: Int) : AppCompatActivity(contentLayoutId), BaseView {

    override fun showError(message: String) {

    }

    override fun showInternetError() {
        stopLoader()
    }

    override fun startLoader() {
        findViewById<View>(R.id.progress_main).visibility = View.VISIBLE
    }

    override fun stopLoader() {
        findViewById<View>(R.id.progress_main).visibility = View.GONE
    }

}