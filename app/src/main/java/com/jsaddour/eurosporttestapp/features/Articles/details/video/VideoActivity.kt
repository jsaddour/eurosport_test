package com.jsaddour.eurosporttestapp.features.Articles.details.video

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.jsaddour.eurosporttestapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoActivity : ComponentActivity() {
    private var mExoPlayer : ExoPlayer? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Player()
            }
        }


    @Composable
    fun Player() {

        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val mContext = LocalContext.current
            val mVideoUrl = requireNotNull(intent.getStringExtra(VIDEO_URL_KEY))

            mExoPlayer = remember(mContext) {
                ExoPlayer.Builder(mContext).build().apply {
                    val dataSourceFactory = DefaultDataSourceFactory(
                        mContext,
                        Util.getUserAgent(mContext, mContext.packageName)
                    )
                    playWhenReady = true
                    val mediaItem = MediaItem.fromUri(Uri.parse(mVideoUrl))
                    val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(mediaItem)
                    prepare(source)
                }
            }

            AndroidView(factory = { context ->
                PlayerView(context).apply {
                    player = mExoPlayer
                }
            })
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        mExoPlayer?.run {
            release()
        }
        mExoPlayer = null
    }

    companion object {

        fun intent(context: Context, url: String) =
            Intent(context, VideoActivity::class.java).apply {
                putExtra(VIDEO_URL_KEY, url)
            }

        private const val VIDEO_URL_KEY = "URL_KEY"
    }
}