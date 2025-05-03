package com.leen.audiolibrary_tp2

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PageFormulaire : AppCompatActivity() {
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
        setContentView(R.layout.activity_formulaire)

        //pour afficher les 3 boutons
        val btnProfile = findViewById<Button>(R.id.btnProfile)
        val btnAccueil = findViewById<Button>(R.id.btnAccueil)
        val btnLibrarie = findViewById<Button>(R.id.btnLibrarie)

        //les fonctionnalités des boutons
        //Page Profile
        val btnEnvoyer1 = findViewById<Button>(R.id.btnProfile)
        btnEnvoyer1.setOnClickListener {
            Log.d(TAG, "btnEnvoyer onClick revenir page profile")
            val intent = Intent(this, MainActivity::class.java)
            mainActivityLauncher.launch(intent)
        }

        //Page Profile
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

        //Page Profile
        val btnEnvoyer3 = findViewById<Button>(R.id.btnLibrarie)
        btnEnvoyer3.setOnClickListener {
            Log.d(TAG, "btnEnvoyer onClick revenir page profile")
            val intent = Intent(this, PageLibrarie::class.java)
            pageLibrarieLauncher.launch(intent)
        }
    }
}