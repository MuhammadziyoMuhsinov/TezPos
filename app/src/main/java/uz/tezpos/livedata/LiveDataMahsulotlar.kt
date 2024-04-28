package uz.tezpos.livedata

import androidx.lifecycle.MutableLiveData
import uz.tezpos.models.Order
import uz.tezpos.models.Product

object LiveDataMahsulotlar {
    private var liveDataMahsulot = MutableLiveData<ArrayList<Product>>()
    fun set(list: ArrayList<Product>){
        liveDataMahsulot.postValue(list)
    }
    fun get() = liveDataMahsulot
}