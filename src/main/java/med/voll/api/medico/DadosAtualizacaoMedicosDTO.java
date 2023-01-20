package med.voll.api.medico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.DadosEnderecoDTO;
import med.voll.api.endereco.Endereco;

//Aqui é criado apenas os campos id e os que podem ser atualizaveis
public record DadosAtualizacaoMedicosDTO(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEnderecoDTO endereco) {
}
