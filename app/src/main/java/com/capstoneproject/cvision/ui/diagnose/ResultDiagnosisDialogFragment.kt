package com.capstoneproject.cvision.ui.diagnose

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.capstoneproject.cvision.R
import com.capstoneproject.cvision.databinding.FragmentResultBinding
import com.capstoneproject.cvision.databinding.FragmentResultDiagnosisDialogBinding
import com.capstoneproject.cvision.ui.diagnose.adapter.SectionPageDiagnosisAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator


class ResultDiagnosisDialogFragment(uri: Uri, percentage: String, classResult: String) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FragmentResultDiagnosisDialogBinding
    private val imageUri = uri
    private val percentageResult = percentage
    private val result = classResult


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultDiagnosisDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.shapeableImageView.setImageURI(imageUri)

        val sectionsPagerAdapter = SectionPageDiagnosisAdapter(this, result, percentageResult)
        binding.viewpager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }


    companion object {
        const val TAG = "DiagnoseDialogFragment"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

    }

}