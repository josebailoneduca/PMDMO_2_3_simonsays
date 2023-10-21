package com.imagenprogramada.simonsays

enum class EDificultad(val filas: Int, val columnas: Int, val velocidad:Float,val nombre: Int) {
    EASY(2,2,1f,R.string.easy),
    MEDIUM(4, 2, 1.4f, R.string.medium),
    HARD(5,2, 1.8f,R.string.hard),
    INSANE(6,3, 2f,R.string.insane);

}