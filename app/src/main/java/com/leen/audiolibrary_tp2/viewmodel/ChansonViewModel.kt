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
import com.leen.audiolibrary_tp2.ui.main.PageModification
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

    // Message toast pour indiquer à l'utilisateur que la modification a été un succès
    private val _messageSuccessModification : MutableLiveData<String> = MutableLiveData<String>()
    val messageSuccessModification = _messageSuccessModification

    // Chanson sélectionné avec le id
    // Variable pour qu'il puisse tenir les changements nécéssaire
    private val _chansonSelectionner : MutableLiveData<Chanson> = MutableLiveData<Chanson>()

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

    // Méthode qui sélectionne le id de la chanson
    fun chercherChansonParId(id : Int) = viewModelScope.launch {
        val chanson = chansonDAO.getChansonById(id)
        // On attribue cette chanson à la variable Live
        _chansonSelectionner.postValue(chanson)
    }

    // Méthode pour la modification d'une chanson
    fun modifierChanson(nouveauNom : String, nouveauArtiste : Artiste, nouveauGenre : Genre) = viewModelScope.launch {
        // On crée une nouvelle variable chanson qui a la valeur de la chanson sélectionné avec l'id
        val chanson = _chansonSelectionner.value
        // On fait une copy d'un objet chanson à une nouvelle valeur
        val chansonModifier = chanson?.copy(nom = nouveauNom, artisteId = nouveauArtiste.id, genreId = nouveauGenre.id)
        // Si la chanson à modifier n'est pas null, on la modifie et on l'attribue à la valeur Live sélectionné
        if(chansonModifier != null){
            chansonDAO.update(chansonModifier)
            _chansonSelectionner.value = chansonModifier
            // Message de succès de la modification en toast
            _messageSuccessModification.value = PageModification.instance.getString(R.string.message_success_modification)
        }
    }

    // Methode pour supprimer un chanson
    fun supprimerArticle(chanson: Chanson) = viewModelScope.launch {
        chansonDAO.delete(chanson)
    }
}