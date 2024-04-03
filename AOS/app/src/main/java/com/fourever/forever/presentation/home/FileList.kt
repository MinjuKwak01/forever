package com.fourever.forever.presentation.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fourever.forever.presentation.component.File

private const val SPACE_BETWEEN_FILES = 12
@Composable
fun FileList(
    fileList: List<String>?,
    onFileClick: (Int) -> Unit,
    loadMoreFile: () -> Unit
) {
    val fileListState = rememberLazyListState()

    if (fileList != null) {
        LazyColumn(state = fileListState) {
            items(fileList) { fileName ->
                File(fileName)
                Spacer(modifier = Modifier.size(SPACE_BETWEEN_FILES.dp))
            }
        }
    }

    fileListState.OnBottomReached(
        loadMoreFile = loadMoreFile
    )
}

@Composable
private fun LazyListState.OnBottomReached(
    loadMoreFile: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull() ?: return@derivedStateOf true

            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect {
                if (it) loadMoreFile()
            }
    }
}

@Preview(showBackground = true)
@Composable
private fun FileListPreview() {
    MaterialTheme {
        FileList(
            fileList = listOf("프로그래밍_언어론_ch03az", "프로그래밍_언어론_ch03az", "프로그래밍_언어론_ch03az", "프로그래밍_언어론_ch03az"),
            onFileClick = {},
            loadMoreFile = {}
        )
    }
}
