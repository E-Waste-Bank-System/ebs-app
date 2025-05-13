package com.example.ebs.ui

///**
// * Provides Factory to create instance of ViewModel for the entire Inventory app
// */
//object AppViewModelProvider {
//    val Factory = viewModelFactory {
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
//
///**
// * Extension function to queries for [Application] object and returns an instance of
// * [EbsApplication].
// */
//fun CreationExtras.EbsApplication(): EbsApplication =
//    (this[AndroidViewModelFactory.APPLICATION_KEY] as EbsApplication)
