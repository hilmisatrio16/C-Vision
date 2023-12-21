package com.capstoneproject.cvision.ui.diagnose

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import com.capstoneproject.cvision.R
import com.capstoneproject.cvision.data.model.predict.RequestPredict
import com.capstoneproject.cvision.data.model.predict.ResponsePrediction
import com.capstoneproject.cvision.data.remote.retrofit.ApiConfig
import com.capstoneproject.cvision.databinding.ActivityDetectionBinding
import com.capstoneproject.cvision.ui.diagnose.CameraActivity.Companion.CAMERAX_RESULT
import com.capstoneproject.cvision.utils.Result
import com.capstoneproject.cvision.utils.reduceFileImage
import com.capstoneproject.cvision.utils.uriToFile
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class DetectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetectionBinding
    private var currentImageUri: Uri? = null
    private var token: String = ""

    private val detectionVM by viewModels<DetectionViewModel> {
        DetectionViewModelFactory.getInstance(this)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, resources.getString(R.string.request_granted), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, resources.getString(R.string.request_denied), Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        detectionVM.getTokenUser().observe(this, Observer {
            if (it.isNotEmpty()) {
                token = it
            }
        })

        binding.btnTakeCamera.setOnClickListener { startCameraX() }
        binding.btnImageGallery.setOnClickListener { startGallery() }
        binding.btnDetection.setOnClickListener {

            if (currentImageUri != null && token.isNotEmpty()) {
                predictImageCataract()
            }

        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun predictImageCataract() {
        currentImageUri?.let { uriImage ->
            val imageFile = uriToFile(uriImage, this).reduceFileImage()
            val tokenUser = token
            detectionVM.predictCataract(token, dataPredict = RequestPredict(tokenUser, imageFile))
                .observe(this, Observer {
                    if (it != null) {
                        when (it) {
                            is Result.Loading -> {
                                Log.d("RESPONSE PREDICT", "load")
                                binding.lottieLoading.visibility = View.VISIBLE
                            }

                            is Result.Success -> {
                                binding.lottieLoading.visibility = View.GONE
                                Log.d("RESPONSE PREDICT", it.data.toString())
                                showResult(it.data)
                                Toast.makeText(this, it.data.message, Toast.LENGTH_SHORT).show()

                            }

                            is Result.Error -> {
                                Log.d("RESPONSE PREDICT", it.error)
                                binding.lottieLoading.visibility = View.GONE
                                Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })

        }

    }

    private fun showResult(dataRespon: ResponsePrediction) {
        //show dialog
        val resultDialog = ResultDiagnosisDialogFragment(
            currentImageUri!!,
            dataRespon
        )
        resultDialog.show(supportFragmentManager, ResultDiagnosisDialogFragment.TAG)
    }

    ///


    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri

            showImage()
        } else {
            Log.d("Photo Picker", "No media or image selected")
        }
    }


    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.imageEye.setImageURI(it)
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}