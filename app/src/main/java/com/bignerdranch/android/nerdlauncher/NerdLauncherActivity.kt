package com.bignerdranch.android.nerdlauncher

import android.content.pm.ResolveInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NerdLauncherActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nerd_launcher)

        recyclerView = findViewById(R.id.app_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    //=====

    private fun setupAdapter() {}

    //=====

    private inner class ActivityHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {}

    private inner class ActivityAdapter(val activities: List<ResolveInfo>)
        : RecyclerView.Adapter<ActivityHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHolder {
            val layoutInflater = LayoutInflater.from(parent.context)

            val view = layoutInflater.inflate(
                android.R.layout.simple_list_item_1,
                parent,
                false
            )

            return ActivityHolder(view)
        }

        override fun onBindViewHolder(holder: ActivityHolder, position: Int) {
            holder.bindActivity(activities[position])
        }

        override fun getItemCount(): Int = activities.size
    }
}
