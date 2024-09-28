package gvfirmeza.client_manager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A rua é obrigatória")
    @Size(min = 3, max = 255, message = "A rua deve ter entre 3 e 255 caracteres")
    private String rua;

    @NotBlank(message = "O número é obrigatório")
    private String numero;

    @NotBlank(message = "O bairro é obrigatório")
    @Size(min = 3, max = 100, message = "O bairro deve ter entre 3 e 100 caracteres")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória")
    @Size(min = 2, max = 100, message = "A cidade deve ter entre 2 e 100 caracteres")
    private String cidade;

    @NotBlank(message = "O estado é obrigatório")
    @Pattern(regexp = "AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO", message = "O estado deve ser um dos estados válidos")
    private String estado;

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve seguir o padrão XXXXX-XXX")
    private String cep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    @JsonBackReference
    private Client cliente;
}