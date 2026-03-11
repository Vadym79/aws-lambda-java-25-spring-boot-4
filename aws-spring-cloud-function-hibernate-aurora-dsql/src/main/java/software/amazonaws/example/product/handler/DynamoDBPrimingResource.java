package software.amazonaws.example.product.handler;

import org.crac.Core;
import org.crac.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import software.amazonaws.example.product.dao.ProductDao;

//@Component
public class DynamoDBPrimingResource implements Resource {

	@Autowired
	private ProductDao productDao;

    private static final Logger logger = LoggerFactory.getLogger(DynamoDBPrimingResource.class);
	
	
	public DynamoDBPrimingResource () {
		 Core.getGlobalContext().register(this);
	}
	
	@Override
	public void beforeCheckpoint(org.crac.Context<? extends Resource> context) throws Exception {
		 logger.info("entered beforeCheckpoint method for DynamoDB priming");
		 productDao.findById(0);
		 logger.info("finished DynamoDB priming");
	}

	@Override
	public void afterRestore(org.crac.Context<? extends Resource> context) throws Exception {	
	
	}
}