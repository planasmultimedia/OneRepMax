package com.example.onerepmax.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onerepmax.domain.entity.Exercise
import com.example.onerepmax.domain.usecase.CalculateWorkoutsOneRepMax
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(
    private val calculateWorkouts: CalculateWorkoutsOneRepMax
) : ViewModel() {

    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises.asStateFlow()

    init {
        calculateOneRepMax()
    }

    private fun calculateOneRepMax() {
        viewModelScope.launch {
              _exercises.value  = calculateWorkouts.execute()
        }
    }
}