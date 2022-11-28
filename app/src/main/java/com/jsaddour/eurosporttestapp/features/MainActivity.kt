package com.jsaddour.eurosporttestapp.features

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.jsaddour.eurosporttestapp.R
import com.jsaddour.eurosporttestapp.features.Articles.list.ArticleList
import com.jsaddour.eurosporttestapp.features.Articles.list.ArticlesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<ArticlesViewModel>()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = colorResource(R.color.dark_blue),
                            titleContentColor = Color.White
                        ),
                        title = {
                            Text(text = stringResource(R.string.title))
                        },
                    )
                }, content = { ArticleList(viewModel.viewState) })
        }
    }
}