package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.entity.User;
import uz.pdp.warehouse.entity.Warehouse;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.repository.UsersRepository;
import uz.pdp.warehouse.repository.WarehouseRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsersService {
    @Autowired
    UsersRepository userRepository;
    @Autowired
    WarehouseRepository warehouseRepository;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUser(Integer id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        }
        return new User();
    }

    public Result addUser(User user){
        if (!user.getFirstName().isEmpty() && !user.getLastName().isEmpty() && !user.getPassword().isEmpty()
        && !user.getWarehouses().isEmpty() && !user.getPhoneNumber().isEmpty()){
            boolean exists = userRepository.existsByPhoneNumber(user.getPhoneNumber());
            if (!exists){
                List<Warehouse> warehouseList = warehouseRepository.findAll();
                Set<Warehouse> warehouses = user.getWarehouses();
                for (Warehouse warehouse : warehouseList) {
                    for (Warehouse warehouseSet : warehouses) {
                        if (warehouse.getId()!=warehouseSet.getId()){
                          return new Result("Kiritilgan warehouse id bo'yicha warehouse topilmadi!", false);
                        }
                    }
                }
                    int code = 0;
                    List<User> userList = userRepository.findAll();
                    if (userList.isEmpty()){
                        code = 1;
                    }else {
                        String codeString = userList.get(userList.size() - 1).getCode();
                        code = Integer.parseInt(codeString)+1;
                    }
                    User user1 = new User();
                    user1.setCode(String.valueOf(code));
                    user1.setActive(user.isActive());
                    user1.setPassword(user.getPassword());
                    user1.setWarehouses(user.getWarehouses());
                    user1.setFirstName(user.getFirstName());
                    user1.setLastName(user.getLastName());
                    user1.setPhoneNumber(user.getPhoneNumber());
                    userRepository.save(user1);
                    return new Result("User qo'shildi!", true);
            }
            return new Result("Kiritilgan telefon raqamli user mavjud!", false);
        }
        return new Result("Qatorlarni to'ldiring!", false);
    }

    public Result editUser(Integer id, User user){
        if (id!=null){
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()){
                User user1 = optionalUser.get();
                if (!user.getFirstName().isEmpty() && !user.getLastName().isEmpty() && !user.getPassword().isEmpty()
                        && !user.getWarehouses().isEmpty() && !user.getPhoneNumber().isEmpty()){
                    if (user1.getPhoneNumber().equals(user.getPhoneNumber())){
                        List<Warehouse> warehouseList = warehouseRepository.findAll();
                        Set<Warehouse> warehouses = user.getWarehouses();
                        for (Warehouse warehouse : warehouseList) {
                            for (Warehouse warehouseSet : warehouses) {
                                if (warehouse.getId()!=warehouseSet.getId()){
                                    return new Result("Kiritilgan warehouse id bo'yicha warehouse topilmadi!", false);
                                }
                            }
                        }
                            int code = 0;
                            List<User> userList = userRepository.findAll();
                            if (userList.isEmpty()){
                                code = 1;
                            }else {
                                String codeString = userList.get(userList.size() - 1).getCode();
                                code = Integer.parseInt(codeString)+1;
                            }
                            user1.setCode(String.valueOf(code));
                            user1.setActive(user.isActive());
                            user1.setPassword(user.getPassword());
                            user1.setWarehouses(user.getWarehouses());
                            user1.setFirstName(user.getFirstName());
                            user1.setLastName(user.getLastName());
                            user1.setPhoneNumber(user.getPhoneNumber());
                            userRepository.save(user1);
                            return new Result("User taxrirlandi!", true);
                    }
                    boolean exists = userRepository.existsByPhoneNumber(user.getPhoneNumber());
                    if (!exists){
                        List<Warehouse> warehouseList = warehouseRepository.findAll();
                        Set<Warehouse> warehouses = user.getWarehouses();
                        for (Warehouse warehouse : warehouseList) {
                            for (Warehouse warehouseSet : warehouses) {
                                if (warehouse.getId()!=warehouseSet.getId()){
                                    return new Result("Kiritilgan warehouse id bo'yicha warehouse topilmadi!", false);
                                }
                            }
                        }
                            int code = 0;
                            List<User> userList = userRepository.findAll();
                            if (userList.isEmpty()){
                                code = 1;
                            }else {
                                String codeString = userList.get(userList.size() - 1).getCode();
                                code = Integer.parseInt(codeString)+1;
                            }
                            user1.setCode(String.valueOf(code));
                            user1.setActive(user.isActive());
                            user1.setPassword(user.getPassword());
                            user1.setWarehouses(user.getWarehouses());
                            user1.setFirstName(user.getFirstName());
                            user1.setLastName(user.getLastName());
                            user1.setPhoneNumber(user.getPhoneNumber());
                            userRepository.save(user1);
                            return new Result("User taxrirlandi!", true);
                    }
                    return new Result("Kiritilgan telefon raqamli user mavjud!", false);
                }
                return new Result("Qatorlarni to'ldiring!", false);
            }
            return new Result("Kiritilgan id bo'yicha user topilmadi!", false);
        }
        return new Result("User id ni kiriting!", false);
    }

    public Result deleteUser(Integer id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            try {
                userRepository.delete(user);
                return new Result("User o'chirildi!", true);
            }catch (Exception e){
                return new Result("User boshqa joyda ishlatilganligi sababli o'chirish imkoniyati yo'q!", false);
            }

        }
        return new Result("Kiritilgan id bo'yicha user topilmadi!", false);
    }

}
