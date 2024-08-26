package com.yunho.worktimemanagement.presentation.appsetting

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yunho.worktimemanagement.R
import com.yunho.worktimemanagement.databinding.LayoutAppSettingBinding

class AppSettingActivity : AppCompatActivity() {

    private lateinit var binding: LayoutAppSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutAppSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            this.setDisplayShowTitleEnabled(true)
            this.setDisplayHomeAsUpEnabled(true)
        }
        title = "General"

        /* TODO
        * 이전 근무 기록 보기 -> 리스트 형태로 (WorkTimeListActivity)
        * 휴일(연차) 설정
        * 로그아웃
        * */
        binding.layoutLogout.root.setOnClickListener {

        }

        binding.layoutWorktimeList.root.setOnClickListener {

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}