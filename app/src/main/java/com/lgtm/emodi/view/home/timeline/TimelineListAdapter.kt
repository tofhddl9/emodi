package com.lgtm.emodi.view.home.timeline

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lgtm.emodi.R
import com.lgtm.emodi.data.Diary
import com.lgtm.emodi.data.SimpleDate
import com.lgtm.emodi.data.asDateFormat
import com.lgtm.emodi.data.asTimeFormat
import com.lgtm.emodi.data.isSaturday
import com.lgtm.emodi.data.isSunday
import com.lgtm.emodi.data.timeInMillisToSimpleDate
import com.lgtm.emodi.databinding.ItemTimelineBinding
import com.lgtm.emodi.utils.CalendarUtil
import com.lgtm.emodi.utils.EmojiStore

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
            binding.emojiView.animation = AnimationUtils.loadAnimation(binding.emojiView.context, R.anim.fade_transition_animation)
            binding.container.animation = AnimationUtils.loadAnimation(binding.container.context, R.anim.fade_scale_animation)

            binding.container.setOnClickListener {
                itemClickListener?.invoke(diary.id)
            }

            binding.titleView.text = diary.title

            val simpleDate = timeInMillisToSimpleDate(diary.date)
            binding.dateView.text = simpleDate.asDateFormat()
            binding.timeView.text = simpleDate.asTimeFormat()

            binding.dayView.text = simpleDate.day.toString()
            binding.dayView.setTextColor(simpleDate.getDateColorInt(binding.dayView.context))

            binding.dayOfWeekView.text = CalendarUtil.dayOfWeekInEnglish(simpleDate.dayOfWeek)
            binding.dayOfWeekView.setTextColor(simpleDate.getDateColorInt(binding.dayView.context))

            binding.emojiView.setImageDrawable(EmojiStore.getEmojiDrawable(binding.emojiView.context, diary.emojiId))
        }

    }
}

@ColorInt
private fun SimpleDate.getDateColorInt(context: Context): Int {
    val colorString = when {
        isSaturday() -> {
            context.resources.getString(0 + R.color.saturday_blue)
        }
        isSunday() -> {
            context.resources.getString(0 + R.color.sunday_red)
        }
        else -> {
            context.resources.getString(0 + R.color.grey_black)
        }
    }

    return Color.parseColor(colorString)
}

private class TimelineDiffCallback : DiffUtil.ItemCallback<Diary>() {

    override fun areItemsTheSame(oldItem: Diary, newItem: Diary): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Diary, newItem: Diary): Boolean {
        return oldItem == newItem
    }

}