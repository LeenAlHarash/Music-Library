package com.leen.audiolibrary_tp2.viewmodel

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.leen.audiolibrary_tp2.PageFormulaire
import com.leen.audiolibrary_tp2.R
import com.leen.audiolibrary_tp2.data.AppDatabase
import com.leen.audiolibrary_tp2.data.Artiste
import com.leen.audiolibrary_tp2.data.Chanson
import com.leen.audiolibrary_tp2.data.ChansonAvecArtisteGenre
import com.leen.audiolibrary_tp2.data.Genre
import kotlinx.coroutines.launch

class ChansonViewModel(application: Application) : AndroidViewModel(application) {

    // Déclaration des attributs
    private val chansonDAO = AppDatabase.getDatabase(application).chansonDAO()

    // Chercher tous les chansons à partir de la base de données
    val chansons: LiveData<List<ChansonAvecArtisteGenre>> = chansonDAO.getAll()

    // Message toast pour gérer l'exception du nom qui doit être unique
    private val _messageErreur : MutableLiveData<String> = MutableLiveData<String>()
    val messageErreur = _messageErreur

    // Message toast pour indiquer à l'utilisateur que l'ajout a été fait avec succès
    private val _messageSuccess: MutableLiveData<String> = MutableLiveData<String>()
    val messageSuccess = _messageSuccess

    // Méthode d'ajout d'une chanson
    fun ajouterChanson(nom: String, artiste : Artiste, genre : Genre) = viewModelScope.launch {
        try{
            chansonDAO.insert(Chanson(nom = nom, artisteId = artiste.id, genreId = genre.id))
            // Message de succès en toast
            _messageSuccess.value = PageFormulaire.instance.getString(R.string.message_success)
        } catch (e: SQLiteConstraintException) {
            // Message d'erreur en toast
            _messageErreur.value = PageFormulaire.instance.getString(R.string.message_exception)
        }
    }

    // Méthode qui attribue un artiste à la chanson
    fun changerArtiste(chanson: Chanson, nouveauArtiste : Artiste){
        viewModelScope.launch {
            if(chanson.artisteId != nouveauArtiste.id){
                chansonDAO.update(chanson.copy(artisteId = nouveauArtiste.id))
            }
        }
    }

    // Méthode qui attribue un genre à la chanson
    fun changerGenre(chanson: Chanson, nouveauGenre : Genre){
        viewModelScope.launch {
            if(chanson.genreId != nouveauGenre.id){
                chansonDAO.update(chanson.copy(artisteId = nouveauGenre.id))
            }
        }
    }
}