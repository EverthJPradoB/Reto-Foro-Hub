package com.ForoHub.ForoHub.Controller;

import com.ForoHub.ForoHub.domain.Curso.Curso;
import com.ForoHub.ForoHub.domain.Curso.CursoRepository;
import com.ForoHub.ForoHub.domain.Topico.*;
import com.ForoHub.ForoHub.domain.Usuario.Usuario;
import com.ForoHub.ForoHub.domain.Usuario.UsuarioRepository;
import com.ForoHub.ForoHub.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/topico")
public class TopicoController {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;


    @PostMapping("/registrar")
    public ResponseEntity registrarTopico(@RequestBody AgendarTopico request) {

        Map<String, Object> response = new HashMap<>();
        Topico topico = null;

        if(!cursoRepository.findById(request.curso_id()).isPresent()){
            throw new ValidacionDeIntegridad("No existe este curso");
        }

        if(!usuarioRepository.findById(request.autor_id()).isPresent()){
            throw new ValidacionDeIntegridad("No existe este autor");
        }
        var autor = usuarioRepository.findById(request.autor_id()).get();
        var curso = cursoRepository.findById(request.curso_id()).get();

        try {
             topico = new Topico(null,request,autor,curso);
            topicoRepository.save(topico);
        } catch (DataAccessException e) {
            response.put("mensaje", "error al insertar en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("Topico", topico);
        response.put("mensaje", "El topico a sido creado");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);


    }
//
//    String título, String mensaje, LocalDateTime fecha,
//
//    Boolean estado, Long autor , Long curso

    //    (título, mensaje, fecha de creación, estado, autor y curso
    @GetMapping("/listar")
    public ResponseEntity obtenerTodosLosTopicos() {

        return ResponseEntity.ok(topicoRepository.findAll().stream().map(ListadoTopico::new));
    }


//    ¿Qué hay de mostrar los primeros 10 resultados ordenados por fecha de creación del tópico en orden ASC?
//
    @GetMapping("/ListTenOrderDate")
    public ResponseEntity resultadoOrdenadosPorFecha() {

        return ResponseEntity.ok(topicoRepository.findFirst10ByOrderByFechaAsc().stream().map(ListadoTopico::new));
    }


    @GetMapping("/ListCriteria")
    public ResponseEntity resultadoNombreCursoAnioEspecifico(
            @RequestBody BusquedaCriteria request
    ) {

        var nombreCurso = request.nombreCurso();

        var anio =request.anio();

        return ResponseEntity.ok(topicoRepository.findByNombreCursoAndAnio(nombreCurso,anio).stream().map(ListadoTopico::new));

    }

    ///Detalle de tópicos

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> obtenerTodosLosTopicosPorID(@PathVariable Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isPresent()) {
            Topico topico = optionalTopico.get();
            System.out.println(topico);
            return ResponseEntity.ok(topico);
        } else {
            // Si no se encuentra el topico con el ID proporcionado
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity actualizarTopico(
            @PathVariable Long id,
            @RequestBody ActualizarTopico topicoActualizado) {

        Optional<Topico> optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isPresent()) {
            Topico topicoExistente = optionalTopico.get();

            // Aplicar reglas de negocio o validaciones necesarias aquí antes de actualizar

            if(topicoActualizado.titulo() != null ){
                topicoExistente.setTítulo(topicoActualizado.titulo());

            }

            if(topicoActualizado.mensaje() != null ){
                topicoExistente.setMensaje(topicoActualizado.mensaje());

            }
            // Actualizar los datos del tópico existente con los datos del tópico actualizado
            // Actualizar otros campos según sea necesario

            // Guardar el tópico actualizado en la base de datos
            topicoRepository.save(topicoExistente);

            return ResponseEntity.ok(topicoExistente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content si la eliminación fue exitosa
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found si no se encontró el tópico con el ID proporcionado
        }
    }



}


