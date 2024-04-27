package uz.tezpos.livedata

import androidx.lifecycle.MutableLiveData
import uz.tezpos.models.User

object LiveDataUser {
    private var liveDataUser = MutableLiveData<ArrayList<User>>()
    fun set(list: ArrayList<User>){
        liveDataUser.postValue(list)
    }
    fun get() = liveDataUser
}