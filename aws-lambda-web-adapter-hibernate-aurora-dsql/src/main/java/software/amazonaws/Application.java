package software.amazonaws;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import software.amazonaws.example.product.controller.ProductController;

import jakarta.annotation.PostConstruct;
import software.amazonaws.example.product.dao.ProductDao;

@Import({ProductController.class })
@SpringBootApplication

public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	@Autowired
	private ProductDao productDao;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    
	@PostConstruct
	public void doPriming() {
		logger.info("entered do priming method ");
		productDao.findById(0);
		logger.info("finished do priming method ");
	}
}