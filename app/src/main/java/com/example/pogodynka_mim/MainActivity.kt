package com.example.pogodynka_mim

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {

    private lateinit var viewPager : ViewPager
    private lateinit var tabLayout : TabLayout
    private lateinit var youngFragment: YoungFragment
    private lateinit var seniorFragment: SeniorFragment
    private lateinit var mainViewModel: MainViewModel
    lateinit var sharedPref: SharedPreferences

    var isChecked = true
  //  private var REQUEST_LOCATION_PERMISSION = 2
//    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tab_layout)

        youngFragment = YoungFragment()
        seniorFragment = SeniorFragment()

        tabLayout.setupWithViewPager(viewPager)


        var viewPagerAdapter = ViewPagerAdapter(supportFragmentManager,0)
        viewPagerAdapter.addFragment(youngFragment,"Young")
        viewPagerAdapter.addFragment(seniorFragment,"Senior")
        viewPager.adapter = viewPagerAdapter

        sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        if (!(sharedPref.getString("city_name", "").isNullOrEmpty()))
        {
            Log.d("KONTROLA","onCreate If")

            mainViewModel.currentCity = sharedPref.getString("city_name", "")?:""
        }
        else
        {
            Log.d("KONTROLA","onCreate Else")

            mainViewModel.currentCity = "Krowiarki"
            sharedPref.edit().putString("city_name",mainViewModel.currentCity).apply()
        }

    }




    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        mainViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(Application())).get(MainViewModel::class.java)
        Log.d("KONTROLA","onCreateView")

        return super.onCreateView(name, context, attrs)
    }

    class ViewPagerAdapter(fm : FragmentManager, behavior: Int ) : FragmentPagerAdapter(fm,behavior) {
        private var fragments : MutableList<Fragment> = mutableListOf()
        private var fragmentTitles : MutableList<String> = mutableListOf()

        fun addFragment(fragment : Fragment, title : String)
        {
            fragments.add(fragment)
            fragmentTitles.add(title)
        }

        override fun getItem(position: Int): Fragment {
            return  fragments.get(position)
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitles.get(position)
        }



    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val checkable = menu!!.findItem(R.id.checkable_menu)
        checkable.setChecked(isChecked)
        Log.d("KONTROLA","onPrepareOptionsMenu")

        if (isChecked)
            checkable.setIcon(R.drawable.ic_baseline_home_24)
        else
            checkable.setIcon(R.drawable.ic_baseline_home_24_black)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.checkable_menu -> {
                Log.d("KONTROLA","onOptionItemSelected")
                isChecked = !isChecked
                item.setChecked(isChecked)
                if (isChecked) {
                    item.setIcon(R.drawable.ic_baseline_home_24)
                    sharedPref.edit().putString("city_name",mainViewModel.currentCity).apply()
                    Log.d("KONTROLA","onOptionItemSelected If")
                }
                else
                {
                    item.setIcon(R.drawable.ic_baseline_home_24_black)
                    sharedPref.edit().putString("city_name","Krowiarki").apply()
                    Log.d("KONTROLA","onOptionItemSelected Else")
                }


                Log.d("~~~", isChecked.toString())
                true
            }
            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.my_menu,menu)
        val menuItem = menu!!.findItem(R.id.app_bar_search)
        val home = menu.findItem(R.id.checkable_menu)
        var searchView = menuItem.actionView as SearchView
        searchView.queryHint = "Search a city here"
        searchView.isIconified=false



        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.currentCity = query
                isChecked = mainViewModel.currentCity.toLowerCase() == (sharedPref.getString("city_name", "")?:"").toLowerCase()
                if (isChecked)
                    home.setIcon(R.drawable.ic_baseline_home_24)
                else
                    home.setIcon(R.drawable.ic_baseline_home_24_black)
                Log.d("Czy miasto jest?",isChecked.toString()  )
                mainViewModel.updateData()
                Log.d("KONTROLA","onQueryTextSubmit")
                return false
            }

        })

        return true
    }
}