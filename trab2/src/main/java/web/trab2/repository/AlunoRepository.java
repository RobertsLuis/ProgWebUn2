package web.trab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.trab2.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    ArrayList<Aluno> findAll();
    @Query("SELECT a FROM Aluno a WHERE a.id = :id")
    Aluno findAlunoById(@Param("id") Long id);

    @Query("SELECT a FROM Aluno a WHERE a.matricula = :matricula")
    Aluno findAlunoByMatricula(@Param("matricula") String matricula);
    int countAlunoByTurma(int turma);

}
