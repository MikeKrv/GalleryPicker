package com.example.gallerypicker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateVMFactory
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @description Selecting a picture from the gallery.
 * @author Mikhail Krivosheev
 */

class MainActivity : AppCompatActivity() {

    private val mImageViewModel by lazy {
        ViewModelProviders.of(this, SavedStateVMFactory(this)).get(ImageViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mImageViewModel.getUri().observe(this, Observer { uri ->
            val image = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            imgPhoto.setImageBitmap(image)
        })

        btnGallery.setOnClickListener { openGallery() }

    }

    private fun openGallery() {
        val imageRequestIntent = Intent(Intent.ACTION_PICK)
        imageRequestIntent.type = INTENT_IMAGE_TYPE
        startActivityForResult(Intent.createChooser(imageRequestIntent, getString(R.string.image_request_text)), IMAGE_REQUEST)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK){
            data?.data?.let {
                mImageViewModel.saveNewUri(it)
            }
        }
    }

    private companion object {
        const val IMAGE_REQUEST = 1111
        const val INTENT_IMAGE_TYPE = "image/*"
    }
}
