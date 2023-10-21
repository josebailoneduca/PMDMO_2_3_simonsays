package com.imagenprogramada.simonsays

/**
 * Almacena los niveles de dificultad y los valores de los mismos
 *
 * @param filas Filas de cuadricula para el nivel de dificultad
 * @param columnas Columnas de cuadricula para el nivel de dificultad
 * @param velocidad Establece la velocidad para el nivel de dificultad (ver)
 * @param nombre Nombre del nivel de dificultad
 * @param orden Indice de orden para los menus
 * @see com.imagenprogramada.simonsays.Juego.comenzarSiguienteNivel  Para ver el calculo de la aplicacion de velocidad
 *
 * @author Jose Javier Bailon Ortiz
 */
enum class EDificultad(val filas: Int, val columnas: Int, val velocidad:Float,val nombre: Int) {
    EASY(2,2,1f,R.string.easy),
    MEDIUM(4, 2, 1.4f, R.string.medium),
    HARD(5,2, 1.8f,R.string.hard),
    INSANE(6,3, 2f,R.string.insane);

}