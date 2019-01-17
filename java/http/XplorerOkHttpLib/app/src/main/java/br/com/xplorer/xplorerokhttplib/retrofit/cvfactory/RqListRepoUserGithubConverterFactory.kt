package br.com.xplorer.xplorerokhttplib.retrofit.cvfactory

import br.com.xplorer.xplorerokhttplib.retrofit.service.RqServiceConverterListRepoUserGithub

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class RqListRepoUserGithubConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        return RqServiceConverterListRepoUserGithub()
    }
}