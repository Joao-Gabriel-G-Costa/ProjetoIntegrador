/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos.classes;

/**
 *
 * @author alice
 */
public class Movimentacao {
    private int idMovimentacao = 0;
    private String descricao = "";
    private String data = "";
    private double valor = 0;

    public Movimentacao() {
    }
    public Movimentacao(int idMovimentacao, String descricao, String data,
            double valor) {
        this.idMovimentacao = idMovimentacao;
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
