package com.capstoneproject.cvision.ui.diagnose

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstoneproject.cvision.R
import com.capstoneproject.cvision.databinding.FragmentDetailResultBinding
import com.capstoneproject.cvision.ui.diagnose.ResultDiagnosisDialogFragment.Companion.RESULT_CLASS
import com.capstoneproject.cvision.ui.diagnose.ResultDiagnosisDialogFragment.Companion.RESULT_JENIS


class DetailResultFragment : Fragment() {

    private lateinit var binding: FragmentDetailResultBinding

    private var result = ""
    private var classPrediction = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailResultBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            result = it.getString(RESULT_JENIS).toString()
            classPrediction = it.getString(RESULT_CLASS).toString()
        }

        binding.tvDiagnose.text = result

        binding.tvDescription.text = if (classPrediction != "0") {
            resources.getString(R.string.normal_description)
        } else {
            resources.getString(R.string.cataract_description)
        }

    }

}