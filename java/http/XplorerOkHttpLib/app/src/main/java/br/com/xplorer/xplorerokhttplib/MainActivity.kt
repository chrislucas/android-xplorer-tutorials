package br.com.xplorer.xplorerokhttplib

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {


    fun testSyncImageRequest(url: String) {
        val okHttpClient = OkHttpClient()
        val requestBuilder = Request.Builder().url(url).build()
        try {
            val response = okHttpClient.newCall(requestBuilder).execute()
            if (response.isSuccessful) {
                val responseBody = response.body()

                val bitmap = BitmapFactory.decodeStream(responseBody?.byteStream())

                Log.i("IMAGE", "${bitmap.width}, ${bitmap.height}")

                val strBody = responseBody?.toString()
                Log.i("RESPONSE_BODY_STR", strBody)
            }
        } catch (e: IOException) {
            Log.e("IOEX", e.message)
        }
    }

    fun testAsyncImageRequest(url: String) {
        val okHttpClient = OkHttpClient()
        // GET
        val request = Request.Builder().url(url).build()

        try {
            val callback = object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                   Log.e("EXCP", e.message)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        val bitmap = BitmapFactory.decodeStream(responseBody?.byteStream())

                        Log.i("IMAGE", "${bitmap.width}, ${bitmap.height}")

                        findViewById<ImageView>(R.id.image_view_test).setImageBitmap(bitmap)

                        response.challenges().forEach {
                            ac ->
                            val infoChallenges = String.format("Scheme %s\nRealm: %s"
                                , ac.scheme()
                                , ac.realm()
                            )
                            Log.i("CHALLENGER_RS", infoChallenges)
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            response.headers().toMultimap().forEach {
                                k, v ->
                                Log.i("RESPONSE_HEADERS", "$k, $v")
                            }
                            call.request().headers().toMultimap().forEach {
                                k, v ->
                                Log.i("REQUEST_HEADERS", "$k, $v")
                            }
                        }

                        Log.i("CONTENT_TYPE", responseBody?.contentType()?.toString())
                        val strBody = responseBody?.toString()
                        Log.i("RESPONSE_BODY_STR", strBody)
                    }
                    else {
                        val code = response.code()
                        Log.e("ERROR_CODE", code.toString())
                    }
                }
            }
            okHttpClient.newCall(request).enqueue(callback)
        } catch (e: IOException) {
            Log.e("IOException", e.message)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testAsyncImageRequest("https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png")
    }
}
