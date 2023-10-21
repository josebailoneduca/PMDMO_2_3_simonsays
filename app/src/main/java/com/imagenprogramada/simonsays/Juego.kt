package com.imagenprogramada.simonsays

/**
 *
 * Logica de control del juego.
 * @param vista referencia a la activity de la app
 *
 * @author Jose Javier Bailon Ortiz
 */
class Juego(val vista: InicioActivity) {

    //ATRIBUTOS*************************************************************************************
    /**
     * Dificultad de la partida actual
     */
    var dificultad:EDificultad = EDificultad.EASY
        get ()=field
        set(d){field=d}

    /**
     * Nivel de la partida actual
     */
    var nivel:Int=0
        get ()=field
        set(n){field=n}

    /**
     * Almacena la secuencia de botones de la partida. Se va agrandando conforme pasan los niveles
     */
    lateinit var secuencia:ArrayList<Int>

    /**
     * Respuestas del jugador en el nivel actual
     */
    lateinit var respuestas:ArrayList<Int>

    /**
     * Inicia una nueva partida estableciendo los parametros de la misma, avisando a la GUI del inicio de partida
     * e iniciando un nuevo nivel
     *
     * @param dificultad Dificultad de la partida
     */
    fun iniciarPartida(dificultad: EDificultad) {
            //configuraciones de parametros de partida
            this.dificultad=dificultad
            this.nivel=0
            //reseteo de la secuencia
            this.secuencia=ArrayList<Int>()
            //aviso a la GUI de que tiene que mostrar el inicio de una partida
            vista.iniciarPartida(dificultad.filas,dificultad.columnas);
            //comenzar el siguiente nivel
            comenzarSiguienteNivel()
    }


    /**
     * Comienza el siguiente nivel de la partida configurando sus parametros y avisando a al GUI
     * de que inicie el nivel
     */
    public fun comenzarSiguienteNivel() {
        //aumenta el nivel
        nivel++;
        //resetea las respuestas
        this.respuestas=ArrayList<Int>()

        //Calcula los paramtros del nivel

        //la espera entre encendido de botones se calcula segun la formula:
        // (600/velocidad)-(100 por cada 2 niveles avanzados) con 150 como minimo
        val espera =((600L/dificultad.velocidad)-(100*(nivel/2))).toLong().coerceAtLeast(150)

        //El tiempo que pasan los botones encendidos se calcula con la formula:
        // 300+(200/velocidad)-(50 por cada 2 niveles avanzados) con 200 como minimo
        val activo = (300L+(200/dificultad.velocidad)-(50*(nivel/2))).toLong().coerceAtLeast(200)

        //genera el DTO con los datos del nivel, secuencia a acertar, y tiempos de animacion
        val pantalla = Pantalla(this.nivel,siguienteSecuencia(),espera,activo)

        //avisa a la GUI de que inicie el nivel
        vista.iniciarNivel(pantalla)
    }

    /**
     * Incrementa la secuencia de botones a pulsar en uno mas al azar
     */
    private fun siguienteSecuencia(): Array<Int> {
        val nuevoBoton = (0..<(dificultad.filas*dificultad.columnas)).random()
        secuencia.add(nuevoBoton)
        return secuencia.toTypedArray()
    }

    /**
     * Registra una pulsacion del usuario y la agrega al registro de pulsaciones para el nivel actual
     * Comprueba si la secuencia pulsada hasta ahora es correcta y segun eso pasa a un nuevo nivel si esta terminada
     * o avisa a la GUI de respuesta erronea
     */
    fun registrarRespuesta(indice: Int) {
        //agregar la respuesta al indica
        this.respuestas.add(indice)
        //comprueba si la secuencia es correcta comparando las respuestas con la secuenca a acertar
        this.respuestas.forEachIndexed forEach@{ index, id->
                if (secuencia.get(index)!=id){
                    //si hay una respuesta incorrecta avisa a la GUI del error
                    vista.respuestaErronea(id,secuencia.get(index))
                    return
                }
            }

        //si la secuencia es completa da el nivel por terminado y avisa a la GUI
        if (respuestas.size==secuencia.size)
            vista.pantallaNivelTerminado()
    }

}