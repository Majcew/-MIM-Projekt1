package com.example.mimapka.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mimapka.R
import com.example.mimapka.data.Response
import com.example.mimapka.databinding.MainLayoutBinding
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding:MainLayoutBinding
    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding =  DataBindingUtil.inflate<MainLayoutBinding>(inflater,R.layout.main_layout,container,false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = activity
        viewModel.weatherinfo.observe(this,doIcons)
        viewModel.urlCode.observe(this,showToast)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button.setOnClickListener {
            viewModel.loadStationInfo(inputCity.text.toString())
        }
    }
    private val doIcons = Observer<Response> //Data Binding nie pozwala na łatwą podmianę obrazków, dlatego wybrałem takie rozwiązanie
    {
        when(viewModel.weatherinfo.value!!.weather[0].main) {
            "Clouds" -> binding.weatherIcon.setBackgroundResource(R.drawable.cloudy_day)
            "Rain" -> binding.weatherIcon.setBackgroundResource(R.drawable.rainy_day)
            "Snow" -> binding.weatherIcon.setBackgroundResource(R.drawable.hail)
            "Extreme" -> binding.weatherIcon.setBackgroundResource(R.drawable.smog)
            "Clear" -> binding.weatherIcon.setBackgroundResource(R.drawable.sunny_day)
            "Thunderstorm" -> binding.weatherIcon.setBackgroundResource(R.drawable.lighting)
            "Drizzle" -> binding.weatherIcon.setBackgroundResource(R.drawable.drizzle)
            "Mist" -> binding.weatherIcon.setBackgroundResource(R.drawable.mist)
            else -> ""
        }
    }
    private val showToast= Observer<Boolean> {
        Toast.makeText(context, "Nie udało się odnaleźć takiego miasta", Toast.LENGTH_SHORT).show()
    }
}
