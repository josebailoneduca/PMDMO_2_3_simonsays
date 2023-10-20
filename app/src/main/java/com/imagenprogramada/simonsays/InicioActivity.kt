package com.imagenprogramada.simonsays

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.gridlayout.widget.GridLayout

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

        val difi:EDificultad =juego.dificultad
        val contenedor = findViewById<ConstraintLayout>(R.id.lyContenedor)
        //limpiar contenedor
        contenedor.removeAllViews()

        //agregar grid
        val grid:GridLayout=crearGrid(difi.filas,difi.columnas)
        contenedor.addView(grid)
        val gParams = grid.layoutParams
        gParams.width=GridLayout.LayoutParams.MATCH_PARENT
        gParams.height=GridLayout.LayoutParams.MATCH_PARENT
        grid.layoutParams=gParams

        //generar botones
        var i=0
        for (c in 0..< difi.columnas){
            for (f in 0..< difi.filas){
                val btn:Button = crearBoton(""+i,c,f)
                val params:GridLayout.LayoutParams=GridLayout.LayoutParams(GridLayout.spec(f, 1,1f), GridLayout.spec(c, 1,1f))
                params.height=0
                params.width=0
                btn.layoutParams=params
                btn.layoutParams=params;



                grid.addView(btn,params)
            }
        }
    }

    private fun crearGrid(filas: Int, columnas: Int): GridLayout {
        Log.i("jjbo","entrada crear grid")
        var grid:GridLayout= GridLayout(this)
        grid.columnCount=columnas
        grid.rowCount=filas
        grid.id=View.generateViewId()
        return grid
    }

    private fun crearBoton( etiqueta:String,columna:Int,fila:Int):Button{
        val btn:Button = Button(this)
        btn.id= View.generateViewId()
        return btn

    }
}