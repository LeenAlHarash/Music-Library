package com.leen.audiolibrary_tp2

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private const val PREFS_KEY_NOM = "nom"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Pour sauvegarder le nom insérer au lancement de l'application
        val inputNom = findViewById<EditText>(R.id.nameSpace)
        val prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val nom = prefs.getString(PREFS_KEY_NOM, "")

        inputNom.setText(nom)
        inputNom.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(nom: Editable?) {
                Log.d("MainActivity", "afterTextChanged : $nom")

                prefs.edit().putString("nom", nom.toString()).apply()
            }
        })
    }
}