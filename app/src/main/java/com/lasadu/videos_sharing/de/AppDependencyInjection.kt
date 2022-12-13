package com.lasadu.videos_sharing.de

import com.lasadu.videos_sharing.login.viewmodel.LoginViewModel
import com.lasadu.videos_sharing.register.viewmodel.RegisterViewModel
import com.lasadu.videos_sharing.splash.viewmodel.LoadUserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppDependencyInjection {
    val appModule = module {
        viewModel{
            RegisterViewModel(get())
        }
        viewModel{
            LoginViewModel(get())
        }
        viewModel{
            LoadUserViewModel(get())
        }
    }
}