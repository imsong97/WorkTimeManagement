package com.yunho.worktimemanagement.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yunho.worktimemanagement.R
import com.yunho.worktimemanagement.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Contract.View {

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: Contract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = Presenter()

        binding.btnSendSlack.setOnClickListener {
            presenter.sendSlackMessage()
        }
    }
}