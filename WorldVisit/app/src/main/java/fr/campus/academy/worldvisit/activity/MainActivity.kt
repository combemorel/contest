package fr.campus.academy.worldvisit.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import fr.campus.academy.worldvisit.R
import fr.campus.academy.worldvisit.adapter.CountriesAdapter
import fr.campus.academy.worldvisit.bdd.AppDatabaseHelper
import fr.campus.academy.worldvisit.bdd.CountryDTO
import fr.campus.academy.worldvisit.ws.CountryWs
import kotlinx.android.synthetic.main.activity_add.*

class MainActivity : AppCompatActivity()
{

    private var mainActivity = this

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list_countries.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        list_countries.layoutManager = layoutManager

        val listCountriesDTO: MutableList<CountryDTO> = AppDatabaseHelper.getDatabase(this)
            .CountryDAO()
            .findAll()

        list_countries.adapter = CountriesAdapter(castList(listCountriesDTO),mainActivity)
    }

    fun castList(list: MutableList<CountryDTO>): MutableList<CountryWs>
    {
        val newList: MutableList<CountryWs> = ArrayList()
        for (country in list)
        {
            newList.add(CountryWs(country.name,country.capital,country.continent,country.flag,country.date.toString()))
        }
        return newList
    }

    fun addCountry(view: View?)
    {
        val intent = Intent(this,AddActivity::class.java)
        startActivity(intent)
    }


}