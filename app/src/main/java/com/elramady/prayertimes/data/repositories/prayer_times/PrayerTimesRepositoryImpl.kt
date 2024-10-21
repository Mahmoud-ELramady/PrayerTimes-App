package com.elramady.prayertimes.data.repositories.prayer_times

import android.util.Log
import com.elramady.prayertimes.common.NetworkUtils.performOperationWithoutMapper
import com.elramady.prayertimes.common.Resource
import com.elramady.prayertimes.data.local.room.DataEntity
import com.elramady.prayertimes.data.local.room.PrayerTimesDatabase
import com.elramady.prayertimes.data.local.room.PrayerTimesEntity
import com.elramady.prayertimes.data.mappers.prayer_times.toPrayerTimes
import com.elramady.prayertimes.data.mappers.prayer_times.toPrayerTimesData
import com.elramady.prayertimes.data.mappers.prayer_times.toPrayerTimesEntity
import com.elramady.prayertimes.data.mappers.prayer_times.toQueryMap
import com.elramady.prayertimes.data.remote.PrayerTimesApi
import com.elramady.prayertimes.domain.models.PrayerTimes
import com.elramady.prayertimes.domain.models.PrayerTimesData
import com.elramady.prayertimes.domain.models.PrayerTimesDataRequest
import com.elramady.prayertimes.domain.models.QiblaDirectionResponse
import com.elramady.prayertimes.domain.repository.PrayerTimesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.ln

class PrayerTimesRepositoryImpl @Inject constructor(
    private val prayerTimesApi: PrayerTimesApi,
    private val prayerTimesDatabase: PrayerTimesDatabase,
):PrayerTimesRepository {

    val database=prayerTimesDatabase.prayerTimesDao
    private var prayerTimesList:List<PrayerTimesData?>? = emptyList()

    override suspend fun getPrayerTimesFromRemote(
        year: String,
        month: String,
        prayerTimesDataRequest: PrayerTimesDataRequest
    ): Flow<Resource<PrayerTimes>> {
        return flow {
            Log.e("responsePrayerTimesImpl1",prayerTimesList.toString())

//            emit(Resource.Loading())
//
//            countries = database.getAllCountries().map { it.toCountry() }
//            if (countries.isNotEmpty()){
//                Log.e("responsePrayerTimesDefault2",countries.toString())
//                emit(Resource.Success(countries))
//                return@flow
//            }


            val resultNetwork=  performOperationWithoutMapper(
                networkCall = {

                    prayerTimesApi.getPrayerTimes(
                        prayerTimesDataQueryMap = prayerTimesDataRequest.toQueryMap(),
                        year = year,
                        month = month
                    )

                },

            )


            resultNetwork.collect{ result ->
                when(result){
                    is Resource.Success -> {
                        Log.e("networkStateInImpl","success")
                        database.clearPrayerTimes()
                        prayerTimesList=result.data?.data?.map { it.toPrayerTimesData() }
                        val prayerTimesEntity= result.data?.toPrayerTimesEntity()

                        database.insertPrayerTimes(
                            prayerTimesEntity
                                ?: (PrayerTimesEntity(dataEntity =  emptyList < DataEntity >())
                        )
                        )

                        Log.e("responsePrayerTimesRoom",prayerTimesList.toString())
                        emit(Resource.Success(database.getPrayerTimes()?.toPrayerTimes()))
                    }

                    is Resource.Error->{
                        Log.e("networkStateInImpl","error")

                        if (database.getPrayerTimes()?.dataEntity.isNullOrEmpty()){
                            emit(Resource.Error(result.message?:"an excepted error occurred"))
                        }else{
                            emit(Resource.Success(database.getPrayerTimes()?.toPrayerTimes()))
                        }
                    }

                    is Resource.Loading->{
                        Log.e("networkStateInImpl","loading")

                        emit(Resource.Loading())
                    }

                }
            }
        }
    }

    override suspend fun getPrayerTimesFromLocal(): PrayerTimes?=database.getPrayerTimes()?.toPrayerTimes()
    override suspend fun getQiblaDirection(
        lat: Double,
        lng: Double
    ): Flow<Resource<QiblaDirectionResponse>>
    = performOperationWithoutMapper(
        networkCall = { prayerTimesApi
            .getQiblaDirection(
                lat=lat,
                lng= lng,
            ) },
        )


}