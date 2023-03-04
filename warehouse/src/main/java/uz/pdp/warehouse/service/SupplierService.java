package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.entity.Supplier;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.repository.SupplierRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    public List<Supplier> getSuppliers(){
        return supplierRepository.findAll();
    }

    public Supplier getSupplier(Integer id){
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isPresent()){
            return optionalSupplier.get();
        }
        return new Supplier();
    }

    public Result addSupplier(Supplier supplier){
        if (!supplier.getName().isEmpty() && !supplier.getPhoneNumber().isEmpty()){
            boolean exists = supplierRepository.existsByPhoneNumber(supplier.getPhoneNumber());
            if (!exists){
                Supplier supplier1 = new Supplier();
                supplier1.setName(supplier.getName());
                supplier1.setPhoneNumber(supplier.getPhoneNumber());
                supplier1.setActive(supplier.isActive());
                supplierRepository.save(supplier1);
                return new Result("Supplier qo'shildi", true);
            }
            return new Result("Kiritilgan telefon nomerli supplier mavjud!", false);
        }
        return new Result("Qatorlarni to'ldiring!", false);
    }

    public Result editSupplier(Integer id, Supplier supplier){
        if (id!=null && !supplier.getName().isEmpty() && !supplier.getPhoneNumber().isEmpty()){
            Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
            if (optionalSupplier.isPresent()){
                Supplier supplier1 = optionalSupplier.get();
                if (supplier1.getPhoneNumber().equals(supplier.getPhoneNumber())){
                    supplier1.setName(supplier.getName());
                    supplier1.setPhoneNumber(supplier.getPhoneNumber());
                    supplier1.setActive(supplier.isActive());
                    supplierRepository.save(supplier1);
                    return new Result("Supplier taxrirlandi!", true);
                }
                boolean exists = supplierRepository.existsByPhoneNumber(supplier.getPhoneNumber());
                if (!exists){
                    supplier1.setName(supplier.getName());
                    supplier1.setPhoneNumber(supplier.getPhoneNumber());
                    supplier1.setActive(supplier.isActive());
                    supplierRepository.save(supplier1);
                    return new Result("Supplier taxrirlandi!", true);
                }
                return new Result("Kiritilgan telefon raqamli supplier mavjud!", false);
            }
            return new Result("Kiritilgan id bo'yicha supplier topilmadi!", false);
        }
        return new Result("Qatorlarni to'ldiring!", false);
    }

    public Result deleteSupplier(Integer id){
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isPresent()){
            Supplier supplier = optionalSupplier.get();
            try {
                supplierRepository.delete(supplier);
                return new Result("Supplier o'chirildi!", true);
            }catch (Exception e){
                return new Result("Bu supplier boshqa joyda xam ishlatilganligi tufayli uni o'chirish imkoniyati yo'q!", false);
            }
        }
        return new Result("Kiritilgan id bo'yicha supplier topilmadi!", false);
    }
}
