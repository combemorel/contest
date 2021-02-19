package fr.campus.academy.worldvisit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import fr.campus.academy.worldvisit.R
import fr.campus.academy.worldvisit.adapter.CountriesAdapter
import fr.campus.academy.worldvisit.ws.CountryWs
import fr.campus.academy.worldvisit.ws.ReseauHelper
import fr.campus.academy.worldvisit.ws.RetrofitSingleton
import fr.campus.academy.worldvisit.ws.WSInterface
import kotlinx.android.synthetic.main.activity_add.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddActivity : AppCompatActivity()
{
    private lateinit var countriesAdapter: CountriesAdapter
    private lateinit var listCountriesWs: MutableList<CountryWs>
    private var addActivity = this

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        list_countries.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        list_countries.layoutManager = layoutManager

        if (ReseauHelper.isConnect(this))
        {
            val service = RetrofitSingleton.retrofit.create(WSInterface::class.java)
            val call = service.wsGet()
            call.enqueue(object : Callback<MutableList<CountryWs>>
            {
                override fun onResponse(
                    call: Call<MutableList<CountryWs>>,
                    response: Response<MutableList<CountryWs>>
                ){
                    if (response.isSuccessful)
                    {
                        val retourWSGet = response.body()
                        if(retourWSGet != null)
                        {
                            listCountriesWs = retourWSGet
                            countriesAdapter = CountriesAdapter(retourWSGet,addActivity)
                            list_countries.adapter = countriesAdapter
                        }
                    }
                }

                override fun onFailure(call: Call<MutableList<CountryWs>>, t: Throwable)
                {
                    Log.e("tag", "${t.message}")
                }
            })
        }

    }

    fun searchCountry(view: View?)
    {
        if (ReseauHelper.isConnect(this))
        {
            val service = RetrofitSingleton.retrofit.create(WSInterface::class.java)
            val call = if(search.text.isNullOrEmpty())
                {
                    service.wsGet()
                }else
                {
                    service.wsGetOne(search.text.toString())
                }

            call.enqueue(object : Callback<MutableList<CountryWs>>
            {
                override fun onResponse(
                    call: Call<MutableList<CountryWs>>,
                    response: Response<MutableList<CountryWs>>
                ){
                    if (response.isSuccessful)
                    {
                        val retourWSGet = response.body()
                        if(retourWSGet != null)
                        {
                            listCountriesWs = retourWSGet
                            countriesAdapter = CountriesAdapter(retourWSGet,addActivity)
                            list_countries.adapter = countriesAdapter
                        }
                    }
                }

                override fun onFailure(call: Call<MutableList<CountryWs>>, t: Throwable)
                {
                    Log.e("tag", "${t.message}")
                }
            })
        }
        countriesAdapter.updateList(listCountriesWs)
    }


}