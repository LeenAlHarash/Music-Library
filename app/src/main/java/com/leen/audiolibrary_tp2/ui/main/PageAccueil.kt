package com.leen.audiolibrary_tp2.ui.main

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.leen.audiolibrary_tp2.R

class PageAccueil : BaseActivity() {

    //pour appeler la page formulaire et librarie
    private val pageFormulaireLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK){
            val retour = result.data?.getStringExtra("resultat")
            Log.d(ContentValues.TAG, "Résultat: $retour")
        }
    }
    private val pageLibrarieLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK){
            val retour = result.data?.getStringExtra("resultat")
            Log.d(ContentValues.TAG, "Résultat: $retour")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil) //on appele le layout activity_accueil.xml

        val nom = intent.getStringExtra("nom")
        Log.d(ContentValues.TAG, "onCreate : $nom")
        val tvMessage = findViewById<TextView>(R.id.tvWelcome)
        tvMessage.text = getString(R.string.bonjour, nom) //appeler le nom de l'utilisateur


        //Pour passer à la page formulaire
        val btnEnvoyer = findViewById<Button>(R.id.btnAjouterChanson)
        //listener pour le bouton
        btnEnvoyer.setOnClickListener {
            Log.d(ContentValues.TAG, "btnEnvoyer onClick Ajouter Chanson")
            val intent = Intent(this, PageFormulaire::class.java)
            pageFormulaireLauncher.launch(intent)
        }

        //Page Librarie
        val btnEnvoyer3 = findViewById<Button>(R.id.btnViewLibrary)
        btnEnvoyer3.setOnClickListener {
            Log.d(ContentValues.TAG, "btnEnvoyer onClick page librarie")
            val intent = Intent(this, PageLibrarie::class.java)
            pageLibrarieLauncher.launch(intent)
        }
    }


    //Les fonctiones logs
    override fun onStart() { super.onStart(); Log.d(ContentValues.TAG, "onStart") }
    override fun onResume() { super.onResume(); Log.d(ContentValues.TAG, "onResume") }
    override fun onPause() { super.onPause(); Log.d(ContentValues.TAG, "onPause") }
    override fun onStop() { super.onStop(); Log.d(ContentValues.TAG, "onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d(ContentValues.TAG, "onDestroy") }
}