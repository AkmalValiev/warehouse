package uz.pdp.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.warehouse.entity.Currency;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.service.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    @Autowired
    CurrencyService currencyService;

    @GetMapping
    public List<Currency> getCurrencies() {
        return currencyService.getCurrencies();
    }

    @GetMapping("/{id}")
    public Currency getCurrency(@PathVariable Integer id) {
        return currencyService.getCurrency(id);
    }

    @PostMapping
    public Result addCurrency(@RequestBody Currency currency) {
        return currencyService.addCurrency(currency);
    }

    @PutMapping("/{id}")
    public Result editCurrency(@PathVariable Integer id, @RequestBody Currency currency) {
        return currencyService.editCurrency(id, currency);
    }

    @DeleteMapping("/{id}")
    public Result deleteCurrency(@PathVariable Integer id) {
        return currencyService.deleteCurrency(id);
    }
}
