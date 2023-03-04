package uz.pdp.warehouse.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputDto {

    private Integer warehouseId;
    private Integer currencyId;
    private String factureNumber;
    private Integer clientId;

}
