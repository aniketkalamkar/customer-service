package com.omnirio.assignment.customerservice.security.filter;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omnirio.assignment.customerservice.domain.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			User user = new ObjectMapper().readValue(req.getInputStream(), User.class);

			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					user.getUserName(), user.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

        PrivateKey privateKey = null;
		try {
			privateKey = getPrivateKey();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        Instant now = Instant.now();
        String jwtToken = Jwts.builder()
        		.setSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal())
        				.getUsername())
                .claim("ROLES", ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getAuthorities().stream().
                		map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(5l, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.RS512,privateKey)
                .compact();

		res.addHeader("token", jwtToken);
	}


	 private  PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
	        String rsaPrivateKey = 
	          		"-----BEGIN RSA PRIVATE KEY-----"+
	        				"MIIEpgIBAAKCAQEAzFjob+9KrzXidDZOh5m/LobRrJ6qtirXUpyna3r5yEIBjgbV"+
	        				"+Sg1n4lMXuYL2Rxg2tlSrj6P0JAHOmydcQbWPyiQSAD9QO12ecmW3ze5t82wpi7b"+
	        				"+AJdZAFW+CTxMYN8d0GkCKRIRCERt45GFX1pyn6u7QoR/nmanW47YBQVYM84oE2V"+
	        				"H34J0GFBv7BEqmGu3qqe3asyONKRz3GQ7/yZsGWBKuBmaVi6OVBHRe2d77+hJs7f"+
	        				"lHk/QVLdqtRKf1niv/OO+oATDgSUm6YNJbckUg/1Jgbctj5h6xKHEbtNgjAe9UPu"+
	        				"TdUVq0IY+eQb8lqKXoFgoR/t0NevxmbbJSZ+cwIDAQABAoIBAQCGpxtbDxJmjBpR"+
	        				"u6tZnU0man5lwhP5gBds/38uytUnSZ9eqFB9Iyramo1xhyNNiG6mJ0hdOypnI1Ew"+
	        				"BXxlizzIV4FjgQtgqllEfTrmkgDWBjsd9ojKTzyc9mkJkMZCe0McDmv5qc9Y3XWg"+
	        				"P6cc6Aw6++lmlxkuV5EzpfyIHhPMOvIXI4rHtkhovHgtmrTAyWg/bkoHU8rdYqpw"+
	        				"iRqWN9OD7DL/TgK9QAt6DXgcWVvGdYYa5u7fa+c/FEQesf3UhMFQ7Nxbj333Exui"+
	        				"zjrkUF6nYFM0YE1ObiYSNztTR01vMieY9oCXK350Aj3ttfJjjrOod3Sbw7KXrMYL"+
	        				"77WDwl1hAoGBAPSm8OTOfh1DXr4wb41Go1Hb9ZWdKB2M3LwRj3hSbICqOWbn6YJ0"+
	        				"nOT8lqpC1Dwk6+wISVGQQNRxSy4k2pz4otsTMiSmM8zie53pTaeTP7hnrwxUt66q"+
	        				"M6yXLu4yMP/eJD8BgrD80XR2F7hYx8zsMTnBrxpuj4slJ4yAbJf0195jAoGBANXT"+
	        				"YLJQCHctNt1MBjL5K1rjqkGfnB0Nhp143EYulqxoOo+4us3cYH2CJeMR8irrA6BA"+
	        				"DNavf7ucdBny3B+sJxNm+2tDbwheEhXoc2gNgBCSnuywu2Wotzu/ndPXfgQqmipa"+
	        				"hZO2fSlbz9b4T90gcInRDuAj2VPA7ogOhdKDQhSxAoGBAM1/DAikibn6xLHr7Uc1"+
	        				"dilkILwy7Wck0qQF/ASM9yZ71Qti8PjrCn4szEDnMW3zdVck/rDMAihvoT55JkeD"+
	        				"cQei8BNop0oJUtwpA1kmjfdO3/HzlvXXcOAffQQGlLHW3YsusaAIO8cfCn2F1Q2D"+
	        				"tzVUQsho5Zly8+1IUe6xckERAoGBAIEOEketHF7p0Dpivz8Wun8h98mhzvSODFiQ"+
	        				"8Mn/JnuqcOX7xeun/ijiW4GgfDxBGjAnWFhfFkYi2MQsm4UWP4NrXYXQ7nvrYxkL"+
	        				"8lO0Cj52cCtFQ+XInccXEOgiIPHc0K2Ncx/DPemve1MxDerVwHa90i+gE3SEdrNx"+
	        				"qH11MXYRAoGBAPEt4/1yt+ukd8WVqKEC/8Rq1mdArMzBVPOP7tZj3KSChZdlXR6z"+
	        				"XDB2RqwpR6tK4grLzJvmBdHJaeqoeViqyX1UiRwSRFTy7n4FK5bclWb5EGZySJKm"+
	        				"nCjU6Kwq02cn1FxiJ2rGeeOsTOFnagEkwdnsHqUqBUgf6Iix3+7fZdJB"+
	        				"-----END RSA PRIVATE KEY-----";

	        rsaPrivateKey = rsaPrivateKey.replace("-----BEGIN PRIVATE KEY-----", "");
	        rsaPrivateKey = rsaPrivateKey.replace("-----END PRIVATE KEY-----", "");

	        KeyStoreKeyFactory keyStoreKeyFactory = 
	        	      new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
//	        keyStoreKeyFactory.getKeyPair(alias)
	        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(rsaPrivateKey));
	        KeyFactory kf = KeyFactory.getInstance("RSA");
	        PrivateKey privKey = kf.generatePrivate(keySpec);
	        return privKey;
	    }
}