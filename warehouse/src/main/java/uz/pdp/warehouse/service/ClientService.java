package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.entity.Client;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Client getClient(Integer id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            return client;
        }
        return new Client();
    }

    public Result addClient(Client client) {
        if (!client.getName().isEmpty() && !client.getPhoneNumber().isEmpty()) {
            boolean exists = clientRepository.existsByPhoneNumber(client.getPhoneNumber());
            if (!exists) {
                Client client1 = new Client();
                client1.setName(client.getName());
                client1.setPhoneNumber(client.getPhoneNumber());
                clientRepository.save(client1);
                return new Result("Client qo'shildi!", true);
            }
            return new Result("Kiritilgan telefon nomerli client mavjud!", false);
        }
        return new Result("Qatorlarni to'ldiring!", false);
    }

    public Result editClient(Integer id, Client client) {
        if (id != null) {
            Optional<Client> optionalClient = clientRepository.findById(id);
            if (optionalClient.isPresent()) {
                Client client1 = optionalClient.get();
                if (!client.getName().isEmpty() && !client.getPhoneNumber().isEmpty()) {
                    if (!client1.getPhoneNumber().equals(client.getPhoneNumber())) {
                        boolean exists = clientRepository.existsByPhoneNumber(client.getPhoneNumber());
                        if (!exists) {
                            client1.setName(client.getName());
                            client1.setPhoneNumber(client.getPhoneNumber());
                            clientRepository.save(client1);
                            return new Result("Client taxrirlandi!", true);
                        }
                        return new Result("Kiritilgan telefon nomerli client mavjud!", false);
                    }
                    client1.setName(client.getName());
                    client1.setPhoneNumber(client.getPhoneNumber());
                    clientRepository.save(client1);
                    return new Result("Client taxrirlandi!", true);
                }
                return new Result("Qatorlarni to'ldiring!", false);
            }
            return new Result("Kiritilgan id bo'yicha client topilmadi!", false);
        }
        return new Result("Client id ni kiriting!", false);
    }

    public Result deleteClient(Integer id){
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()){
            try {
                clientRepository.delete(optionalClient.get());
                return new Result("Client o'chirildi!", true);
            }catch (Exception e){
                return new Result("Client boshqa joyda ishlatilganligi sababli o'chirish imkoni yo'q!", false);
            }

        }
        return new Result("Kiritilgan id bo'yicha client topilmadi!", false);
    }

}
