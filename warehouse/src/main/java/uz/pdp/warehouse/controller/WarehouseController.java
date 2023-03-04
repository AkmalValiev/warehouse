package uz.pdp.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.warehouse.entity.Warehouse;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.repository.WarehouseRepository;
import uz.pdp.warehouse.service.WarehouseService;

import java.util.List;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    WarehouseService warehouseService;

    @GetMapping
    public List<Warehouse> getWarehouses(){
        return warehouseService.getWarehouses();
    }

    @GetMapping("/{id}")
    public Warehouse getWarehouse(@PathVariable Integer id){
        return warehouseService.getWarehouse(id);
    }

    @PostMapping
    public Result addWarehouse(@RequestBody Warehouse warehouse){
        return warehouseService.addWarehouse(warehouse);
    }

    @PutMapping("/{id}")
    public Result editWarehouse(@PathVariable Integer id, @RequestBody Warehouse warehouse){
        return warehouseService.editWarehouse(id, warehouse);
    }

    @DeleteMapping("/{id}")
    public Result deleteWarehouse(@PathVariable Integer id){
        return warehouseService.deleteWarehouse(id);
    }

}
