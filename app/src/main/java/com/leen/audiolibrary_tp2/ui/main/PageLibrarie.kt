package com.leen.audiolibrary_tp2.ui.main

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.text.TextWatcher
import android.text.Editable
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leen.audiolibrary_tp2.R
import com.leen.audiolibrary_tp2.data.Artiste
import com.leen.audiolibrary_tp2.data.ChansonAvecArtisteGenre
import com.leen.audiolibrary_tp2.data.Genre
import com.leen.audiolibrary_tp2.ui.chansons.ChansonAdapter
import com.leen.audiolibrary_tp2.viewmodel.ArtisteViewModel
import com.leen.audiolibrary_tp2.viewmodel.ChansonViewModel
import com.leen.audiolibrary_tp2.viewmodel.GenreViewModel

class PageLibrarie : AppCompatActivity() {
    // JASKARAN: pour le dropdown menu : https://www.youtube.com/watch?v=jXSNobmB7u4&ab_channel=FineGap
    // ^^ ou voir example brocoli, plus facile
    private val filtre = arrayOf("remplir", "ici", "pour", "filtrer")
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var adapterItems: ArrayAdapter<String>

    //pour appeler les pages
    private val mainActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK){
            val retour = result.data?.getStringExtra("resultat")
            Log.d(ContentValues.TAG, "Résultat: $retour")
        }
    }

    private val pageAccueilLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK){
            val retour = result.data?.getStringExtra("resultat")
            Log.d(ContentValues.TAG, "Résultat: $retour")
        }
    }

    private val pageFormulaireLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK){
            val retour = result.data?.getStringExtra("resultat")
            Log.d(ContentValues.TAG, "Résultat: $retour")
        }
    }

    // Ajout des ViewModels pour observer les données depuis la BD
    private val chansonViewModel: ChansonViewModel by viewModels()
    private val artisteViewModel: ArtisteViewModel by viewModels()
    private val genreViewModel: GenreViewModel by viewModels()

    // RecyclerView pour afficher la liste des chansons
    private lateinit var recyclerView: RecyclerView

    // Variables temporaires pour stocker les données avant de les passer à l'adapter
    private var artistes: List<Artiste>? = null
    private var genres: List<Genre>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_librarie)

        //partie drop down
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView)
        adapterItems = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, filtre)
        autoCompleteTextView.setAdapter(adapterItems)

        //les fonctionnalités des boutons
        val btnEnvoyer1 = findViewById<Button>(R.id.btnProfile)
        btnEnvoyer1.setOnClickListener {
            Log.d(ContentValues.TAG, "btnEnvoyer onClick revenir page profile")
            val intent = Intent(this, MainActivity::class.java)
            mainActivityLauncher.launch(intent)
        }

        val btnEnvoyer2 = findViewById<Button>(R.id.btnAccueil)
        btnEnvoyer2.setOnClickListener {
            val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
            val nom = prefs.getString("nom", "") ?: ""
            Log.d(ContentValues.TAG, "btnEnvoyer2 onClick : nom = $nom")
            val intent = Intent(this, PageAccueil::class.java)
            intent.putExtra("nom", nom)
            pageAccueilLauncher.launch(intent)
        }

        val btnEnvoyer3 = findViewById<Button>(R.id.btnFormulaire)
        btnEnvoyer3.setOnClickListener {
            Log.d(ContentValues.TAG, "btnEnvoyer onClick revenir page formulaire")
            val intent = Intent(this, PageFormulaire::class.java)
            pageFormulaireLauncher.launch(intent)
        }

        // Initialiser le RecyclerView
        recyclerView = findViewById(R.id.recyclerViewListe)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observer les artistes
        artisteViewModel.artistes.observe(this) {
            artistes = it
        }

        // Observer les chansons principales pour afficher tout au départ si champ vide
        val rechercheInput = findViewById<EditText>(R.id.rechercheInput)
        chansonViewModel.chansons.observe(this) { chansons ->
            val texteRecherche = rechercheInput.text.toString()
            if (texteRecherche.isBlank()) {
                chansonViewModel.rechercherParNom("")
            }
        }

        // Observer les genres
        genreViewModel.genres.observe(this) {
            genres = it
        }

        // Ajout de la recherche par nom de chanson avec appel au ViewModel
        // Déclaration du champ de recherche (déjà déclaré ci-dessous)
        rechercheInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val texte = s.toString()
                // Si le champ est vide, on recharge toutes les chansons
                if (texte.isBlank()) {
                    chansonViewModel.rechercherParNom("")
                } else {
                    chansonViewModel.rechercherParNom(texte)
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Observer les résultats filtrés et mettre à jour le RecyclerView
        chansonViewModel.chansonsFiltrees.observe(this) { chansonsFiltrees ->
            recyclerView.adapter = ChansonAdapter(
                chansonsFiltrees,
                chansonViewModel,
                artistes ?: emptyList(),
                genres ?: emptyList()
            )
        }

        // Observer les messages d'erreur pour afficher un toast
        chansonViewModel.messageErreur.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}
