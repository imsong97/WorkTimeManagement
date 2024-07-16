package com.yunho.data

import com.yunho.data.slack.SendSlackEntity
import com.yunho.data.slack.SlackRemoteRepository
import io.reactivex.Single

class SlackRepository {

    companion object {
        @Volatile
        private var mInstance: SlackRepository? = null
        val instance: SlackRepository?
            get() = synchronized(SlackRepository::class.java) {
                if (mInstance == null) {
                    mInstance = SlackRepository()
                }
                return mInstance
            }
    }

    fun sendSlackMessage(text: String): Single<Boolean>? =
        SlackRemoteRepository.sendSlackMessage(SendSlackEntity(text = text))
            ?.map {
                it.body()?.string()
            }
            ?.map {
                it == "ok"
            }
            ?.onErrorReturn {
                it.printStackTrace()
                false
            }
}