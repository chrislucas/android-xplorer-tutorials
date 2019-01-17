package br.com.xplorer.xplorerokhttplib.retrofit.service

import br.com.xplorer.xplorerokhttplib.retrofit.converter.ConverterRepoUserGithub
import br.com.xplorer.xplorerokhttplib.retrofit.entities.GithubRepo
import okhttp3.ResponseBody
import retrofit2.Converter

class RqServiceConverterListRepoUserGithub : Converter<ResponseBody, List<GithubRepo>> {
    override fun convert(value: ResponseBody) = ConverterRepoUserGithub.fromJsonToObject(value.string())
}