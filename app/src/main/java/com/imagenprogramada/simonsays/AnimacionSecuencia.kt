package com.imagenprogramada.simonsays

class AnimacionSecuencia(val pantalla:Pantalla,val vista:InicioActivity): Thread() {
    override fun run() {
        pantalla.secuencia.forEach {
            Thread.sleep(pantalla.espera)
            vista.iluminarBoton(it, true)
            Thread.sleep(pantalla.activo)
            vista.iluminarBoton(it, false)
        }
        vista?.runOnUiThread {
            vista.activarUsuario(true);
        }

    }
}