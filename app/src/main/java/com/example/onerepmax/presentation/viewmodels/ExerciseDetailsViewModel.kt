package com.example.onerepmax.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onerepmax.domain.entity.ExerciseDetails
import com.example.onerepmax.domain.usecase.FetchExerciseHistoryDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseDetailsViewModel @Inject constructor(
    private val fetchExercises: FetchExerciseHistoryDetailsUseCase
) : ViewModel() {

    private val _details =  MutableStateFlow(ExerciseDetails.empty)
    val details: StateFlow<ExerciseDetails> = _details.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun getExerciseDetails(exerciseName: String) {
        viewModelScope.launch {
            try {
                _details.value = fetchExercises.execute(exerciseName)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}