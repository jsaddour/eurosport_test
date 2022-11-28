package com.jsaddour.eurosporttestapp.features.Articles.list

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import coil.compose.rememberAsyncImagePainter
import com.jsaddour.eurosporttestapp.R
import com.jsaddour.eurosporttestapp.features.Articles.details.video.VideoActivity
import com.jsaddour.eurosporttestapp.features.Articles.details.story.StoryActivity

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleList(viewState: LiveData<ViewState>) {
    val context = LocalContext.current
    val state = viewState.observeAsState()
    val loading = viewState.value?.loading ?: false

    val pullRefreshState = rememberPullRefreshState(loading, { viewState.value?.refresh?.invoke() })

    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize()
            .pullRefresh(pullRefreshState)

    ) {
        state.value?.items?.let { articles ->
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 64.dp),
            ) {
                items(articles) { item ->
                    when (item) {
                        is Item.Story -> {
                            StoryItem(item)
                        }
                        is Item.Video -> {
                            VideoItem(item)
                        }
                    }
                }
            }
        }
        viewState.value?.error?.getContent()?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
        PullRefreshIndicator(loading, pullRefreshState, Modifier.align(Alignment.TopCenter))

    }
}

@Composable
fun StoryItem(story: Item.Story) {
    val shape = RoundedCornerShape(8.dp)
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .clickable(
                onClick = {
                    startActivity(
                        context,
                        StoryActivity.intent(
                            context,
                            story.id
                        ), null
                    )
                }
            )
            .clip(shape)
            .background(Color.White)
            .padding(bottom = 8.dp),
    ) {
        ConstraintLayout() {
            val (photo, title, sport, details) = createRefs()


            Image(
                painter = rememberAsyncImagePainter(
                    model = story.image
                ),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(photo) { top.linkTo(parent.top) }
                    .fillMaxWidth()
                    .height(256.dp),
                contentScale = ContentScale.FillBounds
            )
            Text(
                modifier = Modifier
                    .constrainAs(title) { top.linkTo(photo.bottom, margin = 12.dp) }
                    .padding(
                        start = 12.dp,
                        end = 12.dp,
                    ),
                text = story.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Text(
                modifier = Modifier
                    .constrainAs(details) {
                        top.linkTo(title.bottom, margin = 8.dp)
                        start.linkTo(parent.start, margin = 12.dp)
                    },
                text = stringResource(R.string.by, story.author, story.date),
                fontSize = 10.sp,
                color = Color.Gray
            )

            Box(
                modifier = Modifier
                    .constrainAs(sport) {
                        bottom.linkTo(title.top)
                        start.linkTo(parent.start, margin = 12.dp)
                    }
                    .clip(shape)
                    .background(colorResource(R.color.dark_blue))
                    .padding(8.dp)
            ) {
                Text(
                    text = story.sport.uppercase(),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 12.sp
                )
            }

        }
    }
}

@Composable
fun VideoItem(video: Item.Video) {
    val shape = RoundedCornerShape(8.dp)
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .clickable(
                onClick = {
                    startActivity(
                        context,
                        VideoActivity.intent(
                            context,
                            video.url
                        ), null
                    )
                }
            )
            .clip(shape)
            .background(Color.White)
            .padding(bottom = 8.dp)
    ) {
        ConstraintLayout() {
            val (photo, title, sport, details, play) = createRefs()


            Image(
                painter = rememberAsyncImagePainter(
                    model = video.thumb
                ),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(photo) { top.linkTo(parent.top) }
                    .fillMaxWidth()
                    .height(256.dp),
                contentScale = ContentScale.FillBounds
            )
            Text(
                modifier = Modifier
                    .constrainAs(title) { top.linkTo(photo.bottom, margin = 12.dp) }
                    .padding(
                        start = 12.dp,
                        end = 12.dp,
                    ),
                text = video.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Text(
                modifier = Modifier
                    .constrainAs(details) {
                        top.linkTo(title.bottom, margin = 8.dp)
                        start.linkTo(parent.start, margin = 12.dp)
                    },
                text = stringResource(R.string.views, video.views),
                fontSize = 10.sp,
                color = Color.Gray
            )

            Image(painterResource(R.drawable.play), null, modifier = Modifier
                .constrainAs(play) {
                    bottom.linkTo(photo.bottom)
                    start.linkTo(photo.start)
                    top.linkTo(photo.top)
                    end.linkTo(photo.end)
                })

            Box(
                modifier = Modifier
                    .constrainAs(sport) {
                        bottom.linkTo(title.top)
                        start.linkTo(parent.start, margin = 12.dp)
                    }
                    .clip(shape)
                    .background(colorResource(R.color.dark_blue))
                    .padding(8.dp)
            ) {
                Text(
                    text = video.sport.uppercase(),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}
