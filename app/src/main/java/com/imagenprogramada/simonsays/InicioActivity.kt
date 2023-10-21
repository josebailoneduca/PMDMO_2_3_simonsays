package com.imagenprogramada.simonsays

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.gridlayout.widget.GridLayout

class InicioActivity : AppCompatActivity() {

    /**
     * Logica del juego
     */
    private lateinit var juego:Juego

    private lateinit var arrayBotones: Array<Button?>
    private lateinit var grid:GridLayout


     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         juego = Juego(this)
         pantallaInicio()
     }

    /**
     *
     */
    private fun pantallaInicio() {
        setContentView(R.layout.activity_inicio)

        findViewById<Button>(R.id.btnPlay).setOnClickListener { v ->
            val id = findViewById<RadioGroup>(R.id.dificultyRadioGroup).checkedRadioButtonId
            val dificultad: EDificultad = when (id) {
                R.id.rbDificultyEasy -> EDificultad.EASY;
                R.id.rbDificultyMedium -> EDificultad.MEDIUM;
                R.id.rbDificultyHard -> EDificultad.HARD;
                else -> EDificultad.EASY

            }
            juego.iniciarJuego(dificultad)
        }
    }

    public fun iniciarPartida(filas:Int, columnas:Int) {
        setContentView(R.layout.partida)
        val btnMenu=findViewById<Button>(R.id.btnMenu)
        btnMenu.setOnClickListener {v ->
            pantallaInicio()
        }
        btnMenu.isVisible=false

        val contenedor = findViewById<ConstraintLayout>(R.id.lyContenedor)
        //limpiar contenedor
        contenedor.removeAllViews()
        //agregar grid
        grid=crearGrid(filas,columnas)
        contenedor.addView(grid)
        //generar botones

        arrayBotones= arrayOfNulls(filas*columnas)
        val nullArray: Array<Int?> = arrayOfNulls(3)

        var i=0
        for (f in 0..< filas){
            for (c in 0..< columnas){
                val btn:Button = crearBoton(""+(i+1),c,f)
                grid.addView(btn)
                btn.setOnClickListener(View.OnClickListener { v ->
                    botonPulsado(v)
                })
                arrayBotones[i]=btn
                i++
            }
        }
    }


    public fun iniciarNivel(pantalla: Pantalla){
        //poner texto nivel
        findViewById<TextView>(R.id.lbNivelValor).text=pantalla.nivel.toString()
        //desactivar botones
        activarUsuario(false)
        //animar interface
        AnimacionSecuencia(pantalla,this).start()
    }

    public fun iluminarBoton(i:Int,encendido:Boolean){
        if (encendido)
            arrayBotones[i]?.setBackgroundResource(R.drawable.boton_iluminado)
        else
            arrayBotones[i]?.setBackgroundResource(R.drawable.boton)
    }
    public fun activarUsuario(activado:Boolean){
        arrayBotones.forEach { b -> b?.isEnabled=activado }
    }
    private fun botonPulsado(v: View?) {
        arrayBotones.forEachIndexed forEach@{ index, btn->
            if (btn?.id==v?.id) {
                juego.registrarRespuesta(index)
                return@forEach
            }
        }
    }

    private fun crearGrid(filas: Int, columnas: Int): GridLayout {
        Log.i("jjbo","entrada crear grid")
        val tgrid:GridLayout= GridLayout(this)
        tgrid.columnCount=columnas
        tgrid.rowCount=filas
        tgrid.id=View.generateViewId()
        val gParams = GridLayout.LayoutParams()
        gParams.width=GridLayout.LayoutParams.MATCH_PARENT
        gParams.height=GridLayout.LayoutParams.MATCH_PARENT
         tgrid.layoutParams=gParams
        return tgrid
    }

    private fun crearBoton( etiqueta:String,columna:Int,fila:Int):Button{
        val btn:Button = Button(this)
        btn.text=etiqueta
        btn.setTextColor(ResourcesCompat.getColorStateList(resources,R.color.boton,null))
        btn.id= View.generateViewId()
        btn.typeface= ResourcesCompat.getFont(this, R.font.betty_jane_family);
        btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,40f)
        btn.setBackgroundResource(R.drawable.boton)
        val params:GridLayout.LayoutParams=GridLayout.LayoutParams(GridLayout.spec(fila, 1,1f), GridLayout.spec(columna, 1,1f))
        params.height=0
        params.width=0
        val margen: Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, resources.displayMetrics).toInt();
        params.setMargins(margen,margen,margen,margen)
        btn.layoutParams=params
        return btn

    }

    fun respuestaErronea(idIncorrecto: Int,idCorrecto:Int) {
        activarUsuario(false)
        arrayBotones[idIncorrecto]?.setBackgroundResource(R.drawable.boton_erroneo)
        arrayBotones[idCorrecto]?.setBackgroundResource(R.drawable.boton_exito)

        findViewById<Button>(R.id.btnMenu).isVisible=true
    }

    fun pantallaNivelTerminado() {
        activarUsuario(false)
        AnimacionExito(this).start()
    }

    fun exito(activado:Boolean) {

        if (activado)
            grid.setBackgroundColor(resources.getColor(R.color.verdeExito,null))
        else
            grid.setBackgroundColor(Color.TRANSPARENT)

    }

    fun finExito() {
        juego.siguienteNivel()
    }


}

