package com.pavlov.lampatest.ui.main

import com.pavlov.lampatest.api.ApiService
import com.pavlov.lampatest.api.RetrofitClient
import com.pavlov.lampatest.base.BasePresenter
import com.pavlov.lampatest.base.BaseView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NewsPresenter(baseView: BaseView) : BasePresenter(baseView) {
    private lateinit var view: NewsView
    private var apiService: ApiService = RetrofitClient.ServiceBuilder.buildService()

    fun attachView(view: NewsView) {
        this.view = view
    }

    fun sendResponse(page: String) {
        super.startLoader()
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            apiService.getNews(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->
                    if (response != null)
                        view.onRequestComplete(response)
                    else
                        super.showError("Error connection")
                    super.stopLoader()
                },
                    { t ->
                        if (t is SocketTimeoutException || t is UnknownHostException)
                            super.showInternetError()
                        else {
                            super.showError("Error connection")
                        }
                        super.stopLoader()
                    })
        )
    }
}