package net.fiap.postech.fastburger.adapters.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.fiap.postech.fastburger.application.domain.enums.CategoryEnum;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    private Long SKU;
    private String name;
    private CategoryEnum categoryEnum;
    private Double price;
    private String description;
    private List<ProductImageEntity> images;
}
