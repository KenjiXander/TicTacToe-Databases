package com.example.application.service;

import com.example.application.model.Estadistica;
import com.example.application.repository.EstadisticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadisticaService {

    private final EstadisticaRepository estadisticaRepository;

    @Autowired
    public EstadisticaService(EstadisticaRepository estadisticaRepository) {
        this.estadisticaRepository = estadisticaRepository;
    }

    public List<Estadistica> getAllEstadisticas() {
        return estadisticaRepository.findAll();
    }

    // Puedes agregar más métodos según sea necesario para la lógica de negocios
}
