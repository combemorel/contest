package fr.campus.academy.worldvisit.ws

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WSInterface
{
    @GET("all")
    fun wsGet(): Call<MutableList<CountryWs>>

    @GET("name/{country}")
    fun wsGetOne(@Path("country") country: String ): Call<MutableList<CountryWs>>
}
