package com.laari.rider.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laari.rider.models.FavLocationsModel

class DashBoardViewModel : ViewModel() {

    var favInsideList: MutableLiveData<ArrayList<FavLocationsModel>>? = null
    var favOutsideList: MutableLiveData<ArrayList<FavLocationsModel>>? = null


    fun getFavouritesInsideList(): LiveData<ArrayList<FavLocationsModel>> {
        if (favInsideList == null) {
            favInsideList = MutableLiveData()
            loadfavInsideLocations()
        }

        return favInsideList!!
    }


    fun getFavouriteOutsideList(): LiveData<ArrayList<FavLocationsModel>> {

        if (favOutsideList == null) {
            favOutsideList = MutableLiveData()

            loadOutsideFavourites()
        }
        return favOutsideList!!
    }

    private fun loadOutsideFavourites() {
        val list = ArrayList<FavLocationsModel>()
        list.add(FavLocationsModel("Office"))
        list.add(FavLocationsModel("Faislabad"))
        list.add(FavLocationsModel("Karachi"))
        favOutsideList!!.value = list
    }


    private fun loadfavInsideLocations() {
        val list = ArrayList<FavLocationsModel>()
        list.add(FavLocationsModel("Home"))
        list.add(FavLocationsModel("Hospital"))
        favInsideList!!.value = list
    }
}




