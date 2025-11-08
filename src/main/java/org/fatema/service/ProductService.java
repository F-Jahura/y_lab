package org.fatema.service;

import org.fatema.model.Product;
import org.fatema.repository.ProductRepository;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.NavigableMap;
import java.util.ArrayList;
import java.util.Collections;


public class ProductService {
    private final ProductRepository productRepository;

    private Map<String, List<Product>> categoryCache = new HashMap<>();
    private Map<String, List<Product>> brandCache = new HashMap<>();
    private Map<String, List<Product>> nameCache = new HashMap<>();
    private NavigableMap<Double, List<Product>> priceCache = new TreeMap<>();


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Product addProduct(Product p) {
        Product newProduct = productRepository.addProduct(p);
        updateCaches(newProduct);
        return newProduct;
    }

    private void updateCaches(Product p) {
        categoryCache.computeIfAbsent(p.getCategory(), k -> new ArrayList<>()).add(p);
        brandCache.computeIfAbsent(p.getBrand(), k -> new ArrayList<>()).add(p);
        nameCache.computeIfAbsent(p.getName(), k -> new ArrayList<>()).add(p);
        priceCache.computeIfAbsent(p.getPrice(), k -> new ArrayList<>()).add(p);
    }

    public void removeProduct(long id) {
        Product product = productRepository.getProductById(id);
        if (product != null) {
            productRepository.removeProduct(id);
            removeFromCaches(product);
        }
    }

    private void removeFromCaches(Product p) {
        List<Product> categoryList = categoryCache.get(p.getCategory());
        if (categoryList != null) {
            categoryList.remove(p);
            if (categoryList.isEmpty()) categoryCache.remove(p.getCategory());
        }

        List<Product> brandList = brandCache.get(p.getBrand());
        if (brandList != null) {
            brandList.remove(p);
            if (brandList.isEmpty()) brandCache.remove(p.getBrand());
        }

        List<Product> nameList = nameCache.get(p.getName());
        if (nameList != null) {
            nameList.remove(p);
            if (nameList.isEmpty()) nameCache.remove(p.getName());
        }

        List<Product> priceList = priceCache.get(p.getPrice());
        if (priceList != null) {
            priceList.remove(p);
            if (priceList.isEmpty()) priceCache.remove(p.getPrice());
        }
    }

    public Product updateProduct(Product p) {
        Product oldProduct = productRepository.getProductById(p.getId());
        if (oldProduct != null) {
            removeFromCaches(oldProduct);
            Product updated = productRepository.updateProduct(p);
            updateCaches(updated);
            return updated;
        }
        return null;
    }

    public Product getProductById(Long id) {
        return productRepository.getProductById(id);
    }

    public List<Product> findByCategory(String category) {
        return categoryCache.getOrDefault(category, Collections.emptyList());
    }

    public List<Product> findByBrand(String brand) {
        return brandCache.getOrDefault(brand, Collections.emptyList());
    }
    public List<Product> findByName(String name) {
        return nameCache.getOrDefault(name, Collections.emptyList());
    }

    public List<Product> findByPriceRange(double minPrice, double maxPrice) {
        NavigableMap<Double, List<Product>> subMap
                = priceCache.subMap(minPrice, true, maxPrice, true);
        List<Product> result = new ArrayList<>();
        for (List<Product> list : subMap.values()) {
            result.addAll(list);
        }
        return result;
    }
}
