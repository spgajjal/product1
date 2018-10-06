package com.capgemini.productapp;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestBody;

import com.capgemini.productapp.controller.ProductController;
import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.service.ProductService;



@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

	@Mock
	ProductService productService;

	@InjectMocks
	ProductController productController;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

	}

	@Test
	public void testProductWhichAddsProduct() throws Exception {
		Product product = new Product(1, "Laptop", "Electronics", 10000);
		when(productService.addProduct(Mockito.isA(Product.class))).thenReturn(product);
		mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON_UTF8).content(
				"{\"productId\":\"1\",\"productName\":\"Laptop\",\"productCategory\":\"Electronics\",\"productPrice\":\"10000\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.productId").exists())
				.andExpect(jsonPath("$.productName").exists()).andExpect(jsonPath("$.productCategory").exists())
				.andExpect(jsonPath("$.productPrice").exists()).andExpect(status().isOk()).andDo(print());

	}

	@Test
	public void testproductwhichUpdateProduct() throws Exception {
		Product product = new Product(1, "Laptop", "Electronics", 10000);
		when(productService.updateProduct(Mockito.isA(Product.class))).thenReturn(product);
		when(productService.findProductById(1)).thenReturn(product);
		mockMvc.perform(put("/product").contentType(MediaType.APPLICATION_JSON_UTF8).content(
				"{\"productId\":\"1\",\"productName\":\"mobile\",\"productCategory\":\"Electronics\",\"productPrice\":\"10000\"}")
				.accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.productId").exists())
				.andExpect(jsonPath("$.productName").exists()).andExpect(jsonPath("$.productCategory").exists())
				.andExpect(jsonPath("$.productPrice").exists()).andExpect(status().isOk()).andDo(print());

	}

	@Test
	public void testproductfindProductById() throws Exception {
		Product product = new Product(2, "mobile", "device", 1000);
		when(productService.findProductById(2)).thenReturn(product);
		mockMvc.perform(get("/products/2").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.productId").exists()).andExpect(status().isOk()).andDo(print());

	}

	@Test
	public void testproductdeleteProduct()  throws Exception {
		Product product=new Product(3,"ss","sp",1000);
		when(productService.findProductById(3)).thenReturn(product);
		mockMvc.perform(delete("/products/3")
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andDo(print());
	
	
}
}