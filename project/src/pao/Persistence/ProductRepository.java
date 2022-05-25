package pao.persistence;

import pao.components.Product;
import pao.setup.AbstractRepository;

import java.util.List;
import java.util.Properties;

public class ProductRepository extends AbstractRepository<Long, Product> implements IProductRepository{
    public ProductRepository(Properties props) {
        super(props);
    }

    @Override
    public void save(Product elem) {

    }

    @Override
    public void delete(Product elem) {

    }

    @Override
    public void update(Product elem) {

    }

    @Override
    public Product findOne(Long id) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return null;
    }
}
