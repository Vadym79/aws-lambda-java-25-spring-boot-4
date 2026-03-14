package software.amazonaws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;

@SpringBootApplication
public class Application {
	
	
	@Bean
    public JsonMapper objectMapper () {
        JsonMapper jsonMapper = JsonMapper.builder()
                .findAndAddModules()
                .disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
                .build();
        return jsonMapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}