package fr.campus.academy.worldvisit.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.campus.academy.worldvisit.R
import fr.campus.academy.worldvisit.activity.AddActivity
import fr.campus.academy.worldvisit.activity.DateActivity
import fr.campus.academy.worldvisit.bdd.AppDatabaseHelper
import fr.campus.academy.worldvisit.bdd.CountryDTO
import fr.campus.academy.worldvisit.ws.CountryWs
import kotlinx.android.synthetic.main.fragment_country.view.*

class CountriesAdapter(
    private var listCountriesWs: MutableList<CountryWs>,
    private var mainActivity: AppCompatActivity
) : RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder
    {
        val viewCourse: View?;
        if(mainActivity.localClassName.equals("activity.AddActivity") ){
            Log.d("TAG", "AddActivity")
            viewCourse = LayoutInflater.from(parent.context)
                            .inflate(R.layout.fragment_country2, parent, false)
        }else {
            Log.d("TAG", "MainActivity")

            viewCourse = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_country, parent, false)
        }
        return CountriesViewHolder(viewCourse)
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int)
    {
        holder.textViewName.text = listCountriesWs[position].name
        holder.textViewCapitale.text = "Capitale : "+listCountriesWs[position].capital
        holder.textViewContinent.text = "Continent : "+listCountriesWs[position].region
        holder.dateView?.text = listCountriesWs[position].date
        holder.flag = listCountriesWs[position].alpha2Code
        val path: String = "http://www.geognos.com/api/en/countries/flag/"+listCountriesWs[position].alpha2Code+".png"

        Picasso.get()
            .load(path)
            .fit()
            .centerCrop()
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = listCountriesWs.size

    fun updateList(countries: MutableList<CountryWs>)
    {
        this.listCountriesWs = countries
        notifyDataSetChanged()
    }

    fun castList(list: MutableList<CountryDTO>): MutableList<CountryWs>
    {
        val newList: MutableList<CountryWs> = ArrayList()
        for (country in list)
        {
            newList.add(
                CountryWs(
                    country.name,
                    country.capital,
                    country.continent,
                    country.flag,
                    country.date
                )
            )
        }
        return newList
    }
    inner class CountriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val textViewName: TextView = itemView.findViewById(R.id.name_fragment)
        val textViewCapitale: TextView = itemView.findViewById(R.id.capital_fragment)
        val textViewContinent: TextView = itemView.findViewById(R.id.continent_fragment)
        val imageView: ImageView = itemView.findViewById(R.id.img)
        var flag: String? = null
        val dateView: TextView? = if(mainActivity.localClassName.equals("activity.MainActivity")) itemView.findViewById(
            R.id.date_fragment
        ) else null
        init
        {
            if(mainActivity.localClassName.equals("activity.AddActivity"))
            {
                itemView.setOnClickListener {
                    val intent = Intent(it.context, DateActivity::class.java)
                    intent.putExtra("name", listCountriesWs[layoutPosition].name)
                    intent.putExtra("capital", listCountriesWs[layoutPosition].capital)
                    intent.putExtra("continent", listCountriesWs[layoutPosition].region)
                    intent.putExtra("flag", flag)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    it.context.startActivity(intent)
                    (mainActivity as Activity).finish()
                }
            }else
            {
                itemView.btn_fragment.setOnClickListener {
                    val countryDTO: CountryDTO = AppDatabaseHelper.getDatabase(it.context)
                        .CountryDAO()
                        .findOneByName(textViewName.text.toString())

                    AppDatabaseHelper.getDatabase(it.context)
                        .CountryDAO()
                        .delete(
                            CountryDTO(
                                countryDTO.id,
                                countryDTO.name,
                                countryDTO.flag,
                                countryDTO.capital,
                                countryDTO.continent,
                                countryDTO.date
                            )
                        )

                    val listCountryDTO: MutableList<CountryDTO> = AppDatabaseHelper.getDatabase(it.context)
                        .CountryDAO()
                        .findAll()

                    updateList(castList(listCountryDTO))
                }
            }
        }
    }


}

