package com.example.passwordapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.MenuItemCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.passwordapp.R
import com.example.passwordapp.adapter.PassportAdapter
import com.example.passwordapp.database.PassportDatabase
import com.example.passwordapp.database.User
import com.example.passwordapp.databinding.FragmentAllBinding
import com.example.passwordapp.util.toast

class AllFragment : Fragment() {

    private var _binding: FragmentAllBinding? = null
    private val binding get() = _binding!!
    private val database by lazy { PassportDatabase.invoke(requireContext()) }
    private lateinit var passportAdapter: PassportAdapter
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
        passportAdapter = PassportAdapter()
        passportList = database.dao().getAllPassport() as MutableList<User>
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = passportAdapter
        }
        passportAdapter.passportList = passportList
        passportAdapter.onItemClicked = {
            val bundle = bundleOf("entity" to it)
            findNavController().navigate(R.id.action_allFragment_to_detailFragment, bundle)
        }
        passportAdapter.onDeleteClicked = { entity, pos ->
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Delete")
                setIcon(R.drawable.ic_baseline_delete_outline_24)
                setMessage("Delete this item?")
                setPositiveButton("Yes") { di, _ ->
                    database.dao().deletePassport(entity)
                    passportList.removeAt(pos)
                    passportAdapter.notifyItemRemoved(pos)
                    toast("Passport deleted!")
                    di.dismiss()
                }
                setNeutralButton("Cancel", null)
            }.create().show()
        }
        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu.findItem(R.id.search)
        val searchView = MenuItemCompat.getActionView(menuItem) as SearchView
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
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
        for (entity in passportList) {
            if (entity.name.lowercase().contains(text.lowercase()) || entity.lastName.lowercase()
                    .contains(text.lowercase())
            ) {
                list.add(entity)
            }
        }
        passportAdapter.updateList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}