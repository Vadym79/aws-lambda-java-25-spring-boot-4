package software.amazonaws;

import static org.springframework.aot.hint.MemberCategory.*;

import java.util.HashSet;

import org.joda.time.DateTime;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;

import software.amazonaws.example.product.entity.Product;
import software.amazonaws.example.product.entity.Products;
import software.amazonaws.example.product.handler.StreamLambdaHandler;

@Configuration
@RegisterReflectionForBinding({DateTime.class, APIGatewayProxyRequestEvent.class, HashSet.class, 
	APIGatewayProxyRequestEvent.ProxyRequestContext.class, APIGatewayProxyRequestEvent.RequestIdentity.class,
	Product.class, Products.class, StreamLambdaHandler.class})

@ImportRuntimeHints(ApplicationConfiguration.ApplicationRuntimeHintsRegistrar.class)

public class ApplicationConfiguration {
	
	public static class ApplicationRuntimeHintsRegistrar implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.reflection()
                   .registerType(
                            Product.class,
                            ACCESS_PUBLIC_FIELDS, INVOKE_PUBLIC_METHODS, INVOKE_PUBLIC_CONSTRUCTORS
                    ).registerType(
                            Products.class,
                            ACCESS_PUBLIC_FIELDS, INVOKE_PUBLIC_METHODS, INVOKE_PUBLIC_CONSTRUCTORS
                    ).registerType(
                    		StreamLambdaHandler.class, UNSAFE_ALLOCATED, ACCESS_DECLARED_FIELDS, ACCESS_PUBLIC_FIELDS,  
                    		INVOKE_DECLARED_METHODS, INVOKE_PUBLIC_METHODS, 
                    		INVOKE_PUBLIC_CONSTRUCTORS, INVOKE_DECLARED_CONSTRUCTORS
                    );
        }
    }

}