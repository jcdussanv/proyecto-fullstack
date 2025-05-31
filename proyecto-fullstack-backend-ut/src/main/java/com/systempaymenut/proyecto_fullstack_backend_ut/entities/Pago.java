package com.systempaymenut.proyecto_fullstack_backend_ut.entities;

import java.time.LocalDate;

import com.systempaymenut.proyecto_fullstack_backend_ut.enums.PagoStatus;
import com.systempaymenut.proyecto_fullstack_backend_ut.enums.TypePago;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder // permitir construir objetos de esta clase con el patron que se llama builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate fecha;
    private double cantidad;
    private TypePago type;
    private PagoStatus status;
    private String file;

    // relacion en la bd
    @ManyToOne
    private Estudiante estudiante;

}
