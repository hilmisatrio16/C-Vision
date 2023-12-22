package com.capstoneproject.cvision.ui.diagnose

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import com.capstoneproject.cvision.R
import com.capstoneproject.cvision.data.model.predict.Penanganan
import com.capstoneproject.cvision.data.model.predict.ResponsePrediction
import com.capstoneproject.cvision.databinding.ActivityDetectionBinding
import com.capstoneproject.cvision.ml.ModelUnquant
import com.capstoneproject.cvision.ui.diagnose.CameraActivity.Companion.CAMERAX_RESULT
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

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

            if (currentImageUri != null) {
                val image = currentImageUri?.let { it1 -> convertUriToBitmap(this, it1) }
                val dimensionImage = image?.let { minOf(it.width, image.height) }
                val thumbnailImage = dimensionImage?.let { ThumbnailUtils.extractThumbnail(image, it, dimensionImage) }
                val scaledImageInput = thumbnailImage?.let { Bitmap.createScaledBitmap(it, 224, 224, false) }
                predictImageCataract(scaledImageInput!!)

            }else{
                Toast.makeText(this, "Please Selected image", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun convertUriToBitmap(context: Context, uri: Uri): Bitmap? {

        var bitmapImage: Bitmap? = null
        try {
            val contentResolver: ContentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(uri)
            bitmapImage = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ERROR", e.message.toString())
        }
        return bitmapImage

    }




    private fun predictImageCataract(imageBitmap: Bitmap) {
        try {
            val model = ModelUnquant.newInstance(this)


            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)

            val byteBuffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3)
                byteBuffer.order(ByteOrder.nativeOrder())

            val intValuesArr = IntArray(224 * 224)

            imageBitmap.getPixels(
                intValuesArr,
                0,
                imageBitmap.width,
                0,
                0,
                imageBitmap.width,
                imageBitmap.height
            )

            var pixelImg = 0

            for (i in 0 until 224) {
                for (j in 0 until 224) {
                    val `val` = intValuesArr[pixelImg++]
                    byteBuffer.putFloat(((`val` shr 16) and 0xFF) * (1f / 255f))
                    byteBuffer.putFloat(((`val` shr 8) and 0xFF) * (1f / 255f))
                    byteBuffer.putFloat((`val` and 0xFF) * (1f / 255f))
                }
            }

            inputFeature0.loadBuffer(byteBuffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            val confidencesOutput = outputFeature0.floatArray

            resultModel(confidencesOutput)
            model.close()
        } catch (exp: Exception) {
            Log.e("ERROR", exp.message.toString())
        }

    }

    private fun resultModel(confidencesOutput: FloatArray) {
        var maxPosition = 0
        var maxConfidence = 0f

        val classes = arrayOf("Katarak", "Normal")

        for (i in confidencesOutput.indices) {
                if (confidencesOutput[i] > maxConfidence) {
                    maxConfidence = confidencesOutput[i]
                    maxPosition = i
                    Log.d("Confidence", "${classes[i]}: ${confidencesOutput[i]}")
                }
            }
        showResult(ResponsePrediction(false, "success", Penanganan(maxPosition.toString(), classes[maxPosition])))
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