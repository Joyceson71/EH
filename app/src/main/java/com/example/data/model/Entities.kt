package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserEntity(
    @PrimaryKey val id: Long = 1,
    val username: String = "GhostSec",
    val email: String = "ghostsec@hackpath.io",
    val rank: String = "Script Kiddie",
    val level: Int = 1,
    val xp: Int = 0,
    val streak: Int = 1,
    val completedDaysCount: Int = 0,
    val currentDay: Int = 1,
    val lastActiveDate: Long = System.currentTimeMillis()
)

@Entity(tableName = "curriculum_days")
data class DayEntity(
    @PrimaryKey val id: Int, // 1..120
    val phase: Int,          // 1..6
    val dayNumber: Int,      // 1..120
    val title: String,
    val subtitle: String,
    val concept: String,
    val keyTakeaways: String,
    val commandsCode: String,
    val videoTitle: String,
    val videoUrl: String,
    val videoChannel: String,
    val videoDuration: String,
    val readTitle: String,
    val readUrl: String,
    val readSource: String,
    val labTitle: String,
    val labUrl: String,
    val labPlatform: String, // TryHackMe, HackTheBox, PicoCTF, PortSwigger, OverTheWire
    val labDescription: String,
    val xpReward: Int = 50
)

@Entity(tableName = "day_progress")
data class DayProgressEntity(
    @PrimaryKey val dayId: Int,
    val isCompleted: Boolean = false,
    val theoryDone: Boolean = false,
    val videoDone: Boolean = false,
    val readingDone: Boolean = false,
    val labDone: Boolean = false,
    val quizDone: Boolean = false,
    val quizScore: Int = 0,
    val completedTimestamp: Long? = null
)

@Entity(tableName = "quizzes")
data class QuizQuestionEntity(
    @PrimaryKey val id: Long,
    val dayId: Int,
    val question: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctIndex: Int,
    val explanation: String
)

@Entity(tableName = "resources")
data class ResourceEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val url: String,
    val category: String, // "Platform", "Web Pentest", "Cheatsheet", "PrivEsc", "Wordlists", "Tool"
    val description: String,
    val tag: String,
    val isBookmarked: Boolean = false
)

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dayId: Int,
    val content: String,
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val dayId: Int,
    val savedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "badges")
data class BadgeEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val iconName: String = "shield",
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null
)

@Entity(tableName = "skill_nodes")
data class SkillNodeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val phaseIndex: Int,
    val description: String
)

@Entity(tableName = "activity_logs")
data class ActivityLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val dateString: String,
    val xpEarned: Int,
    val timestamp: Long = System.currentTimeMillis()
)
