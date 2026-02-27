package software.amazonaws.example.product.dao;


import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazonaws.example.product.entity.Product;

@Repository
public class ProductDao {
	private static final Logger logger = LoggerFactory.getLogger(ProductDao.class);
	private static final String PRODUCT_TABLE_NAME = System.getenv("PRODUCT_TABLE_NAME");
	private static final String REGION = System.getenv("REGION");

	private static final DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
			.credentialsProvider(DefaultCredentialsProvider.builder().build()).region(Region.of(REGION.toLowerCase()))
			// .httpClient(Apache5HttpClient.builder().build())
			.overrideConfiguration(ClientOverrideConfiguration.builder().build()).build();

	public Optional<Product> getProduct(String id) {
		var getItemResponse = dynamoDbClient.getItem(GetItemRequest.builder()
				.key(Map.of("PK", AttributeValue.builder().s(id).build())).tableName(PRODUCT_TABLE_NAME).build());
		if (getItemResponse.hasItem()) {
			return Optional.of(ProductMapper.productFromDynamoDB(getItemResponse.item()));

		} else {
			return Optional.empty();
		}
	}

	public void createProduct(Product product) {
		dynamoDbClient.putItem(PutItemRequest.builder().tableName(PRODUCT_TABLE_NAME)
				.item(ProductMapper.productToDynamoDb(product)).build());
	}
}
