package com.yunho.worktimemanagement.data.slack

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response

object SlackRemoteRepository {

    fun sendSlackMessage(entity: SendSlackEntity): Single<Response<ResponseBody>>? =
        SlackAPI.instance?.mAPI?.sendSlackMessage(entity)
}