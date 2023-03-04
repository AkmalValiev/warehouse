package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.entity.Currency;
import uz.pdp.warehouse.entity.Input;
import uz.pdp.warehouse.entity.Supplier;
import uz.pdp.warehouse.entity.Warehouse;
import uz.pdp.warehouse.payload.InputDto;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.repository.CurrencyRepository;
import uz.pdp.warehouse.repository.InputRepository;
import uz.pdp.warehouse.repository.SupplierRepository;
import uz.pdp.warehouse.repository.WarehouseRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InputService {

    @Autowired
    InputRepository inputRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    SupplierRepository supplierRepository;

    public List<Input> getInputs() {
        return inputRepository.findAll();
    }

    public Input getInput(Integer id) {
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (optionalInput.isPresent()) {
            return optionalInput.get();
        }
        return new Input();
    }

    public Result addInput(InputDto inputDto){
        if (inputDto.getCurrencyId()!=null && inputDto.getSupplierId()!=null
        && !inputDto.getFactureNumber().isEmpty() && inputDto.getWarehouseId()!=null){
            Optional<Currency> optionalCurrency = currencyRepository.findById(inputDto.getCurrencyId());
            if (optionalCurrency.isPresent()){
                Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplierId());
                if (optionalSupplier.isPresent()){
                    Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDto.getWarehouseId());
                    if (optionalWarehouse.isPresent()){
                        Timestamp timestamp = Timestamp.from(Instant.now());
                        int code = 0;
                        List<Input> inputList = inputRepository.findAll();
                        if (inputList.isEmpty()){
                            code = 1;
                        }else {
                            code = Integer.parseInt(inputList.get(inputList.size()-1).getCode())+1;
                        }
                        Input input = new Input();
                        input.setCode(String.valueOf(code));
                        input.setDate(timestamp);
                        input.setCurrency(optionalCurrency.get());
                        input.setSupplier(optionalSupplier.get());
                        input.setWarehouse(optionalWarehouse.get());
                        input.setFactureNumber(inputDto.getFactureNumber());
                        inputRepository.save(input);
                        return new Result("Input qo'shildi!", true);
                    }
                    return new Result("Kiritilgan id bo'yicha warehouse topilmadi!", false);
                }
                return new Result("Kiritilgan id bo'yicha supplier topilmadi!", false);
            }
            return new Result("Kiritilgan id bo'yicha currency topilmadi!", false);
        }
        return new Result("Qatorlarni to'ldiring!", false);
    }

    public Result editInput(Integer id, InputDto inputDto){
        if (id!=null){
            if (inputDto.getCurrencyId()!=null && inputDto.getSupplierId()!=null
                    && !inputDto.getFactureNumber().isEmpty() && inputDto.getWarehouseId()!=null){
                Optional<Input> optionalInput = inputRepository.findById(id);
                if (optionalInput.isPresent()){
                    Input input = optionalInput.get();
                    Optional<Currency> optionalCurrency = currencyRepository.findById(inputDto.getCurrencyId());
                    if (optionalCurrency.isPresent()){
                        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplierId());
                        if (optionalSupplier.isPresent()){
                            Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDto.getWarehouseId());
                            if (optionalWarehouse.isPresent()){
                                Timestamp timestamp = Timestamp.from(Instant.now());
                                input.setCode(String.valueOf(input.getCode()));
                                input.setDate(timestamp);
                                input.setCurrency(optionalCurrency.get());
                                input.setSupplier(optionalSupplier.get());
                                input.setWarehouse(optionalWarehouse.get());
                                input.setFactureNumber(inputDto.getFactureNumber());
                                inputRepository.save(input);
                                return new Result("Input taxrirlandi!", true);
                            }
                            return new Result("Kiritilgan id bo'yicha warehouse topilmadi!", false);
                        }
                        return new Result("Kiritilgan id bo'yicha supplier topilmadi!", false);
                    }
                    return new Result("Kiritilgan id bo'yicha currency topilmadi!", false);
                }
                return new Result("Kiritilgan id bo'yicha input topilmadi!", false);
            }
            return new Result("Qatorlarni to'ldiring!", false);
        }
        return new Result("Input id ni kiriting!", false);
    }

    public Result deleteInput(Integer id){
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (optionalInput.isPresent()){
            try {
                inputRepository.delete(optionalInput.get());
                return new Result("Input o'chirildi!", true);
            }catch (Exception e){
                return new Result("Input boshqa joyda ishlatilganligi sababli o'chirish imkoni yo'q!", false);
            }

        }
        return new Result("Kiritilgan id bo'yicha input topilmadi!", false);
    }

}
