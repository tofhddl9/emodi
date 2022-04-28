package com.lgtm.emoji_diary.view.home.timeline

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lgtm.emoji_diary.data.Diary
import com.lgtm.emoji_diary.databinding.ItemTimelineBinding

class TimelineListAdapter(
    private val itemClickListener: ((Long) -> Unit)? = null
) : ListAdapter<Diary, TimelineListAdapter.TimelineVH>(TimelineDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTimelineBinding.inflate(layoutInflater, parent, false)

        return TimelineVH(binding)
    }

    override fun onBindViewHolder(holder: TimelineVH, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }


    class TimelineVH(
        private val binding: ItemTimelineBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(diary: Diary, itemClickListener: ((Long) -> Unit)?) {
            binding.root.setOnClickListener {
                itemClickListener?.invoke(diary.id)
            }

            binding.titleView.text = diary.title
        }

    }
}

private class TimelineDiffCallback : DiffUtil.ItemCallback<Diary>() {

    override fun areItemsTheSame(oldItem: Diary, newItem: Diary): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Diary, newItem: Diary): Boolean {
        return oldItem == newItem
    }

}