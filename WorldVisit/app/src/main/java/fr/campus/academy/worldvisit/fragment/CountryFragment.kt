package fr.campus.academy.worldvisit.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.campus.academy.worldvisit.R
import kotlinx.android.synthetic.main.fragment_country.*


/**
 * A simple [Fragment] subclass.
 * Use the [CountryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CountryFragment : Fragment()
{
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)

        val arguments = requireArguments()

        name_fragment.text = arguments.getString("name")
        continent_fragment.text = arguments.getString("continent")
        capital_fragment.text = arguments.getString("capital")
//        date_fragment.text = arguments.getString

        val flag = arguments.getString("alpha2Code")

        Picasso.get()
            .load("http://www.geognos.com/api/en/countries/flag/$flag.png")
            .fit()
            .centerCrop()
            .into(img)
    }
}