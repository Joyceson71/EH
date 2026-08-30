package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SkillNodeEntity
import com.example.ui.components.CyberCard
import com.example.ui.components.CyberProgressBar
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.HackPathViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillTreeScreen(
    viewModel: HackPathViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val skills = uiState.skillNodes
    val completedCount = uiState.user?.completedDaysCount ?: 0

    val phases = listOf(
        1 to "Phase 1: Systems & Networks",
        2 to "Phase 2: OSINT & Reconnaissance",
        3 to "Phase 3: Exploitation & PrivEsc",
        4 to "Phase 4: Web Security & OWASP",
        5 to "Phase 5: Active Directory & Pivoting",
        6 to "Phase 6: Professional Capstone"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "SKILL GRAPH & CAPABILITIES",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    ),
                    color = ElectricViolet
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
            item {
                CyberCard(
                    borderColor = ElectricViolet.copy(alpha = 0.5f),
                    glowEffect = true
                ) {
                    Text(
                        text = "PENETRATION TESTING COMPETENCY MATRIX",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        ),
                        color = ElectricViolet
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Mastery is automatically unlocked and updated as you complete curriculum mission days and pass knowledge check quizzes.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMuted
                    )
                }
            }

            phases.forEach { (phaseNum, phaseTitle) ->
                val phaseSkills = skills.filter { it.phaseIndex == phaseNum }
                if (phaseSkills.isNotEmpty()) {
                    item {
                        Text(
                            text = phaseTitle.uppercase(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            ),
                            color = NeonMint,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    items(phaseSkills, key = { it.id }) { skill ->
                        val phaseStartDay = (phaseNum - 1) * 20 + 1
                        val phaseEndDay = phaseNum * 20
                        val completedInPhase = (phaseStartDay..phaseEndDay).count {
                            uiState.progressMap[it]?.isCompleted == true
                        }
                        val mastery = (completedInPhase.toFloat() / 20f).coerceIn(0f, 1f)
                        val isMastered = mastery >= 1.0f

                        CyberCard(
                            backgroundColor = SurfaceDark,
                            borderColor = if (isMastered) NeonMint else BorderDark
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            text = skill.name,
                                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                            color = TextPrimary
                                        )
                                        if (isMastered) {
                                            StatusBadge(text = "MASTERED", color = NeonMint)
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = skill.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextMuted
                                    )
                                }

                                Text(
                                    text = "${(mastery * 100).toInt()}%",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    ),
                                    color = if (isMastered) NeonMint else ElectricViolet
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            CyberProgressBar(progress = mastery, height = 6)
                        }
                    }
                }
            }
        }
    }
}
