package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.entity.Output;
import uz.pdp.warehouse.entity.OutputProduct;
import uz.pdp.warehouse.entity.Product;
import uz.pdp.warehouse.payload.OutputProductDto;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.repository.OutputProductRepository;
import uz.pdp.warehouse.repository.OutputRepository;
import uz.pdp.warehouse.repository.ProductRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OutputProductService {

    @Autowired
    OutputProductRepository outputProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OutputRepository outputRepository;

    public List<OutputProduct> getOutputProducts(){
        return outputProductRepository.findAll();
    }

    public OutputProduct getOutputProduct(Integer id){
        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(id);
        if (optionalOutputProduct.isPresent()){
            return optionalOutputProduct.get();
        }
        return new OutputProduct();
    }

    public Result addOutputProduct(OutputProductDto outputProductDto){
        if (outputProductDto.getOutputId()!=null && outputProductDto.getProductId()!=null
        && outputProductDto.getAmount()!=null && outputProductDto.getPrice()!=null){
            Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductId());
            if (optionalProduct.isPresent()){
                Product product = optionalProduct.get();
                Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputId());
                if (optionalOutput.isPresent()){
                    Output output = optionalOutput.get();
                    Date date = new Date();
                    OutputProduct outputProduct = new OutputProduct();
                    outputProduct.setProduct(product);
                    outputProduct.setOutput(output);
                    outputProduct.setPrice(outputProductDto.getPrice());
                    outputProduct.setAmount(outputProductDto.getAmount());
                    outputProduct.setExpireDate(date);
                    outputProductRepository.save(outputProduct);
                    return new Result("OutputProduct qo'shildi!", true);
                }
                return new Result("Kiritilgan id bo'yicha output topilmadi!", false);
            }
            return new Result("Kiritilgan id bo'yicha product topilmadi!", false);
        }
        return new Result("Qatorlarni to'ldiring!", false);
    }

    public Result editOutputProduct(Integer id, OutputProductDto outputProductDto){
        if (id!=null){
            Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(id);
            if (optionalOutputProduct.isPresent()){
                OutputProduct outputProduct = optionalOutputProduct.get();
                if (outputProductDto.getOutputId()!=null && outputProductDto.getProductId()!=null
                        && outputProductDto.getAmount()!=null && outputProductDto.getPrice()!=null){
                    Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductId());
                    if (optionalProduct.isPresent()){
                        Product product = optionalProduct.get();
                        Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputId());
                        if (optionalOutput.isPresent()){
                            Output output = optionalOutput.get();
                            Date date = new Date();
                            outputProduct.setProduct(product);
                            outputProduct.setOutput(output);
                            outputProduct.setPrice(outputProductDto.getPrice());
                            outputProduct.setAmount(outputProductDto.getAmount());
                            outputProduct.setExpireDate(date);
                            outputProductRepository.save(outputProduct);
                            return new Result("OutputProduct taxrirlandi!", true);
                        }
                        return new Result("Kiritilgan id bo'yicha output topilmadi!", false);
                    }
                    return new Result("Kiritilgan id bo'yicha product topilmadi!", false);
                }
                return new Result("Qatorlarni to'ldiring!", false);
            }
            return new Result("Kiritilgan id bo'yicha outputProduct topilmadi!", false);
        }
        return new Result("Id ni kiriting!", false);
    }

    public Result deleteOutputProduct(Integer id){
        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(id);
        if (optionalOutputProduct.isPresent()){
            OutputProduct outputProduct = optionalOutputProduct.get();
            try {
                outputProductRepository.delete(outputProduct);
                return new Result("OutputProduct o'chirildi!", true);
            }catch (Exception e){
                return new Result("OutputProduct boshqa joyda ishlatilganligi uchun o'chirish imkonsiz!", false);
            }

        }
        return new Result("Kiritilgan id bo'yicha outputProduct topilmadi!", false);
    }

}
