package br.com.xplorer.xplorerokhttplib.retrofit.converter

import android.util.Log
import br.com.xplorer.xplorerokhttplib.retrofit.entities.GithubRepo
import org.json.JSONArray
import org.json.JSONException


class ConverterRepoUserGithub {
    companion object {
        fun fromJsonToObject(str: String) : List<GithubRepo> {
            val list = arrayListOf<GithubRepo>()
            val jsonArray = JSONArray(str)
            try {
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    list.add(GithubRepo(jsonObject.getLong("id")
                        ,  jsonObject.getString("name"), jsonObject.getString("url")))
                }
            } catch (e: JSONException) {
                Log.e("EXCEPTION_REPO_PARSE", e.message)
            }
            return list.toList()
        }
    }
}



