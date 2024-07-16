package com.ForoHub.ForoHub.infra.security;


import com.ForoHub.ForoHub.domain.Usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //obtener el valor del token
//        Authorization nombre por defecto es un estandar
        System.out.println("Este es el inicio del filter");
       var token = request.getHeader("Authorization");

        if (token != null) {
            System.out.println("validamos ue el token no es nulo");
            token = token.replace("Bearer ","");

//            System.out.println(token);
//            System.out.println(tokenService.getSubject(token));

            var nombreUsuario = tokenService.getSubject(token);

            System.out.println("nombreUsuario---" + nombreUsuario);
            if (nombreUsuario != null) {
                //token valido
                var usuario = usuarioRepository.findByCorreo(nombreUsuario);
                var authentication = new UsernamePasswordAuthenticationToken
                        (usuario,null,usuario.getAuthorities());// forsamos un inicio de session

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
//            throw  new RuntimeException("El token enviado no es valido");
        }

        filterChain.doFilter(request,response);


    }

    //flitrer chain Representa el conjunto de filtros encargados de interceptar solicitudes.
    //flitrer chain Se puede utilizar para bloquear una solicitud.


}
