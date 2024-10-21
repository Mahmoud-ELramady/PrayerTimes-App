package com.elramady.prayertimes.common

import android.util.Log

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

object NetworkUtils {

    fun <T,U> performNetworkOperation(
        networkCall: suspend () -> U,
        mapResponse: (U) -> T
    ): Flow<Resource<T>> {
        return flow {
            try {
                emit(Resource.Loading<T>())
                val response =  networkCall()
                emit(Resource.Success(mapResponse(response)))
                Log.e("successResponse", "successResponse")

            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> {
                        Log.e("HttpException", "Bad Request: ${e.message()}")
                    }
                    401 -> {
                        Log.e("HttpException", "Unauthorized: ${e.message()}")
                    }
                    404 -> {
                        Log.e("HttpException", "Not Found: ${e.message()}")
                    }
                    // Handle other error codes if needed
                    else -> {
                        Log.e("HttpException", "Unexpected error: ${e.message()}")
                    }
                }
                Log.e("errorApiExceptionHttpException",e.message.toString())
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("Can't reach server, check your internet connection"))
                Log.e("errorApiExceptionIOException\n",e.message.toString())

            } catch (e: Exception) {
                Log.e("errorApiException\n",e.message.toString())
                emit(Resource.Error(e.message ?: "An unknown error occurred"))
            }





        }

    }


    fun <T> performOperationWithoutMapper(
        networkCall: suspend () -> T,
    ): Flow<Resource<T>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response =  networkCall()
                emit(Resource.Success(response))
                Log.e("successResponse", "successResponse")

            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> {
                        Log.e("HttpException", "Bad Request: ${e.message()}")
                    }
                    401 -> {
                        Log.e("HttpException", "Unauthorized: ${e.message()}")
                    }
                    404 -> {
                        Log.e("HttpException", "Not Found: ${e.message()}")
                    }
                    // Handle other error codes if needed
                    else -> {
                        Log.e("HttpException", "Unexpected error: ${e.message()}")
                    }
                }
                Log.e("errorApiHttpException",e.message.toString())
                emit(Resource.Error<T>(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error<T>("Can't reach server, check your internet connection"))
                Log.e("errorApiIOException",e.message.toString())

            } catch (e: Exception) {
                Log.e("errorApiException",e.message.toString())
                emit(Resource.Error<T>(e.message ?: "An unknown error occurred"))
            }





        }



    }


}