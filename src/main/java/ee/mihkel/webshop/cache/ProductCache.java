package ee.mihkel.webshop.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ee.mihkel.webshop.model.database.Product;
import ee.mihkel.webshop.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
@Log4j2
public class ProductCache {
    // Google Guava Cache
    @Autowired
    ProductRepository productRepository;

    // cache loading otsustab, kas võtab cache-st (ülikiire ja vähese jõudlusega)
    // või võtab andmebaasist
    private final LoadingCache<Long, Product> productLoadingCache = CacheBuilder
            .newBuilder()
            .expireAfterAccess(10, TimeUnit.SECONDS)
            .build(new CacheLoader<Long, Product>() {
                @Override
                public Product load(Long id) {
                    log.info("võtan andmebaasist");
                    return productRepository.findById(id).get();
                }
            });

    // avalik funktsioon, mis võtab cache-st
    public Product getProduct(Long id) throws ExecutionException {
        log.info("võtan toote");
        return productLoadingCache.get(id);
    }

    public void emptyCache() {
        productLoadingCache.invalidateAll();
    }

    public void updateCache(Product product) {
        productLoadingCache.put(product.getId(), product);
    }
}
