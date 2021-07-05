package br.com.rayllanderson.atvidade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RespostaQuestaoTest {

    @Test
    void deveInstanciarUmaResposta(){
        assertDoesNotThrow(() -> new RespostaQuestao(new Avaliacao(), new Aluno(), 5));
    }

    @Test
    void naoDeveInstanciarSeAAvaliacaoForNula(){
        assertThrows(IllegalArgumentException.class, () -> new RespostaQuestao(null, new Aluno(), 10));
    }

    @Test
    void naoDeveInstanciarSeOAlunoForNulo(){
        assertThrows(IllegalArgumentException.class, () -> new RespostaQuestao(new Avaliacao(), null, 10));
    }

    @Test
    void naoDeveInstanciarSeANotaForMenorQue0(){
        assertThrows(IllegalArgumentException.class, () -> new RespostaQuestao(new Avaliacao(), new Aluno(), -1));
    }

    @Test
    void naoDeveInstanciarSeANotaForMaiorQue10(){
        assertThrows(IllegalArgumentException.class, () -> new RespostaQuestao(new Avaliacao(), new Aluno(), 21));
    }
}