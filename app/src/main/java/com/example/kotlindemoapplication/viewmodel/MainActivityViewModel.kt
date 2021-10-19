package com.example.kotlindemoapplication.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlindemoapplication.network.BookListModel
import com.example.kotlindemoapplication.network.RetrofitInstance
import com.example.kotlindemoapplication.network.RetrofitService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel: ViewModel(), Observable {

    //declaration
    var bookList: MutableLiveData<BookListModel> = MutableLiveData()

    @Bindable
    val queryString = MutableLiveData<String>()

    fun getBookListObserver(): MutableLiveData<BookListModel> {
        return bookList
    }

    fun makeApiCall(query: String) {
        val retroInstance  = RetrofitInstance.getRetroInstance().create(RetrofitService::class.java)
        retroInstance.getBookListFromAPI(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getBookListObserverRx())
    }

    private fun getBookListObserverRx():Observer<BookListModel> {
        return object : Observer<BookListModel> {
            override fun onComplete() {
                //hide progress indicator .
            }

            override fun onError(e: Throwable) {
                bookList.postValue(null)
            }

            override fun onNext(t: BookListModel) {
                bookList.postValue(t)
            }

            override fun onSubscribe(d: Disposable) {
                //start showing progress indicator.
            }
        }
    }

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }
}