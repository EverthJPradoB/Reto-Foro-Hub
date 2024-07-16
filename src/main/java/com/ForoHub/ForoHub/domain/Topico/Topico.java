package com.ForoHub.ForoHub.domain.Topico;

import com.ForoHub.ForoHub.domain.Curso.Curso;
import com.ForoHub.ForoHub.domain.Usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "topico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String  título;
    private String mensaje;

    private LocalDateTime fecha;

    private Boolean status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Topico(Long id,AgendarTopico request, Usuario autor, Curso curso) {
        this.id = id;
        this.título = request.título();
        this.mensaje = request.mensaje();
        this.fecha = request.fecha();
        this.status = true;
        this.autor = autor;
        this.curso = curso;
    }

    public Topico(AgendarTopico request, Usuario autor, Curso curso) {
        this.título = request.título();
        this.mensaje = request.mensaje();
        this.fecha = request.fecha();
        this.status = true;
        this.autor = autor;
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "Topico{" +
                "id=" + id +
                ", título='" + título + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", fecha=" + fecha +
                ", status=" + status +
                ", autor=" + autor +
                ", curso=" + curso +
                '}';
    }
}
