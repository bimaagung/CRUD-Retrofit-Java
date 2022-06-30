package com.otetcode.crudretrofitjava.Network

import com.google.gson.GsonBuilder
import com.otetcode.crudretrofitjava.Model.Person
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface PersonApiClient {

    // Get one article by it's id
    @GET("api/person/{id}")
    fun getPerson(@Path("id") id: Int): Observable<Person>

    // Add new article
    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("api/person")
    fun addPerson(@Body person: Person): Observable<Person>

    companion object {

        fun create(): PersonApiClient {

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://192.168.43.43:8080/")
                .build()

            return retrofit.create(PersonApiClient::class.java)

        }
    }
}