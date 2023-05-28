package com.example.passwordapp.fragment

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.passwordapp.R
import com.example.passwordapp.adapter.PasswordAdapter
import com.example.passwordapp.database.PasswordDatabase
import com.example.passwordapp.database.User
import com.example.passwordapp.databinding.FragmentAllBinding
import com.example.passwordapp.util.toast

class AllFragment : Fragment() {
    private var _binding: FragmentAllBinding? = null
    private val binding get() = _binding!!
    private val database by lazy { PasswordDatabase.invoke(requireContext()) }
    private lateinit var passportAdapter: PasswordAdapter
    private var passportList: MutableList<User> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        passportAdapter = PasswordAdapter()
        passportList = database.dao().getAllPassword() as MutableList<User>
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = passportAdapter
        }
        passportAdapter.passwordList = passportList
        passportAdapter.onItemClicked = { user ->
            val bundle = bundleOf("entity" to user)
            findNavController().navigate(R.id.action_allFragment_to_detailFragment, bundle)
        }
        passportAdapter.onDeleteClicked = { user, pos ->
            AlertDialog.Builder(requireContext()).apply {
                setTitle("O'chirish")
                setIcon(R.drawable.ic_baseline_delete_outline_24)
                setMessage("Ma'lumot o'chirilsinmi ?")
                setPositiveButton("Xa") { di, _ ->
                    database.dao().deletePassword(user)
                    passportList.removeAt(pos)
                    passportAdapter.notifyItemRemoved(pos)
                    toast("Passport o'chirildi !")
                    di.dismiss()
                }
                setNeutralButton("Bekor qilish", null)
            }.create().show()
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu.findItem(R.id.search)
        val searchView = MenuItemCompat.getActionView(menuItem) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                filterAdapter(p0.toString())
                return true
            }
        })
    }

    private fun filterAdapter(text: String) {
        val list = mutableListOf<User>()
        for (user in passportList) {
            if (user.name.lowercase().contains(text.lowercase()) || user.lastName.lowercase()
                    .contains(text.lowercase())
            ) {
                list.add(user)
            }
        }
        passportAdapter.updateList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}