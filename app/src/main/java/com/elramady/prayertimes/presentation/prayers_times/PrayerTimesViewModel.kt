package com.elramady.prayertimes.presentation.prayers_times

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elramady.prayertimes.common.DateUtils.formatTimeLeft
import com.elramady.prayertimes.common.DateUtils.getCurrentDate
import com.elramady.prayertimes.common.DateUtils.getCurrentMonthNumber
import com.elramady.prayertimes.common.DateUtils.getDayFromDate
import com.elramady.prayertimes.common.DateUtils.getFormattedDate
import com.elramady.prayertimes.common.DateUtils.getMonthFromDate
import com.elramady.prayertimes.common.DateUtils.getNextPrayer
import com.elramady.prayertimes.common.DateUtils.getYearFromDate
import com.elramady.prayertimes.common.Resource
import com.elramady.prayertimes.domain.models.PrayerTimesDataRequest
import com.elramady.prayertimes.domain.models.location.CurrentLocation
import com.elramady.prayertimes.domain.use_case.current_location.GetAddressLocationUseCase
import com.elramady.prayertimes.domain.use_case.current_location.GetCurrentLocationUseCase
import com.elramady.prayertimes.domain.use_case.prayer_times.GetPrayerTimesFromRemoteUseCase
import com.elramady.prayertimes.domain.use_case.qibla_direction.GetQiblaDirectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PrayerTimesViewModel @Inject constructor(
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val getAddressLocationUseCase: GetAddressLocationUseCase,
    private val getPrayerTimesFromRemoteUseCase: GetPrayerTimesFromRemoteUseCase,
    private val getQiblaDirectionUseCase: GetQiblaDirectionUseCase,
):ViewModel() {

    var prayerTimesScreenState = mutableStateOf(PrayerTimesScreenState())
        private set


    private fun getPrayerTimes() {
        viewModelScope.launch {
            setRequestDataAndGetCurrentDate()

            getPrayerTimesFromRemoteUseCase(
                year = prayerTimesScreenState.value.year,
                month = prayerTimesScreenState.value.month,
                prayerTimesDataRequest =prayerTimesScreenState.value.prayerTimeRequest?: PrayerTimesDataRequest()

            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.e("prayerTimesResponse", "success")
                        Log.e("prayerTimesResponse", result.data.toString())

                        val prayerTimes = result.data
                        if (prayerTimes != null) {
                            prayerTimesScreenState.value = prayerTimesScreenState.value.copy(
                                error = "",
                                isLoading = false,
                               prayerTimesList = prayerTimes
                            )
                            calculateNextPrayer()
                        } else {
                            prayerTimesScreenState.value = prayerTimesScreenState.value.copy(
                                error = "An unexpected error occurred",
                                isLoading = false,
                            )
                        }
                    }

                    is Resource.Error -> {
                        prayerTimesScreenState.value = prayerTimesScreenState.value.copy(
                            isLoading = false,
                            error = result.message ?: "An unexpected error occurred"
                        )
                        Log.e(
                            "prayerTimesResponse",
                            result.data.toString()
                        )
                    }

                    is Resource.Loading -> {
                        Log.e("prayerTimesResponse", "loading")

                        prayerTimesScreenState.value = prayerTimesScreenState.value.copy(
                            isLoading = true,
                            error = ""
                        )

                    }

                    else -> {}
                }
            }


        }
    }

    private fun setRequestDataAndGetCurrentDate() {
        val currentDate = Date()
        val locationData=prayerTimesScreenState.value.userLocation
        prayerTimesScreenState.value = prayerTimesScreenState.value.copy(
            currentDate = getFormattedDate(currentDate),
            prayerTimeRequest = PrayerTimesDataRequest(
                longitude = locationData?.longitude,
                latitude = locationData?.latitude,
                method = 2
            ),
            year = getYearFromDate(currentDate),
            month = getCurrentMonthNumber().toString(),
            day = getDayFromDate(currentDate)
        )

    }

    private fun calculateNextPrayer(){
        val prayerTimes = prayerTimesScreenState.value.prayerTimesList?.dataPrayerTimes
        prayerTimes?.let {
            val item = it.find { it?.day == prayerTimesScreenState.value.day }
            val timings= mapOf(
                "Fajr" to item?.timings?.fajr,
                "Dhuhr" to item?.timings?.duhr,
                "Asr" to item?.timings?.asr,
                "Maghrib" to item?.timings?.maghrib,
                "Isha" to item?.timings?.isha
            )
            val nextPrayer = getNextPrayer(timings)
            nextPrayer.let { (prayerName, timeLeft) ->
                prayerTimesScreenState.value = prayerTimesScreenState.value.copy(
                    nextPrayerName = prayerName,
                    nextPrayerTimeLeft = formatTimeLeft(timeLeft).toString()
                )

            }
        }
    }

    fun getCurrentLocation() {
        Log.e("getCurrentLocation", "getCurrentLocation")
        viewModelScope.launch {
            Log.e("getCurrentLocation", "getCurrentLocationInViewModelScope")

            getCurrentLocationUseCase().collect { result ->
                Log.e("getCurrentLocation", "getCurrentLocationInViewModel")

                when (result) {
                    is Resource.Success -> {
                        val userLocation = result.data
                        Log.e("getCurrentLocation", userLocation.toString())
                        if (userLocation != null) {
                            prayerTimesScreenState.value = prayerTimesScreenState.value.copy(
                                error = "",
                                isLoading = false,
                                userLocation = userLocation
                            )
                            Log.e("locationLatitudeViewModel",userLocation.latitude.toString())
                            Log.e("locationLongitudeViewModel",userLocation.longitude.toString())
                            getAddressFromLocation(userLocation)
                            getPrayerTimes()
                        } else {
                            prayerTimesScreenState.value = prayerTimesScreenState.value.copy(
                                error = "An unexpected error occurred",
                                isLoading = false,
                                address = null
                            )
                        }

                    }

                    is Resource.Error -> {
                        Log.e("getCurrentLocation", "getCurrentLocationInViewModelError")

                        prayerTimesScreenState.value = prayerTimesScreenState.value.copy(
                            isLoading = false,
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }

                    is Resource.Loading -> {
                        Log.e("getCurrentLocation", "getCurrentLocationInViewModelLoadind")

                        prayerTimesScreenState.value = prayerTimesScreenState.value.copy(
                            isLoading = true,
                            error = ""
                        )

                    }
                }
            }
        }


    }

    private fun getAddressFromLocation(userLocation: CurrentLocation) {
        viewModelScope.launch {
            getAddressLocationUseCase(userLocation).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val address = result.data
                        if (address != null) {
                            prayerTimesScreenState.value = prayerTimesScreenState.value.copy(
                                error = "",
                                isLoading = false,
                                address = result.data
                            )
                        } else {
                            prayerTimesScreenState.value = prayerTimesScreenState.value.copy(
                                error = "An unexpected error occurred",
                                isLoading = false,
                                address = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        prayerTimesScreenState.value = prayerTimesScreenState.value.copy(
                            isLoading = false,
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }

                    is Resource.Loading -> {
                        prayerTimesScreenState.value = prayerTimesScreenState.value.copy(
                            isLoading = true,
                            error = ""
                        )

                    }
                }
            }
        }

    }

     fun getDirectionQibla(){
         viewModelScope.launch {
             getQiblaDirectionUseCase(
                 lat = prayerTimesScreenState.value.userLocation?.latitude?:0.0,
                 lng = prayerTimesScreenState.value.userLocation?.longitude?:0.0
             ).collect { result ->
                 when (result) {
                     is Resource.Success -> {
                         Log.e("qiblaDirectionResponse", "success")
                         Log.e("qiblaDirectionResponse", result.data.toString())

                         val qiblaDirectionResponse = result.data
                         if (qiblaDirectionResponse != null) {
                             prayerTimesScreenState.value = prayerTimesScreenState.value.copy(
                                 error = "",
                                 isLoading = false,
                                 qiblaDirection = qiblaDirectionResponse
                             )
                         } else {
                             prayerTimesScreenState.value = prayerTimesScreenState.value.copy(
                                 error = "An unexpected error occurred",
                                 isLoading = false,
                             )
                         }
                     }

                     is Resource.Error -> {
                         prayerTimesScreenState.value = prayerTimesScreenState.value.copy(
                             isLoading = false,
                             error = result.message ?: "An unexpected error occurred"
                         )
                         Log.e(
                             "qiblaDirectionResponse",
                             result.data.toString()
                         )
                     }

                     is Resource.Loading -> {
                         Log.e("prayerTimesResponse", "loading")

                         prayerTimesScreenState.value = prayerTimesScreenState.value.copy(
                             isLoading = true,
                             error = ""
                         )

                     }

                     else -> {}
                 }
             }


         }

     }

}