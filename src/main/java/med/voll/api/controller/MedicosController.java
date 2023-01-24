package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("medicos")
public class MedicosController {

    @Autowired //para instanciar o atributo na classe controller
    private MedicoRepository repository;

    @PostMapping
    @Transactional //transacao ativa no bd
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedicosDTO dados, UriComponentsBuilder uriBuilder){
        var medico = new Medico(dados);

        repository.save(medico);//metodo para salvar no banco de dados;

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();


        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedicosDTO(medico));
    }

    @GetMapping
    // o tipo Page desse metodo, é uma lista com paginação
    // @PageableDefault para ordenar e ja vir quando nao passar nada na url. Se passar na url, vai sobrescrever
    public ResponseEntity<Page<DadosListagemMedicoDTO>> listar(@PageableDefault(size = 10, sort = {"email"}) Pageable paginacao){
        //paginacao, o spring ja vai fazer a paginacao automaticamente
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedicoDTO::new);
        //find all by ativo true - ele ja monta a consulta - findallby nome do atributo como é boolean, ja cria como True

        return ResponseEntity.ok(page);
    }

    @PutMapping @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedicosDTO dados){
        var medico = repository.getReferenceById(dados.id()); //ir no banco de dados e carregar o medico de acordo com o id
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedicosDTO(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    //Response Entity para alterar os retornos
    public ResponseEntity excluir(@PathVariable Long id){ //essa anotação para o spring entender que é um parametro do path, da url
        var medico = repository.getReferenceById(id); //pegar o id
        medico.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    //Response Entity para alterar os retornos
    public ResponseEntity detalhar (@PathVariable Long id){
        var medico = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoMedicosDTO(medico));
    }




}
