package com.example.pogodynka_mim

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pogodynka_mim.model.entities.Data
import kotlinx.android.synthetic.main.fragment_senior.*
import kotlinx.android.synthetic.main.fragment_young.*
import kotlin.math.roundToInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SeniorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SeniorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(Application())).get(MainViewModel::class.java)

        //co zrobic, jesli obserwowane dane ulegna zmianie
        val weatherObserver = Observer<Data> {
            newRequest ->
            run {
                if(newRequest.weather.isNotEmpty()) //jeśli nie bedzie to pusta odpowiedz
                {
                    val resID = resources.getIdentifier("drawable/a" + newRequest.weather[0].icon, null, context?.packageName)
                    imageViewSenior.setImageResource(resID)
                    textViewSenior_temp.text = (newRequest.main.temp - 273.15).roundToInt().toString()
                    textViewSenior_sunRise.text = "Sunrise: " + ((newRequest.sys.sunrise % 86400.0)/3600 +2).roundToInt().toString() + ":" + (((newRequest.sys.sunrise % 86400.0) % 3600) /60 ).roundToInt().toString()

                    textViewSenior_sunSet.text = "Sunset: " + ((newRequest.sys.sunset % 86400.0)/3600 +2).roundToInt().toString() + ":" + (((newRequest.sys.sunset % 86400.0) % 3600) /60 ).roundToInt().toString()
                    textViewSenior_description.text = newRequest.weather[0].description

                    textViewSenior_name.text = newRequest.name

                }
                else
                {
                    textViewSenior_temp.text = ""
                    textViewSenior_sunRise.text = ""

                    textViewSenior_sunSet.text = ""
                    textViewSenior_description.text = ""
                    textViewSenior_name.text = "city not found"

                    val resID = resources.getIdentifier("drawable/ic_baseline_error_24", null, context?.packageName)
                    imageViewSenior.setImageResource(resID)

                }


            }
        }

        //obserwujemy livedata data z mainviewmodelu
        mainViewModel.data.observe(viewLifecycleOwner,weatherObserver)

        mainViewModel.updateData()


        return inflater.inflate(R.layout.fragment_senior, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SeniorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SeniorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}