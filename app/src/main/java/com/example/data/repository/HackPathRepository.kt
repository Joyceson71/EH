package com.example.data.repository

import com.example.data.database.HackPathDatabase
import com.example.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class HackPathRepository(private val database: HackPathDatabase) {

    private val userDao = database.userDao()
    private val curriculumDao = database.curriculumDao()
    private val quizDao = database.quizDao()
    private val resourceDao = database.resourceDao()
    private val noteDao = database.noteDao()
    private val badgeDao = database.badgeDao()
    private val skillDao = database.skillDao()
    private val activityLogDao = database.activityLogDao()

    // User Operations
    fun getUser(): Flow<UserEntity?> = userDao.getUser()

    suspend fun getOrCreateUser(): UserEntity = withContext(Dispatchers.IO) {
        val user = userDao.getUserSync()
        if (user != null) return@withContext user
        val newUser = UserEntity(
            id = 1,
            username = "GhostSec",
            xp = 0,
            streak = 1,
            completedDaysCount = 0,
            currentDay = 1,
            rank = "Script Kiddie",
            level = 1,
            lastActiveDate = System.currentTimeMillis()
        )
        userDao.insertUser(newUser)
        newUser
    }

    suspend fun addXp(amount: Int) = withContext(Dispatchers.IO) {
        val user = userDao.getUserSync() ?: return@withContext
        val newXp = user.xp + amount
        val newLevel = (newXp / 500) + 1
        val newRank = calculateRank(newLevel)
        userDao.updateXp(newXp, newLevel, newRank)
    }

    private fun calculateRank(level: Int): String {
        return when {
            level >= 20 -> "Elite Red Teamer"
            level >= 15 -> "Senior Penetration Tester"
            level >= 10 -> "Exploit Developer"
            level >= 6 -> "Cyber Operator"
            level >= 3 -> "Security Analyst"
            else -> "Script Kiddie"
        }
    }

    // Curriculum Operations
    fun getAllDays(): Flow<List<DayEntity>> = curriculumDao.getAllDays()

    fun getDaysByPhase(phase: Int): Flow<List<DayEntity>> = curriculumDao.getDaysByPhase(phase)

    fun getDayById(dayId: Int): Flow<DayEntity?> = curriculumDao.getDayById(dayId)

    fun getDayProgress(dayId: Int): Flow<DayProgressEntity?> = curriculumDao.getDayProgress(dayId)

    fun getAllProgress(): Flow<List<DayProgressEntity>> = curriculumDao.getAllProgress()

    suspend fun toggleDayCompletion(dayId: Int, isCompleted: Boolean) = withContext(Dispatchers.IO) {
        var progress = curriculumDao.getDayProgressSync(dayId)
        if (progress == null) {
            progress = DayProgressEntity(
                dayId = dayId,
                isCompleted = isCompleted,
                theoryDone = isCompleted,
                videoDone = isCompleted,
                readingDone = isCompleted,
                labDone = isCompleted,
                quizDone = isCompleted,
                quizScore = if (isCompleted) 100 else 0,
                completedTimestamp = if (isCompleted) System.currentTimeMillis() else null
            )
            curriculumDao.insertOrUpdateProgress(progress)
        } else {
            progress = progress.copy(
                isCompleted = isCompleted,
                theoryDone = isCompleted,
                videoDone = isCompleted,
                readingDone = isCompleted,
                labDone = isCompleted,
                quizDone = isCompleted,
                completedTimestamp = if (isCompleted) System.currentTimeMillis() else null
            )
            curriculumDao.insertOrUpdateProgress(progress)
        }

        val completedCount = curriculumDao.getCompletedDaysCount()
        val user = userDao.getUserSync()
        if (user != null) {
            val xpGain = if (isCompleted) 100 else -100
            val newXp = (user.xp + xpGain).coerceAtLeast(0)
            val newLevel = (newXp / 500) + 1
            val newRank = calculateRank(newLevel)
            userDao.updateUser(
                user.copy(
                    completedDaysCount = completedCount,
                    currentDay = (completedCount + 1).coerceAtMost(120),
                    xp = newXp,
                    level = newLevel,
                    rank = newRank,
                    lastActiveDate = System.currentTimeMillis()
                )
            )
        }

        // Log activity
        if (isCompleted) {
            logActivity("Completed Day $dayId", 100)
            checkAndUnlockBadges(completedCount)
        }
    }

    suspend fun updateSubtaskProgress(
        dayId: Int,
        theory: Boolean? = null,
        video: Boolean? = null,
        reading: Boolean? = null,
        lab: Boolean? = null,
        quiz: Boolean? = null
    ) = withContext(Dispatchers.IO) {
        var progress = curriculumDao.getDayProgressSync(dayId) ?: DayProgressEntity(dayId = dayId)
        val newTheory = theory ?: progress.theoryDone
        val newVideo = video ?: progress.videoDone
        val newReading = reading ?: progress.readingDone
        val newLab = lab ?: progress.labDone
        val newQuiz = quiz ?: progress.quizDone

        val isAllCompleted = newTheory && newVideo && newReading && newLab && newQuiz
        val wasCompleted = progress.isCompleted

        progress = progress.copy(
            theoryDone = newTheory,
            videoDone = newVideo,
            readingDone = newReading,
            labDone = newLab,
            quizDone = newQuiz,
            isCompleted = isAllCompleted,
            completedTimestamp = if (isAllCompleted && !wasCompleted) System.currentTimeMillis() else progress.completedTimestamp
        )
        curriculumDao.insertOrUpdateProgress(progress)

        if (isAllCompleted && !wasCompleted) {
            toggleDayCompletion(dayId, true)
        }
    }

    // Notes
    fun getNoteForDay(dayId: Int): Flow<NoteEntity?> = noteDao.getNoteForDay(dayId)

    suspend fun saveNote(dayId: Int, content: String) = withContext(Dispatchers.IO) {
        val existing = noteDao.getNoteForDaySync(dayId)
        if (existing != null) {
            noteDao.updateNote(existing.copy(content = content, updatedAt = System.currentTimeMillis()))
        } else {
            noteDao.insertNote(NoteEntity(dayId = dayId, content = content))
        }
    }

    // Bookmarks
    fun isBookmarked(dayId: Int): Flow<Boolean> = curriculumDao.isBookmarked(dayId)

    suspend fun toggleBookmark(dayId: Int) = withContext(Dispatchers.IO) {
        val existing = curriculumDao.getBookmarkSync(dayId)
        if (existing != null) {
            curriculumDao.removeBookmark(dayId)
        } else {
            curriculumDao.insertBookmark(BookmarkEntity(dayId = dayId))
        }
    }

    fun getAllBookmarks(): Flow<List<BookmarkEntity>> = curriculumDao.getAllBookmarks()

    // Quizzes
    fun getQuizQuestionsForDay(dayId: Int): Flow<List<QuizQuestionEntity>> = quizDao.getQuestionsForDay(dayId)

    // Resources
    fun getAllResources(): Flow<List<ResourceEntity>> = resourceDao.getAllResources()

    fun getResourcesByCategory(category: String): Flow<List<ResourceEntity>> = resourceDao.getResourcesByCategory(category)

    fun searchResources(query: String): Flow<List<ResourceEntity>> = resourceDao.searchResources(query)

    suspend fun toggleResourceBookmark(id: Long, isBookmarked: Boolean) = withContext(Dispatchers.IO) {
        resourceDao.updateBookmarkStatus(id, isBookmarked)
    }

    // Badges
    fun getAllBadges(): Flow<List<BadgeEntity>> = badgeDao.getAllBadges()

    private suspend fun checkAndUnlockBadges(completedDays: Int) {
        if (completedDays >= 1) badgeDao.unlockBadge("badge_first_blood")
        if (completedDays >= 20) badgeDao.unlockBadge("badge_phase1")
        if (completedDays >= 40) badgeDao.unlockBadge("badge_phase2")
        if (completedDays >= 60) badgeDao.unlockBadge("badge_phase3")
        if (completedDays >= 80) badgeDao.unlockBadge("badge_phase4")
        if (completedDays >= 100) badgeDao.unlockBadge("badge_phase5")
        if (completedDays >= 120) badgeDao.unlockBadge("badge_phase6")
    }

    // Skill Graph
    fun getAllSkillNodes(): Flow<List<SkillNodeEntity>> = skillDao.getAllSkillNodes()

    // Activity Log
    fun getRecentActivity(): Flow<List<ActivityLogEntity>> = activityLogDao.getRecentActivity()

    private suspend fun logActivity(title: String, xpEarned: Int) {
        val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
        val dateStr = sdf.format(Date())
        activityLogDao.insertLog(
            ActivityLogEntity(
                title = title,
                dateString = dateStr,
                xpEarned = xpEarned
            )
        )
    }
}
