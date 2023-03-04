package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.entity.Input;
import uz.pdp.warehouse.entity.InputProduct;
import uz.pdp.warehouse.entity.Product;
import uz.pdp.warehouse.payload.InputProductDto;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.repository.InputProductRepository;
import uz.pdp.warehouse.repository.InputRepository;
import uz.pdp.warehouse.repository.ProductRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InputProductService {

    @Autowired
    InputProductRepository inputProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    InputRepository inputRepository;

    public List<InputProduct> getInputProducts(){
        return inputProductRepository.findAll();
    }

    public InputProduct getInputProduct(Integer id){
        Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(id);
        if (optionalInputProduct.isPresent()){
            return optionalInputProduct.get();

        }
        return new InputProduct();
    }

    public Result addInputProduct(InputProductDto inputProductDto){
        if (inputProductDto.getProductId()!=null && inputProductDto.getInputId()!=null
        && inputProductDto.getAmount()!=null && inputProductDto.getPrice()!=null){
            Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
            if (optionalProduct.isPresent()){
                Product product = optionalProduct.get();
                Optional<Input> optionalInput = inputRepository.findById(inputProductDto.getInputId());
                if (optionalInput.isPresent()){
                    Input input = optionalInput.get();
                    Date date = new Date();
                    InputProduct inputProduct = new InputProduct();
                    inputProduct.setProduct(product);
                    inputProduct.setInput(input);
                    inputProduct.setPrice(inputProductDto.getPrice());
                    inputProduct.setAmount(inputProductDto.getAmount());
                    inputProduct.setExpireDate(date);
                    inputProductRepository.save(inputProduct);
                    return new Result("InputProduct qo'shildi!", true);
                }
                return new Result("Kiritilgan id bo'yicha input topilmadi!", false);
            }
            return new Result("Kiritilgan id bo'yicha product topilmadi!", false);
        }
        return new Result("Qatorlarni to'ldiring!", false);
    }

    public Result editInputProduct(Integer id, InputProductDto inputProductDto){
        if (id!=null){
            Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(id);
            if (optionalInputProduct.isPresent()){
                InputProduct inputProduct = optionalInputProduct.get();
                Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
                if (optionalProduct.isPresent()){
                    Product product = optionalProduct.get();
                    Optional<Input> optionalInput = inputRepository.findById(inputProductDto.getInputId());
                    if (optionalInput.isPresent()){
                        Input input = optionalInput.get();
                        Date date = new Date();
                        inputProduct.setProduct(product);
                        inputProduct.setInput(input);
                        inputProduct.setPrice(inputProductDto.getPrice());
                        inputProduct.setAmount(inputProductDto.getAmount());
                        inputProduct.setExpireDate(date);
                        inputProductRepository.save(inputProduct);
                        return new Result("InputProduct taxrirlandi!", true);
                    }
                    return new Result("Kiritilgan id bo'yicha input topilmadi!", false);
                }
                return new Result("Kiritilgan id bo'yicha product topilmadi!", false);
            }
            return new Result("Kiritilgan id bo'yicha inputProduct topilmadi!", false);
        }
        return new Result("InputProduct id ni kiriting!", false);

    }

    public Result deleteInputProduct(Integer id){
        Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(id);
        if (optionalInputProduct.isPresent()){
            try {
                inputProductRepository.delete(optionalInputProduct.get());
                return new Result("InputProduct o'chirildi!", true);
            }catch (Exception e){
                return new Result("Bu inputProduct boshqa joyda ishlatilganligi sababli, o'chirishning imkoni yo'q!", false);
            }
        }
        return new Result("Kiritilgan id bo'yicha inputProduct topilmadi!", false);
    }

}
