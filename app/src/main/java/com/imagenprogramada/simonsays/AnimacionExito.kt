package com.imagenprogramada.simonsays

class AnimacionExito(val vista: InicioActivity): Thread() {

    override fun run() {
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
        vista?.runOnUiThread {
            vista.finExito();
        }
        }

}
