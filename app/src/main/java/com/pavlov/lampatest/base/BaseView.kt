package com.pavlov.lampatest.base

interface BaseView {
    fun showError(message: String)
    fun showInternetError()
    fun startLoader()
    fun stopLoader()
}