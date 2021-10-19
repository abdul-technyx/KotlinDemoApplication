package com.example.kotlindemoapplication.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    //end for getting book list on searching
    @GET("volumes")
    fun getBookListFromAPI(@Query("q") query: String): Observable<BookListModel>

}