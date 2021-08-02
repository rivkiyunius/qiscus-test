package com.rivki.samplechatsdk.ui.contacts

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivki.samplechatsdk.R
import com.rivki.samplechatsdk.base.BaseActivity
import com.rivki.samplechatsdk.base.DiffCallback
import com.rivki.samplechatsdk.databinding.ActivityContactBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ContactActivity: BaseActivity() {
    @Inject lateinit var diffCallback: DiffCallback
    private val viewModel: ContactViewModel by viewModels()
    private val contactBinding by lazy { ActivityContactBinding.inflate(layoutInflater) }
    private lateinit var layoutManagers: LinearLayoutManager
    private lateinit var contactAdapter: ContactAdapter


    override fun onViewReady(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_contact)
        setContentView(contactBinding.root)
        viewModel.fetchContact()
        layoutManagers = LinearLayoutManager(this)
        contactAdapter = ContactAdapter(diffCallback)
        with(contactBinding.rvChats){
            layoutManager = layoutManagers
            adapter = contactAdapter
        }
    }

    override fun observeData() {
        viewModel.getContact.onResult {
            contactAdapter.setMovie(it)
        }
    }
}