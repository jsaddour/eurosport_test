package com.jsaddour.eurosporttestapp.features.Articles.details.story

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.jsaddour.eurosporttestapp.R
import com.jsaddour.eurosporttestapp.features.Articles.list.ArticlesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoryActivity : ComponentActivity() {
    private val viewModel by viewModels<StoryViewModel>()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoryDetail(viewModel.viewState)
        }
    }


    companion object {


        fun intent(context: Context, storyId: Int) =
            Intent(context, StoryActivity::class.java).apply {
                putExtra(STORY_ID_KEY, storyId)
            }

        const val STORY_ID_KEY = "STORY_ID_KEY"

    }
}