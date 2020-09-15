package com.lucianghimpu.matchmefy.presentation.dialogs

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lucianghimpu.matchmefy.appServices.PreferencesService
import com.lucianghimpu.matchmefy.presentation.BaseViewModel

class DialogViewModel<T : Dialog>(
    application: Application,
    preferencesService: PreferencesService
) : BaseViewModel(application, preferencesService) {
    private val _dialog = MutableLiveData<T>()
    val dialog: LiveData<T>
        get() = _dialog

    fun initData(data: T) {
        _dialog.value = data
    }
}

