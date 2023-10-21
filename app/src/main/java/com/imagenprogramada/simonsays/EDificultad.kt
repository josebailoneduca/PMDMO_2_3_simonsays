package com.imagenprogramada.simonsays

enum class EDificultad(val filas: Int, val columnas: Int, val velocidad:Float,val nombre: Int) {
    EASY(2,2,1f,R.string.easy),
    MEDIUM(4, 2, 1.5f, R.string.medium),
    HARD(6,2, 2f,R.string.hard);

}