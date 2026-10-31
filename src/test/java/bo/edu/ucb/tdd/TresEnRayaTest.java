package bo.edu.ucb.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TresEnRayaTest {

    private TresEnRaya juego;

    @BeforeEach
    void setUp() {
        juego = new TresEnRaya();
    }

    @Test
    @DisplayName("R1: X fuera del tablero lanza excepción")
    void cuandoXFueraDelTableroEntoncesExcepcion() {
        RuntimeException e = assertThrows(RuntimeException.class, () -> juego.jugar(5, 2));
        assertEquals("X está fuera del tablero", e.getMessage());
    }

    @Test
    @DisplayName("R1: Y fuera del tablero lanza excepción")
    void cuandoYFueraDelTableroEntoncesExcepcion() {
        RuntimeException e = assertThrows(RuntimeException.class, () -> juego.jugar(2, 5));
        assertEquals("Y está fuera del tablero", e.getMessage());
    }

    @Test
    @DisplayName("R1: casilla ocupada lanza excepción")
    void cuandoCasillaOcupadaEntoncesExcepcion() {
        juego.jugar(2, 1);
        RuntimeException e = assertThrows(RuntimeException.class, () -> juego.jugar(2, 1));
        assertEquals("La casilla está ocupada", e.getMessage());
    }

    @Test
    @DisplayName("R2: el primer turno es de X")
    void primerTurnoEsDeX() {
        assertEquals('X', juego.siguienteJugador());
    }

    @Test
    @DisplayName("R2: después de X juega O")
    void despuesDeXJuegaO() {
        juego.jugar(1, 1);
        assertEquals('O', juego.siguienteJugador());
    }

    @Test
    @DisplayName("R3: sin línea completa no hay ganador")
    void cuandoJuegaSinCompletarLineaEntoncesSinGanador() {
        assertEquals("Sin ganador", juego.jugar(1, 1));
    }

    @Test
    @DisplayName("R3: línea horizontal completa gana")
    void cuandoLineaHorizontalEntoncesGana() {
        juego.jugar(1, 1);
        juego.jugar(1, 2);
        juego.jugar(2, 1);
        juego.jugar(2, 2);
        assertEquals("X es el ganador", juego.jugar(3, 1));
    }

    @Test
    @DisplayName("R3: línea vertical completa gana")
    void cuandoLineaVerticalEntoncesGana() {
        juego.jugar(2, 1);
        juego.jugar(1, 1);
        juego.jugar(3, 1);
        juego.jugar(1, 2);
        juego.jugar(2, 2);
        assertEquals("O es el ganador", juego.jugar(1, 3));
    }

    @Test
    @DisplayName("R3: diagonal principal gana")
    void cuandoDiagonalPrincipalEntoncesGana() {
        juego.jugar(1, 1);
        juego.jugar(1, 2);
        juego.jugar(2, 2);
        juego.jugar(1, 3);
        assertEquals("X es el ganador", juego.jugar(3, 3));
    }

    @Test
    @DisplayName("R3: diagonal secundaria gana")
    void cuandoDiagonalSecundariaEntoncesGana() {
        juego.jugar(1, 3);
        juego.jugar(1, 1);
        juego.jugar(2, 2);
        juego.jugar(1, 2);
        assertEquals("X es el ganador", juego.jugar(3, 1));
    }

    @Test
    @DisplayName("R4: tablero lleno sin ganador es empate")
    void cuandoTableroLlenoEntoncesEmpate() {
        juego.jugar(1, 1);
        juego.jugar(1, 2);
        juego.jugar(1, 3);
        juego.jugar(2, 1);
        juego.jugar(2, 3);
        juego.jugar(2, 2);
        juego.jugar(3, 1);
        juego.jugar(3, 3);
        assertEquals("Empate", juego.jugar(3, 2));
    }
}
