package uz.pdp.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.warehouse.entity.InputProduct;
import uz.pdp.warehouse.payload.InputProductDto;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.service.InputProductService;

import java.util.List;

@RestController
@RequestMapping("/inputProduct")
public class InputProductController {

    @Autowired
    InputProductService inputProductService;

    @GetMapping
    public List<InputProduct> getInputProducts(){
        return inputProductService.getInputProducts();
    }

    @GetMapping("/{id}")
    public InputProduct getInputProduct(@PathVariable Integer id){
        return inputProductService.getInputProduct(id);
    }

    @PostMapping
    public Result addInputProduct(@RequestBody InputProductDto inputProductDto){
        return inputProductService.addInputProduct(inputProductDto);
    }

    @PutMapping("/{id}")
    public Result editInputProduct(@PathVariable Integer id, @RequestBody InputProductDto inputProductDto){
        return inputProductService.editInputProduct(id, inputProductDto);
    }

    @DeleteMapping("/{id}")
    public Result deleteInputProduct(@PathVariable Integer id){
        return inputProductService.deleteInputProduct(id);
    }

}
