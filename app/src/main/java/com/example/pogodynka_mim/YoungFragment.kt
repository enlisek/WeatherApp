package com.example.pogodynka_mim

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pogodynka_mim.model.entities.Data
import kotlinx.android.synthetic.main.fragment_young.*
import kotlin.math.roundToInt


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [YoungFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class YoungFragment : Fragment() {
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
        mainViewModel = ViewModelProvider(requireActivity(),ViewModelProvider.AndroidViewModelFactory.getInstance(Application())).get(MainViewModel::class.java)

        val weatherObserver = Observer<Data> {
            newRequest ->
            run {




                if(newRequest.weather.isNotEmpty())
                {
                    val resID = resources.getIdentifier("drawable/a" + newRequest.weather[0].icon, null, context?.packageName)
                    imageView1.setImageResource(resID)
                    textView_temp.text = (newRequest.main.temp - 273.15).roundToInt().toString()
                    textView_feels_like.text = "feels like: " + (newRequest.main.feels_like - 273.15).roundToInt().toString() + 0x00B0.toChar() +"C"
                    textView_max_min.text = (newRequest.main.temp_max - 273.15).roundToInt().toString() + 0x00B0.toChar() +"C / " + (newRequest.main.temp_min - 273.15).roundToInt().toString() + 0x00B0.toChar() +"C"
                    textView_sunrise.text = ((newRequest.sys.sunrise % 86400.0)/3600 +2).roundToInt().toString() + ":" + (((newRequest.sys.sunrise % 86400.0) % 3600) /60 ).roundToInt().toString() + " / " +
                            ((newRequest.sys.sunset % 86400.0)/3600 +2).roundToInt().toString() + ":" + (((newRequest.sys.sunset % 86400.0) % 3600) /60 ).roundToInt().toString()
                    textView_description.text = newRequest.weather[0].description
                    textView_wind.text = newRequest.wind.speed.toString() + " m/s"
                    textView_pressure.text = newRequest.main.pressure.toString() + " hPa"
                    textView_visibility.text = newRequest.visibility.toString() + " m "
                    textView_humidity.text = newRequest.main.humidity.toString() + " % "
                    textView_name.text = newRequest.name
                    textView_lanLon.text = "(" + newRequest.coord.lat.toString() + 0x00B0.toChar() +  "," + newRequest.coord.lon.toString() +0x00B0.toChar() + ")"

                }
                else
                {
                    textView_name.text="city not found"
                    textView_pressure.text=""

                    textView_temp.text = ""
                    textView_feels_like.text = ""
                    textView_max_min.text = ""
                    textView_sunrise.text = ""
                    textView_description.text = ""
                    textView_wind.text = ""
                    textView_pressure.text = ""
                    textView_visibility.text =""
                    textView_humidity.text = ""
                    textView_lanLon.text = ""

                    val resID = resources.getIdentifier("drawable/ic_baseline_error_24", null, context?.packageName)
                    imageView1.setImageResource(resID)

                }


            }
        }

        mainViewModel.data.observe(viewLifecycleOwner,weatherObserver)

        mainViewModel.updateData()
        return inflater.inflate(R.layout.fragment_young, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment YoungFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            YoungFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}