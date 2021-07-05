package br.com.rayllanderson.atvidade;

public class RespostaQuestao {

    private Avaliacao avaliacao;

    private Aluno aluno;

    private int nota;


    //construtor

    public RespostaQuestao(Avaliacao avaliacao,Aluno aluno,int nota){

        if(avaliacao == null){
            throw new IllegalArgumentException("A avaliação não pode ser nula");
        }

        if(aluno == null){
            throw new IllegalArgumentException("O aluno não pode ser nula");
        }

        if(nota < 0){
            throw new IllegalArgumentException("A nota não pode ser menor que zero");
        }

        if(nota > 10){
            throw new IllegalArgumentException("A nota não pode ser maior que10");
        }
    }

}
