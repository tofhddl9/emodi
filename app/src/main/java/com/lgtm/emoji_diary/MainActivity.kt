package com.lgtm.emoji_diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lgtm.emoji_diary.databinding.ActivityMainBinding
import com.lgtm.emoji_diary.delegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding<ActivityMainBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}