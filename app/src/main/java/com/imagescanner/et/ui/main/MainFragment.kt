@file:Suppress("DEPRECATION")

package com.imagescanner.et.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.imagescanner.et.R
import kotlinx.android.synthetic.main.main_fragment.*
import java.io.File

private const val FILE_NAME = "photo.jpg"
private const val REQUEST_CODE = 42
private lateinit var photoFile : File
@Suppress("UNREACHABLE_CODE")
class MainFragment(val state: Context) : Fragment() {

    companion object {
        fun newInstance() = MainFragment()

        private fun MainFragment() {

        }
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

        btnTakePicture.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(FILE_NAME)
            //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile)
            val fileProvider = FileProvider.getUriForFile(this.state, " com.imagescanner.et.fileprovider", photoFile)
            takePictureIntent.putExtra((MediaStore.EXTRA_OUTPUT), fileProvider)
            startActivityForResult(takePictureIntent, REQUEST_CODE)
        }
    }

    private fun getPhotoFile(fileName: String): File {
        val storageDirectory= getActivity()?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(fileName,  ".jpg", storageDirectory)



    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            //val takenImage = data?.extras?.get("data") as Bitmap
            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)

            imageView.setImageBitmap(takenImage)
        }else{
            super.onActivityResult(requestCode, resultCode, data)

        }
    }

}

