package com.rivki.samplechatsdk.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.rivki.samplechatsdk.R
import com.rivki.samplechatsdk.base.BaseActivity
import com.rivki.samplechatsdk.databinding.ActivityLoginBinding
import com.rivki.samplechatsdk.ui.roomlist.RoomListActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private val viewModel: LoginViewModel by viewModels()
    private val activityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onViewReady(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_login)
        setContentView(activityLoginBinding.root)
        with(activityLoginBinding){
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                viewModel.doLogin(email, password)
            }
        }
    }

    override fun observeData() {
        viewModel.isLogin.onResult{
            startActivity(Intent(this@LoginActivity, RoomListActivity::class.java))
            finish()
        }
    }
}