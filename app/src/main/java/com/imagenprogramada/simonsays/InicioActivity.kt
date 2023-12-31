package com.imagenprogramada.simonsays

import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.gridlayout.widget.GridLayout

/**
 * Activity principal y unica de la aplicacion. Gestiona la GUI cargando 2 layout segun se este en el menu inicial o en partida
 * Se encarga de gestionar la generacion de la cuadricula de botones, responder a las pulsaciones avisando a la clase Juego de ellas
 * y activando animaciones segun la demanda
 *
 * @author Jose Javier Bailon Ortiz
 */
class InicioActivity : AppCompatActivity() {


    //ATRIBUTOS*************************************************************************************
    /**
     * Logica del juego
     */
    private lateinit var juego: Juego

    /**
     * Almacena referencias a los botones de la cuadricula de la partida actual
     */
    private lateinit var arrayBotones: Array<Button?>

    /**
     * Referencia a la cuadricula de la actual
     */
    private lateinit var grid: GridLayout

    /**
     * Lista de colores a asignar
     */
    private lateinit var listaColores: TypedArray

    //METODOS DE CICLO DE VIDA**********************************************************************
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listaColores = resources.obtainTypedArray(R.array.colores_botones)
        //inicializa la logica
        juego = Juego(this)
        //muestra el menu inicial
        menuInicial()
    }

    //METODOS PRIVADOS******************************************************************************
    /**
     *Muestra el menu inicial
     */
    private fun menuInicial() {
        //carga layout inicio
        setContentView(R.layout.menu_inicial)

        //definir la dificultad seleccionada
        val dificultadSeleccionada = when (juego.dificultad) {
            EDificultad.EASY -> R.id.rbDificultyEasy;
            EDificultad.MEDIUM -> R.id.rbDificultyMedium;
            EDificultad.HARD -> R.id.rbDificultyHard;
            EDificultad.INSANE -> R.id.rbDificultyInsane;
            else -> R.id.rbDificultyEasy
        }
        findViewById<RadioGroup>(R.id.dificultyRadioGroup).check(dificultadSeleccionada)

        //gestiona el boton de inicio de partida
        findViewById<Button>(R.id.btnPlay).setOnClickListener {
            //recoger el valor de dificultad seleccionado
            val idRadioButtonSeleccionado =
                findViewById<RadioGroup>(R.id.dificultyRadioGroup).checkedRadioButtonId
            val dificultad: EDificultad = when (idRadioButtonSeleccionado) {
                R.id.rbDificultyEasy -> EDificultad.EASY;
                R.id.rbDificultyMedium -> EDificultad.MEDIUM;
                R.id.rbDificultyHard -> EDificultad.HARD;
                R.id.rbDificultyInsane -> EDificultad.INSANE;
                else -> EDificultad.EASY
            }
            //pide a la logica iniciar el juego
            juego.iniciarPartida(dificultad)
        }
    }

    /**
     * Construye un gridlayout segun los parametros suministrados
     *
     * @param filas Las filas del grid
     * @param columnas Las columnas del grid
     */
    private fun crearGrid(filas: Int, columnas: Int) {

        //crear el grid
        grid = GridLayout(this)
        grid.columnCount = columnas
        grid.rowCount = filas
        grid.id = View.generateViewId()

        //parametros de layout
        val params = GridLayout.LayoutParams()
        params.width = GridLayout.LayoutParams.MATCH_PARENT
        params.height = GridLayout.LayoutParams.MATCH_PARENT
        grid.layoutParams = params

        //recoge la vista contenedor donde se colocara el grid
        val contenedor = findViewById<ConstraintLayout>(R.id.lyContenedor)
        //limpiar contenedor
        contenedor.removeAllViews()
        //agregar grid al contenedor
        contenedor.addView(grid)
    }


    /**
     * Crea los botones y los coloca en el grid
     *
     */
    private fun crearBotones() {
        val filas = grid.rowCount
        val columnas = grid.columnCount
        //generar botones
        arrayBotones = arrayOfNulls(filas * columnas)
        //bucle de filas y columnas para ir generando los botones
        //y colocandolos en la cuadricula
        var i = 0
        for (f in 0..<filas) {
            for (c in 0..<columnas) {
                //generar boton
                val btn: Button = crearBoton("" + (i + 1), c, f, i)
                //agregarlo en la cuadricula
                grid.addView(btn)
                //listener del click al boton
                btn.setOnClickListener { v ->
                    onBotonClicked(v)
                }
                //almacenamiento del boton en el array de botones
                arrayBotones[i] = btn
                i++
            }
        }
    }

    /**
     * Crea un boton segun los parametros suministrados
     *
     * @param etiqueta Etiqueta de texto del boton
     * @param columna Columna para el parametro de layout
     * @param fila Fila para el parametro de layout
     * @param i Indice del boton
     */
    private fun crearBoton(etiqueta: String, columna: Int, fila: Int, i: Int): Button {
        //creacion del boton y caracteristicas basicas
        val btn: Button = Button(this)
        btn.text = etiqueta
        btn.setTextColor(ResourcesCompat.getColorStateList(resources, R.color.texto_boton, null))
        btn.id = View.generateViewId()
        btn.typeface = ResourcesCompat.getFont(this, R.font.betty_jane_family);
        btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40f)

        btn.setBackgroundResource(R.drawable.boton)
        //color del boton
        val idColor = listaColores.getResourceId(i.mod(listaColores.length()), 0)
        ViewCompat.setBackgroundTintMode(btn,PorterDuff.Mode.OVERLAY)
        ViewCompat.setBackgroundTintList(
            btn,
            ResourcesCompat.getColorStateList(resources, idColor, null)
        );

        //Definicion de los parametros de layout
        // fila, columna y weight para los Gridlayout params
        val params: GridLayout.LayoutParams =
            GridLayout.LayoutParams(GridLayout.spec(fila, 1, 1f), GridLayout.spec(columna, 1, 1f))
        params.height = 0
        params.width = 0
        //margen
        val margen: Int =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, resources.displayMetrics)
                .toInt();
        params.setMargins(margen, margen, margen, margen)
        btn.layoutParams = params

        //retorno del boton creado
        return btn
    }


    /**
     * Gestiona la pulsacion de un boton de la cuadricula
     *
     * @param v View del boton pulsado
     */
    private fun onBotonClicked(v: View?) {
        //recorrer la lista de botones para ver el pulsado
        arrayBotones.forEachIndexed forEach@{ index, btn ->
            if (btn?.id == v?.id) {
                //enviar a la logica el indice del boton pulsado
                juego.registrarRespuesta(index)
                return@forEach
            }
        }
    }

    /**
     * Activa/desactiva los botones de la cuacricula haciendo que el usuario pueda o no pueda clicarlos
     *
     * @param activado True para habilitar, False para deshabilitar
     */
    private fun activarBotones(activado: Boolean) {
        arrayBotones.forEach { b -> b?.isEnabled = activado }
    }


    //METODOS PUBLICOS*****************************************************************************

    /**
     * Configura la gui para una partida
     * @param filas filas de la cuadricula
     * @param columnas columnas de la cuadricula
     */
    public fun iniciarPartida(filas: Int, columnas: Int) {
        //carga el layout partida
        setContentView(R.layout.partida)
        //configura el boton de volver al menu inicial
        val btnMenu = findViewById<Button>(R.id.btnMenu)
        btnMenu.setOnClickListener {
            menuInicial()
        }
        //oculta el boton de volver al menu (se mostrara cuando termine la partida)
        btnMenu.isVisible = false

        //agregar grid para  los botones
        crearGrid(filas, columnas)

        //crear botones
        crearBotones()
    }


    /**
     * Gestiona el inicio de un nuevo nivel
     *
     * @param pantalla Informacion sobre los datos de la pantalla actual (nivel, secuencia, velocidad...)
     */
    public fun iniciarNivel(pantalla: Pantalla) {
        //poner texto nivel
        findViewById<TextView>(R.id.lbNivelValor).text = pantalla.nivel.toString()
        //desactivar botones para que el usuario no pueda clicarlos
        activarBotones(false)
        //Crear la animacion de la secuencia de botones
        AnimacionSecuencia(pantalla, this).start()
    }


    /**
     * Ilumina/apaga un boton
     *
     * @param i Indice del array de botones
     * @param encendido True si debe ser encendido. False si debe ser apagado
     */
    public fun iluminarBoton(i: Int, encendido: Boolean) {
        arrayBotones[i]?.isPressed = encendido
    }


    /**
     * Configura la GUI para mostrar la informacion de una respuesta erronea y fin de partida
     *
     * @param idIncorrecto Indice del boton incorrecto pulsado
     * @param idCorrecto Indice del boton que se deberia haber pulsado
     */
    fun respuestaErronea(idIncorrecto: Int, idCorrecto: Int) {
        //desactivar los botones
        activarBotones(false)
        //poner botones a rojo si es el incorrecto, verde si es el correcto y gris si no es ninungo de ellos
        arrayBotones.forEachIndexed { index, btn ->
            when (index) {
                idIncorrecto -> {
                    ViewCompat.setBackgroundTintList(
                        btn!!,
                        ResourcesCompat.getColorStateList(resources, R.color.boton_error, null)
                    )
                    btn.setText(R.string.x)
                }

                idCorrecto -> {
                    ViewCompat.setBackgroundTintList(
                        btn!!,
                        ResourcesCompat.getColorStateList(resources, R.color.boton_acierto, null)
                    )
                    btn.setText(R.string.ok)
                }

                else -> ViewCompat.setBackgroundTintList(
                    btn!!,
                    ResourcesCompat.getColorStateList(resources, R.color.boton_gris, null)
                )
            };
        }

        //mostrar el boton de volver al menu inicial
        findViewById<Button>(R.id.btnMenu).isVisible = true
    }

    /**
     * Configura la pantalla para la animacion de un nivel terminado desactivando los botones y
     * mostrando la animacion de exito
     */
    fun pantallaNivelTerminado() {
        activarBotones(false)
        AnimacionExito(this).start()
    }

    /**
     * Pone la GUI en estado de exito pintando el fondo de verde
     * @param activado True si debe ser pintada de verde, False si debe volver al estado original
     */
    fun exito(activado: Boolean) {

        if (activado)
            grid.setBackgroundColor(resources.getColor(R.color.verdeExito, null))
        else
            grid.setBackgroundColor(Color.TRANSPARENT)
    }

    /**
     * Acciones a ejecutar tras terminar la animacion de exito
     */
    fun onAnimacionExitoTerminada() {
        //avisar a la logica
        juego.comenzarSiguienteNivel()
    }

    /**
     * Acciones a ejecutar tras terminar la animacion de la secuencia de botones del nivel
     */
    fun onAnimacionSecuenciaTerminada() {
        //activar los botones
        activarBotones(true);
    }

}//fin de clase

