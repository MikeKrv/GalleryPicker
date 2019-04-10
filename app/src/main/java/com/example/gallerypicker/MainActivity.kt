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

class MainActivity : AppCompatActivity() {

    private lateinit var mImageViewModel: ImageViewModel
    private val IMAGE_REQUEST = 1111
    private val INTENT_IMAGE_TYPE = "image/*"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mImageViewModel = ViewModelProviders.of(this, SavedStateVMFactory(this))
            .get(ImageViewModel::class.java)

        mImageViewModel.getUri().observe(this, Observer { uri ->
            val image = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            img_Photo.setImageBitmap(image)
        })

        btn_Gallery.setOnClickListener { openGallery() }

    }

    private fun openGallery() {
        val imageRequestIntent = Intent(Intent.ACTION_PICK)
        imageRequestIntent.type = INTENT_IMAGE_TYPE
        startActivityForResult(Intent.createChooser(imageRequestIntent, "Select image"), IMAGE_REQUEST)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK){
            val imageUri = data?.data
            mImageViewModel.saveNewUri(imageUri)
        }

    }
}
