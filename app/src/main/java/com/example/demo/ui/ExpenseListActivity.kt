package com.example.demo.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.adapter.AlertsAdapter
import com.example.demo.database.AlertViewModel
import com.example.demo.databinding.ActivityExpenseListBinding
import com.example.demo.models.Alert
import com.example.demo.models.AlertsResponseClass
import com.example.demo.util.ApiState
import com.example.demo.util.Utilities.showToast
import com.example.demo.viewmodel.AlertsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExpenseListActivity : AppCompatActivity(), AlertsAdapter.OnItemClickListener {
    private lateinit var binding: ActivityExpenseListBinding
    private lateinit var postAdapter: AlertsAdapter
    private val mainViewModel: AlertsListViewModel by viewModels()
    private lateinit var context: Context
    private val alertViewModel: AlertViewModel by viewModels()
    private var alertsList: List<Alert> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpenseListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intializeViews()
        intializeData()
    }

    fun intializeViews() {
        context = this
        postAdapter = AlertsAdapter(ArrayList(), this, context)
        binding.rvExpenseList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ExpenseListActivity)
            adapter = postAdapter
        }
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                v: RecyclerView,
                h: RecyclerView.ViewHolder,
                t: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(h: RecyclerView.ViewHolder, dir: Int) =
                deleteItemFromDb(h.adapterPosition)
        }).attachToRecyclerView(binding.rvExpenseList)
    }

    private fun deleteItemFromDb(adapterPosition: Int) {
        alertViewModel.delete(alertsList.get(adapterPosition))
        lifecycleScope.launch {
            delay(1000L)

        }

    }

    private fun callApi() {
        val mHashMap = HashMap<String, Any>()
        mHashMap["marine_id"] = "0"
        mHashMap["alert_module_type"] = "VesselsOperations"
        mHashMap["page"] = "1"
        mainViewModel.getExpenseListItem(mHashMap)
        lifecycleScope.launchWhenStarted {
            mainViewModel.postStateFlow.collect { it ->
                when (it) {
                    is ApiState.Loading -> {
                    }
                    is ApiState.Failure -> {
                        showToast(context, it.msg.toString())
                    }
                    is ApiState.Success<*> -> {
                        binding.rvExpenseList.isVisible = true
                        var result = it.result as AlertsResponseClass
                        alertsList = result.alerts
                        postAdapter.setData(result.alerts)
                        postAdapter.notifyDataSetChanged()
                        for (i in 0 until result.alerts.size) {
                            //saving to db
                            alertViewModel.insert(result.alerts.get(i))
                        }
                    }
                    is ApiState.Empty -> {

                    }
                }
            }
        }
    }

    fun intializeData() {
        alertViewModel.getList.observe(this, Observer {response->
            alertsList = (response as ArrayList<Alert>)
            if (alertsList.size>0){
                //load from db
                postAdapter.setData(response as ArrayList<Alert>)
                postAdapter.notifyDataSetChanged()
            }
            else{
                // get data from server
                callApi()
            }
        })
    }

    override fun onItemClicked(data: Alert) {

    }
}


