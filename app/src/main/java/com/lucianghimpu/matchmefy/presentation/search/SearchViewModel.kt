package com.lucianghimpu.matchmefy.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lucianghimpu.matchmefy.data.dataModels.Image
import com.lucianghimpu.matchmefy.data.dataModels.User
import com.lucianghimpu.matchmefy.presentation.BaseViewModel

class SearchViewModel : BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    init {
        _users.value = listOf(
            User(display_name = "lucian", images = listOf(Image(url = "https://i.picsum.photos/id/237/200/300.jpg", height = "200", width = "200"))),
            User(display_name = "Andrei", images = listOf(Image(url = "https://i.picsum.photos/id/237/200/300.jpg", height = "200", width = "200"))),
            User(display_name = "Mihai", images = listOf(Image(url = "https://i.picsum.photos/id/237/200/300.jpg", height = "200", width = "200"))))
    }
}
