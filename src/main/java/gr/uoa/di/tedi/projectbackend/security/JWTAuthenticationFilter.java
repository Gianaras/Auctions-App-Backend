package gr.uoa.di.tedi.projectbackend.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static gr.uoa.di.tedi.projectbackend.security.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            // extract username and password from request
            gr.uoa.di.tedi.projectbackend.model.User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), gr.uoa.di.tedi.projectbackend.model.User.class);

            // check if username and password given are correct
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (AuthenticationException e) {
            System.out.println("Authentication exception");
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {
        // create token
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));

        // add token to header
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);

        // add an admin header (shows if logged-in user is an admin)
        if (auth.getAuthorities().iterator().next().toString().equals("ROLE_admin"))
            res.addHeader(ADMIN_STRING, "true");
        else res.addHeader(ADMIN_STRING, "false");

        // add username header
        res.addHeader(USERNAME_STRING, ((User) auth.getPrincipal()).getUsername());
    }
}
