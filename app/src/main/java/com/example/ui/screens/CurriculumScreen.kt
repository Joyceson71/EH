package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DayEntity
import com.example.ui.components.CyberCard
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.HackPathViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurriculumScreen(
    viewModel: HackPathViewModel,
    onNavigateToDay: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchInput by remember { mutableStateOf(uiState.searchQuery) }
    var showBookmarksOnly by remember { mutableStateOf(false) }

    val filteredDays = remember(uiState.days, uiState.selectedPhase, searchInput, showBookmarksOnly, uiState.bookmarks) {
        uiState.days.filter { day ->
            val matchesPhase = (uiState.selectedPhase == 0) || (day.phase == uiState.selectedPhase)
            val matchesSearch = searchInput.isBlank() ||
                    day.title.contains(searchInput, ignoreCase = true) ||
                    day.subtitle.contains(searchInput, ignoreCase = true) ||
                    day.concept.contains(searchInput, ignoreCase = true) ||
                    "Day ${day.dayNumber}".contains(searchInput, ignoreCase = true)
            val matchesBookmark = !showBookmarksOnly || uiState.bookmarks.contains(day.dayNumber)
            matchesPhase && matchesSearch && matchesBookmark
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        // Search & Filter Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceDark)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "120-DAY CURRICULUM",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                ),
                color = NeonMint
            )

            // Search Bar
            OutlinedTextField(
                value = searchInput,
                onValueChange = {
                    searchInput = it
                    viewModel.setSearchQuery(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("curriculum_search_input"),
                placeholder = {
                    Text(
                        text = "Search CVEs, Nmap, SQLi, Active Directory, Wireshark...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextMuted
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = TextMuted
                    )
                },
                trailingIcon = {
                    if (searchInput.isNotEmpty()) {
                        IconButton(onClick = {
                            searchInput = ""
                            viewModel.setSearchQuery("")
                        }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Clear", tint = TextMuted)
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonMint,
                    unfocusedBorderColor = BorderDark,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    cursorColor = NeonMint,
                    focusedContainerColor = BackgroundDark,
                    unfocusedContainerColor = BackgroundDark
                )
            )

            // Phase Filter Horizontal Scroll
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                item {
                    FilterChip(
                        selected = uiState.selectedPhase == 0 && !showBookmarksOnly,
                        onClick = {
                            showBookmarksOnly = false
                            viewModel.setPhaseFilter(0)
                        },
                        label = { Text("All Days (1-120)") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = NeonMint,
                            selectedLabelColor = BackgroundDark,
                            containerColor = BackgroundDark,
                            labelColor = TextPrimary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = BorderDark,
                            selectedBorderColor = NeonMint,
                            enabled = true,
                            selected = uiState.selectedPhase == 0 && !showBookmarksOnly
                        )
                    )
                }

                item {
                    FilterChip(
                        selected = showBookmarksOnly,
                        onClick = { showBookmarksOnly = !showBookmarksOnly },
                        label = {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(
                                    imageVector = if (showBookmarksOnly) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text("Saved (${uiState.bookmarks.size})")
                            }
                        },
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
                            selected = showBookmarksOnly
                        )
                    )
                }

                val phaseLabels = listOf(
                    1 to "P1: Foundations (1–20)",
                    2 to "P2: Recon (21–40)",
                    3 to "P3: Exploits (41–60)",
                    4 to "P4: Web Security (61–80)",
                    5 to "P5: Active Directory (81–100)",
                    6 to "P6: Capstone (101–120)"
                )

                items(phaseLabels) { (phaseNum, label) ->
                    FilterChip(
                        selected = uiState.selectedPhase == phaseNum && !showBookmarksOnly,
                        onClick = {
                            showBookmarksOnly = false
                            viewModel.setPhaseFilter(phaseNum)
                        },
                        label = { Text(label) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ElectricViolet,
                            selectedLabelColor = BackgroundDark,
                            containerColor = BackgroundDark,
                            labelColor = TextPrimary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = BorderDark,
                            selectedBorderColor = ElectricViolet,
                            enabled = true,
                            selected = uiState.selectedPhase == phaseNum && !showBookmarksOnly
                        )
                    )
                }
            }
        }

        // Days List
        if (filteredDays.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.SearchOff,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "No missions found",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary
                    )
                    Text(
                        text = "Try adjusting your search query or phase filter",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp)
            ) {
                items(filteredDays, key = { it.dayNumber }) { day ->
                    val isDone = uiState.progressMap[day.dayNumber]?.isCompleted == true
                    val isBookmarked = uiState.bookmarks.contains(day.dayNumber)

                    CyberCard(
                        modifier = Modifier
                            .clickable { onNavigateToDay(day.dayNumber) }
                            .testTag("curriculum_day_${day.dayNumber}"),
                        borderColor = if (isDone) NeonMint.copy(alpha = 0.5f) else BorderDark,
                        backgroundColor = SurfaceDark
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // Day Number Badge
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isDone) NeonMint.copy(alpha = 0.15f) else SurfaceElevated)
                                        .border(
                                            width = 1.dp,
                                            color = if (isDone) NeonMint else BorderDark,
                                            shape = RoundedCornerShape(8.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "DAY",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = FontFamily.Monospace
                                            ),
                                            color = if (isDone) NeonMint else TextMuted
                                        )
                                        Text(
                                            text = "${day.dayNumber}",
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                fontWeight = FontWeight.Black,
                                                fontFamily = FontFamily.Monospace
                                            ),
                                            color = if (isDone) NeonMint else TextPrimary
                                        )
                                    }
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        StatusBadge(
                                            text = "Phase ${day.phase}",
                                            color = when (day.phase) {
                                                1 -> NeonMint
                                                2 -> Color(0xFF00E5FF)
                                                3 -> ElectricViolet
                                                4 -> ThreatOrange
                                                5 -> Color(0xFFFF4081)
                                                else -> Color(0xFFFFD700)
                                            }
                                        )
                                        Text(
                                            text = "+${day.xpReward} XP",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontFamily = FontFamily.Monospace,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            color = ElectricViolet
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = day.title,
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = TextPrimary,
                                        maxLines = 1
                                    )

                                    Text(
                                        text = day.subtitle,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextMuted,
                                        maxLines = 1
                                    )
                                }
                            }

                            // Actions: Bookmark & Checkbox
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                IconButton(
                                    onClick = { viewModel.toggleBookmark(day.dayNumber) },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                        contentDescription = "Bookmark",
                                        tint = if (isBookmarked) ThreatOrange else TextMuted,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                IconButton(
                                    onClick = { viewModel.toggleDayCompletion(day.dayNumber) },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isDone) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                        contentDescription = "Complete",
                                        tint = if (isDone) NeonMint else BorderDark,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
