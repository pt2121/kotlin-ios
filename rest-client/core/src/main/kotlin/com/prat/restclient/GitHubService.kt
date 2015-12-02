package com.prat.restclient

import retrofit.http.GET
import retrofit.http.Path
import rx.Observable

/**
 * Created by pt2121 on 12/1/15.
 */
public interface GitHubService {

  @GET("/repos/{owner}/{repo}")
  fun repo(@Path("owner") owner: String, @Path("repo") repo: String): Observable<Repo>

}