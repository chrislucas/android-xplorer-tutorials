package br.com.xplorer.xplorerokhttplib.retrofit.endpoint

import br.com.xplorer.xplorerokhttplib.retrofit.entities.GithubRepo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EndpointGithub {

    @GET("/users/{user}/repos")
    fun requestUserRepositories(@Path("user") user: String) : Call<List<GithubRepo>>
}