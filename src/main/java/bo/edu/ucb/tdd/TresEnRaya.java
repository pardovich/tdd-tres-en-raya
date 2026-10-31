package bo.edu.ucb.tdd;

public class TresEnRaya {

    private static final int TAMANIO = 3;
    private static final char VACIO = '\0';

    private final char[][] tablero = new char[TAMANIO][TAMANIO];
    private char ultimoJugador = VACIO;

    public String jugar(int x, int y) {
        validarEje(x, "X");
        validarEje(y, "Y");
        char jugador = siguienteJugador();
        colocar(x, y, jugador);
        ultimoJugador = jugador;

        if (hayGanador()) {
            return ultimoJugador + " es el ganador";
        }
        if (tableroLleno()) {
            return "Empate";
        }
        return "Sin ganador";
    }

    public char siguienteJugador() {
        return ultimoJugador == 'X' ? 'O' : 'X';
    }

    private void validarEje(int valor, String eje) {
        if (valor < 1 || valor > TAMANIO) {
            throw new RuntimeException(eje + " está fuera del tablero");
        }
    }

    private void colocar(int x, int y, char jugador) {
        if (tablero[x - 1][y - 1] != VACIO) {
            throw new RuntimeException("La casilla está ocupada");
        }
        tablero[x - 1][y - 1] = jugador;
    }

    private boolean hayGanador() {
        int diagonal = 0;
        int antidiagonal = 0;
        for (int i = 0; i < TAMANIO; i++) {
            int fila = 0;
            int columna = 0;
            for (int j = 0; j < TAMANIO; j++) {
                if (tablero[i][j] == ultimoJugador) columna++;
                if (tablero[j][i] == ultimoJugador) fila++;
            }
            if (fila == TAMANIO || columna == TAMANIO) return true;
            if (tablero[i][i] == ultimoJugador) diagonal++;
            if (tablero[i][TAMANIO - 1 - i] == ultimoJugador) antidiagonal++;
        }
        return diagonal == TAMANIO || antidiagonal == TAMANIO;
    }

    private boolean tableroLleno() {
        for (char[] columna : tablero) {
            for (char casilla : columna) {
                if (casilla == VACIO) return false;
            }
        }
        return true;
    }
}
