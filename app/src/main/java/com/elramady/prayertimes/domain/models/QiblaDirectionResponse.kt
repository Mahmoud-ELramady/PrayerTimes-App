package com.elramady.prayertimes.domain.models


data class QiblaDirectionResponse(
    val data: QiblaDirectionResponseData,

)

data class QiblaDirectionResponseData(
    val direction: Double,
    val latitude: Double,
    val longitude: Double
)