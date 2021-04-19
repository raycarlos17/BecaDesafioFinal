package com.app.bitcoinapp

import com.app.bitcoinapp.model.api.CoinRestApiTask_old
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ApiRequestUnityTest {
    @Test
    fun testSuccessfullCoinApiRequest() {
        val request = mockApiTrendingRequest()
        val successfullRequest = request.isSuccessful

        println(request.code())

        assertEquals(200, request.code())
        assertEquals(true, successfullRequest)
    }

    private fun mockApiTrendingRequest(): Response<List<Coin>> {
        val coinRestApiTask = CoinRestApiTask_old()
        val request = coinRestApiTask.retrofitApi().getAllAssets().execute()
        return request
    }
}