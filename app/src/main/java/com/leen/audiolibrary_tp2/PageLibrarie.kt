package com.leen.audiolibrary_tp2

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leen.audiolibrary_tp2.data.Artiste
import com.leen.audiolibrary_tp2.data.ChansonAvecArtisteGenre
import com.leen.audiolibrary_tp2.data.Genre
import com.leen.audiolibrary_tp2.ui.chansons.ChansonAdapter
import com.leen.audiolibrary_tp2.viewmodel.ArtisteViewModel
import com.leen.audiolibrary_tp2.viewmodel.ChansonViewModel
import com.leen.audiolibrary_tp2.viewmodel.GenreViewModel

class PageLibrarie : AppCompatActivity() {
    //pour le dropdown menu : https://www.youtube.com/watch?v=jXSNobmB7u4&ab_channel=FineGap
    private val filtre = arrayOf("remplir", "ici", "pour", "filtrer")
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var adapterItems: ArrayAdapter<String>

    //pour appeler les pages
    private val mainActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK){
            val retour = result.data?.getStringExtra("resultat")
            Log.d(TAG, "Résultat: $retour")
        }
    }

    private val pageAccueilLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK){
            val retour = result.data?.getStringExtra("resultat")
            Log.d(TAG, "Résultat: $retour")
        }
    }

    private val pageFormulaireLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK){
            val retour = result.data?.getStringExtra("resultat")
            Log.d(TAG, "Résultat: $retour")
        }
    }

    // Ajout des ViewModels pour observer les données depuis la BD
    private val chansonViewModel: ChansonViewModel by viewModels()
    private val artisteViewModel: ArtisteViewModel by viewModels()
    private val genreViewModel: GenreViewModel by viewModels()

    // ecyclerView pour afficher la liste des chansons
    private lateinit var recyclerView: RecyclerView

    // Variables temporaires pour stocker les données avant de les passer à l'adapter
    private var chansons: List<ChansonAvecArtisteGenre>? = null
    private var artistes: List<Artiste>? = null
    private var genres: List<Genre>? = null

    // Fonction qui met à jour le RecyclerView seulement si les 3 listes sont prêtes
    private fun mettreAJourRecyclerView() {
        if (chansons != null && artistes != null && genres != null) {
            val adapter = ChansonAdapter(chansons!!, chansonViewModel, artistes!!, genres!!)
            recyclerView.adapter = adapter
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_librarie)

        //partie drop down
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView)
        adapterItems = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, filtre)
        autoCompleteTextView.setAdapter(adapterItems)

        //les fonctionnalités des boutons
        //Page Profile
        val btnEnvoyer1 = findViewById<Button>(R.id.btnProfile)
        btnEnvoyer1.setOnClickListener {
            Log.d(TAG, "btnEnvoyer onClick revenir page profile")
            val intent = Intent(this, MainActivity::class.java)
            mainActivityLauncher.launch(intent)
        }

        //Page Accueil
        val btnEnvoyer2 = findViewById<Button>(R.id.btnAccueil)
        btnEnvoyer2.setOnClickListener {
            //récupérer le nom sauvegardé
            val prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
            val nom = prefs.getString("nom", "") ?: ""
            Log.d(TAG, "btnEnvoyer2 onClick : nom = $nom")
            val intent = Intent(this, PageAccueil::class.java)
            intent.putExtra("nom", nom)
            pageAccueilLauncher.launch(intent)
        }

        //Page Formulaire
        val btnEnvoyer3 = findViewById<Button>(R.id.btnFormulaire)
        btnEnvoyer3.setOnClickListener {
            Log.d(TAG, "btnEnvoyer onClick revenir page formulaire")
            val intent = Intent(this, PageFormulaire::class.java)
            pageFormulaireLauncher.launch(intent)
        }

        // Initialiser le RecyclerView
        recyclerView = findViewById(R.id.recyclerViewListe)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observer les chansons
        chansonViewModel.chansons.observe(this) {
            chansons = it
            mettreAJourRecyclerView()
        }

        // Observer les artistes
        artisteViewModel.artistes.observe(this) {
            artistes = it
            mettreAJourRecyclerView()
        }

        // Observer les genres
        genreViewModel.genres.observe(this) {
            genres = it
            mettreAJourRecyclerView()
        }
    }
}
