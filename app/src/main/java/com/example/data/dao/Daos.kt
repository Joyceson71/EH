package com.example.data.dao

import androidx.room.*
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUser(): Flow<UserEntity?>

    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    suspend fun getUserSync(): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("UPDATE user_profile SET xp = :newXp, level = :newLevel, rank = :newRank, lastActiveDate = :now WHERE id = 1")
    suspend fun updateXp(newXp: Int, newLevel: Int, newRank: String, now: Long = System.currentTimeMillis())
}

@Dao
interface CurriculumDao {
    @Query("SELECT * FROM curriculum_days ORDER BY dayNumber ASC")
    fun getAllDays(): Flow<List<DayEntity>>

    @Query("SELECT * FROM curriculum_days WHERE phase = :phase ORDER BY dayNumber ASC")
    fun getDaysByPhase(phase: Int): Flow<List<DayEntity>>

    @Query("SELECT * FROM curriculum_days WHERE id = :dayId LIMIT 1")
    fun getDayById(dayId: Int): Flow<DayEntity?>

    @Query("SELECT COUNT(*) FROM curriculum_days")
    suspend fun getTotalDaysCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDays(days: List<DayEntity>)

    @Query("SELECT * FROM day_progress ORDER BY dayId ASC")
    fun getAllProgress(): Flow<List<DayProgressEntity>>

    @Query("SELECT * FROM day_progress WHERE dayId = :dayId LIMIT 1")
    fun getDayProgress(dayId: Int): Flow<DayProgressEntity?>

    @Query("SELECT * FROM day_progress WHERE dayId = :dayId LIMIT 1")
    suspend fun getDayProgressSync(dayId: Int): DayProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProgress(progress: DayProgressEntity)

    @Query("SELECT COUNT(*) FROM day_progress WHERE isCompleted = 1")
    suspend fun getCompletedDaysCount(): Int

    // Bookmarks
    @Query("SELECT * FROM bookmarks ORDER BY savedAt DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM bookmarks WHERE dayId = :dayId LIMIT 1")
    suspend fun getBookmarkSync(dayId: Int): BookmarkEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE dayId = :dayId)")
    fun isBookmarked(dayId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE dayId = :dayId")
    suspend fun removeBookmark(dayId: Int)
}

@Dao
interface QuizDao {
    @Query("SELECT * FROM quizzes WHERE dayId = :dayId ORDER BY id ASC")
    fun getQuestionsForDay(dayId: Int): Flow<List<QuizQuestionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuizQuestionEntity>)
}

@Dao
interface ResourceDao {
    @Query("SELECT * FROM resources ORDER BY id ASC")
    fun getAllResources(): Flow<List<ResourceEntity>>

    @Query("SELECT * FROM resources WHERE category = :category ORDER BY id ASC")
    fun getResourcesByCategory(category: String): Flow<List<ResourceEntity>>

    @Query("SELECT * FROM resources WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' OR tag LIKE '%' || :query || '%'")
    fun searchResources(query: String): Flow<List<ResourceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResources(resources: List<ResourceEntity>)

    @Query("UPDATE resources SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateBookmarkStatus(id: Long, isBookmarked: Boolean)
}

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE dayId = :dayId LIMIT 1")
    fun getNoteForDay(dayId: Int): Flow<NoteEntity?>

    @Query("SELECT * FROM notes WHERE dayId = :dayId LIMIT 1")
    suspend fun getNoteForDaySync(dayId: Int): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)
}

@Dao
interface BadgeDao {
    @Query("SELECT * FROM badges ORDER BY id ASC")
    fun getAllBadges(): Flow<List<BadgeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBadges(badges: List<BadgeEntity>)

    @Query("UPDATE badges SET isUnlocked = 1, unlockedAt = :unlockedAt WHERE id = :badgeId")
    suspend fun unlockBadge(badgeId: String, unlockedAt: Long = System.currentTimeMillis())
}

@Dao
interface SkillDao {
    @Query("SELECT * FROM skill_nodes ORDER BY phaseIndex ASC, id ASC")
    fun getAllSkillNodes(): Flow<List<SkillNodeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkillNodes(nodes: List<SkillNodeEntity>)
}

@Dao
interface ActivityLogDao {
    @Query("SELECT * FROM activity_logs ORDER BY timestamp DESC LIMIT 20")
    fun getRecentActivity(): Flow<List<ActivityLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: ActivityLogEntity)
}
