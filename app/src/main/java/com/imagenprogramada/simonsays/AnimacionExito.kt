package com.imagenprogramada.simonsays

/**
 * Ejecuta la animacion de exito en un hilo propio
 *
 * @author Jose Javier Bailon Ortiz
 */
class AnimacionExito(val vista: InicioActivity): Thread() {

    override fun run() {
        //activa y desactiva dos veces consecutivas el estado de exito de la GUI
            for (i in 1..2) {
                vista?.runOnUiThread {
                    vista.exito(true)
                }
                Thread.sleep(150)
                vista?.runOnUiThread {
                    vista.exito(false)
                }
                Thread.sleep(150)
            }
        //avisar a la activity de que la animacion ha terminado
        vista?.runOnUiThread {
            vista.onAnimacionExitoTerminada();
        }
    }

}
