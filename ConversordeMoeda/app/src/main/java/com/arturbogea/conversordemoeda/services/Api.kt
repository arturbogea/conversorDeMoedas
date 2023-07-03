package com.arturbogea.conversordemoeda.services

import com.arturbogea.conversordemoeda.model.Moedas
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface Api {

    @GET(":moedas")

    fun conversorMoedas(@Path("moedas") moedas: String) : Call<Moedas>

}

