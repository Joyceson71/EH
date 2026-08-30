package com.example.ui.screens

import androidx.compose.animation.core.*
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DayEntity
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.HackPathViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: HackPathViewModel,
    onNavigateToDay: (Int) -> Unit,
    onNavigateToCurriculum: () -> Unit,
    onNavigateToSkills: () -> Unit,
    onNavigateToResources: () -> Unit,
    onNavigateToTerminal: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val user = uiState.user
    val completedCount = user?.completedDaysCount ?: 0
    val totalProgress = completedCount / 120f
    val currentDayNum = user?.currentDay ?: 1
    val currentDay = uiState.days.find { it.dayNumber == currentDayNum } ?: uiState.days.firstOrNull()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp)
    ) {
        // 1. User Header & Hacker Terminal Bar
        item {
            CyberCard(
                borderColor = BorderDark,
                backgroundColor = SurfaceDark
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(SurfaceElevated)
                                .border(1.5.dp, NeonMint, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Terminal,
                                contentDescription = "Terminal Icon",
                                tint = NeonMint,
                                modifier = Modifier.size(26.dp)
                            )
                        }

                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = user?.username ?: "GhostSec",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = TextPrimary
                                )
                                StatusBadge(
                                    text = "LVL ${user?.level ?: 1}",
                                    color = ElectricViolet
                                )
                            }
                            Text(
                                text = user?.rank ?: "Script Kiddie",
                                style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                                color = NeonMint
                            )
                        }
                    }

                    // Streak Badge
                    Surface(
                        color = ThreatOrange.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ThreatOrange.copy(alpha = 0.4f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = "Streak",
                                tint = ThreatOrange,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "${user?.streak ?: 1}d",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = ThreatOrange
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // XP Bar
                val xpCurrent = user?.xp ?: 0
                val xpTarget = ((user?.level ?: 1)) * 500
                val xpProgress = (xpCurrent % 500) / 500f

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "XP: $xpCurrent / $xpTarget",
                        style = MaterialTheme.typography.labelSmall.copy(fontFamily = FontFamily.Monospace),
                        color = TextMuted
                    )
                    Text(
                        text = "${(xpProgress * 100).toInt()}% to next rank",
                        style = MaterialTheme.typography.labelSmall.copy(fontFamily = FontFamily.Monospace),
                        color = ElectricViolet
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))
                CyberProgressBar(progress = xpProgress, height = 6)
            }
        }

        // 2. Active Target / Hero Banner
        item {
            CyberCard(
                borderColor = NeonMint.copy(alpha = 0.6f),
                backgroundColor = SurfaceDark,
                glowEffect = true
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusBadge(
                        text = "CURRENT TARGET • DAY $currentDayNum",
                        color = NeonMint,
                        icon = Icons.Default.Flag
                    )
                    Text(
                        text = "Phase ${currentDay?.phase ?: 1}",
                        style = MaterialTheme.typography.labelSmall.copy(fontFamily = FontFamily.Monospace),
                        color = TextMuted
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = currentDay?.title ?: "Linux Command Line Essentials",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp
                    ),
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = currentDay?.subtitle ?: "Master the fundamental navigation, pipes, and file manipulation.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextMuted,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onNavigateToDay(currentDayNum) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("continue_day_button"),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NeonMint,
                        contentColor = BackgroundDark
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "CONTINUE MISSION (DAY $currentDayNum)",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    )
                }
            }
        }

        // 3. 120-Day Overall Program Metrics
        item {
            CyberCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "120-DAY CURRICULUM PROGRESS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            ),
                            color = TextMuted
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$completedCount / 120 Days Completed",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = TextPrimary
                        )
                    }

                    Text(
                        text = "${(totalProgress * 100).toInt()}%",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace
                        ),
                        color = NeonMint
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                CyberProgressBar(progress = totalProgress, height = 10)
            }
        }

        // 4. Quick Action Sandbox Hub
        item {
            Text(
                text = "TACTICAL TOOLS & SANDBOX",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                ),
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Terminal Sandbox
                CyberCard(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigateToTerminal() }
                        .testTag("terminal_sandbox_card"),
                    backgroundColor = SurfaceElevated
                ) {
                    Icon(
                        imageVector = Icons.Default.Terminal,
                        contentDescription = "Terminal",
                        tint = NeonMint,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Terminal Sandbox",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = TextPrimary
                    )
                    Text(
                        text = "Interactive CLI",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                }

                // Skill Tree
                CyberCard(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigateToSkills() }
                        .testTag("skill_tree_card"),
                    backgroundColor = SurfaceElevated
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountTree,
                        contentDescription = "Skills",
                        tint = ElectricViolet,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Skill Matrix",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = TextPrimary
                    )
                    Text(
                        text = "24 Capabilities",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                }

                // Resource Library
                CyberCard(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigateToResources() }
                        .testTag("resources_card"),
                    backgroundColor = SurfaceElevated
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = "Library",
                        tint = ThreatOrange,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Cheatsheets",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = TextPrimary
                    )
                    Text(
                        text = "Labs & Tools",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                }
            }
        }

        // 5. Phase Breakdown Grid
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CURRICULUM PHASES (1-6)",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    ),
                    color = TextMuted
                )

                TextButton(onClick = onNavigateToCurriculum) {
                    Text(
                        text = "VIEW ALL 120 DAYS →",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        ),
                        color = NeonMint
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            val phases = listOf(
                Triple(1, "Phase 1: Foundations (Days 1–20)", "Linux, Bash, Python & Networking"),
                Triple(2, "Phase 2: Reconnaissance (Days 21–40)", "OSINT, Nmap, Shodan & Subdomains"),
                Triple(3, "Phase 3: Exploitation (Days 41–60)", "Metasploit, Reverse Shells, SQLi, Buffer Overflows"),
                Triple(4, "Phase 4: Web Security (Days 61–80)", "OWASP Top 10, Burp Suite, IDOR, SSRF, XXE"),
                Triple(5, "Phase 5: Active Directory (Days 81–100)", "Kerberoasting, BloodHound, Mimikatz, Pivoting"),
                Triple(6, "Phase 6: Capstone & Certs (Days 101–120)", "Report Writing, Exam Labs, Interview Prep")
            )

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                phases.forEach { (phaseNum, title, desc) ->
                    val phaseDays = uiState.days.filter { it.phase == phaseNum }
                    val phaseDone = phaseDays.count { uiState.progressMap[it.dayNumber]?.isCompleted == true }
                    val phaseTotal = phaseDays.size.coerceAtLeast(20)
                    val phaseProg = if (phaseTotal > 0) phaseDone.toFloat() / phaseTotal else 0f

                    CyberCard(
                        modifier = Modifier.clickable {
                            viewModel.setPhaseFilter(phaseNum)
                            onNavigateToCurriculum()
                        },
                        backgroundColor = SurfaceElevated
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = desc,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextMuted,
                                    maxLines = 1
                                )
                            }

                            StatusBadge(
                                text = "$phaseDone / $phaseTotal",
                                color = if (phaseDone == phaseTotal && phaseTotal > 0) NeonMint else ElectricViolet
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        CyberProgressBar(progress = phaseProg, height = 4)
                    }
                }
            }
        }

        // 6. Recent Activity Log
        if (uiState.recentActivity.isNotEmpty()) {
            item {
                Text(
                    text = "RECENT OPS LOG",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    ),
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(8.dp))

                CyberCard {
                    uiState.recentActivity.take(4).forEachIndexed { index, activity ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = NeonMint,
                                    modifier = Modifier.size(14.dp)
                                )
                                Column {
                                    Text(
                                        text = activity.title,
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = activity.dateString,
                                        style = MaterialTheme.typography.labelSmall.copy(fontFamily = FontFamily.Monospace),
                                        color = TextMuted
                                    )
                                }
                            }

                            Text(
                                text = "+${activity.xpEarned} XP",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = ElectricViolet
                            )
                        }
                        if (index < uiState.recentActivity.take(4).size - 1) {
                            HorizontalDivider(color = BorderDark, modifier = Modifier.padding(vertical = 6.dp))
                        }
                    }
                }
            }
        }
    }
}
