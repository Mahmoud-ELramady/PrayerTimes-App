package com.elramady.prayertimes.presentation.prayers_times.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.elramady.prayertimes.R
import com.elramady.prayertimes.presentation.component.IconWithBackground
import com.elramady.prayertimes.presentation.ui.theme.PrimaryColor

@Composable
fun NextPrayerItem(
    modifier: Modifier = Modifier,
    nextPrayer:String,
    timeLeft:String,
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, PrimaryColor),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 13.dp)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Next\nPrayer",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall,
            )

            Text(
                text = nextPrayer,
                textAlign = TextAlign.Center ,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )



            Column {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Time left",
                    textAlign = TextAlign.Center ,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),

                    text = timeLeft,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,

                    )

            }

        }







    }
}