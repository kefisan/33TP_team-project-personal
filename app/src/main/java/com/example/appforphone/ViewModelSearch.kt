package com.example.appforphone

import androidx.lifecycle.ViewModel

class ViewModelSearch: ViewModel() {

    lateinit var titlesList: List<String>
    lateinit var descriptionsList: List<String>
    lateinit var imagesList: List<Int>

    lateinit var filteredTitlesList: List<String>
    lateinit var filteredDescriptionsList: List<String>
    lateinit var filteredImagesList: List<Int>
    lateinit var filteredIndices: List<Int>

    fun setData(titles: List<String>,descriptions: List<String>, images: List<Int>){
        titlesList = titles.toList()
        descriptionsList = descriptions.toList()
        imagesList= images.toList()
    }

    fun search(input: String){
        if(input.length<3){
            filteredTitlesList = titlesList
            filteredDescriptionsList = descriptionsList
            filteredImagesList = imagesList
        }
        else{
            filteredIndices = titlesList.indices.filter{index->
                titlesList[index].contains(input,ignoreCase = true) ||
                        descriptionsList[index].contains(input,ignoreCase = true)}
        }
        filteredTitlesList = filteredIndices.map{titlesList[it]}
        filteredDescriptionsList = filteredIndices.map{descriptionsList[it]}
        filteredImagesList = filteredIndices.map{imagesList[it]}
    }
}