package com.example.appforphone

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ViewModelSearch: ViewModel() {

    lateinit var birdList: List<Bird>

    lateinit var filteredBird: List<Bird>

    fun setData(birds: List<Bird>){
        birdList = birds
    }

    fun search(input: String){
        if(input.length<3){
            filteredBird=birdList
        }
        else{
            filteredBird = birdList.filter{bird->
                bird.title.contains(input,ignoreCase = true) ||
                        bird.desc.contains(input,ignoreCase = true)}
        }
    }
}