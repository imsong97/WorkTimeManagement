package com.yunho.worktimemanagement.presentation.daysetting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yunho.worktimemanagement.databinding.FragmentTimeSettingBinding
import com.yunho.worktimemanagement.presentation.daysetting.timepicker.WorkTimePickerDialog

class TimeSettingFragment : Fragment() {

    private lateinit var binding: FragmentTimeSettingBinding
    private var startTime: String = "00:00"
    private var endTime: String = "00:00"

    enum class TimeType(val value: Int) {
        START(1), END(2)
    }

    companion object {
        fun getInstance(startTime: String, endTime: String): TimeSettingFragment {
            val bundle = Bundle().run {
                this.putString("startTime", startTime)
                this.putString("endTime", endTime)
                this
            }
            val fragment = TimeSettingFragment().apply {
                this.arguments = bundle
            }

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimeSettingBinding.inflate(inflater)

        arguments?.let {
            startTime = it.getString("startTime", "00:00")
            endTime = it.getString("endTime", "00:00")
        }

        binding.layoutGoWork.setOnClickListener {
            showTimePicker(TimeType.START)
        }

        binding.layoutGetoffWork.setOnClickListener {
            showTimePicker(TimeType.END)
        }

        binding.goWorkTime.text = startTime
        binding.getoffWorkTime.text = endTime

        return binding.root
    }

    private fun showTimePicker(timeType: TimeType) {
        val type = timeType.value
        val time = if (timeType == TimeType.START) {
            startTime
        } else {
            endTime
        }

        WorkTimePickerDialog.getInstance(time, type)
            .show(requireActivity().supportFragmentManager.beginTransaction(), "WorkTimePickerDialog")
    }

}