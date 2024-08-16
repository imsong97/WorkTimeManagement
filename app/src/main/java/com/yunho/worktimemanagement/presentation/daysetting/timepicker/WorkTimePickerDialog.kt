package com.yunho.worktimemanagement.presentation.daysetting.timepicker

import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.yunho.worktimemanagement.R

class WorkTimePickerDialog : DialogFragment(), OnTimeSetListener {

    private val type by lazy { arguments?.getInt("type") ?: START }

    companion object {
        const val START = 1
        const val END = 2

        fun getInstance(time: String, type: Int): WorkTimePickerDialog {
            val bundle = Bundle().run {
                this.putString("time", time)
                this.putInt("type", type)
                this
            }

            val fragment = WorkTimePickerDialog().apply {
                this.arguments = bundle
            }

            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val time = arguments?.getString("time") ?: "00:00"

        val hour = time.split(":".toRegex())
            .dropLastWhile { it.isEmpty() }
            .toTypedArray()[0]
            .trim { it <= ' ' }
            .toInt()
        val minute = time.split(":".toRegex())
            .dropLastWhile { it.isEmpty() }
            .toTypedArray()[1]
            .trim { it <= ' ' }
            .toInt()

        return TimePickerDialog(requireActivity(), R.style.SpinnerTimePickerDialog, this, hour, minute, true)
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        Log.d("WorkTimePickerDialog", "++onTimeSet $p0 / $p1 / $p2 ++")
        // TODO rx event
    }
}