package com.example.passwordapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CalendarView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.passwordapp.R
import com.example.passwordapp.database.PasswordDatabase
import com.example.passwordapp.database.User
import com.example.passwordapp.databinding.FragmentAddBinding
import com.example.passwordapp.util.Objects
import com.example.passwordapp.util.toByteArray
import com.example.passwordapp.util.toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class AddUpdateFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val database by lazy { PasswordDatabase(requireContext()) }
    private lateinit var region: String
    private lateinit var gender: String
    var day = ""
    var months = ""
    var year = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, Objects.regions)
        binding.autoCompleteRegion.setAdapter(arrayAdapter)
        binding.autoCompleteRegion.setOnItemClickListener { _, _, i, _ ->
            region = Objects.regions[i]
        }
        val arrayAdapter2 =
            ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, Objects.genders)
        binding.autoCompleteGender.setAdapter(arrayAdapter2)
        binding.autoCompleteGender.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, pos, _ ->
                gender = Objects.genders[pos]
            }
        binding.btnSave.setOnClickListener {
            saveToDatabase()
        }
        binding.editGotDate.setOnClickListener {
            showDialog(binding.editGotDate)
        }
        binding.imageView.setOnClickListener {
            pickImageFromNewGallery.launch("image/*")
        }
    }

    private fun showDialog(editText: TextInputEditText) {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.date_dialog, null)
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        val calendarView: CalendarView = view.findViewById(R.id.calendarView)
        val btnGet: MaterialButton = view.findViewById(R.id.btnGet)
        var date = ""
        alertDialog.apply {
            setView(view)
        }
        calendarView.setOnDateChangeListener { _, year, months, day ->
            date = "${year}-${months.plus(1)}-${day}"
            this.months = months.plus(1).toString()
            this.day = day.toString()
        }
        btnGet.setOnClickListener {
            val year = date.subSequence(0, 4).toString().toInt().plus(10)
            editText.setText(date)
            binding.editLifeTime.setText("${year}-${this.months}-${this.day}")
            alertDialog.dismiss()
        }
        alertDialog.show()

    }

    private fun saveToDatabase() {
        val name = binding.editName.text.toString().trim()
        val lastName = binding.editLastName.text.toString().trim()
        val midName = binding.editMidName.text.toString().trim()
        val dateBirthday = binding.textDateBirthday.text.toString().trim()
        val gotDate = binding.editGotDate.text.toString().trim()
        val lifeTime = binding.editLifeTime.text.toString().trim()

        if (name.isNotBlank() && lifeTime.isNotBlank() && ::region.isInitialized && ::gender.isInitialized) {
            AlertDialog.Builder(requireContext()).apply {
                setMessage(
                    "Ma’lumotlariningiz to’g’ri ekanligiga \n" +
                            "ishonchingiz komilmi?"
                )
                setPositiveButton("Ha") { di, _ ->
                    database.dao().savePassword(
                        User(
                            0,
                            name, lastName, midName, region,
                            dateBirthday, gotDate, lifeTime,
                            gender, binding.imageView.toByteArray()
                        )
                    )
                    toast("Ma'lumotlar bazasiga saqlandi !")
                    clearData()
                }
                setNegativeButton("Yo'q", null)
            }.create().show()
        } else {
            toast("Ma'lumotlarni to'ldiring !")
        }
    }

    private val pickImageFromNewGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri ?: return@registerForActivityResult
            binding.imageView.setImageURI(uri)
        }

    private fun clearData() {
        binding.editName.text?.clear()
        binding.editLastName.text?.clear()
        binding.editMidName.text?.clear()
        binding.editGotDate.text?.clear()
        binding.editLifeTime.text?.clear()
        binding.city.text?.clear()
        binding.textDateBirthday.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}