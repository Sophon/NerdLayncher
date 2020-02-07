package com.bignerdranch.android.nerdlauncher

import android.content.Intent
import android.content.pm.ResolveInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NerdLauncherActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nerd_launcher)

        recyclerView = findViewById(R.id.app_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        setupAdapter()
    }

    //=====

    private fun setupAdapter() {
        val startupIntentFilter = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val activities = packageManager.queryIntentActivities(startupIntentFilter, 0)
        activities.sortWith(Comparator { act1, act2 ->
            String.CASE_INSENSITIVE_ORDER.compare(
                act1.loadLabel(packageManager).toString(),
                act2.loadLabel(packageManager).toString()
            )
        })

        recyclerView.adapter = ActivityAdapter(activities)
    }

    //=====

    private inner class ActivityHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val nameTextView = itemView as TextView
        private lateinit var resolveInfo: ResolveInfo

        init {
            nameTextView.setOnClickListener(this)
        }

        fun bindActivity(resolveInfo: ResolveInfo) {
            this.resolveInfo = resolveInfo
            val packageManager = itemView.context.packageManager
            nameTextView.text = resolveInfo.loadLabel(packageManager).toString()
        }

        override fun onClick(view: View) {
            val activityInfo = resolveInfo.activityInfo

            val intent = Intent(Intent.ACTION_MAIN).apply {
                setClassName(
                    activityInfo.packageName,
                    activityInfo.name
                )
            }

            val context = view.context

            context.startActivity(intent)
        }
    }

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
