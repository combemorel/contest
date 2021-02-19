package fr.campus.academy.worldvisit.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.util.Log
import androidx.preference.PreferenceManager
import fr.campus.academy.worldvisit.R
import fr.campus.academy.worldvisit.bdd.AppDatabaseHelper
import fr.campus.academy.worldvisit.bdd.CountryDTO
import kotlinx.android.synthetic.main.activity_date.*

class DateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date)
    }

    fun addBDD (view: View)
    {

        val name = intent.getStringExtra("name")
        val capital = intent.getStringExtra("capital")
        val continent = intent.getStringExtra("continent")
        val image = intent.getStringExtra("flag")
        val date: String = "'"+date_picker.dayOfMonth.toString()+"-"+date_picker.month.toString()+"-"+date_picker.year.toString()+"'"
        Log.d("TAG" ,name.toString())
        Log.d("TAG" ,capital.toString())
        Log.d("TAG" ,continent.toString())
        Log.d("TAG" ,image.toString())
        Log.d("TAG" ,date)

        if(continent != null && name != null && capital != null && image != null)
        {
            val country = AppDatabaseHelper.getDatabase(this)
                .CountryDAO()
                .findOneByName(name)

            if(country == null)
            {
                AppDatabaseHelper.getDatabase(this)
                    .CountryDAO()
                    .create(CountryDTO(0,name,image,capital,continent,date))

                val intent = Intent(this, MainActivity::class.java)

                startActivity(intent)
                finish()

            } else
            {
                Toast.makeText(this,"Ce Pays est déja présent dans vos Favoris", Toast.LENGTH_LONG).show()
            }
        }
    }
}