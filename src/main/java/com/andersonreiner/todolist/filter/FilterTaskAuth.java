package com.andersonreiner.todolist.filter;
import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                
                // pegar a autenticação(usuário e senha)
                var authorization = request.getHeader("Authorization");
                

                var user_password_encrypted = authorization.substring("Basic".length()).trim();

                byte[] user_password = Base64.getDecoder().decode(user_password_encrypted);

                var user_and_password = new String(user_password);

                String[] fim_user_and_password = user_and_password.split(":");
                String username = fim_user_and_password[0];
                String password = fim_user_and_password[1];

                System.out.println(username);
                System.out.println(password);

                // validar usário

                // validar senha

                filterChain.doFilter(request, response);
    }

}
