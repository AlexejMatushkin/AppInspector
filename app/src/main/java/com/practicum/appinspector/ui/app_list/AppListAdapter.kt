package com.practicum.appinspector.ui.app_list


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.appinspector.databinding.ItemAppBinding
import com.practicum.appinspector.model.InstalledApp

class AppListAdapter(
    private val onClick: (InstalledApp) -> Unit
) : RecyclerView.Adapter<AppListAdapter.Holder>() {

    private var items = listOf<InstalledApp>()

    @SuppressLint("NotifyDataSetChanged")
    fun submit(list: List<InstalledApp>) {
        items = list
        notifyDataSetChanged()
    }

    class Holder(val binding: ItemAppBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemAppBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items[position]
        holder.binding.appName.text = item.appName
        holder.binding.packageName.text = item.packageName

        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount(): Int = items.size
}
