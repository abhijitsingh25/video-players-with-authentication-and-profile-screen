package com.dimhans.dimhansiddapp

import ProgressViewModel
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dimhans.dimhansiddapp.ui.theme.DimhansIDDAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DimhansIDDAppTheme {
                // Create ProgressViewModel here (shared for all screens)
                val progressViewModel: ProgressViewModel = viewModel(
                    factory = ProgressViewModelFactory(application)

                )

                // Pass it into your navigation so both ProfileScreen and ToothpasteScreen can use it
                AppNavigation(progressViewModel)
//                AppNavigation()
            }
        }
    }
}


class ProgressViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProgressViewModel::class.java)) {
            return ProgressViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
