package com.imagenprogramada.simonsays

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup

class InicioActivity : AppCompatActivity() {
    lateinit var juego:Juego
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         juego = Juego()
         pantallaInicio()
     }

    private fun pantallaInicio() {
        setContentView(R.layout.activity_inicio)

        findViewById<RadioGroup>(R.id.dificultyRadioGroup).setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.rbDificultyEasy -> juego.dificultad=EDificultad.EASY;
                R.id.rbDificultyMedium -> juego.dificultad=EDificultad.MEDIUM;
                R.id.rbDificultyHard -> juego.dificultad=EDificultad.HARD;
            }
        }

        findViewById<Button>(R.id.btnPlay).setOnClickListener({ v ->
            iniciarJuego()
        })
    }

    private fun iniciarJuego() {
        setContentView(R.layout.partida)
    }
}