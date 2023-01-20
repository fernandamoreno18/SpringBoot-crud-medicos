package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicos")
public class MedicosController {

    @Autowired //para instanciar o atributo na classe controller
    private MedicoRepository repository;

    @PostMapping
    @Transactional //transacao ativa no bd
    public void cadastrar(@RequestBody @Valid DadosCadastroMedicosDTO dados){
        repository.save(new Medico(dados));//metodo para salvar no banco de dados;
    }

    @GetMapping
    // o tipo Page desse metodo, é uma lista com paginação
    // @PageableDefault para ordenar e ja vir quando nao passar nada na url. Se passar na url, vai sobrescrever
    public Page<DadosListagemMedicoDTO> listar(@PageableDefault(size = 10, sort = {"email"}) Pageable paginacao){
        //paginacao, o spring ja vai fazer a paginacao automaticamente
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedicoDTO::new);
        //find all by ativo true - ele ja monta a consulta - findallby nome do atributo como é boolean, ja cria como True
    }

    @PutMapping @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedicosDTO dados){
        var medico = repository.getReferenceById(dados.id()); //ir no banco de dados e carregar o medico de acordo com o id
        medico.atualizarInformacoes(dados);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){ //essa anotação para o spring entender que é um parametro do path, da url
        var medico = repository.getReferenceById(id); //pegar o id
        medico.excluir();

    }



}
