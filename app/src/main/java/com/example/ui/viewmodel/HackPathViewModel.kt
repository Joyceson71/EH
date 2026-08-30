package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.database.HackPathDatabase
import com.example.data.model.*
import com.example.data.repository.HackPathRepository
import com.example.data.seed.CurriculumSeeder
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class UiState(
    val isLoading: Boolean = true,
    val user: UserEntity? = null,
    val selectedPhase: Int = 0, // 0 means All Phases
    val searchQuery: String = "",
    val days: List<DayEntity> = emptyList(),
    val progressMap: Map<Int, DayProgressEntity> = emptyMap(),
    val bookmarks: Set<Int> = emptySet(),
    val resources: List<ResourceEntity> = emptyList(),
    val selectedResourceCategory: String = "All",
    val badges: List<BadgeEntity> = emptyList(),
    val skillNodes: List<SkillNodeEntity> = emptyList(),
    val recentActivity: List<ActivityLogEntity> = emptyList(),
    val currentDayDetail: DayEntity? = null,
    val currentDayProgress: DayProgressEntity? = null,
    val currentDayQuestions: List<QuizQuestionEntity> = emptyList(),
    val currentDayNote: NoteEntity? = null
)

class HackPathViewModel(application: Application) : AndroidViewModel(application) {

    private val database = HackPathDatabase.getDatabase(application)
    private val repository = HackPathRepository(database)

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            // Seed database if empty
            CurriculumSeeder.seedDatabaseIfEmpty(database)
            loadInitialData()
        }
    }

    private fun loadInitialData() {
        // Collect User
        viewModelScope.launch {
            repository.getUser().collect { user ->
                _uiState.update { it.copy(user = user ?: it.user) }
            }
        }

        // Collect All Days
        viewModelScope.launch {
            repository.getAllDays().collect { daysList ->
                _uiState.update { it.copy(days = daysList, isLoading = false) }
            }
        }

        // Collect Progress
        viewModelScope.launch {
            repository.getAllProgress().collect { progressList ->
                val map = progressList.associateBy { it.dayId }
                _uiState.update { it.copy(progressMap = map) }
            }
        }

        // Collect Bookmarks
        viewModelScope.launch {
            repository.getAllBookmarks().collect { bList ->
                val set = bList.map { it.dayId }.toSet()
                _uiState.update { it.copy(bookmarks = set) }
            }
        }

        // Collect Resources
        viewModelScope.launch {
            repository.getAllResources().collect { resList ->
                _uiState.update { it.copy(resources = resList) }
            }
        }

        // Collect Badges
        viewModelScope.launch {
            repository.getAllBadges().collect { badgeList ->
                _uiState.update { it.copy(badges = badgeList) }
            }
        }

        // Collect Skill Nodes
        viewModelScope.launch {
            repository.getAllSkillNodes().collect { sList ->
                _uiState.update { it.copy(skillNodes = sList) }
            }
        }

        // Collect Activity Logs
        viewModelScope.launch {
            repository.getRecentActivity().collect { actList ->
                _uiState.update { it.copy(recentActivity = actList) }
            }
        }
    }

    fun setPhaseFilter(phase: Int) {
        _uiState.update { it.copy(selectedPhase = phase) }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun setResourceCategory(category: String) {
        _uiState.update { it.copy(selectedResourceCategory = category) }
    }

    fun toggleDayCompletion(dayId: Int) {
        val currentProgress = _uiState.value.progressMap[dayId]
        val isNowCompleted = !(currentProgress?.isCompleted ?: false)
        viewModelScope.launch {
            repository.toggleDayCompletion(dayId, isNowCompleted)
        }
    }

    fun updateSubtask(
        dayId: Int,
        theory: Boolean? = null,
        video: Boolean? = null,
        reading: Boolean? = null,
        lab: Boolean? = null,
        quiz: Boolean? = null
    ) {
        viewModelScope.launch {
            repository.updateSubtaskProgress(
                dayId = dayId,
                theory = theory,
                video = video,
                reading = reading,
                lab = lab,
                quiz = quiz
            )
        }
    }

    fun toggleBookmark(dayId: Int) {
        viewModelScope.launch {
            repository.toggleBookmark(dayId)
        }
    }

    fun toggleResourceBookmark(resource: ResourceEntity) {
        viewModelScope.launch {
            repository.toggleResourceBookmark(resource.id, !resource.isBookmarked)
        }
    }

    fun loadDayDetail(dayId: Int) {
        viewModelScope.launch {
            repository.getDayById(dayId).collect { day ->
                _uiState.update { it.copy(currentDayDetail = day) }
            }
        }
        viewModelScope.launch {
            repository.getDayProgress(dayId).collect { prog ->
                _uiState.update { it.copy(currentDayProgress = prog) }
            }
        }
        viewModelScope.launch {
            repository.getQuizQuestionsForDay(dayId).collect { questions ->
                _uiState.update { it.copy(currentDayQuestions = questions) }
            }
        }
        viewModelScope.launch {
            repository.getNoteForDay(dayId).collect { note ->
                _uiState.update { it.copy(currentDayNote = note) }
            }
        }
    }

    fun saveDayNote(dayId: Int, content: String) {
        viewModelScope.launch {
            repository.saveNote(dayId, content)
        }
    }

    fun addXp(amount: Int) {
        viewModelScope.launch {
            repository.addXp(amount)
        }
    }
}
