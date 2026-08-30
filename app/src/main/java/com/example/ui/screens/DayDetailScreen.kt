package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DayEntity
import com.example.data.model.QuizQuestionEntity
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.HackPathViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayDetailScreen(
    dayId: Int,
    viewModel: HackPathViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToDay: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(dayId) {
        viewModel.loadDayDetail(dayId)
    }

    val day = uiState.currentDayDetail ?: uiState.days.find { it.dayNumber == dayId }
    val progress = uiState.currentDayProgress ?: uiState.progressMap[dayId]
    val isCompleted = progress?.isCompleted == true
    val isBookmarked = uiState.bookmarks.contains(dayId)
    val questions = uiState.currentDayQuestions

    var userNoteText by remember(uiState.currentDayNote) {
        mutableStateOf(uiState.currentDayNote?.content ?: "")
    }

    // Quiz interaction states
    var selectedAnswers by remember { mutableStateOf<Map<Long, Int>>(emptyMap()) }
    var submittedQuizzes by remember { mutableStateOf<Set<Long>>(emptySet()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "DAY $dayId / 120",
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
                actions = {
                    IconButton(onClick = { viewModel.toggleBookmark(dayId) }) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) ThreatOrange else TextMuted
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceDark)
            )
        },
        bottomBar = {
            Surface(
                color = SurfaceDark,
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderDark)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (dayId > 1) {
                        OutlinedButton(
                            onClick = { onNavigateToDay(dayId - 1) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderDark)
                        ) {
                            Text("← PREV", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                        }
                    }

                    Button(
                        onClick = {
                            viewModel.toggleDayCompletion(dayId)
                            val status = if (!isCompleted) "Completed! +${day?.xpReward ?: 50} XP" else "Marked incomplete"
                            Toast.makeText(context, status, Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .weight(2f)
                            .height(44.dp)
                            .testTag("day_complete_toggle_button"),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isCompleted) ElectricViolet else NeonMint,
                            contentColor = BackgroundDark
                        )
                    ) {
                        Icon(
                            imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isCompleted) "MISSION COMPLETE" else "MARK AS COMPLETE",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                    }

                    if (dayId < 120) {
                        OutlinedButton(
                            onClick = { onNavigateToDay(dayId + 1) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderDark)
                        ) {
                            Text("NEXT →", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        if (day == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = NeonMint)
            }
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(BackgroundDark)
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp)
            ) {
                // Header Banner Card
                item {
                    CyberCard(
                        borderColor = if (isCompleted) NeonMint.copy(alpha = 0.6f) else BorderDark,
                        glowEffect = isCompleted
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            StatusBadge(
                                text = "PHASE ${day.phase}",
                                color = when (day.phase) {
                                    1 -> NeonMint
                                    2 -> Color(0xFF00E5FF)
                                    3 -> ElectricViolet
                                    4 -> ThreatOrange
                                    5 -> Color(0xFFFF4081)
                                    else -> Color(0xFFFFD700)
                                }
                            )

                            StatusBadge(
                                text = "+${day.xpReward} XP",
                                color = ElectricViolet
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = day.title,
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            color = TextPrimary
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = day.subtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextMuted
                        )
                    }
                }

                // Daily Tasks Interactive Checklist
                item {
                    CyberCard {
                        Text(
                            text = "MISSION OBJECTIVES",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            ),
                            color = TextMuted
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        val tasks = listOf(
                            Triple("1. Master Theory & Concepts", progress?.theoryDone ?: false) {
                                viewModel.updateSubtask(dayId, theory = !(progress?.theoryDone ?: false))
                            },
                            Triple("2. Watch Curated Video Lecture", progress?.videoDone ?: false) {
                                viewModel.updateSubtask(dayId, video = !(progress?.videoDone ?: false))
                            },
                            Triple("3. Read Essential Documentation", progress?.readingDone ?: false) {
                                viewModel.updateSubtask(dayId, reading = !(progress?.readingDone ?: false))
                            },
                            Triple("4. Solve Hands-on Lab Challenge", progress?.labDone ?: false) {
                                viewModel.updateSubtask(dayId, lab = !(progress?.labDone ?: false))
                            },
                            Triple("5. Pass Daily Knowledge Check", progress?.quizDone ?: false) {
                                viewModel.updateSubtask(dayId, quiz = !(progress?.quizDone ?: false))
                            }
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            tasks.forEach { (taskTitle, isDone, onToggle) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(6.dp))
                                        .clickable { onToggle() }
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isDone) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                        contentDescription = null,
                                        tint = if (isDone) NeonMint else TextMuted,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = taskTitle,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = if (isDone) FontWeight.SemiBold else FontWeight.Normal
                                        ),
                                        color = if (isDone) TextPrimary else TextMuted
                                    )
                                }
                            }
                        }
                    }
                }

                // 1. Core Concept & Theory
                item {
                    CyberCard {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Psychology,
                                contentDescription = null,
                                tint = NeonMint,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "CORE THEORY & ARCHITECTURE",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = TextPrimary
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = day.concept,
                            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                            color = TextPrimary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Key Takeaways Callout Box
                        Surface(
                            color = SurfaceElevated,
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, ElectricViolet.copy(alpha = 0.4f))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "KEY TAKEAWAYS",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = ElectricViolet
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = day.keyTakeaways,
                                    style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp),
                                    color = TextPrimary
                                )
                            }
                        }
                    }
                }

                // 2. Terminal Commands & Cheatsheet
                if (day.commandsCode.isNotBlank()) {
                    item {
                        CyberCard {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Code,
                                    contentDescription = null,
                                    tint = NeonMint,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "TERMINAL COMMANDS & SYNTAX",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    ),
                                    color = TextPrimary
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            TerminalCodeBlock(code = day.commandsCode)
                        }
                    }
                }

                // 3. Curated Video Lecture
                item {
                    CyberCard {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayCircle,
                                contentDescription = null,
                                tint = ThreatOrange,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "CURATED VIDEO LECTURE",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = TextPrimary
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = day.videoTitle,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = TextPrimary
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            StatusBadge(text = day.videoChannel, color = TextMuted)
                            StatusBadge(text = day.videoDuration, color = ThreatOrange)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(day.videoUrl))
                                context.startActivity(intent)
                                viewModel.updateSubtask(dayId, video = true)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SurfaceElevated,
                                contentColor = TextPrimary
                            )
                        ) {
                            Icon(imageVector = Icons.Default.Launch, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("WATCH VIDEO LECTURE", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // 4. Essential Reading
                item {
                    CyberCard {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = Color(0xFF00E5FF),
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "ESSENTIAL DOCUMENTATION",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = TextPrimary
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = day.readTitle,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = TextPrimary
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        StatusBadge(text = day.readSource, color = Color(0xFF00E5FF))

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(day.readUrl))
                                context.startActivity(intent)
                                viewModel.updateSubtask(dayId, reading = true)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SurfaceElevated,
                                contentColor = TextPrimary
                            )
                        ) {
                            Icon(imageVector = Icons.Default.OpenInBrowser, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("OPEN DOCUMENTATION", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // 5. Hands-on Lab Challenge
                item {
                    CyberCard(
                        borderColor = NeonMint.copy(alpha = 0.5f),
                        glowEffect = true
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Biotech,
                                contentDescription = null,
                                tint = NeonMint,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "HANDS-ON LAB CHALLENGE",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = TextPrimary
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = day.labTitle,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = TextPrimary
                            )
                            StatusBadge(text = day.labPlatform, color = NeonMint)
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = day.labDescription,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextMuted
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(day.labUrl))
                                context.startActivity(intent)
                                viewModel.updateSubtask(dayId, lab = true)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NeonMint,
                                contentColor = BackgroundDark
                            )
                        ) {
                            Icon(imageVector = Icons.Default.RocketLaunch, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("LAUNCH HANDS-ON LAB", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // 6. Interactive Daily Quiz
                if (questions.isNotEmpty()) {
                    item {
                        CyberCard {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Quiz,
                                    contentDescription = null,
                                    tint = ElectricViolet,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "DAILY KNOWLEDGE CHECK",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    ),
                                    color = TextPrimary
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            questions.forEach { q ->
                                val selected = selectedAnswers[q.id]
                                val isSubmitted = submittedQuizzes.contains(q.id)
                                val isCorrect = selected == q.correctIndex

                                Text(
                                    text = q.question,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = TextPrimary
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                val options = listOf(
                                    0 to ("A. " + q.optionA),
                                    1 to ("B. " + q.optionB),
                                    2 to ("C. " + q.optionC),
                                    3 to ("D. " + q.optionD)
                                )

                                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    options.forEach { (idx, optText) ->
                                        val isThisSelected = selected == idx
                                        val optionBorderColor = when {
                                            isSubmitted && idx == q.correctIndex -> NeonMint
                                            isSubmitted && isThisSelected && !isCorrect -> ThreatOrange
                                            isThisSelected -> ElectricViolet
                                            else -> BorderDark
                                        }

                                        Surface(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(6.dp))
                                                .clickable(enabled = !isSubmitted) {
                                                    selectedAnswers = selectedAnswers + (q.id to idx)
                                                },
                                            color = if (isThisSelected) SurfaceElevated else BackgroundDark,
                                            shape = RoundedCornerShape(6.dp),
                                            border = androidx.compose.foundation.BorderStroke(1.dp, optionBorderColor)
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(10.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    text = optText,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = if (isThisSelected) TextPrimary else TextMuted
                                                )
                                                if (isSubmitted && idx == q.correctIndex) {
                                                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = NeonMint, modifier = Modifier.size(16.dp))
                                                }
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                if (!isSubmitted) {
                                    Button(
                                        onClick = {
                                            if (selected != null) {
                                                submittedQuizzes = submittedQuizzes + q.id
                                                if (selected == q.correctIndex) {
                                                    viewModel.addXp(25)
                                                    viewModel.updateSubtask(dayId, quiz = true)
                                                    Toast.makeText(context, "Correct! +25 XP", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    Toast.makeText(context, "Incorrect. Review the explanation!", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        },
                                        enabled = selected != null,
                                        shape = RoundedCornerShape(6.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = ElectricViolet,
                                            contentColor = BackgroundDark
                                        )
                                    ) {
                                        Text("SUBMIT ANSWER", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                                    }
                                } else {
                                    Surface(
                                        color = SurfaceElevated,
                                        shape = RoundedCornerShape(6.dp),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, BorderDark)
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Text(
                                                text = if (isCorrect) "✓ CORRECT" else "✗ INCORRECT",
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace),
                                                color = if (isCorrect) NeonMint else ThreatOrange
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = q.explanation,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = TextPrimary
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                }

                // 7. Hacker Lab Notes
                item {
                    CyberCard {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.EditNote,
                                contentDescription = null,
                                tint = NeonMint,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "HACKER LAB NOTES (PERSISTENT)",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = TextPrimary
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = userNoteText,
                            onValueChange = {
                                userNoteText = it
                                viewModel.saveDayNote(dayId, it)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp),
                            placeholder = {
                                Text(
                                    text = "Record target IPs, user flags, credentials, exploit strings, or personal takeaways here...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextMuted
                                )
                            },
                            textStyle = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = FontFamily.Monospace,
                                color = TextPrimary
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = NeonMint,
                                unfocusedBorderColor = BorderDark,
                                focusedContainerColor = BackgroundDark,
                                unfocusedContainerColor = BackgroundDark,
                                cursorColor = NeonMint
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }
            }
        }
    }
}
