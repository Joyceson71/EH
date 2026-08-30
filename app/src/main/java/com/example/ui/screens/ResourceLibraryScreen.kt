package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CyberCard
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.HackPathViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourceLibraryScreen(
    viewModel: HackPathViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    var searchInput by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf("All", "Platform", "Web Pentest", "Cheatsheet", "PrivEsc", "Wordlists", "Tool")

    val filteredResources = remember(uiState.resources, searchInput, selectedCategory) {
        uiState.resources.filter { res ->
            val matchesCat = (selectedCategory == "All") || (res.category.equals(selectedCategory, ignoreCase = true))
            val matchesSearch = searchInput.isBlank() ||
                    res.title.contains(searchInput, ignoreCase = true) ||
                    res.description.contains(searchInput, ignoreCase = true) ||
                    res.tag.contains(searchInput, ignoreCase = true)
            matchesCat && matchesSearch
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "RESOURCE ARSENAL",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    ),
                    color = ThreatOrange
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceDark)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceDark)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = searchInput,
                onValueChange = { searchInput = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("resource_search_input"),
                placeholder = {
                    Text(
                        text = "Search cheatsheets, SecLists, PortSwigger, tools...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextMuted
                    )
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = TextMuted)
                },
                trailingIcon = {
                    if (searchInput.isNotEmpty()) {
                        IconButton(onClick = { searchInput = "" }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Clear", tint = TextMuted)
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ThreatOrange,
                    unfocusedBorderColor = BorderDark,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    cursorColor = ThreatOrange,
                    focusedContainerColor = BackgroundDark,
                    unfocusedContainerColor = BackgroundDark
                )
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(categories) { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ThreatOrange,
                            selectedLabelColor = BackgroundDark,
                            containerColor = BackgroundDark,
                            labelColor = TextPrimary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = BorderDark,
                            selectedBorderColor = ThreatOrange,
                            enabled = true,
                            selected = selectedCategory == cat
                        )
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp)
        ) {
            items(filteredResources, key = { it.id }) { res ->
                CyberCard(
                    backgroundColor = SurfaceDark,
                    borderColor = if (res.isBookmarked) ThreatOrange.copy(alpha = 0.5f) else BorderDark
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                StatusBadge(text = res.category, color = ThreatOrange)
                                if (res.tag.isNotBlank()) {
                                    StatusBadge(text = res.tag, color = NeonMint)
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = res.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = TextPrimary
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = res.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMuted
                            )
                        }

                        IconButton(onClick = { viewModel.toggleResourceBookmark(res) }) {
                            Icon(
                                imageVector = if (res.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Bookmark",
                                tint = if (res.isBookmarked) ThreatOrange else TextMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(res.url))
                            context.startActivity(intent)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SurfaceElevated,
                            contentColor = TextPrimary
                        )
                    ) {
                        Icon(imageVector = Icons.Default.Launch, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ACCESS ARSENAL LINK",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                    }
                }
            }
        }
    }
}
