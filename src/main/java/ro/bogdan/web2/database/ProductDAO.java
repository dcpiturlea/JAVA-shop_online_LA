package ro.bogdan.web2.database;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Product> findAll() {
        return jdbcTemplate.query("select * from product", new ProductRowMapper());
    }

    public Product findById(Integer id) {
        return jdbcTemplate.query("select * from product where id = " + id, new ProductRowMapper()).get(0);
    }

}
