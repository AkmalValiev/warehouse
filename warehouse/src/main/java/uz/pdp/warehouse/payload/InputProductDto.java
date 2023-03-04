package uz.pdp.warehouse.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputProductDto {

    private Integer productId;
    private Double amount;
    private Double price;
    private Integer inputId;

}
