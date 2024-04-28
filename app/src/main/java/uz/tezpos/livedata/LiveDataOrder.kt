package uz.tezpos.livedata

import androidx.lifecycle.MutableLiveData
import uz.tezpos.models.Order
import uz.tezpos.models.User

object LiveDataOrder {
    private var livedataOrder = MutableLiveData<ArrayList<Order>>()
    fun set(list: ArrayList<Order>){
        livedataOrder.postValue(list)
    }
    fun get() = livedataOrder
}