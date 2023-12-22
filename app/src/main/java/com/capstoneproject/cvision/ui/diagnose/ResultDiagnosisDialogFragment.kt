package com.capstoneproject.cvision.ui.diagnose

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.capstoneproject.cvision.R
import com.capstoneproject.cvision.data.model.predict.ResponsePrediction
import com.capstoneproject.cvision.databinding.FragmentResultDiagnosisDialogBinding
import com.capstoneproject.cvision.ui.diagnose.adapter.SectionPageDiagnosisAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator


class ResultDiagnosisDialogFragment(uri: Uri, dataResult: ResponsePrediction) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FragmentResultDiagnosisDialogBinding
    private val imageUri = uri
    private val result = dataResult.penanganan.terdeteksiJenis
    private val classPrediction = dataResult.penanganan.classTerprediksi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultDiagnosisDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageResult.setImageURI(imageUri)

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        val sectionsPagerAdapter = SectionPageDiagnosisAdapter(this, result, classPrediction)
        binding.viewpager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }


    companion object {
        const val TAG = "DiagnoseDialogFragment"

        const val RESULT_JENIS = "RESULT_JENIS"
        const val RESULT_CLASS = "RESULT_CLASS"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

    }

}