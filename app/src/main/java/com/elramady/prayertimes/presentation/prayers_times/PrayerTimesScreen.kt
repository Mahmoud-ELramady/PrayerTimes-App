package com.elramady.prayertimes.presentation.prayers_times

import android.Manifest
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.elramady.permissionscheck.presentation.permissions.PermissionsCheckScreen
import com.elramady.prayertimes.MainActivity
import com.elramady.prayertimes.presentation.component.AppBarPrayerTimes
import com.elramady.prayertimes.presentation.component.HorizontalCardSlider
import com.elramady.prayertimes.presentation.component.QabilahArchComposable
import com.elramady.prayertimes.presentation.component.SpacerHeight
import com.elramady.prayertimes.presentation.component.TextWithButton
import com.elramady.prayertimes.presentation.graphs.GraphScreen
import com.elramady.prayertimes.presentation.prayers_times.component.NextPrayerItem
import com.elramady.prayertimes.presentation.ui.theme.PrimaryColor

@Composable
fun PrayerTimesScreen(modifier: Modifier = Modifier, navController: NavController) {
    var showPermissionScreen by rememberSaveable { mutableStateOf(true) }
    var isGrantedResult by rememberSaveable { mutableStateOf(false) }
    var currentDay by rememberSaveable { mutableStateOf("") }
    var currentIndex by rememberSaveable { mutableIntStateOf(0)  }
    val viewModel: PrayerTimesViewModel = viewModel(LocalContext.current as MainActivity)
    val prayerScreenState = viewModel.prayerTimesScreenState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(Color.White)

        ,
        verticalArrangement = Arrangement.SpaceBetween

    ) {

        // if isGrantedResult is true -> get Location
        LaunchedEffect(isGrantedResult) {
            if (isGrantedResult){
                viewModel.getCurrentLocation()
            }

        }

        //if isGrantedResult is false -> force user to take permission
        if (!isGrantedResult&&!showPermissionScreen){
            TextWithButton(title = "You should give permission location", buttonText = "Give Permission") {
                showPermissionScreen=true
            }
        }


        if (isGrantedResult){

            if (prayerScreenState.isLoading) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()

                }
            }else if (prayerScreenState.error!=""){
                TextWithButton(title = "check your internet connection\n or turn on location", buttonText = "try again") {
                    viewModel.getCurrentLocation()
                }
            }

//           if (prayerScreenState.prayerTimesList?.dataPrayerTimes!=null) {}

               Log.e("dayHome", currentDay.toString())
               AppBarPrayerTimes(
                   day = if (currentDay == "") prayerScreenState.currentDate ?: "" else currentDay,
                   location = "${prayerScreenState.address?.city}, ${prayerScreenState.address?.governorate}",
                   onClickNextPrayer = {
                       if ((prayerScreenState.prayerTimesList?.dataPrayerTimes?.size
                               ?: 0) != currentIndex + 1
                       ) {
                           currentIndex++

                       }

                   },
                   onClickPreviousPrayer = {
                       if (currentIndex != 0) {
                           currentIndex--
                       }
                   }
               )

               NextPrayerItem(
                   nextPrayer = prayerScreenState.nextPrayerName,
                   timeLeft = prayerScreenState.nextPrayerTimeLeft
               )

               SpacerHeight(height = 10)

               Log.e("prayerTimeDay", prayerScreenState.day.toString())
               HorizontalCardSlider(
                   modifier = Modifier,
                   prayersTimeList = prayerScreenState.prayerTimesList?.dataPrayerTimes,
                   day = if (currentIndex == 0 && currentDay == "") prayerScreenState.day else prayerScreenState.prayerTimesList?.dataPrayerTimes?.get(
                       currentIndex
                   )?.day ?: ""
               ) { day, index ->
                   currentDay = day
                   currentIndex = index
               }
               SpacerHeight(height = 10)
               QabilahArchComposable(
                   modifier = Modifier,
                   text = "Qabilah Direction",
                   backgroundColor = PrimaryColor
               ) {
                   navController.navigate(GraphScreen.QABILAH)
               }




        }


        if (showPermissionScreen) {
            PermissionsCheckScreen(
                isRationalUiFirst = false,
                onDismiss = {
                    showPermissionScreen = false // Hide the permission screen on dismiss
                },
                titleRationalUi = "Permission Needed",
                descriptionRationalUi = "Permission is needed for location services",
                permissions = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
            ) { isGranted ->
                if (isGranted) {
                    Log.e("locationPermissionCheck", "true")
                    isGrantedResult = true
                } else {
                    Log.e("locationPermissionCheck", "false")
                    isGrantedResult = false

                }

                showPermissionScreen = false

            }
        }




    }
}