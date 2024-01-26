package com.example.application.service;

import com.example.application.model.Jugador;
import com.example.application.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    // Agrega aquí otros métodos de servicio que necesites.
}
