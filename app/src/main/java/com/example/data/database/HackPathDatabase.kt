package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.dao.*
import com.example.data.model.*

@Database(
    entities = [
        UserEntity::class,
        DayEntity::class,
        DayProgressEntity::class,
        QuizQuestionEntity::class,
        ResourceEntity::class,
        NoteEntity::class,
        BookmarkEntity::class,
        BadgeEntity::class,
        SkillNodeEntity::class,
        ActivityLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class HackPathDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun curriculumDao(): CurriculumDao
    abstract fun quizDao(): QuizDao
    abstract fun resourceDao(): ResourceDao
    abstract fun noteDao(): NoteDao
    abstract fun badgeDao(): BadgeDao
    abstract fun skillDao(): SkillDao
    abstract fun activityLogDao(): ActivityLogDao

    companion object {
        @Volatile
        private var INSTANCE: HackPathDatabase? = null

        fun getDatabase(context: Context): HackPathDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HackPathDatabase::class.java,
                    "hackpath_database.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
