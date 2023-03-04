package uz.pdp.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.warehouse.entity.User;
import uz.pdp.warehouse.entity.Warehouse;

import java.util.Set;

public interface UsersRepository extends JpaRepository<User, Integer> {
    boolean existsByPhoneNumber(String phoneNumber);
}
