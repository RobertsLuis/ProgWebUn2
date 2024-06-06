package web.trab2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import web.trab2.model.Aluno;
import web.trab2.repository.AlunoRepository;

import java.util.Optional;

import static java.lang.Long.parseLong;

@Controller
public class RouterController {

    @Autowired
    AlunoRepository repository;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/novo")
    public String novoAluno() {
        return "novo";
    }


    @GetMapping("/edit/{id}")
    public Object editNotas(@PathVariable("id") String id) {
        ModelAndView mv = new ModelAndView("edit");
        /*
            Aqui você obtém do banco de dados um objeto Aluno a partir do id informado
            Se o aluno com o id existir nos dados, você injeta no Model & View o dto correspondente
         */
        Aluno alunoEdicao = repository.findAlunoById(parseLong(id));

        if (alunoEdicao != null){
            System.out.println("Aluno sendo editado: " + alunoEdicao.toString());
            mv.addObject("aluno", alunoEdicao);
            return mv;
        }else {
            return "redirect:/novo";
        }
    }
}
