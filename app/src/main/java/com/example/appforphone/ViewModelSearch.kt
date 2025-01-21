package com.example.appforphone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ViewModelSearch: ViewModel() {

    lateinit var birdsList: List<Bird>

    private val _filteredBirds = MutableStateFlow<List<Bird>>(emptyList())
    val filteredBirds: StateFlow<List<Bird>> get() = _filteredBirds


    fun setData(birds: List<Bird>){
        birdsList = birds
        _filteredBirds.value = birds
    }

    fun search(input: String) {
        viewModelScope.launch {
            delay(300)

            _filteredBirds.value =
                if (input.length < 3) {
                    birdsList
                }
                else {
                    birdsList.filter { bird ->
                        bird.title.contains(input, ignoreCase = true) ||
                                bird.desc.contains(input, ignoreCase = true)
                    }
                }

            }
    }
}