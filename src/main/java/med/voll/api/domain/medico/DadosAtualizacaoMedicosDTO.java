package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEnderecoDTO;

//Aqui é criado apenas os campos id e os que podem ser atualizaveis
public record DadosAtualizacaoMedicosDTO(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEnderecoDTO endereco) {
}
