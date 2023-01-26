package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
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
        //recuperar o token e verificar se está valido.
        var tokenJWT = recuperarToken(request);
        //se tem o cabeçalho ele entra
        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);

            //se o token esta valido, ele considera que ela esta autorizada e logada
            var usuario = usuarioRepository.findByLogin(subject);

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);


        }
//        System.out.println(subject);

        //para chamar o proximo filtro
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var autorizacionHeader = request.getHeader("Authorization");


        if(autorizacionHeader != null){
            //para tirar o prefixo e deixar só o token
            return autorizacionHeader.replace("Bearer ", "");
        }
        return null;
    }
}
