package br.com.xplorer.xplorerokhttplib

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.xplorer.xplorerokhttplib.retrofit.cvfactory.RqListRepoUserGithubConverterFactory
import br.com.xplorer.xplorerokhttplib.retrofit.endpoint.EndpointGithub
import br.com.xplorer.xplorerokhttplib.retrofit.entities.GithubRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class XplorerGithubAPI : AppCompatActivity() {


    fun testRequestReposGithub(endpoint: EndpointGithub, user: String) {

        endpoint.requestUserRepositories(user).enqueue(object : Callback<List<GithubRepo>>{
            override fun onFailure(call: Call<List<GithubRepo>>, t: Throwable) {
                Log.e("RS_FAILURE", t.message)
            }

            override fun onResponse(call: Call<List<GithubRepo>>, response: Response<List<GithubRepo>>) {
                if (response.isSuccessful) {
                    response.body()?.forEach {
                        println(it)
                    }
                }
                else {
                    val errorBody = response.errorBody()
                    Log.e("RS_FAILURE", "${errorBody?.string()}, ${response.code()}")
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xplorer_github_api)

        val endpoint = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(RqListRepoUserGithubConverterFactory())
            .build()
            .create(EndpointGithub::class.java)

        // testRequestReposGithub(endpoint, "chrislucas")

    }
}
