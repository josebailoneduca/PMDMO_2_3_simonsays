package com.imagenprogramada.simonsays

import kotlin.random.Random

class Juego(val vista: InicioActivity) {
    var dificultad:EDificultad = EDificultad.EASY
        get ()=field
        set(d){field=d}

    var nivel:Int=0
        get ()=field
        set(n){field=n}

    lateinit var secuencia:ArrayList<Int>
    lateinit var respuestas:ArrayList<Int>
    fun iniciarJuego(dificultad: EDificultad) {
            this.dificultad=dificultad
            this.nivel=0
            this.secuencia=ArrayList<Int>()
            vista.iniciarPartida(dificultad.filas,dificultad.columnas);

            siguienteNivel()
    }


    public fun siguienteNivel() {
        nivel++;
        this.respuestas=ArrayList<Int>()
        val espera =((1000L/dificultad.velocidad)-(100*(nivel/2))).toLong().coerceAtLeast(150)
        val activo = (300L+(200/dificultad.velocidad)-(50*(nivel/2))).toLong().coerceAtLeast(200)
        val pantalla = Pantalla(this.nivel,siguienteSecuencia(),espera,activo)
        vista.iniciarNivel(pantalla)
    }

    private fun siguienteSecuencia(): Array<Int> {
        secuencia.add((0..<(dificultad.filas*dificultad.columnas)).random())
        return secuencia.toTypedArray()
    }

    fun registrarRespuesta(indice: Int) {
        this.respuestas.add(indice)
        this.respuestas.forEachIndexed forEach@{ index, id->
                if (secuencia.get(index)!=id){
                    vista.respuestaErronea(id,secuencia.get(index))
                    return
                }
            }
        if (respuestas.size==secuencia.size)
            vista.pantallaNivelTerminado()
    }

}