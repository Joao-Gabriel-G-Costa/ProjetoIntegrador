package modelos.classes;


public class Movimentacao {
    private int idMovimentacao = 0;
    private int idVeiculo = 0;
    private int idDespesa = 0;
    private String descricao = "";
    private String data = "";
    private double valor = 0;

    public Movimentacao() {
    }

    public Movimentacao(int idMovimentacao, int idVeiculo, int idDespesa,
                        String descricao, String data, double valor) {
        this.idMovimentacao = idMovimentacao;
        this.idVeiculo = idVeiculo;
        this.idDespesa = idDespesa;
        this.descricao = descricao;
        this.data = data;
        this.valor = valor;
    }

    public int getIdMovimentacao() {
        return idMovimentacao;
    }

    public void setIdMovimentacao(int idMovimentacao) {
        this.idMovimentacao = idMovimentacao;
    }

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public int getIdDespesa() {
        return idDespesa;
    }

    public void setIdDespesa(int idDespesa) {
        this.idDespesa = idDespesa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    
}
