package com.adarsh.ecomProj.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.adarsh.ecomProj.EcomProjectApplication;
import com.adarsh.ecomProj.model.Product;
import com.adarsh.ecomProj.repository.ProductRepo;

@Service
public class ProductService {

    private final EcomProjectApplication ecomProjectApplication;
	
	@Autowired
	private ProductRepo repo;

    ProductService(EcomProjectApplication ecomProjectApplication) {
        this.ecomProjectApplication = ecomProjectApplication;
    }

	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		
		return repo.findAll();
	}

	public Product getProductById(int id) {
	    
	    // Calling JpaRepository's findById() method
	    // It returns an Optional<Product>
	    // Optional is used to handle cases where the product may not exist
	    return repo.findById(id)
	               
	               // If product is found → return that product
	               // If product is NOT found → return a new empty Product object
	    		// not a good idea for real projects. instead use Exception Handling
	               .orElse(null);
	}

	// create a new product
	public Product addProduct(Product product, MultipartFile imageFile) throws IOException{
		
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		product.setImageData(imageFile.getBytes());
		return repo.save(product);
	}

	// update an existing product
	public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
		product.setImageType(imageFile.getContentType());
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageData(imageFile.getBytes());
		return repo.save(product);
	}

	// actual function to delete product using its id
	public void deleteProduct(int id) {
		repo.deleteById(id);
	}

	public List<Product> searchProducts(String keyword) {
		return repo.searchProducts(keyword);
	}
	
}
