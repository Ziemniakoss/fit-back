package pl.fitback.security.authenication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class JsonObjectAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            String requestBody = extractRequestBody(request);
            LoginCredentials credentials = objectMapper.readValue(requestBody, LoginCredentials.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getLogin(), credentials.getPassword());

            setDetails(request, token);
            return getAuthenticationManager().authenticate(token);
        } catch (BadCredentialsException e) {
            throw e;
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private String extractRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder bodyBuilder = new StringBuilder();

        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            bodyBuilder.append(line);
        }

        return bodyBuilder.toString();
    }

}
