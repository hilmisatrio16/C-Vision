package com.capstoneproject.cvision.ui.diagnose.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstoneproject.cvision.ui.diagnose.DetailResultFragment
import com.capstoneproject.cvision.ui.diagnose.ResultFragment

class SectionPageDiagnosisAdapter (fragment: Fragment, resultClass: String, percentageClass: String) :
    FragmentStateAdapter(fragment) {

    var result: String = ""
    var percentage: String = ""


    init {
        result = resultClass
        percentage = percentageClass
    }

    override fun createFragment(position: Int): Fragment {

        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = ResultFragment()
            1 -> fragment = DetailResultFragment()
        }

        //ADD CONSTANT
        fragment!!.arguments = Bundle().apply {
            putString("RESULT CLASS", result)
            putString("RESULT PERCENTAGE", percentage)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}