package com.elramady.prayertimes.domain.models.location

data class AddressLocation(
    val country: String?="",
    val city: String?="",
    val street: String?="",
    val postalCode: String?="",
    val governorate: String?="",
)