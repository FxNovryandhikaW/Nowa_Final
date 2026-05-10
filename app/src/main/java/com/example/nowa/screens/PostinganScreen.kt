package com.example.kelompok_nokonteks_tam_nowa.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kelompok_nokonteks_tam_nowa.viewmodel.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostinganScreen(viewModel: PostViewModel = viewModel()) {
    val posts = viewModel.posts
    val isLoading = viewModel.isLoading

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daftar Postingan") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(padding)) {
                items(posts) { post ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = post.title, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = post.body, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
