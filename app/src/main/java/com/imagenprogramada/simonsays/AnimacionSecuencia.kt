package com.imagenprogramada.simonsays

/**
 * Ejecuta la animacion de iluminar los botones segun una secuencia en un hilo propio.
 * Va recorriendo la secuencia e iluminando y apagando los botones haciendo las esperas apropiadas
 * segun determinan los datos de la secuencia
 *
 * @author Jose Javier Bailon Ortiz
 */
class AnimacionSecuencia(val pantalla:Pantalla,val vista:InicioActivity): Thread() {
    override fun run() {
        //recorrer los pasos de la secuencia
        pantalla.secuencia.forEach {
            //espera apagada
            Thread.sleep(pantalla.espera)
            //iluminar el boton
            vista.iluminarBoton(it, true)
            //espera iluminada
            Thread.sleep(pantalla.activo)
            //apagar boton
            vista.iluminarBoton(it, false)
        }
        //avisar a la activity de que la animacion de secuencia ha terminado
        vista?.runOnUiThread {
            vista.onAnimacionSecuenciaTerminada()
        }

    }
}