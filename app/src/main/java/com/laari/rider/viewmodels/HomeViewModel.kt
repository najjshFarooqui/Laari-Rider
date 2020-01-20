package com.laari.rider.viewmodels
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.laari.rider.models.ModelsData
import com.laari.rider.models.VehicleColorsData
import com.laari.rider.models.VehicleMakersData
import com.laari.rider.models.VehicleTypeSData
import com.laari.rider.utility.DirectionsJSONParser
import com.laari.rider.utility.PATH_WIDTH
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class HomeViewModel : ViewModel() {

    val routeLiveData: MutableLiveData<PolylineOptions> = MutableLiveData()
    fun getRoute(): LiveData<PolylineOptions> = routeLiveData

    var typesList: MutableLiveData<List<VehicleTypeSData>>? = null
    var makersList: MutableLiveData<List<VehicleMakersData>>? = null
    var modelsList: MutableLiveData<List<ModelsData>>? = null
    var colorsList: MutableLiveData<List<VehicleColorsData>>? = null





    fun getRoute(url: String) {
        doAsync {
            var data = ""
            try {
                data = downloadUrl(url)

                val jObject = JSONObject(data)
                val parser = DirectionsJSONParser()
                val routes = parser.parse(jObject)
                var polylineOptions: PolylineOptions? = null

                for (i in routes.indices) {
                    val points = java.util.ArrayList<LatLng>()
                    polylineOptions = PolylineOptions()
                    val path = routes[i]

                    for (j in path.indices) {
                        val point = path[j]
                        val lat = java.lang.Double.parseDouble(point["lat"]!!)
                        val lng = java.lang.Double.parseDouble(point["lng"]!!)
                        val position = LatLng(lat, lng)
                        points.add(position)
                    }

                    polylineOptions.addAll(points)
                    polylineOptions.width(PATH_WIDTH)
                    polylineOptions.color(Color.BLACK)
                    uiThread {
                        routeLiveData.value = polylineOptions
                    }
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun downloadUrl(strUrl: String): String {
        var data = ""
        var iStream: InputStream? = null
        var urlConnection: HttpURLConnection? = null
        try {
            val url = URL(strUrl)
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.connect()
            iStream = urlConnection.inputStream
            val br = BufferedReader(InputStreamReader(iStream!!))
            data = iStream.bufferedReader().use(BufferedReader::readText)
            br.close()
        } catch (e: Exception) {
            Log.d("abc123", e.toString())
        } finally {
            iStream!!.close()
            urlConnection!!.disconnect()
        }
        return data
    }


    fun getTypesList(): LiveData<List<VehicleTypeSData>> {
        if (typesList == null) {
            typesList = MutableLiveData()
            loadVehicleType()
        }
        return typesList!!
    }

    fun getMakersList(): LiveData<List<VehicleMakersData>> {
        if (makersList == null) {
            makersList = MutableLiveData()
            loadMakers()
        }
        return makersList!!
    }

    fun getModelsList(): LiveData<List<ModelsData>> {
        if (modelsList == null) {
            modelsList = MutableLiveData()
            loadModels()
        }
        return modelsList!!
    }

    fun getColorsList(): LiveData<List<VehicleColorsData>> {
        if (colorsList == null) {
            colorsList = MutableLiveData()
            loadColors()
        }
        return colorsList!!
    }


    fun loadVehicleType() {
        val list = ArrayList<VehicleTypeSData>()
        list.add(VehicleTypeSData("Type"))
        list.add(VehicleTypeSData("Rickshaw"))
        list.add(VehicleTypeSData("Car"))
        list.add(VehicleTypeSData("Mini"))
        list.add(VehicleTypeSData("Micro"))
        list.add(VehicleTypeSData("Trike"))
        typesList!!.value = list
    }


    fun loadMakers() {
        val list = ArrayList<VehicleMakersData>()
        list.add(VehicleMakersData("Make"))
        list.add(VehicleMakersData("Skoda"))
        list.add(VehicleMakersData("Tata"))
        list.add(VehicleMakersData("Hyundai"))
        list.add(VehicleMakersData("Mahindra"))
        list.add(VehicleMakersData("Maruti Suzuki"))
        list.add(VehicleMakersData("BMW"))
        list.add(VehicleMakersData("Audi"))
        list.add(VehicleMakersData("Mercedez"))
        makersList!!.value = list
    }


    fun loadModels() {
        val list = ArrayList<ModelsData>()
        list.add(ModelsData("Model"))
        list.add(ModelsData("swift"))
        list.add(ModelsData("c9"))
        list.add(ModelsData("11 pro"))
        list.add(ModelsData("note 4"))
        list.add(ModelsData("i 10"))
        list.add(ModelsData("wagon r"))
        modelsList!!.value = list


    }


    fun loadColors() {
        val list = ArrayList<VehicleColorsData>()
        list.add(VehicleColorsData("Color"))
        list.add(VehicleColorsData("White"))
        list.add(VehicleColorsData("Violet"))
        list.add(VehicleColorsData("Indigo"))
        list.add(VehicleColorsData("Blue"))
        list.add(VehicleColorsData("Green"))
        list.add(VehicleColorsData("Yellow"))
        list.add(VehicleColorsData("Orange"))
        list.add(VehicleColorsData("Red"))
            colorsList!!.value = list

    }


}


