package com.elramady.prayertimes.presentation.qabilah

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.abs

class GyroscopeSensorManager(context: Context) : SensorEventListener {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val gyroscope: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    private val _rotation = MutableStateFlow(0f)
    val rotation: StateFlow<Float> = _rotation

    private var lastSentValue: Float = 0f
    private var stableValueCount = 0 // Count how many stable values have been received
    private val stabilityThreshold = 5 // Number of stable readings required
    private val deadZoneThreshold = 1.0f // Adjust this value as needed

    init {
        gyroscope?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        } ?: Log.e("GyroscopeSensorManager", "Gyroscope sensor not available!")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
                val rotationVector = it.values
                val rotationMatrix = FloatArray(9)
                val orientation = FloatArray(3)

                // Get the rotation matrix from the rotation vector
                SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationVector)

                // Get the orientation angles (azimuth, pitch, roll)
                SensorManager.getOrientation(rotationMatrix, orientation)

                // Convert azimuth to degrees
                val azimuthInDegrees = Math.toDegrees(orientation[0].toDouble()).toFloat()

                // Check if the change is significant
                if (abs(azimuthInDegrees - lastSentValue) > deadZoneThreshold) {
                    lastSentValue = azimuthInDegrees
                    stableValueCount = 0 // Reset the count since we've received a significant change
                    _rotation.value = azimuthInDegrees
                } else {
                    stableValueCount++
                    // If we have a number of stable readings, we can reset the lastSentValue
                    if (stableValueCount >= stabilityThreshold) {
                        lastSentValue = azimuthInDegrees // Allow slight changes within the stable range
                    }
                }

                // Log the azimuth value for debugging
                Log.d("GyroscopeSensorManager", "Azimuth in degrees: $azimuthInDegrees")
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle accuracy changes if needed
    }
}

