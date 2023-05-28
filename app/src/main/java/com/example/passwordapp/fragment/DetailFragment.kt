package com.example.passwordapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.passwordapp.database.User
import com.example.passwordapp.databinding.FragmentDetailBinding
import com.example.passwordapp.util.toBitmap

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = arguments?.getParcelable("entity")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user?.let {
            binding.imageView.setImageBitmap(it.image.toBitmap())
            binding.textUserName.text = user?.lastName
            binding.textName.text = user?.name
            binding.textMiddleName.text = user?.middleName
            binding.textDateBirthday.text = user?.dataBirthday
            binding.textGetDate.text = user?.gotDate
            binding.textMuddati.text = user?.lifeTime
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}