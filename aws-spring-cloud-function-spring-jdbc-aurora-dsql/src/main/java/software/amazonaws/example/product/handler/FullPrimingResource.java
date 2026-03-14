package software.amazonaws.example.product.handler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.crac.Core;
import org.crac.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.adapter.aws.FunctionInvoker;
import org.springframework.stereotype.Component;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent.ProxyRequestContext;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent.RequestIdentity;

import tools.jackson.databind.json.JsonMapper;


//@Component
public class FullPrimingResource implements  Resource {

   @Autowired 
   private JsonMapper objectMapper;
	
    private static final Logger logger = LoggerFactory.getLogger(FullPrimingResource.class);
	
	public FullPrimingResource () {
		Core.getGlobalContext().register(this);
	}
	
	@Override
	public void beforeCheckpoint(org.crac.Context<? extends Resource> context) throws Exception {
		 logger.info("entered beforeCheckpoint method for full priming");
		 new FunctionInvoker("getProductByIdHandler").handleRequest(new ByteArrayInputStream(this.getAPIGatewayProxyRequestEventAsJson().getBytes(StandardCharsets.UTF_8)), 
				 new ByteArrayOutputStream(), new MockLambdaContext());
		 logger.info("finished full priming");
	}

	@Override
	public void afterRestore(org.crac.Context<? extends Resource> context) throws Exception {	
	
	}
	
	
    private String getAPIGatewayProxyRequestEventAsJson() throws Exception{
    	final APIGatewayProxyRequestEvent proxyRequestEvent = new APIGatewayProxyRequestEvent ();
    	proxyRequestEvent.setHttpMethod("GET");
    	proxyRequestEvent.setPathParameters(Map.of("id","0"));
        
    	 
    	/* 
    	proxyRequestEvent.setResource("/products/{id}");
    	proxyRequestEvent.setPath("/products/0");
    
        
    	final ProxyRequestContext proxyRequestContext = new ProxyRequestContext();
    	final RequestIdentity requestIdentity= new RequestIdentity();
    	requestIdentity.setApiKey("blabla");
    	proxyRequestContext.setIdentity(requestIdentity);
    	proxyRequestEvent.setRequestContext(proxyRequestContext);
    	*/
    	return objectMapper.writeValueAsString(proxyRequestEvent);		
    }

}