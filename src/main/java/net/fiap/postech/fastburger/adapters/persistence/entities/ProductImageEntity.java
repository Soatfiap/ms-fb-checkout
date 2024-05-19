package net.fiap.postech.fastburger.adapters.persistence.entities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageEntity {

    private Long id;
    private String url;
}
