package com.lgtm.emoji_diary.widgets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lgtm.emoji_diary.databinding.ItemEmojiPickerBinding
import com.lgtm.emoji_diary.utils.EmojiInfo
import com.lgtm.emoji_diary.utils.EmojiStore

class EmojiPickerAdapter(
    private val itemClickListener: ((Long) -> Unit)? = null
) : RecyclerView.Adapter<EmojiPickerAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemEmojiPickerBinding.inflate(layoutInflater, parent, false)

        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(EmojiStore.emojiDrawableResMap[position], itemClickListener)
    }

    class VH(
        private val binding: ItemEmojiPickerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(emoji: EmojiInfo?, itemClickListener: ((Long) -> Unit)?) {
            emoji ?: return

            binding.emojiView.setImageDrawable(EmojiStore.getEmojiDrawable(binding.root.context, emoji.id))
            binding.emojiView.setOnClickListener {
                itemClickListener?.invoke(emoji.id)
            }
        }

    }

    override fun getItemCount(): Int = EmojiStore.emojiDrawableResMap.size
}
