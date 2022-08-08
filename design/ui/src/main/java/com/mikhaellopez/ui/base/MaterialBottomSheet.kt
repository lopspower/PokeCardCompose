package com.mikhaellopez.ui.base

import android.app.Activity
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.mikhaellopez.ui.theme.BaseTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//region Extensions
fun Activity.getViewGroupParent(): ViewGroup =
    findViewById(android.R.id.content)

fun Fragment.getViewGroupParent(): ViewGroup =
    requireActivity().getViewGroupParent()

fun Activity.showAsBottomSheet(
    isWrapped: Boolean = true,
    content: @Composable (() -> Unit) -> Unit
) {
    addContentToView(getViewGroupParent(), isWrapped, content)
}

fun Fragment.showAsBottomSheet(
    isWrapped: Boolean = true,
    content: @Composable (() -> Unit) -> Unit
) {
    addContentToView(getViewGroupParent(), isWrapped, content)
}

fun ViewGroup.showAsBottomSheet(
    isWrapped: Boolean = true,
    content: @Composable (() -> Unit) -> Unit
) {
    addContentToView(this, isWrapped, content)
}
//endregion

private fun addContentToView(
    viewGroup: ViewGroup,
    isWrapped: Boolean,
    content: @Composable (() -> Unit) -> Unit
) {
    viewGroup.addView(
        ComposeView(viewGroup.context).apply {
            setContent {
                BaseTheme {
                    MaterialBottomSheet(
                        composeView = this,
                        parent = viewGroup,
                        isWrapped = isWrapped,
                        content = content
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MaterialBottomSheet(
    composeView: ComposeView,
    parent: ViewGroup,
    isWrapped: Boolean,
    content: @Composable (() -> Unit) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        confirmStateChange = {
            it != ModalBottomSheetValue.HalfExpanded
        }
    )
    var isSheetOpened by remember { mutableStateOf(false) }
    ModalBottomSheetLayout(
        sheetBackgroundColor = Color.Transparent,
        sheetState = modalBottomSheetState,
        sheetContent = {
            if (isWrapped) {
                WrapperBottomSheet(coroutineScope, modalBottomSheetState) {
                    content {
                        hideBottomSheet(coroutineScope, modalBottomSheetState)
                    }
                }
            } else {
                content {
                    hideBottomSheet(coroutineScope, modalBottomSheetState)
                }
            }
        },
        content = {}
    )
    BackHandler {
        hideBottomSheet(coroutineScope, modalBottomSheetState)
    }
    // Take action based on hidden state
    LaunchedEffect(modalBottomSheetState.currentValue) {
        when (modalBottomSheetState.currentValue) {
            ModalBottomSheetValue.Hidden -> {
                when {
                    isSheetOpened -> parent.removeView(composeView)
                    else -> {
                        isSheetOpened = true
                        modalBottomSheetState.show()
                    }
                }
            }
            else -> {
                Log.i("BottomSheet", "Bottom sheet ${modalBottomSheetState.currentValue} state")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun WrapperBottomSheet(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
            .background(MaterialTheme.colors.surface)
    ) {
        Box(modifier = Modifier.padding(top = 35.dp)) {
            content()
        }
        Divider(
            color = MaterialTheme.colors.primary,
            thickness = 5.dp,
            modifier = Modifier
                .padding(top = 12.dp)
                .align(Alignment.TopCenter)
                .width(70.dp)
                .clip(RoundedCornerShape(50.dp))
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(22.dp)
        ) {
            Button(
                shape = CircleShape,
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                ),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                modifier = Modifier.size(28.dp),
                onClick = { hideBottomSheet(coroutineScope, modalBottomSheetState) }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    tint = Color.White,
                    contentDescription = "Close Bottom Sheet",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
private fun hideBottomSheet(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState
) {
    coroutineScope.launch {
        modalBottomSheetState.hide()
    }
}