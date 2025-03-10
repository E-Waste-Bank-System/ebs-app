package com.example.ebs.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.ebs.EbsApplication

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
//object AppViewModelProvider {
//    val Factory = viewModelFactory {
//
//        initializer {
//            SignInViewModel(EbsApplication().container.itemsRepository)
//        }
//
//        initializer {
//            SignUpViewModel(EbsApplication().container.itemsRepository)
//        }
//        initializer {
//            DashboardViewModel(
//                this.createSavedStateHandle(),
//                EbsApplication().container.dataTestRepository
//            )
//        }
//
//        initializer {
//            DetectionListViewModel(EbsApplication().container.dataTestRepository)
//        }
//
//        initializer {
//            NotifikasiViewModel(EbsApplication().container.dataTestRepository)
//        }
//
//        initializer {
//            ProfileViewModel(
//                this.createSavedStateHandle(),
//                EbsApplication().container.dataTestRepository
//            )
//        }
//
//        initializer {
//            WasteDetailViewModel(EbsApplication().container.dataTestRepository)
//        }
//    }
//}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [EbsApplication].
 */
fun CreationExtras.EbsApplication(): EbsApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as EbsApplication)
