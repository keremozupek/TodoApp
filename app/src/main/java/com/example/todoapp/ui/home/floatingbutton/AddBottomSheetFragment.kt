package com.example.todoapp.ui.home.floatingbutton

import android.app.*
import android.content.Context.ALARM_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentBottomsheetAddBinding
import com.example.todoapp.room.TodoEntity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.sql.Date
import java.util.*

@AndroidEntryPoint
class AddBottomSheetFragment : BottomSheetDialogFragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private val viewModel: AddBottomSheetViewModel by viewModels()
    private var _binding: FragmentBottomsheetAddBinding? = null
    private val binding get() = _binding!!
    private val selectedTime = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomsheetAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDatePickerDialogClickListener()
        setAddButtonOnClick()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setDatePickerDialogClickListener() {
        binding.editTextDate.setOnClickListener {
            //Current date for date picker dialog
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePicker = DatePickerDialog(requireContext(), this, year, month, day)
            datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
            datePicker.show()
        }
        binding.editTextTime.setOnClickListener {
            //current hour for time picker dialog
            val c = Calendar.getInstance()
            val hourOfDay = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            val timePicker = TimePickerDialog(requireContext(), this, hourOfDay, minute, true)
            timePicker.show()
        }
    }

    private fun setAddButtonOnClick() {
        binding.addButton.setOnClickListener {
            if (binding.editTextDate.text == getString(R.string.select_date) || binding.editTextTime.text == getString(R.string.select_time)) {
                Toast.makeText(context, "You have to select date and time", Toast.LENGTH_LONG).show()
            } else {
                viewModel.createTodo(
                    binding.editTextTitle.text.toString(),
                    Date(selectedTime.timeInMillis),
                    binding.prioritySpinner.selectedItem.toString().toInt()
                )
                setAlarm()
                findNavController().navigate(R.id.action_addBottomSheetFragment_to_homeFragment)
            }
        }
    }

    private fun setAlarm() {
        if (selectedTime.timeInMillis > Calendar.getInstance().timeInMillis) {
            viewModel.setAlarm(binding.editTextTitle.text.toString(), selectedTime.timeInMillis)
        } 
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        selectedTime[Calendar.YEAR] = year
        selectedTime[Calendar.MONTH] = month
        selectedTime[Calendar.DAY_OF_MONTH] = dayOfMonth
        val x = Date(selectedTime.timeInMillis)
        binding.editTextDate.text = x.toString()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        selectedTime[Calendar.HOUR_OF_DAY] = hourOfDay
        selectedTime[Calendar.MINUTE] = minute
        binding.editTextTime.text = hourOfDay.toString() + ":" + minute.toString()
    }
}
