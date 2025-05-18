package com.leen.audiolibrary_tp2.ui.chansons

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView // Ajout pour affichage lecture seule
import androidx.recyclerview.widget.RecyclerView
import com.leen.audiolibrary_tp2.R
import com.leen.audiolibrary_tp2.data.Artiste
import com.leen.audiolibrary_tp2.data.ChansonAvecArtisteGenre
import com.leen.audiolibrary_tp2.data.Genre
import com.leen.audiolibrary_tp2.ui.main.PageModification
import com.leen.audiolibrary_tp2.viewmodel.ChansonViewModel

// Ajout des artistes et genres passés dynamiquement au constructeur
class ChansonAdapter(
    var listeChansons: List<ChansonAvecArtisteGenre>,
    val chansonViewModel: ChansonViewModel,
    val artistes: List<Artiste>,
    val genres: List<Genre>
) : RecyclerView.Adapter<ChansonAdapter.ChansonViewHolder>() {

    // Constructeur de ChansonViewHolder qui met une chanson dans la page à l'aide d'un élément UI
    class ChansonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Spinners conservés pour usage futur (formulaire par exemple)
        /*
        val spinnerArtiste = itemView.findViewById<Spinner>(R.id.spinner_artiste)
        val spinnerGenre = itemView.findViewById<Spinner>(R.id.spinner_genre)
        */

        // TextView pour affichage lecture seule
        val textViewTitre: TextView = itemView.findViewById(R.id.textViewTitre)
        val textViewArtiste: TextView = itemView.findViewById(R.id.textViewArtiste)
        val textViewGenre: TextView = itemView.findViewById(R.id.textViewGenre)
        val btnSupprimer: Button = itemView.findViewById(R.id.btnSupprimer)
        val btnModifier: Button = itemView.findViewById(R.id.btnModifier)
    }

    // Prépare la vue de l'activité librairie lorsque les chansons apparaissent
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChansonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chanson, parent, false) // Correction ici : layout d’un item, pas toute la page :)
        return ChansonViewHolder(view)
    }

    // Retourne le nombre d'éléments dans la liste des chansons
    override fun getItemCount(): Int {
        return listeChansons.size
    }

    // Affiche une chanson avec ses infos en lecture seule (titre, artiste, genre)
    override fun onBindViewHolder(holder: ChansonViewHolder, position: Int) {
        val chanson = listeChansons[position].chanson
        val artiste = listeChansons[position].artiste
        val genre = listeChansons[position].genre


        // Remplir les champs TextView avec les données de la chanson
        holder.textViewTitre.text = chanson.nom
        holder.textViewArtiste.text = artiste.nom
        holder.textViewGenre.text = genre.nom

        // quand on click sur l'X dans la librarie on supprime la chanson
        holder.btnSupprimer.setOnClickListener {
            chansonViewModel.supprimerArticle(chanson)
        }
        // quand on click sur le bouton modifier on modifie la chanson
        holder.btnModifier.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, PageModification::class.java)
            context.startActivity(intent)
        }


        // Si tu veux réactiver les Spinner plus tard, décommente ce bloc :
        /*
        // Création d’un ArrayAdapter pour les artistes
        val artistesAdapter = ArrayAdapter(holder.itemView.context, android.R.layout.simple_spinner_item, artistes)
        artistesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.spinnerArtiste.adapter = artistesAdapter

        val positionArtiste = artistes.indexOfFirst { it.id == chanson.artisteId }
        if (positionArtiste != -1) {
            holder.spinnerArtiste.setSelection(positionArtiste)
        }

        holder.spinnerArtiste.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val artiste = parent?.selectedItem as Artiste
                if (artiste.id != chanson.artisteId) {
                    chansonViewModel.changerArtiste(chanson, artiste)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Création d’un ArrayAdapter pour les genres
        val genresAdapter = ArrayAdapter(holder.itemView.context, android.R.layout.simple_spinner_item, genres)
        genresAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.spinnerGenre.adapter = genresAdapter

        val positionGenre = genres.indexOfFirst { it.id == chanson.genreId }
        if (positionGenre != -1) {
            holder.spinnerGenre.setSelection(positionGenre)
        }

        holder.spinnerGenre.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val genre = parent?.selectedItem as Genre
                if (genre.id != chanson.genreId) {
                    chansonViewModel.changerGenre(chanson, genre)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        */
    }
}