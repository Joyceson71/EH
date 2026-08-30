package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BadgeEntity
import com.example.ui.components.CyberCard
import com.example.ui.components.CyberProgressBar
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.HackPathViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: HackPathViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val user = uiState.user
    val badges = uiState.badges
    var selectedBadge by remember { mutableStateOf<BadgeEntity?>(null) }

    if (selectedBadge != null) {
        val b = selectedBadge!!
        AlertDialog(
            onDismissRequest = { selectedBadge = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(
                        imageVector = if (b.isUnlocked) Icons.Default.EmojiEvents else Icons.Default.Lock,
                        contentDescription = null,
                        tint = if (b.isUnlocked) NeonMint else TextMuted
                    )
                    Text(text = b.title, color = TextPrimary)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = b.description, style = MaterialTheme.typography.bodyMedium, color = TextMuted)
                    StatusBadge(
                        text = if (b.isUnlocked) "UNLOCKED" else "LOCKED",
                        color = if (b.isUnlocked) NeonMint else ThreatOrange
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { selectedBadge = null },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonMint, contentColor = BackgroundDark)
                ) {
                    Text("DISMISS", fontFamily = FontFamily.Monospace)
                }
            },
            containerColor = SurfaceDark,
            shape = RoundedCornerShape(12.dp)
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "OPERATOR PROFILE",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    ),
                    color = NeonMint
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceDark)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp)
        ) {
            // Hacker ID Card
            item {
                CyberCard(
                    borderColor = NeonMint.copy(alpha = 0.6f),
                    glowEffect = true
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(SurfaceElevated)
                                .border(2.dp, NeonMint, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = "Avatar",
                                tint = NeonMint,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Column {
                            Text(
                                text = user?.username ?: "GhostSec",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "[ ${user?.rank ?: "Script Kiddie"} ]",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = NeonMint
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                StatusBadge(text = "LEVEL ${user?.level ?: 1}", color = ElectricViolet)
                                StatusBadge(text = "${user?.xp ?: 0} XP", color = NeonMint)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = BorderDark)
                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${user?.completedDaysCount ?: 0}",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = NeonMint
                            )
                            Text("Days Done", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${user?.streak ?: 1}d",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = ThreatOrange
                            )
                            Text("Streak", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${badges.count { it.isUnlocked }} / ${badges.size}",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = ElectricViolet
                            )
                            Text("Badges", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                        }
                    }
                }
            }

            // Badges Section
            item {
                Text(
                    text = "ACHIEVEMENT BADGES",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    ),
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(10.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    badges.chunked(2).forEach { rowBadges ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowBadges.forEach { badge ->
                                CyberCard(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { selectedBadge = badge },
                                    backgroundColor = if (badge.isUnlocked) SurfaceElevated else SurfaceDark,
                                    borderColor = if (badge.isUnlocked) NeonMint.copy(alpha = 0.5f) else BorderDark
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(36.dp)
                                                .clip(CircleShape)
                                                .background(if (badge.isUnlocked) NeonMint.copy(alpha = 0.15f) else BackgroundDark)
                                                .border(
                                                    1.dp,
                                                    if (badge.isUnlocked) NeonMint else BorderDark,
                                                    CircleShape
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = if (badge.isUnlocked) Icons.Default.EmojiEvents else Icons.Default.Lock,
                                                contentDescription = null,
                                                tint = if (badge.isUnlocked) NeonMint else TextMuted,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }

                                        Column {
                                            Text(
                                                text = badge.title,
                                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                                color = if (badge.isUnlocked) TextPrimary else TextMuted,
                                                maxLines = 1
                                            )
                                            Text(
                                                text = if (badge.isUnlocked) "Unlocked" else "Locked",
                                                style = MaterialTheme.typography.labelSmall.copy(fontFamily = FontFamily.Monospace),
                                                color = if (badge.isUnlocked) NeonMint else TextMuted
                                            )
                                        }
                                    }
                                }
                            }
                            if (rowBadges.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

            // Recent Activity Logs
            if (uiState.recentActivity.isNotEmpty()) {
                item {
                    Text(
                        text = "SECURITY OPERATIONS AUDIT LOG",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        ),
                        color = TextMuted
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    CyberCard {
                        uiState.recentActivity.forEachIndexed { idx, act ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = act.title,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = act.dateString,
                                        style = MaterialTheme.typography.labelSmall.copy(fontFamily = FontFamily.Monospace),
                                        color = TextMuted
                                    )
                                }
                                Text(
                                    text = "+${act.xpEarned} XP",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    ),
                                    color = ElectricViolet
                                )
                            }
                            if (idx < uiState.recentActivity.size - 1) {
                                HorizontalDivider(color = BorderDark, modifier = Modifier.padding(vertical = 6.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
