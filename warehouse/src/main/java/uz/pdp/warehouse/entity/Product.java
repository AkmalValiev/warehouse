package uz.pdp.warehouse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.warehouse.entity.template.AbsEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends AbsEntity {

    @ManyToOne
    private Category category;

    @OneToOne
    private Attachment file;

    @Column(nullable = false, unique = true)
    private String code;

    @ManyToOne
    private Measurement measurement;

}
