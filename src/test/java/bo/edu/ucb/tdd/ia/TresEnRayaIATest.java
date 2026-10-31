package bo.edu.ucb.tdd.ia;

import bo.edu.ucb.tdd.TresEnRaya;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Pruebas generadas por IA con el prompt de ia/prompt.md */
class TresEnRayaIATest {

    private TresEnRaya juego;

    @BeforeEach
    void setUp() { juego = new TresEnRaya(); }

    @Test @DisplayName("x = 0 lanza excepción")
    void xCeroLanzaExcepcion() { assertThrows(RuntimeException.class, () -> juego.jugar(0, 1)); }

    @Test @DisplayName("x = 4 lanza excepción")
    void xCuatroLanzaExcepcion() { assertThrows(RuntimeException.class, () -> juego.jugar(4, 1)); }

    @Test @DisplayName("y = 0 lanza excepción")
    void yCeroLanzaExcepcion() { assertThrows(RuntimeException.class, () -> juego.jugar(1, 0)); }

    @Test @DisplayName("y = 4 lanza excepción")
    void yCuatroLanzaExcepcion() { assertThrows(RuntimeException.class, () -> juego.jugar(1, 4)); }

    @Test @DisplayName("coordenadas negativas lanzan excepción")
    void coordenadasNegativasLanzanExcepcion() { assertThrows(RuntimeException.class, () -> juego.jugar(-1, -1)); }

    @Test @DisplayName("las 4 esquinas son válidas")
    void esquinasSonValidas() {
        assertDoesNotThrow(() -> juego.jugar(1, 1));
        assertDoesNotThrow(() -> juego.jugar(3, 3));
        assertDoesNotThrow(() -> juego.jugar(1, 3));
        assertDoesNotThrow(() -> juego.jugar(3, 1));
    }

    @Test @DisplayName("casilla ocupada lanza excepción")
    void casillaOcupadaLanzaExcepcion() {
        juego.jugar(2, 2);
        assertThrows(RuntimeException.class, () -> juego.jugar(2, 2));
    }

    @Test @DisplayName("empieza X")
    void empiezaX() { assertEquals('X', juego.siguienteJugador()); }

    @Test @DisplayName("los turnos se alternan X, O, X, O")
    void turnosSeAlternan() {
        juego.jugar(1, 1); assertEquals('O', juego.siguienteJugador());
        juego.jugar(1, 2); assertEquals('X', juego.siguienteJugador());
        juego.jugar(1, 3); assertEquals('O', juego.siguienteJugador());
    }

    @Test @DisplayName("una jugada inválida no cambia el turno")
    void jugadaInvalidaNoCambiaTurno() {
        juego.jugar(1, 1);
        assertThrows(RuntimeException.class, () -> juego.jugar(1, 1));
        assertEquals('O', juego.siguienteJugador());
    }

    @Test @DisplayName("primera jugada devuelve Sin ganador")
    void primeraJugadaSinGanador() { assertEquals("Sin ganador", juego.jugar(2, 2)); }

    @Test @DisplayName("X gana en la fila 1")
    void xGanaFila1() { jugarSecuencia(1,1, 1,2, 2,1, 2,2); assertEquals("X es el ganador", juego.jugar(3, 1)); }

    @Test @DisplayName("X gana en la fila 2")
    void xGanaFila2() { jugarSecuencia(1,2, 1,1, 2,2, 2,1); assertEquals("X es el ganador", juego.jugar(3, 2)); }

    @Test @DisplayName("X gana en la fila 3")
    void xGanaFila3() { jugarSecuencia(1,3, 1,1, 2,3, 2,1); assertEquals("X es el ganador", juego.jugar(3, 3)); }

    @Test @DisplayName("X gana en la columna 1")
    void xGanaColumna1() { jugarSecuencia(1,1, 2,1, 1,2, 2,2); assertEquals("X es el ganador", juego.jugar(1, 3)); }

    @Test @DisplayName("X gana en la columna 2")
    void xGanaColumna2() { jugarSecuencia(2,1, 1,1, 2,2, 1,2); assertEquals("X es el ganador", juego.jugar(2, 3)); }

    @Test @DisplayName("X gana en la columna 3")
    void xGanaColumna3() { jugarSecuencia(3,1, 1,1, 3,2, 1,2); assertEquals("X es el ganador", juego.jugar(3, 3)); }

    @Test @DisplayName("X gana en la diagonal principal")
    void xGanaDiagonalPrincipal() { jugarSecuencia(1,1, 2,1, 2,2, 3,1); assertEquals("X es el ganador", juego.jugar(3, 3)); }

    @Test @DisplayName("X gana en la diagonal secundaria")
    void xGanaDiagonalSecundaria() { jugarSecuencia(3,1, 1,1, 2,2, 2,1); assertEquals("X es el ganador", juego.jugar(1, 3)); }

    @Test @DisplayName("O gana en una columna")
    void oGanaColumna() { jugarSecuencia(1,1, 2,1, 3,3, 2,2, 1,3); assertEquals("O es el ganador", juego.jugar(2, 3)); }

    @Test @DisplayName("tablero lleno sin ganador es Empate")
    void empate() { jugarSecuencia(1,1, 2,1, 3,1, 2,2, 1,2, 1,3, 2,3, 3,2); assertEquals("Empate", juego.jugar(3, 3)); }

    @Test @DisplayName("ganar en la última casilla devuelve ganador y no empate")
    void ganarEnUltimaCasillaNoEsEmpate() {
        // Corregido: la secuencia original de la IA no formaba ninguna línea para X
        jugarSecuencia(1,1, 2,1, 3,1, 3,2, 1,2, 1,3, 2,2, 2,3);
        assertEquals("X es el ganador", juego.jugar(3, 3));
    }

    @Test @DisplayName("no se puede jugar después de que alguien gana")
    @Disabled("Requisito inventado por la IA: la kata no define qué pasa tras una victoria")
    void noSePuedeJugarTrasGanar() {
        jugarSecuencia(1,1, 1,2, 2,1, 2,2, 3,1);
        assertThrows(RuntimeException.class, () -> juego.jugar(3, 3));
    }

    private void jugarSecuencia(int... coords) {
        for (int i = 0; i < coords.length; i += 2) juego.jugar(coords[i], coords[i + 1]);
    }
}
