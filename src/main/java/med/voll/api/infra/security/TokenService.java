package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}") //passar como parametro a propriedade
    //variavel de ambiente   = NOME_VARIAVEL:se naõ encontrar, passa uma aleatório
    private String secret; //criar no application.properties

    public String gerarToken(Usuario usuario){

        //https://jwt.io/
        //testar o token, p ver se esta ok as informações

        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Voll.med") //identificacao da API
                    .withSubject(usuario.getLogin()) //quem é o dono do token
//                    .withClaim("id", usuario.getId()) //pode coloca o que vai buacar, perfil..
                    .withExpiresAt(dataExpiracao())      //duracao do token
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            // Invalid Signing configuration / Couldn't convert Claims.
            throw new RuntimeException("Erro ao gerar token jwt", exception);
        }


    }

    //vai passar um token, verificar se esta valido e devolver o usuario o subject
    public String getSubject(String tokenJWT){
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    // specify an specific claim validations
                    .withIssuer("API Voll.med")
                    // reusable verifier instance
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }

    }

    private Instant dataExpiracao() {
                                                                       //fuso horario br
        return LocalDateTime.now().plusYears(1).toInstant(ZoneOffset.of("-03:00"));

        //hora atual + 2 horas
    }
}
