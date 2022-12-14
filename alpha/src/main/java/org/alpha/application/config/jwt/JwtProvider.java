package org.alpha.application.config.jwt;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JwtProvider {

    private String secretKey="Petition-Handshake-Extruding-Moisture-Shakable9";

    //In milliseconds
    private long validTime= 3600000; //1h*
}
