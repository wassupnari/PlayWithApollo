package com.apollo.nari.playwithapollo.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.BaseObservable
import com.apollo.nari.playwithapollo.interfaces.BaseView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class ObservableViewModel : ViewModel(), BaseView {

    private val untilDestroySubscriptions = CompositeDisposable()
    var observableSpinnerVisibility = MutableLiveData<Boolean>()

    fun initialize() {}

    protected fun untilDestroy(disposable: Disposable) {
        untilDestroySubscriptions.add(disposable)
    }

    protected override fun onCleared() {
        super.onCleared()
        untilDestroySubscriptions.clear()
    }

    override fun showSpinner() {
        observableSpinnerVisibility.setValue(true)
    }

    override fun hideSpinner() {
        observableSpinnerVisibility.setValue(false)
    }

}