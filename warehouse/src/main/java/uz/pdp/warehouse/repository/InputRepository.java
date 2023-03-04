package uz.pdp.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.warehouse.entity.Input;
import uz.pdp.warehouse.entity.Warehouse;

public interface InputRepository extends JpaRepository<Input, Integer> {
}
