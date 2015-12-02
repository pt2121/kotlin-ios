package com.prat.restclient

import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import rx.Observable

/**
 * Created by pt2121 on 12/1/15.
 */
class WebService {

  val API_URL: String = "https://api.github.com"

  public fun repo(): Observable<Repo> {
    val retrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build()
    val api = retrofit.create(GitHubService::class.java)
    return api.repo("prt2121", "kotlin-ios")
  }

  companion object {
    fun newInstance(): WebService = WebService()
  }
}