package com.leen.audiolibrary_tp2.viewmodel

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.graphics.pdf.PdfDocument.Page
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.leen.audiolibrary_tp2.ui.main.PageFormulaire
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

    // Message toast pour indiquer à l'utilisateur d'ajouter un artiste
    private val _messageErreurArtiste : MutableLiveData<String> = MutableLiveData<String>()
    val messageErreurArtiste = _messageErreurArtiste

    // Message toast pour indiquer à l'utilisateur d'ajouter un genre
    private val _messageErreurGenre : MutableLiveData<String> = MutableLiveData<String>()
    val messageErreurGenre = _messageErreurGenre

    // Message toast pour indiquer à l'utilisateur d'ajouter un nom à la chanson
    private val _messageErreurNom : MutableLiveData<String> = MutableLiveData<String>()
    val messageErreurNom = _messageErreurGenre

    // Méthode d'ajout d'une chanson
    fun ajouterChanson(nom: String, artiste : Artiste, genre : Genre) = viewModelScope.launch {
        // On est obliger de mettre le try catch dans le bloc else, sinon les vérifications du
        // id de l'artiste et du genre ne fonctionneront pas
        // De plus, j'ai comparé artiste.id et genre.id à -1 parce que c'est le chiffre
        // qui est associé à "Aucun artiste/genre sélectionné", c'est comme le null dans
        // notre cas
        // Vérifier si l'artiste est null et afficher un message d'erreur si oui
        if (artiste.id == -1) {
            _messageErreurArtiste.value = PageFormulaire.instance.getString(R.string.message_erreur_artiste)
        // Vérifier si le genre est null et afficher un message d'erreur si oui
        } else if (genre.id == -1) {
            _messageErreurGenre.value = PageFormulaire.instance.getString(R.string.message_erreur_genre)
        } else {
            // Sinon, si tous les options sont sélectionné, on ajoute la chanson
            try {
                chansonDAO.insert(Chanson(nom = nom, artisteId = artiste.id, genreId = genre.id))
                // Message de succès en toast
                _messageSuccess.value = PageFormulaire.instance.getString(R.string.message_success)
            } catch (e: SQLiteConstraintException) {
                // Message d'erreur en toast (pour garder le nom de la chanson unique)
                _messageErreur.value = PageFormulaire.instance.getString(R.string.message_exception)
            }
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
                chansonDAO.update(chanson.copy(genreId = nouveauGenre.id))
            }
        }
    }

    // Methode pour supprimer un chanson
    fun supprimerArticle(chanson: Chanson) = viewModelScope.launch {
        chansonDAO.delete(chanson)
    }
}