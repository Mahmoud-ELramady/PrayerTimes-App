package com.elramady.prayertimes.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elramady.prayertimes.common.CommonInteractionSource
import com.elramady.prayertimes.presentation.ui.theme.PrimaryColor


val HalfCircleShape: Shape = GenericShape { size, _ ->
    moveTo(size.width / 2, 0f) // Move to the top center of the circle
    arcTo(
        rect = Rect(0f, 0f, size.width, size.height),
        startAngleDegrees = 180f,
        sweepAngleDegrees = 180f,
        forceMoveTo = false
    )
    lineTo(size.width, size.height) // Bottom right corner
    lineTo(0f, size.height) // Bottom left corner
    close() // Close the shape
}

@Composable
fun QabilahArchComposable(modifier: Modifier,
                  backgroundColor: Color,
                  text:String,
                  onClick:()->Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clipToBounds()
        ,
        contentColor = Color.White
      //  onClick = {  }
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth(),
            //.height(150.dp)  // Adjust height for half-circle
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .clickable(
                        interactionSource = CommonInteractionSource.interactionSource,
                        indication = null
                    ) {
                        onClick.invoke()
                    }
                    .height(120.dp)  // Height for the arc (half-circle)
            ) {
                drawArc(
                    color = PrimaryColor,  // Set background color
                    startAngle = -180f,  // Start drawing from the left (-180 degrees)
                    sweepAngle = 180f,  // Sweep to the right (cover 180 degrees for a half-circle)
                    useCenter = true,  // This fills the arc to the center
                    size = size.copy(height = size.height * 2),  // Double height for a perfect half-circle
                    style = Fill  // Fill the arc instead of stroke
                )
            }

            // Text centered on the half-circle
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }

}