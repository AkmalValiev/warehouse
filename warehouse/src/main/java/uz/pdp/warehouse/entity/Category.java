package uz.pdp.warehouse.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.pdp.warehouse.entity.template.AbsEntity;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Category extends AbsEntity {

    @ManyToOne
    private Category parentCategory;
}
