package de.aroio.application.nsd.flow

import android.net.nsd.NsdServiceInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

class DiscoveryAdapter : RecyclerView.Adapter<DiscoveryViewHolder>() {

    private fun <T> RecyclerView.Adapter<*>.autoNotify(old: List<T>, new: List<T>, compare: (T, T) -> Boolean) {
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    compare(old[oldItemPosition], new[newItemPosition])

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    old[oldItemPosition] == new[newItemPosition]

            override fun getOldListSize() = old.size

            override fun getNewListSize() = new.size
        }).dispatchUpdatesTo(this)
    }

    private var items: List<DiscoveryRecord> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { left, right -> left.name == right.name }
    }

    override fun onBindViewHolder(holder: DiscoveryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoveryViewHolder =
            LayoutInflater.from(parent.context)
                    .inflate(android.R.layout.simple_list_item_1, parent, false)
                    .let { DiscoveryViewHolder(it) }

    override fun getItemCount(): Int = items.size

    fun addItem(discoveryRecord: DiscoveryRecord) {
        items = items.plus(discoveryRecord)
    }

    fun removeItem(discoveryRecord: DiscoveryRecord) {
        items = items.minus(discoveryRecord)
    }

    fun clear() {
        items = listOf()
    }

}

class DiscoveryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val nameTextView: TextView = itemView.findViewById(android.R.id.text1)

    fun bind(discoveryRecord: DiscoveryRecord) {
        nameTextView.text = discoveryRecord.name
    }

}

data class DiscoveryRecord(
        val name: String
)

fun NsdServiceInfo.toDiscoveryRecord() = DiscoveryRecord(serviceName)