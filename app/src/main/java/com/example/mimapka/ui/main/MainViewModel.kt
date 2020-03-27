package com.example.mimapka.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mimapka.data.API_GET
import com.example.mimapka.data.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {
    private val _weatherinfo = MutableLiveData<Response>()
    val weatherinfo: LiveData<Response> = _weatherinfo

    var urlCode = MutableLiveData<Boolean>(true)

    fun loadStationInfo(cityName:String){
        val json = runBlocking {
            withContext(Dispatchers.Default) {
                API_GET.hitAPI(cityName)
            }
        }
        if(json != null) {
            _weatherinfo.value = json
        }
        else {
            urlCode.value = urlCode.value != true
        }
    }
    fun takeTime(milliSec: Long): String? {
        return String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(milliSec) % 24,
            TimeUnit.MILLISECONDS.toMinutes(milliSec) % 60
        )
    }
    /*private fun getImage(){

    }*/
}
