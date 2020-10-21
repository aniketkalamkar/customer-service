package com.omnirio.assignment.customerservice.security.filter;

import static com.omnirio.assignment.customerservice.security.filter.SecurityConstants.HEADER_NAME;
import static com.omnirio.assignment.customerservice.security.filter.SecurityConstants.KEY;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_NAME);

        if (header == null) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = null;
		try {
			authentication = authenticate(request);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken authenticate(HttpServletRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String token = request.getHeader(HEADER_NAME);
        if (token != null) {
            Claims user = Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(token)
                    .getBody();
          
//        	  PublicKey publicKey = getPublicKey();
//
//        	  Claims user = Jwts.parser().setSigningKey(publicKey)
//                    .parseClaimsJws(token).getBody();

            final Collection authorities =
                    Arrays.stream(user.get("ROLES").toString().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
            
                      if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }else{
                return  null;
            }
            
            
        }
        return null;
    }
    
    private  PublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String rsaPublicKey = "-----BEGIN PUBLIC KEY-----" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1ywb8UDRosg5ws4XVzWI" +
                "Gv4ht+ksM8gQB0CHGQ3jmN1bTrFG6rL4hnQeUTfLaFNOyk/t7+5fMTZ9wFr9ymnS" +
                "5P6L3EucWUUPojtTt/idA+Azi8paKUSiPLN9A7OF4VJniZLKhcgjH7nXjJVPKP5v" +
                "yV3yObwwKvTKKiFHGjV0pYG3oI4RgpIKaZBLgyv7/1dH/LjbCtubtuNeynsJYqC4" +
                "F+sWVrCkVdXT6vrvMwSkxByAtzOT+pkWl1AVqAr+yviNT5a7gFgOsEyBknF7CmS5" +
                "sNbJ5ACiuINtA0dTGN6ldFHEio18w/eGmOW32rU+TBGpd/kNRU/R+ATeQtIlxi6O" +
                "7wIDAQAB" +
                "-----END PUBLIC KEY-----";
        rsaPublicKey = rsaPublicKey.replace("-----BEGIN PUBLIC KEY-----", "");
        rsaPublicKey = rsaPublicKey.replace("-----END PUBLIC KEY-----", "");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(rsaPublicKey));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(keySpec);
        return publicKey;
    }
}
