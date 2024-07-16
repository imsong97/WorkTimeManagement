package com.yunho.data.slack

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response

internal object SlackRemoteRepository {

    internal fun sendSlackMessage(entity: SendSlackEntity): Single<Response<ResponseBody>>? =
        SlackAPI.instance?.mAPI?.sendSlackMessage(entity)
}