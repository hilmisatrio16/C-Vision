package com.capstoneproject.cvision.ui.diagnose

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstoneproject.cvision.databinding.FragmentResultBinding
import com.capstoneproject.cvision.ui.diagnose.ResultDiagnosisDialogFragment.Companion.RESULT_CLASS
import com.capstoneproject.cvision.ui.diagnose.ResultDiagnosisDialogFragment.Companion.RESULT_JENIS


class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding

    private var result = ""
    private var classPrediction = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResultBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            result = it.getString(RESULT_JENIS).toString()
            classPrediction = it.getString(RESULT_CLASS).toString()
        }

        binding.tvDiagnose.text = result

        Log.d("HASIL", result)

    }

}