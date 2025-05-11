package com.leen.audiolibrary_tp2.ui.chansons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.leen.audiolibrary_tp2.R
import com.leen.audiolibrary_tp2.data.Artiste
import com.leen.audiolibrary_tp2.data.ChansonAvecArtisteGenre
import com.leen.audiolibrary_tp2.data.Genre
import com.leen.audiolibrary_tp2.viewmodel.ChansonViewModel

class ChansonAdapter(var listeChansons: List<ChansonAvecArtisteGenre>, val chansonViewModel: ChansonViewModel) : RecyclerView.Adapter<ChansonAdapter.ChansonViewHolder>() {

    // Déclaration d'un array adapter pour la liste des artistes et des genres
    private lateinit var artistesAdapter : ArrayAdapter<Artiste>
    private lateinit var genresAdapter : ArrayAdapter<Genre>

    // Constructeur de ChansonViewHolder qui met une chanson dans la page à l'aide d'un élément UI
    class ChansonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val spinnerArtiste = itemView.findViewById<Spinner>(R.id.spinner_artiste)
        val spinnerGenre = itemView.findViewById<Spinner>(R.id.spinner_genre)
    }

    // Prépare la vue de l'activité librairie lorsque les chansons apparait
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChansonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_librarie, parent, false)
        return ChansonViewHolder(view)
    }

    // Retourne le nombre d'éléments dans la liste des chansons
    override fun getItemCount(): Int {
        return listeChansons.size
    }

    // sadsdsa idk help
    override fun onBindViewHolder(holder: ChansonViewHolder, position: Int) {
        val chanson = listeChansons[position].chanson

        // Chercher le id de l'artiste sélectionner
        holder.spinnerArtiste.adapter = artistesAdapter
        holder.spinnerArtiste.setSelection(chanson.artisteId ?: -1)
        holder.spinnerArtiste.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var artiste = parent?.selectedItem as Artiste
                if(artiste.id == -1) {
                    artiste == null
                }

                chansonViewModel.changerArtiste(chanson, artiste)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Chercher le id du genre sélectionné
        holder.spinnerGenre.adapter = genresAdapter
        holder.spinnerGenre.setSelection(chanson.genreId ?: -1)
        holder.spinnerGenre.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var genre = parent?.selectedItem as Genre
                if(genre?.id == -1){
                    genre == null
                }

                chansonViewModel.changerGenre(chanson, genre)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { TODO("Not yet implemented") }
        }
    }

}