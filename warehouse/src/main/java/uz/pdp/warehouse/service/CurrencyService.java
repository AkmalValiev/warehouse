package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.number.money.CurrencyUnitFormatter;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.entity.Currency;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.repository.CurrencyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    CurrencyRepository currencyRepository;

    public List<Currency> getCurrencies(){
        return currencyRepository.findAll();
    }

    public Currency getCurrency(Integer id){
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (optionalCurrency.isPresent()){
            return optionalCurrency.get();
        }
        return new Currency();
    }

    public Result addCurrency(Currency currency){
        if (!currency.getName().isEmpty()){
            boolean exists = currencyRepository.existsByName(currency.getName());
            if (!exists){
                Currency currency1 = new Currency();
                currency1.setName(currency.getName());
                currency1.setActive(currency.isActive());
                currencyRepository.save(currency1);
                return new Result("Currency qo'shildi!", true);
            }
            return new Result("Kiritilgan nomli currency mavjud!", false);
        }
        return new Result("Currency nomini kiriting!", false);
    }

    public Result editCurrency(Integer id, Currency currency){
        if (id!=null && !currency.getName().isEmpty()){
            Optional<Currency> optionalCurrency = currencyRepository.findById(id);
            if (optionalCurrency.isPresent()){
                Currency currency1 = optionalCurrency.get();
                if (!currency1.getName().equals(currency.getName())){
                    boolean exists = currencyRepository.existsByName(currency.getName());
                    if (!exists) {
                        currency1.setName(currency.getName());
                        currency1.setActive(currency.isActive());
                        currencyRepository.save(currency1);
                        return new Result("Currency taxrirlandi!", true);
                    }
                    return new Result("Bunaqa nomli currency mavjud!", false);
                }
                currency1.setName(currency.getName());
                currency1.setActive(currency.isActive());
                currencyRepository.save(currency1);
                return new Result("Currency taxrirlandi!", true);
            }
            return new Result("Kiritilgan id bo'yicha currency topilmadi!", false);
        }
        return new Result("Qatorlarni to'ldiring!", false);
    }

    public Result deleteCurrency(Integer id){
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (optionalCurrency.isPresent()){
            Currency currency = optionalCurrency.get();
            try {
                currencyRepository.delete(currency);
                return new Result("Currency o'chirildi!", true);
            }catch (Exception e){
                return new Result("Currency boshqa joyda ishlatilganligi sababli o'chirish imkoni yo'q!", false);
            }

        }
        return new Result("Kiritilgan id bo'yicha currency topilmadi!", false);
    }

}
