package com.leen.audiolibrary_tp2

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PageAccueil : AppCompatActivity() {

    //pour appeler la page formulaire et librarie
    private val pageFormulaireLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK){
            val retour = result.data?.getStringExtra("resultat")
            Log.d(TAG, "Résultat: $retour")
        }
    }
    private val pageLibrarieLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK){
            val retour = result.data?.getStringExtra("resultat")
            Log.d(TAG, "Résultat: $retour")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil) //on appele le layout activity_accueil.xml

        val nom = intent.getStringExtra("nom")
        Log.d(TAG, "onCreate : $nom")
        val tvMessage = findViewById<TextView>(R.id.tvWelcome)
        tvMessage.text = "Bonjour $nom !" //appeler le nom de l'utilisateur


        //Pour passer à la page formulaire
        val btnEnvoyer = findViewById<Button>(R.id.btnAjouterChanson)
        //listener pour le bouton
        btnEnvoyer.setOnClickListener {
            Log.d(TAG, "btnEnvoyer onClick Ajouter Chanson")
            val intent = Intent(this, PageFormulaire::class.java)
            pageFormulaireLauncher.launch(intent)
        }

        //Page Librarie
        val btnEnvoyer3 = findViewById<Button>(R.id.btnViewLibrary)
        btnEnvoyer3.setOnClickListener {
            Log.d(TAG, "btnEnvoyer onClick page librarie")
            val intent = Intent(this, PageLibrarie::class.java)
            pageLibrarieLauncher.launch(intent)
        }
    }


    //Les fonctiones logs
    override fun onStart() { super.onStart(); Log.d(TAG, "onStart") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy") }
}