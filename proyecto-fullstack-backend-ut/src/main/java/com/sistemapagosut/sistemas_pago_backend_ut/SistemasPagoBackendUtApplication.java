package com.sistemapagosut.sistemas_pago_backend_ut;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.systempaymenut.proyecto_fullstack_backend_ut.entities.Estudiante;
import com.systempaymenut.proyecto_fullstack_backend_ut.entities.Pago;
import com.systempaymenut.proyecto_fullstack_backend_ut.enums.PagoStatus;
import com.systempaymenut.proyecto_fullstack_backend_ut.enums.TypePago;
import com.systempaymenut.proyecto_fullstack_backend_ut.repository.EstudianteRepository;
import com.systempaymenut.proyecto_fullstack_backend_ut.repository.PagoRepository;

@SpringBootApplication
public class SistemasPagoBackendUtApplication {

	SistemasPagoBackendUtApplication(PagoRepository pagoRepository) {
	}

	public static void main(String[] args) {
		SpringApplication.run(SistemasPagoBackendUtApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(EstudianteRepository estudianteRepository, PagoRepository pagoRepository) {
		return args -> {
			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Edier")
					.apellido("Bermudez")
					.codigo("0001")
					.programaId("ISI2025")
					.build());

			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Ana")
					.apellido("Peres")
					.codigo("0002")
					.programaId("ISI2025")
					.build());

			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Gabriel")
					.apellido("cuero")
					.codigo("0003")
					.programaId("ISI2025")
					.build());

			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Jhonatan")
					.apellido("Morales")
					.codigo("0004")
					.programaId("ISI2025")
					.build());

			// obtener tipo de pago para diferentes para cada estudiante
			TypePago typePago[] = TypePago.values();

			Random random = new Random();

			estudianteRepository.findAll().forEach(estudiante -> {

				for (int i = 0; i < 10; i++) {
					int index = random.nextInt(typePago.length);

					// construir un objeto Pago con valores aleatorios
					Pago pago = Pago.builder()
							.cantidad(1000 + (int) (Math.random() * 20000))
							.type(typePago[index])
							.status(PagoStatus.CREADO)
							.fecha(LocalDate.now())
							.estudiante(estudiante)
							.build();

					pagoRepository.save(pago);

				}
			});

		};
	}

}