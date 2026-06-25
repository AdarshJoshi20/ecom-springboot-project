package com.adarsh.ecomProj.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.adarsh.ecomProj.model.Product;
import com.adarsh.ecomProj.service.ProductService;
import org.springframework.web.bind.annotation.CrossOrigin;
//import sun.print.resources.serviceui;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService service;


	// This annotation maps HTTP GET requests to this method.
	// When a client sends a GET request to "/products",
	// this method will be executed.
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts()
	{
		// Calling the service layer method to fetch all products
		// from the database.
		// The service internally talks to the repository.

		List<Product> productList = service.getAllProducts();

		// Creating a ResponseEntity object:
		// - Body  → productList (data returned to client)
		// - Status → HttpStatus.OK (200, meaning request successful)

		return new ResponseEntity<>(productList, HttpStatus.OK);
	}

	// This annotation maps HTTP GET requests to this method.
	// When a client sends a GET request to "/product/{id}",
	// this method will be executed.
	//
	// Example:
	// http://localhost:8080/product/5
	@GetMapping("/product/{id}")  // Function for getting new product
	public ResponseEntity<Product>  getProduct(@PathVariable int id) {

		Product product = service.getProductById(id);

		if(product != null)
			return new ResponseEntity<>(service.getProductById(id), HttpStatus.OK);

		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


	// For adding new product
	@PostMapping("/product")
	public ResponseEntity<?> addProduct(@RequestPart Product product,
			@RequestPart MultipartFile imageFile)
	{
		try {
			Product product1 = service.addProduct(product, imageFile); 
			return new ResponseEntity<>(product1, HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// for getting the image of the product (image is uploaded using the form shown in front-end)
	@GetMapping("/product/{productId}/image")
	public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
		Product product = service.getProductById(productId);
		byte[] imageFile = product.getImageData();

		return ResponseEntity.ok()
				.contentType(MediaType.valueOf(product.getImageType()))
				.body(imageFile);

	}

	// Update functionality
	// it requires 3 things: 1-> id oƒ the product 
	//						 2-> object of Product itself
	//						 3-> image of the Product object
	@PutMapping("/product/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable int id, 
			@RequestPart Product product,
			@RequestPart MultipartFile imageFile)
	{
		Product prod1  = null;
		try {
			prod1 = service.updateProduct(id, product, imageFile); 
			// service updates the product and returns it
		}
		catch (IOException e) {
			return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
		}

		// if prod1 is returned 
		// that means the producţ prod1 was successfully updated hence an alert will be shown
		//else the alert will say "Failed to update"
		if(prod1 != null)
		{
			return new ResponseEntity<>("Updated", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<> ("Failed to update", HttpStatus.BAD_REQUEST); 
		}
	}

	//Delete functionality

	@DeleteMapping("/product/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable int id){
		Product product = service.getProductById(id);
		if(product != null)
		{
			service.deleteProduct(id);
			return new ResponseEntity<>("Deleted", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
		}
	}
	
	// this method searches a particular product based on the keyword provided 
	@GetMapping("/products/search")
	public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword)
	{
		System.out.println("printing" + keyword);
		List<Product> products = service.searchProducts(keyword);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
}




