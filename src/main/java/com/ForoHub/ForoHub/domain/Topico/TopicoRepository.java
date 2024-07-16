package com.ForoHub.ForoHub.domain.Topico;

import com.ForoHub.ForoHub.domain.Usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicoRepository  extends JpaRepository<Topico,Long> {


    List<Topico> findFirst10ByOrderByFechaAsc();

    @Query("SELECT t FROM Topico t INNER JOIN t.curso c WHERE c.nombre = :nombreCurso AND YEAR(t.fecha) = :anio")
    List<Topico> findByNombreCursoAndAnio(String nombreCurso, int anio);


}
