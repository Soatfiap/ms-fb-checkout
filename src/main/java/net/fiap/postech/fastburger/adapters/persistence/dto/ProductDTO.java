package net.fiap.postech.fastburger.adapters.persistence.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.fiap.postech.fastburger.application.domain.enums.CategoryEnum;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @NotBlank
    private String name;

    @NotNull
    private CategoryEnum categoryEnum;

    @NotNull
    private Double price;

    @NotBlank
    private String description;

    @Valid
    private List<ProductImageDTO> images;
}
