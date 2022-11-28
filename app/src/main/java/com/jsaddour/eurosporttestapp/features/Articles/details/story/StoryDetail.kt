package com.jsaddour.eurosporttestapp.features.Articles.details.story

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.LiveData
import coil.compose.rememberAsyncImagePainter
import com.jsaddour.eurosporttestapp.R

@Composable
fun StoryDetail(viewState: LiveData<ViewState>) {
    val state = viewState.observeAsState()
    val shape = RoundedCornerShape(8.dp)

        state.value?.story?.let { story ->
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
            ) {
                ConstraintLayout() {
                    val (photo, title, sport, details, content) = createRefs()
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

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .constrainAs(content) {
                                top.linkTo(details.bottom, margin = 12.dp)
                                start.linkTo(parent.start, margin = 12.dp)
                                end.linkTo(parent.end, margin = 12.dp)
                            },
                        text = story.teaser,
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }

