package com.karthic.ecommerce.service;

import com.karthic.ecommerce.exception.APIException;
import com.karthic.ecommerce.exception.ResourceNotFoundException;
import com.karthic.ecommerce.model.Category;
import com.karthic.ecommerce.model.Product;
import com.karthic.ecommerce.payload.ProductDTO;
import com.karthic.ecommerce.payload.ProductResponse;
import com.karthic.ecommerce.repositories.CategoryRepository;
import com.karthic.ecommerce.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{


    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;


    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException("Category","category",categoryId));

        boolean isProductNotPresent = true;

        List<Product> products = category.getProducts();
        for (Product value : products) {
            if (value.getProductName().equals(productDTO.getProductName())) {
                isProductNotPresent = false;
                break;
            }

        }

        if(isProductNotPresent){

        Product product = modelMapper.map(productDTO,Product.class);
        product.setCategory(category);
        product.setImage("test.png");
        double specialPrice = product.getPrice()-((product.getDiscount()*0.01)*product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct,ProductDTO.class);}
        else {
            throw new APIException("Product already exists!");
        }
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> pageProducts = productRepository.findAll(pageDetails);

        List<Product> products = pageProducts.getContent();
        List<ProductDTO> productDTOS = products.stream().
                map(product -> modelMapper.map(product,ProductDTO.class)).toList();


        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException("Category","category",categoryId));

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> pageProducts = productRepository.findByCategoryOrderByPriceAsc(category,pageDetails);

        List<Product> products = pageProducts.getContent();

        if(products.isEmpty()){
            throw new APIException(category.getCategoryName() + " category does not have any products");
        }

        List<ProductDTO> productDTOS = products.stream().
                map(product -> modelMapper.map(product,ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);


        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());
        return productResponse;

    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> pageProducts = productRepository.findByProductNameLikeIgnoreCase('%'+keyword + '%',pageDetails);


        List<Product> products =pageProducts.getContent();


        List<ProductDTO> productDTOS = products.stream().
                map(product -> modelMapper.map(product,ProductDTO.class)).toList();

        if(products.isEmpty()){
            throw new APIException("Products not found with keyword "+ keyword);
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);


        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {

        Product productFromDB = productRepository.findById(productId).
                orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));

        Product product = modelMapper.map(productDTO,Product.class);

        productFromDB.setProductName(product.getProductName());
        productFromDB.setDescription(product.getDescription());
        productFromDB.setQuantity(product.getQuantity());
        productFromDB.setDiscount(product.getDiscount());
        productFromDB.setPrice(product.getPrice());
        productFromDB.setSpecialPrice(product.getSpecialPrice());

        Product savedProduct = productRepository.save(productFromDB);



        return modelMapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));

        productRepository.delete(product);
        return modelMapper.map(product,ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {

        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));


        String fileName = fileService.uploadImage(path,image);

        productFromDB.setImage(fileName);

        Product updatedProduct = productRepository.save(productFromDB);

        return modelMapper.map(updatedProduct,ProductDTO.class);
    }



}
