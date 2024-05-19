package net.fiap.postech.fastburger.adapters.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {

    private Long id;
    private String cpf;
    private String nome;
    private String email;
}
