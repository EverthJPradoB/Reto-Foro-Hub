package com.ForoHub.ForoHub.domain.Topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendarTopico(

                 @NotBlank
                 String  título,
                 @NotBlank
                 String mensaje,

                 LocalDateTime fecha,
                 @NotNull
                 Long autor_id,
                 @NotNull
                 Long curso_id

) {

}
