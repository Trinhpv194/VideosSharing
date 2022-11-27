package com.lasadu.videos_sharing.de

import com.lasadu.videos_sharing.login.viewmodel.LoginViewModel
import com.lasadu.videos_sharing.register.viewmodel.RegisterViewModel
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
    }
}