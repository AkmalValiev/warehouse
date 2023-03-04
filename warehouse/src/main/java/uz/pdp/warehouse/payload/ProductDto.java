package uz.pdp.warehouse.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String name;
    private boolean active;
    private Integer categoryId;
    private Integer attachmentId;
    private String code;
    private Integer measurementId;


}
