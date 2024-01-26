package com.example.application.repository;

import com.example.application.model.Jugador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JugadorRepository extends MongoRepository<Jugador, String> {

    // Buscar un jugador por nombre
    Optional<Jugador> findByNombre(String nombre);

    // Encuentra jugadores con un número específico de partidas ganadas
    @Query("{ 'partidasGanadas' : ?0 }")
    List<Jugador> findByPartidasGanadas(int partidasGanadas);

    // Otros métodos personalizados que puedas necesitar
}
