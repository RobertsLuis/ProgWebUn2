package web.trab2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.trab2.config.Utils;
import web.trab2.model.Aluno;
import web.trab2.model.AlunoDto;
import web.trab2.repository.AlunoRepository;

import java.util.ArrayList;
import java.util.Optional;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

@RestController
public class Controller {

    @Autowired
    private AlunoRepository repository;

    @GetMapping("/getAll")
    public ResponseEntity<ArrayList<Aluno>> getAll() {

        /*
            Aqui você consulta o Repository para retornar um array list com todos os dados.
            Evidentemente, você deve remover este return null.
         */
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }

    @GetMapping("/getByMatricula")
    public ResponseEntity<Object> getByMatricula(@RequestParam String matricula) {

        Aluno aluno = repository.findAlunoByMatricula(matricula);
        if (aluno != null) {
            return ResponseEntity.status(HttpStatus.OK).body(aluno);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado");
        }
    }

    @PostMapping("/updateAluno")
    public ResponseEntity<Object> updateAluno(@RequestBody AlunoDto dto) {
        /*
            Aqui você atualiza os dados de um aluno. Note que findById te retornará um objeto
            do tipo Optional. Ele poderá indicar se o id do dado realmente existe. Caso exista,
            use o Repository para salvar o objeto Aluno. Se o aluno com o id informado não
            existir, responda status NOT_FOUND como o corpo da mensagem "Não há aluno com este id".
            O último return não pode ser null. Corrija isso.
         */
        Aluno alunoBusca = repository.findAlunoById(parseLong(dto.id));

        if (alunoBusca != null){
                Aluno alunoEdicao = new Aluno(dto);
                System.out.println("Saving...");
                repository.save(alunoEdicao);
                return ResponseEntity.status(HttpStatus.OK).body("");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há aluno com este id");
        }
    }

    @PostMapping("/novoAluno")
    public ResponseEntity<Object> novoAluno(@RequestBody AlunoDto dto) {
        /*
        Aqui você registra um novo aluno, que você cria a partir do dto.
                PRESTE ATENÇÃO. A turma não pode exceder 10 alunos. A interface AlunoRepository
            contém um recurso para contagem de alunos. Examine e decida como proceder.
                Se o número de alunos exceder o limite, retorne um erro com status PAYLOAD_TOO_LARGE
        e agregue no corpo da mensagem a string "Dados em excesso".
                Evidentemente, remova o null do último return enquanto agrega o status OK.
         */

        int counter = repository.countAlunoByTurma(parseInt(dto.turma));
        if (counter >= 10){
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Dados em excesso");
        }else{
            try {
                Aluno alunoData = new Aluno(dto);
                repository.save(alunoData);
                return ResponseEntity.status(HttpStatus.OK).body("");
            }catch (Exception e) {
                System.out.println("Erro na inserção do objeto" + dto.toString() + "\n" + e);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
            }
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> deleteAluno(@RequestBody String id) {
        /*
            Deixei este código presente por cortesia, assim como os dois métodos abaixo.
         */
        System.out.println("Tring to convert: " + id + " "+ id.getClass().getName());
        Long alunoId = Long.parseLong(id.replaceAll("\"", ""));
        System.out.println(alunoId);
        this.repository.deleteById(alunoId);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @GetMapping("/reset")
    public ResponseEntity<Object> reset() {
        this.repository.deleteAll();
        Utils.startDb(this.repository);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @GetMapping("/zerar")
    public ResponseEntity<Object> zerar() {
        this.repository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PostMapping("/log")
    public void logMessage(@RequestBody String message) {
        System.out.println("Mensagem do cliente: " + message);
    }
}
