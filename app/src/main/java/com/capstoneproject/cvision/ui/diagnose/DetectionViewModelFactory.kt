package com.capstoneproject.cvision.ui.diagnose

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstoneproject.cvision.data.repository.AuthenticationRepository
import com.capstoneproject.cvision.data.repository.PredictsRepository
import com.capstoneproject.cvision.di.Injection

class DetectionViewModelFactory private constructor(private val predictsRepository: PredictsRepository, private val authenticationRepository: AuthenticationRepository):
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetectionViewModel::class.java)) {
            return DetectionViewModel(predictsRepository, authenticationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: DetectionViewModelFactory? = null
        fun getInstance(context: Context): DetectionViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: DetectionViewModelFactory(Injection.providePredictsRepository(context), Injection.provideAuthRepository(context))
            }.also { instance = it }
    }
}