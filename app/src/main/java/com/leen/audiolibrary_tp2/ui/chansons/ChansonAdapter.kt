package com.leen.audiolibrary_tp2.ui.chansons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leen.audiolibrary_tp2.R
import com.leen.audiolibrary_tp2.data.ChansonAvecArtisteGenre
import com.leen.audiolibrary_tp2.viewmodel.ChansonViewModel

class ChansonAdapter(var listeChansons: List<ChansonAvecArtisteGenre>, val chansonViewModel: ChansonViewModel) : RecyclerView.Adapter<ChansonAdapter.ChansonViewHolder>() {


    // Constructeur de ChansonViewHolder qui met une chanson dans la page à l'aide d'un élément UI
    class ChansonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    // Prépare la vue de l'activité librairie lorsque les chansons apparait
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChansonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_librarie, parent, false)
        return ChansonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listeChansons.size
    }

    override fun onBindViewHolder(holder: ChansonViewHolder, position: Int) {
    }

}