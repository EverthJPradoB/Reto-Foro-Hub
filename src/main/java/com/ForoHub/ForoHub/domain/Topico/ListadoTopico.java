package com.ForoHub.ForoHub.domain.Topico;

import com.ForoHub.ForoHub.domain.Curso.Curso;
import com.ForoHub.ForoHub.domain.Usuario.Usuario;

import java.time.LocalDateTime;

public record ListadoTopico(

        String título, String mensaje, LocalDateTime fecha,

        Boolean estado, Usuario autor , Curso curso


) {

    public ListadoTopico(Topico topico) {
        this(topico.getTítulo(),topico.getMensaje(),topico.getFecha(),topico.getStatus(),topico.getAutor(),topico.getCurso());
    }
}
