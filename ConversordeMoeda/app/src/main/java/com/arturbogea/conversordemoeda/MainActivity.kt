package com.arturbogea.conversordemoeda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.arturbogea.conversordemoeda.databinding.ActivityMainBinding
import com.arturbogea.conversordemoeda.model.Moedas
import com.arturbogea.conversordemoeda.services.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://economia.awesomeapi.com.br/json/last/")
            .build()
            .create(Api::class.java)


        binding.btConverter.setOnClickListener {

            val reais = binding.txtreais.text.toString()
            var moedas = "USD_BRL"

            if (reais.isEmpty()){
                Toast.makeText(this, "Digite um valor", Toast.LENGTH_LONG).show()
            }else{
                retrofit.conversorMoedas(moedas).enqueue(object : Callback<Moedas>{
                    override fun onResponse(call: Call<Moedas>, response: Response<Moedas>) {
                        if (response.isSuccessful){
                            conversor(response)
                        }else{
                            Toast.makeText(applicationContext, "Erro", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<Moedas>, t: Throwable) {
                        Toast.makeText(applicationContext, "Erro Inesperado", Toast.LENGTH_LONG).show()
                    }

                })
            }


        }

    }

    private fun conversor(response: Response<Moedas>){
        var reais = binding.txtreais.text.toString()
        var usd = response.body()!!.USDBRL
        var calcUSD = usd.get("high").toString()
        val usdReal = (reais.toDouble() * calcUSD.toDouble())
        binding.txtdolar.setText("US$ $usdReal")
    }




}
