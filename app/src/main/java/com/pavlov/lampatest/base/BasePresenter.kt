package com.pavlov.lampatest.base

open class BasePresenter(private var baseView: BaseView) : BaseView {
    override fun showError(message: String) {
        baseView.showError(message)
    }

    override fun showInternetError() {
        baseView.showInternetError()
    }

    override fun startLoader() {
        baseView.startLoader()
    }

    override fun stopLoader() {
        baseView.stopLoader()
    }
}