package com.sistemapagosut.sistemas_pago_backend_ut.services;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.systempaymenut.proyecto_fullstack_backend_ut.entities.Pago;
import com.systempaymenut.proyecto_fullstack_backend_ut.enums.PagoStatus;
import com.systempaymenut.proyecto_fullstack_backend_ut.enums.TypePago;
import com.systempaymenut.proyecto_fullstack_backend_ut.repository.Estudiante;
import com.systempaymenut.proyecto_fullstack_backend_ut.repository.EstudianteRepository;
import com.systempaymenut.proyecto_fullstack_backend_ut.repository.PagoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional // para asegurar que los metodos de la clase se ejecuten dentro de una
               // transaccion
public class PagoService {

    // inyeccion de pago dependencia Pagorepository para interactuar con la bd
    // Pagos
    @Autowired
    private PagoRepository pagoRepository;

    // inyeccion de pendencia de estudianteRepository para obtener la informacion de
    // los
    // estudiantes desde la base de datos
    @Autowired
    private EstudianteRepository EstudianteRepository;

    /**
     * metodo para guardar el pago en la bd y almacenar un file en el servidor
     * 
     * @param file             archivo pdf que se va ha subir al servidor
     * @param cantidad         monto del pago realizado
     * @param type             tipo de pago CHEQUE EFECTIVO TRANSACCION
     *                         DEPOSITO
     * @param date             fecha en la que se realiza el pago
     * @param codigoEstudiante codigo del estudiante que realiza el pago
     * @return objeto pago guradado en la bd
     * @throws IOException excepcion lanzada si ocurre un error al manejar el file
     *                     pdf
     */

    public Pago savePago(MultipartFile file, double cantidad, TypePago type, LocalDate date, String codigoEstudiante)
            throws IOException {

        /**
         * construir la ruta donde se guradara el archivo dentro del sistema
         * System.getProperty("user.home"): obtiene la ruta del directorio personal del
         * usuario actual SO
         * Paths.get : construir una ruta dentro del directorio personal en la
         * carpeta"enset-Data/pagos"
         * 
         */
        Path folderPath = Paths.get(System.getProperty("userhome"), "enset-data", "pagos");

        // verificar si la carpeta ya existe si no la debe crear
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        // generemos un nombre unico para el archivo usando UUID(identifiador unico
        // universal)
        String fileName = UUID.randomUUID().toString();

        // construimos la ruta del archivo agreagado la extension .pdf
        Path filePath = Paths.get(System.getProperty("username"), "enset-data", "pagos", fileName + ".pdf");

        // guardamos el archivo resivido en la ubicaion especificada dentro del sitema
        // de archivos
        Files.copy(file.getInputStream(), filePath);

        // busacamos el estudiante que realiza el pago con su codigo
        Estudiante estudiante = EstudianteRepository.findByCodigo(codigoEstudiante);

        // creamos un nuevo pago utilizando el patron de diseño builder
        Pago pago = Pago.builder()
                .type(type)
                .status(PagoStatus.CREADO)
                .fecha(date)
                .estudiante(estudiante)
                .cantidad(cantidad)
                .file(filePath.toUri().toString())// ruta del archivo pdf almacenado
                .build();// construccion final del pago

        return pagoRepository.save(pago);

    }

    public byte[] getArchivoPorId(Long pagoId) throws IOException {

        // busca un objeto pago en la bd por su ID
        Pago pago = pagoRepository.findById(pagoId).get();

        /**
         * pago.getfile: obtiene la URI del archivo guardado como una cadena de texto
         * URI.create: convierte la caddena de texto en un objeto URI
         * PathOf: convierte la URI en un path para poder acceer al archivo
         * Files.readAllBytes: lee el contenido del archivo y lo devuelve en un array
         * vector de byte
         * esto permite obtener el contenido del archivo para su psterior uso por
         * ejemplo
         * descargarlo
         */

        return Files.readAllBytes(Path.of(URI.create(pago.getFile())));

    }

    public Pago actualizarPagoPorStatus(PagoStatus status, Long id) {

            //busca un objeto pago en la bd por su ID
            Pago pago = pagoRepository.findById(id).get();

            //actualiza el estado de pago (validado o rechazado)
            pago.setStatus(status);

            //guarda el objeto pago actualizado en la bd y lo devuelve
            return pagoRepository.save(pago);

    }