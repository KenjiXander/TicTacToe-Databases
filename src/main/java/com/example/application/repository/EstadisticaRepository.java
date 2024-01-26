package com.example.application.repository;

import com.example.application.model.Estadistica;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EstadisticaRepository extends MongoRepository<Estadistica, String> {
    // Puedes agregar consultas personalizadas si es necesario
}
