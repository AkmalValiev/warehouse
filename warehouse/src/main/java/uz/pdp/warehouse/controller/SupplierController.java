package uz.pdp.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.warehouse.entity.Supplier;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.service.SupplierService;

import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @GetMapping
    public List<Supplier> getSuppliers(){
        return supplierService.getSuppliers();
    }

    @GetMapping("/{id}")
    public Supplier getSupplier(@PathVariable Integer id){
        return supplierService.getSupplier(id);
    }

    @PostMapping
    public Result addSupplier(@RequestBody Supplier supplier){
        return supplierService.addSupplier(supplier);
    }

    @PutMapping("/{id}")
    public Result editSupplier(@PathVariable Integer id, @RequestBody Supplier supplier){
        return supplierService.editSupplier(id, supplier);
    }

    @DeleteMapping("/{id}")
    public Result deleteSupplier(@PathVariable Integer id){
        return supplierService.deleteSupplier(id);
    }

}
