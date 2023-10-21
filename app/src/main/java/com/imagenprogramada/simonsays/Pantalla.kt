package com.imagenprogramada.simonsays

/**
 * DTO de los datos de un nivel de juego
 *
 * @param nivel Numero de nivel
 * @param secuencia Secuencia de botones a pulsar para el nivel
 * @param espera Tiempo de espera en ms entre la iluminacion de cada boton
 * @param activo Tiempo en ms que debe permanecer encendido cada boton
 *
 * @author Jose Javier Bailon Ortiz
 */
class Pantalla(val nivel:Int, val secuencia:Array<Int>, val espera:Long, val activo:Long) {

}