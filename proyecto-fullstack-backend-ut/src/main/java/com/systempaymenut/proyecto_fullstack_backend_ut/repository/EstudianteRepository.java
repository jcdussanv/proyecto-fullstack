package com.systempaymenut.proyecto_fullstack_backend_ut.repository;

import java.util.List;
import java.lang.String;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systempaymenut.proyecto_fullstack_backend_ut.entities.Estudiante;
import com.systempaymenut.proyecto_fullstack_backend_ut.enums.PagoStatus;
import com.systempaymenut.proyecto_fullstack_backend_ut.enums.TypePago;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, String> {
    // Método personalizado para buscar un estudiante por su código
    com.systempaymenut.proyecto_fullstack_backend_ut.repository.Estudiante findByCodigo(String codigo);

    // Método personalizado para obtener la lista de estudiantes pertenecientes a un
    // programa específico
    List<Estudiante> findByProgramaId(PagoStatus status);

    // lista de pagos segun el tipo seleccionado
    List<Estudiante> findByType(TypePago type);

}