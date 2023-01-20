package med.voll.api.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.endereco.DadosEnderecoDTO;

public record DadosCadastroMedicosDTO(
        @NotBlank //campo obrigatorio, nao pode ser nulo e vazio
        String nome,
        @NotBlank @Email
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}") //Expressao regular, qual a expressao - \\d(dizer q é um digito) {4,6} de 4 a 6 digitos
        String crm,
        @NotNull
        Especialidade especialidade,
        @NotNull @Valid //@valid para ele validar esse outro objeto tambem
        DadosEnderecoDTO endereco) {
}
