package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.entity.Client;
import uz.pdp.warehouse.entity.Currency;
import uz.pdp.warehouse.entity.Output;
import uz.pdp.warehouse.entity.Warehouse;
import uz.pdp.warehouse.payload.OutputDto;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.repository.ClientRepository;
import uz.pdp.warehouse.repository.CurrencyRepository;
import uz.pdp.warehouse.repository.OutputRepository;
import uz.pdp.warehouse.repository.WarehouseRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OutputService {

    @Autowired
    OutputRepository outputRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CurrencyRepository currencyRepository;

    public List<Output> getOutputs(){
        return outputRepository.findAll();
    }

    public Output getOutput(Integer id){
        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (optionalOutput.isPresent()){
            return optionalOutput.get();
        }
        return new Output();
    }

    public Result addOutput(OutputDto outputDto){
        if (outputDto.getClientId()!=null && outputDto.getCurrencyId()!=null
        && outputDto.getWarehouseId()!=null && !outputDto.getFactureNumber().isEmpty()){
            Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
            if (optionalWarehouse.isPresent()){
                Warehouse warehouse = optionalWarehouse.get();
                Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
                if (optionalClient.isPresent()){
                    Client client = optionalClient.get();
                    Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyId());
                    if (optionalCurrency.isPresent()){
                        Currency currency = optionalCurrency.get();
                        Timestamp timestamp = Timestamp.from(Instant.now());
                        int code =0;
                        List<Output> outputList = outputRepository.findAll();
                        if (outputList.isEmpty()){
                            code = 1;
                        }else {
                            code = Integer.parseInt(outputList.get(outputList.size()-1).getCode())+1;
                        }
                        Output output = new Output();
                        output.setClient(client);
                        output.setCode(String.valueOf(code));
                        output.setDate(timestamp);
                        output.setCurrency(currency);
                        output.setWarehouse(warehouse);
                        output.setFactureNumber(outputDto.getFactureNumber());
                        outputRepository.save(output);
                        return new Result("Output qo'shildi!", true);
                    }
                    return new Result("Kiritilgan id bo'yicha currency topilmadi!",false);
                }
                return new Result("Kiritilgan id bo'yicha client topilmadi!",false);
            }
            return new Result("Kiritilgan id bo'yicha warehouse topilmadi!",false);
        }
        return new Result("Qatorlarni to'ldiring!", false);
    }

    public Result editOutput(Integer id, OutputDto outputDto){
        if (id!=null){
            Optional<Output> optionalOutput = outputRepository.findById(id);
            if (optionalOutput.isPresent()){
                Output output = optionalOutput.get();
                if (outputDto.getClientId()!=null && outputDto.getCurrencyId()!=null
                        && outputDto.getWarehouseId()!=null && !outputDto.getFactureNumber().isEmpty()){
                    Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
                    if (optionalWarehouse.isPresent()){
                        Warehouse warehouse = optionalWarehouse.get();
                        Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
                        if (optionalClient.isPresent()){
                            Client client = optionalClient.get();
                            Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyId());
                            if (optionalCurrency.isPresent()){
                                Currency currency = optionalCurrency.get();
                                Timestamp timestamp = Timestamp.from(Instant.now());
                                output.setClient(client);
                                output.setCode(output.getCode());
                                output.setDate(timestamp);
                                output.setCurrency(currency);
                                output.setWarehouse(warehouse);
                                output.setFactureNumber(outputDto.getFactureNumber());
                                outputRepository.save(output);
                                return new Result("Output taxrirlandi!", true);
                            }
                            return new Result("Kiritilgan id bo'yicha currency topilmadi!",false);
                        }
                        return new Result("Kiritilgan id bo'yicha client topilmadi!",false);
                    }
                    return new Result("Kiritilgan id bo'yicha warehouse topilmadi!",false);
                }
                return new Result("Qatorlarni to'ldiring!", false);
            }
            return new Result("Kiritilgan id bo'yicha output topilmadi!", false);
        }
        return new Result("Id ni kiriting!", false);
    }

    public Result deleteOutput(Integer id){
        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (optionalOutput.isPresent()){
            try {
                outputRepository.delete(optionalOutput.get());
                return new Result("Output o'chirildi!", true);
            }catch (Exception e){
                return new Result("Output boshqa joyda ishlatilganligi uchun o'shirishning imkani yo'q!", false);
            }

        }
        return new Result("Kiritilgan id bo'yicha output topilmadi!", false);
    }
}
