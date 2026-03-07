package software.amazonaws.example.product.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import software.amazonaws.example.product.entity.Product;

@Repository
public class ProductDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * create a product and return its id
	 * 
	 * @param product product
	 * @return product id
	 */
	public int createProduct(Product product) throws Exception {
		var productSequenceNextValQuery = "SELECT nextval('product_id')";
		var id = this.getJDBCTemplate().queryForObject(productSequenceNextValQuery, Integer.class);
		var createProductQuery = "INSERT INTO products VALUES (?, ?, ?)";
		this.getJDBCTemplate().update(createProductQuery, id, product.name(), product.price());
		return id;
	}

	/**
	 * returns product by its id
	 * 
	 * @param id -product id
	 * @return
	 * @throws Exception
	 */
	public Optional<Product> getProductById(int id) throws Exception {
		var findProductByIdQuery = "SELECT * FROM products WHERE id = ?";
		try {
			return this.getJDBCTemplate().queryForObject(findProductByIdQuery, new ProductMapper(), id);
		  } catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	private JdbcTemplate getJDBCTemplate() {
		return this.jdbcTemplate !=null ?this.jdbcTemplate:new JdbcTemplate(DsqlDataSourceConfig.datasource);
	}
	private static class ProductMapper implements RowMapper<Optional<Product>> {

		@Override
		public Optional<Product> mapRow(ResultSet rs, int rowNum) throws SQLException {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			int price = rs.getInt("price");
			return Optional.of(new Product(id, name, price));
		}
	}
}