package com.example.application;

public class TicTacToeGame {

    private final String ESPACIO_VACIO = " ";
    public String simboloJugador1;
    //    private String jugadorActual;
    private String jugadorActual = "O";
    private String nombreJugador1;
    private String nombreJugador2;
    private String[][] tablero = {
            {ESPACIO_VACIO, ESPACIO_VACIO, ESPACIO_VACIO},
            {ESPACIO_VACIO, ESPACIO_VACIO, ESPACIO_VACIO},
            {ESPACIO_VACIO, ESPACIO_VACIO, ESPACIO_VACIO}
    };

    // Nueva variable para almacenar el símbolo del jugador inicial
    private String simboloJugadorPrincipio;

    public String getsimboloJugadorActual() {
        return jugadorActual;
    }

    //     Nuevo método para establecer el símbolo del jugador inicial
    public void setSimboloJugadorPrincipio(String simbolo) {
        simboloJugadorPrincipio = simbolo;
        jugadorActual = simbolo;  // Establece el primer jugador como el jugador inicial
    }


    public void hacerMovimiento(int fila, int colum) {
        // Verificar si la celda está vacía
        if (tablero[fila][colum].equals(ESPACIO_VACIO)) {
            // Establecer el símbolo del jugador actual en la celda
            tablero[fila][colum] = jugadorActual;

            // Cambiar al otro jugador
            jugadorActual = jugadorActual.equals("X") ? "O" : "X";
        }
    }


    public boolean ganaJuego() {
        // Verificar filas
        for (int fila = 0; fila < 3; fila++) {
            if (verificarFilasColum(tablero[fila][0], tablero[fila][1], tablero[fila][2])) {
                return true;
            }
        }

        // Verificar columnas
        for (int colum = 0; colum < 3; colum++) {
            if (verificarFilasColum(tablero[0][colum], tablero[1][colum], tablero[2][colum])) {
                return true;
            }
        }

        // Verificar diagonales
        if (verificarFilasColum(tablero[0][0], tablero[1][1], tablero[2][2]) || verificarFilasColum(tablero[0][2], tablero[1][1], tablero[2][0])) {
            return true;
        }

        return false;
    }

    private boolean verificarFilasColum(String p1, String p2, String p3) {
        return (!p1.equals(ESPACIO_VACIO) && p1.equals(p2) && p1.equals(p3));
    }

    public String getSimboloJugador1() {
        return simboloJugador1;
    }


    public void setNombreJugador1(String nombreJugador1) {
        this.nombreJugador1 = nombreJugador1;
    }

    public void setNombreJugador2(String nombreJugador2) {
        this.nombreJugador2 = nombreJugador2;
    }

    // Opcionalmente, puedes añadir métodos para obtener los nombres de los jugadores
    public String getNombreJugador1() {
        return nombreJugador1;
    }

    public String getNombreJugador2() {
        return nombreJugador2;
    }

    public boolean empate() {
        // Verificar si todas las celdas están ocupadas
        for (int fila = 0; fila < 3; fila++) {
            for (int colum = 0; colum < 3; colum++) {
                if (tablero[fila][colum].equals(ESPACIO_VACIO)) {
                    // Todavía hay al menos una celda vacía, el juego no está en empate
                    return false;
                }
            }
        }

        // Si no hay celdas vacías, el juego está en empate
        return true;
    }

    public void cambiarJugador() {
        jugadorActual = jugadorActual.equals("X") ? "O" : "X";
    }

    public void reiniciarJuego() {
        // Reinicia el tablero
        for (int fila = 0; fila < 3; fila++) {
            for (int colum = 0; colum < 3; colum++) {
                tablero[fila][colum] = ESPACIO_VACIO;
            }
        }

        // Reinicia el símbolo del jugador actual al símbolo del jugador inicial
        jugadorActual = simboloJugadorPrincipio;
    }

    public Object getsimboloJugador1() {
        return simboloJugador1;
    }
}