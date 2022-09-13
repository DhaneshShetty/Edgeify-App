package com.ddevs.edgeify

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddevs.edgeify.network.ApiClient
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class MainViewModel:ViewModel() {
    private var _loading:MutableLiveData<Boolean> = MutableLiveData()
    val loading:MutableLiveData<Boolean> get() = _loading
    private var _error:MutableLiveData<String> = MutableLiveData()
    val error:MutableLiveData<String> get() = _error
    private lateinit var fileUri:Uri
    private var bitmap: Bitmap? = null
    private var _resultUri:MutableLiveData<Pair<String,String>?> = MutableLiveData(null)
    val resultUri:LiveData<Pair<String,String>?>
        get()=_resultUri

    fun setUri(uri: Uri){
        fileUri =uri
    }

    fun setBitmap(bitmap: Bitmap){
        this.bitmap = bitmap
    }

    fun getBitmap() = bitmap

    fun reset(){
        bitmap = null
    }

    fun getUri():Uri = fileUri
    fun processImage(file: File){
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = ApiClient.service.processImage(
                    MultipartBody.Part.createFormData(
                        name = "img",
                        filename = file.name,
                        body = file.asRequestBody()
                    )
                )
                if (result.isSuccessful) {
                    val pair = Pair(
                        result.body()?.originalLink.toString(),
                        result.body()?.edgeLink.toString()
                    )
                    _resultUri.value = pair
                    _loading.value = false
                } else {
                    _error.value = "Something went wrong"
                    _loading.value = false
                }
            }
            catch(ex:Exception){
                _loading.value = false
                _error.value = "Something went wrong"
                Log.e("Exception",ex.message.toString())
            }
        }
    }
}