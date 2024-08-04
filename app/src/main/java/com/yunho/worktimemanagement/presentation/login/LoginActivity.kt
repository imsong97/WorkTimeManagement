package com.yunho.worktimemanagement.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yunho.worktimemanagement.databinding.ActivityLoginBinding
import com.yunho.worktimemanagement.presentation.main.WorkTimeMainActivity
import com.yunho.worktimemanagement.utils.PreferenceWrapper

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val preferenceWrapper by lazy { PreferenceWrapper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (preferenceWrapper.isAutoLogin()) {
            startWorkTimeActivity()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            if (binding.keepLogin.isChecked) {
                preferenceWrapper.setAutoLogin(true)
                preferenceWrapper.setId(binding.textId.text.toString())
                preferenceWrapper.setPassword(binding.textPassword.text.toString())
            }
            startWorkTimeActivity()
        }
    }

    private fun startWorkTimeActivity() {
        Intent(this, WorkTimeMainActivity::class.java).also {
            startActivity(it)
        }
        finish()
    }
}