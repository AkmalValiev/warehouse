package uz.pdp.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.warehouse.entity.Product;
import uz.pdp.warehouse.payload.ProductDto;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Integer id){
        return productService.getProduct(id);
    }

    @PostMapping
    public Result addProduct(@RequestBody ProductDto productDto){
        return productService.addProduct(productDto);
    }

    @PutMapping("/{id}")
    public Result editProduct(@PathVariable Integer id, @RequestBody ProductDto productDto){
        return productService.editProduct(id, productDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return productService.deleteProduct(id);
    }

}
