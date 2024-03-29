package com.example.security.config;

import com.example.security.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    /*
     * @Value: Pega a variavel do application.properties utilizando o ${local.da.variavel}
     *
     * setIssuer: Nome da API que gerou o token;
     * setSubject: String que identifique unicamente o usuário do token;
     * setIssuedAt: Data que o token foi gerado;
     * setExpiration: Data de expiração do token. (Setar um tempo no application.properties);
     * secret: senha secreta do token. (Setar uma senha com vários caracteres no application.properties);
     * signWith: criptografia do token, recebe como parametros o algoritmo de criptografia e o secret(senha da aplicação);
     *
     * authentication.getPrincipal(): Retorna o Usuário autenticado do sistema, ou seja, o usuário que está logado.
     *      obs: O método retorna um Object, com isso é necessário realizar o cast para o tipo User.
     *
     */

    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.secret}")
    private String secret;

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date dateExpiration = new Date(now.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("API Security JWT")
                .setSubject(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(dateExpiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
