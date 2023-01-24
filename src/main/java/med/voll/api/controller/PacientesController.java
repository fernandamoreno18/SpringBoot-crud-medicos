package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.paciente.DadosCadastroPacientesDTO;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pacientes")
public class PacientesController {

    @Autowired //para instanciar o atributo na classe controller
    private PacientesRepository repository;

    @PostMapping
    public void cadastrar(@RequestBody @Valid DadosCadastroPacientesDTO dados){
        repository.save(new Paciente(dados));
    }
}
