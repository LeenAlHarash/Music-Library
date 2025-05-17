package com.leen.audiolibrary_tp2.ui.main

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.leen.audiolibrary_tp2.R

private const val PREFS_KEY_NOM = "nom"

class MainActivity : AppCompatActivity() {

    //pour appeler la page accueil
    private val pageAccueilLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK){
            val retour = result.data?.getStringExtra("resultat")
            Log.d(ContentValues.TAG, "Résultat: $retour")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(ContentValues.TAG, "onCreate")
        setContentView(R.layout.activity_main)

        //Pour sauvegarder le nom insérer au lancement de l'application
        val inputNom = findViewById<EditText>(R.id.nameSpace)
        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
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

        //Pour passer à la page d'accueil
        val btnEnvoyer = findViewById<Button>(R.id.btnSaveProfile)
        val afficherNom = findViewById<EditText>(R.id.nameSpace)


        //listener pour le bouton
        btnEnvoyer.setOnClickListener {
            val nom = afficherNom.text.toString()
            //Toast pour forcer l'utilisateur à entrer un nom
            if (nom.isEmpty()) {
                Toast.makeText(this, getString(R.string.toastProfile), Toast.LENGTH_SHORT).show()
            } else {
                Log.d(ContentValues.TAG, "btnEnvoyer onClick : $nom")
                val intent = Intent(this, PageAccueil::class.java)
                intent.putExtra("nom", nom)
                pageAccueilLauncher.launch(intent)
            }
        }
    }

    //Les fonctiones logs
    override fun onStart() { super.onStart(); Log.d(ContentValues.TAG, "onStart")
    }
    override fun onResume() { super.onResume(); Log.d(ContentValues.TAG, "onResume")
    }
    override fun onPause() { super.onPause(); Log.d(ContentValues.TAG, "onPause")
    }
    override fun onStop() { super.onStop(); Log.d(ContentValues.TAG, "onStop")
    }
    override fun onDestroy() { super.onDestroy(); Log.d(ContentValues.TAG, "onDestroy")
    }
}