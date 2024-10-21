package com.elramady.prayertimes.presentation.graphs


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elramady.prayertimes.presentation.prayers_times.PrayerTimesScreen
import com.elramady.prayertimes.presentation.qabilah.QabilahScreen


@Composable
fun RootNavigationGraph(
    navController: NavHostController,

) {
        NavHost(
            navController = navController,
            startDestination = GraphScreen.PRAYER_TIMES,
        ) {

            composable<GraphScreen.PRAYER_TIMES> {
                PrayerTimesScreen(navController=navController)
            }
            composable<GraphScreen.QABILAH> {
                QabilahScreen()
            }

        }


}
