package com.example.gallerypicker

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 * @description Utility class for saving state
 * @author Mikhail Krivosheev
 */

class ImageViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val IMAGE_KEY = "image"

    private val mState : SavedStateHandle = savedStateHandle

    fun getUri() : LiveData<Uri>{
        return mState.getLiveData(IMAGE_KEY)
    }

    fun saveNewUri(newURI: Uri?) {
        mState.set(IMAGE_KEY, newURI)
    }

}