package com.yunho.worktimemanagement.data

import com.yunho.worktimemanagement.BuildConfig
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST

class SlackAPI {

    private val BASEURL: String = "https://hooks.slack.com/services/"
    val mAPI: API
    private val mRetrofit: Retrofit

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request().newBuilder()

                it.proceed(request.build())
            }
            .build()

        mRetrofit = Retrofit.Builder()
            .baseUrl(BASEURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mAPI = mRetrofit.create(API::class.java)
    }

    companion object {
        @Volatile
        private var mInstance: SlackAPI? = null

        val instance: SlackAPI?
            get() = synchronized(SlackAPI::class.java) {
                if (mInstance == null) {
                    mInstance = SlackAPI()
                }
                return mInstance
            }
    }

    interface API {
        @POST("T07C8GL2B9A/B07C62EPTUK/${BuildConfig.HOOKS_KEY}")
        fun sendSlackMessage(entity: SendSlackEntity): Single<Response<ResponseBody>>
    }
}