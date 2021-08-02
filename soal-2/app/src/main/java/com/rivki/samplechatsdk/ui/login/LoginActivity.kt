package com.rivki.samplechatsdk.ui.login

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.rivki.samplechatsdk.R
import com.rivki.samplechatsdk.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewReady(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        viewModel.doLogin()
    }

    override fun observeData() {
        viewModel.isLogin.observe(this){
            Log.d("FROM_MAIN", it.name)
        }
    }
}