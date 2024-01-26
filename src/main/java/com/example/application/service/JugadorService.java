package com.example.application.service;

import com.example.application.model.Jugador;
import com.example.application.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;

    @Autowired
    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    public List<Jugador> findAllJugadores() {
        return jugadorRepository.findAll();
    }

    public Optional<Jugador> findJugadorPorNombre(String nombre) {
        return jugadorRepository.findByNombre(nombre);
    }

    public Jugador saveJugador(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    public void deleteJugador(String id) {
        jugadorRepository.deleteById(id);
    }

    public void actualizarEstadisticasVictoria(String nombreJugador) {
        Optional<Jugador> jugadorOpt = findJugadorPorNombre(nombreJugador);
        if (jugadorOpt.isPresent()) {
            Jugador jugador = jugadorOpt.get();
            jugador.setPartidasJugadas(jugador.getPartidasJugadas() + 1);
            jugador.setPartidasGanadas(jugador.getPartidasGanadas() + 1);
            saveJugador(jugador);
        }
    }

    public void actualizarEstadisticasEmpate(String nombreJugador1, String nombreJugador2) {
        actualizarEstadisticasPorEmpate(nombreJugador1);
        actualizarEstadisticasPorEmpate(nombreJugador2);
    }

    private void actualizarEstadisticasPorEmpate(String nombreJugador) {
        Optional<Jugador> jugadorOpt = findJugadorPorNombre(nombreJugador);
        if (jugadorOpt.isPresent()) {
            Jugador jugador = jugadorOpt.get();
            jugador.setPartidasJugadas(jugador.getPartidasJugadas() + 1);
            jugador.setPartidasEmpatadas(jugador.getPartidasEmpatadas() + 1);
            saveJugador(jugador);
        }
    }
}
