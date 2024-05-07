package com.example.onerepmax.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onerepmax.domain.entity.ExerciseMaxOneRepRecord
import com.example.onerepmax.domain.usecase.CalculateMaxRepUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(
    private val calculateWorkouts: CalculateMaxRepUseCase
) : ViewModel() {

    private val _exercises = MutableStateFlow<List<ExerciseMaxOneRepRecord>>(emptyList())
    val exercises: StateFlow<List<ExerciseMaxOneRepRecord>> = _exercises.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        calculateOneRepMax()
    }

    private fun calculateOneRepMax() {
        viewModelScope.launch {
            try {
                _exercises.value = calculateWorkouts.execute()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}