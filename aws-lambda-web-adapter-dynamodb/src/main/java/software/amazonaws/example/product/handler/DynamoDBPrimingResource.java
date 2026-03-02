package software.amazonaws.example.product.handler;

import org.crac.Core;
import org.crac.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazonaws.example.product.dao.ProductDao;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;


@Configuration
public class DynamoDBPrimingResource implements Resource {
	
	private static final Logger logger = LoggerFactory.getLogger(DynamoDBPrimingResource.class);
	    
	@Autowired
	private ProductDao productDao;
	
	public DynamoDBPrimingResource () {
		Core.getGlobalContext().register(this);
	}

	@Override
	public void beforeCheckpoint(org.crac.Context<? extends Resource> context) throws Exception {
		 logger.info("Before Checkpoint");
		 productDao.getProduct("0");
		 logger.info("After Checkpoint"); 
	}

	@Override
	public void afterRestore(org.crac.Context<? extends Resource> context) throws Exception {
		logger.info("After Restore");	
	}
	
}