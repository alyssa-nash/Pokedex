package com.syriusdevelopment.pokedex.data

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.*
import retrofit2.Response


/**
 * A repository which provides resource from local database as well as remote end point.
 *
 * [ResultType] represents the type for database.
 * [RequestType] represents the type for network.
 */
abstract class NetworkBoundResource<ResultType, RequestType> {

    // Called to save the result of the API response into the database
    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: RequestType)

    // Called to get the cached data from the database
    @MainThread
    protected abstract fun getFromLocal(): Flow<ResultType>

    // Called to create the API call
    @MainThread
    protected abstract suspend fun getFromRemote(): Response<RequestType>

    // Called when the fetch fails. The child class may want to reset components like rate limiter
    protected open fun onFetchFailed() {}

    // Returns a Flow object that represents the resource that's implemented in the base class
    fun asFlow(): Flow<Resource<ResultType>> = flow {
        emit(Resource.loading())
        emit(Resource.success(getFromLocal().first()))
        val apiResponse = getFromRemote()
        val bodyResponse = apiResponse.body()
        if (apiResponse.isSuccessful && bodyResponse != null) {
            saveRemoteData(bodyResponse)
        } else emit(Resource.error(apiResponse.message()))

        emitAll(getFromLocal().map {
            Resource.success(it)
        })
    }
}