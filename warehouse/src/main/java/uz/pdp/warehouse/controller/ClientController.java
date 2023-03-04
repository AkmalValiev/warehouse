package uz.pdp.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.warehouse.entity.Client;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping
    public List<Client> getClients(){
        return clientService.getClients();
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable Integer id){
        return clientService.getClient(id);
    }

    @PostMapping
    public Result addClient(@RequestBody Client client){
        return clientService.addClient(client);
    }

    @PutMapping("/{id}")
    public Result editClient(@PathVariable Integer id, @RequestBody Client client){
        return clientService.editClient(id, client);
    }

    @DeleteMapping("/{id}")
    public Result deleteClient(@PathVariable Integer id){
        return clientService.deleteClient(id);
    }

}
