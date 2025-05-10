package com.leen.audiolibrary_tp2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.leen.audiolibrary_tp2.data.AppDatabase
import com.leen.audiolibrary_tp2.data.ChansonAvecArtisteGenre
import kotlinx.coroutines.launch

class ChansonViewModel(application: Application) : AndroidViewModel(application) {

    // Déclaration des attributs
    private val chansonDAO = AppDatabase.getDatabase(application).chansonDAO()

    // Chercher tous les chansons à partir de la base de données
    val chansons: LiveData<List<ChansonAvecArtisteGenre>> = chansonDAO.getAll()
}