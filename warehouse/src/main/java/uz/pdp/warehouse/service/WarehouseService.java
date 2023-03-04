package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.entity.Warehouse;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.repository.WarehouseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    WarehouseRepository warehouseRepository;

    public List<Warehouse> getWarehouses(){
        return warehouseRepository.findAll();
    }

    public Warehouse getWarehouse(Integer id){
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
        if (optionalWarehouse.isPresent()){
            return optionalWarehouse.get();
        }
        return new Warehouse();
    }

    public Result addWarehouse(Warehouse warehouse){
        if (!warehouse.getName().isEmpty()){
            boolean exists = warehouseRepository.existsByName(warehouse.getName());
            if (!exists){
                Warehouse warehouse1 = new Warehouse();
                warehouse1.setName(warehouse.getName());
                warehouse1.setActive(warehouse.isActive());
                warehouseRepository.save(warehouse1);
                return new Result("Warehouse qo'shildi!", true);
            }
            return new Result("Bunaqa nomli warehouse mavjud!", false);
        }
        return new Result("Warehouse nomini kiriting!", false);
    }

    public Result editWarehouse(Integer id, Warehouse warehouse) {
        if (id != null && !warehouse.getName().isEmpty()) {
            Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
            if (optionalWarehouse.isPresent()) {
                Warehouse warehouse1 = optionalWarehouse.get();
                if (!warehouse1.getName().equals(warehouse.getName())){
                    boolean exists = warehouseRepository.existsByName(warehouse.getName());
                    if (!exists){
                        warehouse1.setName(warehouse.getName());
                        warehouse1.setActive(warehouse.isActive());
                        warehouseRepository.save(warehouse1);
                        return new Result("Warehouse taxrirlandi!", true);
                    }
                    return new Result("Bunaqa nomli warehouse mavjud!", false);
                }
                warehouse1.setName(warehouse.getName());
                warehouse1.setActive(warehouse.isActive());
                warehouseRepository.save(warehouse1);
                return new Result("Warehouse taxrirlandi!", true);
            }
            return new Result("Kiritilgan id bo'yicha warehouse topilmadi!", false);
        }
        return new Result("Qatorlarni to'ldiring!", false);
    }

    public Result deleteWarehouse(Integer id){
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
        if (optionalWarehouse.isPresent()){
            Warehouse warehouse = optionalWarehouse.get();
            try {
                warehouseRepository.delete(warehouse);
                return new Result("Warehouse o'chirildi!", true);
            }catch (Exception e){
                return new Result("Bu warehouse ni o'chirish uchun ichini bo'shatish kerak bo'ladi!", false);
            }
        }
        return new Result("Kiritilgan id bo'yicha warehouse topilmadi!", false);
    }
}
