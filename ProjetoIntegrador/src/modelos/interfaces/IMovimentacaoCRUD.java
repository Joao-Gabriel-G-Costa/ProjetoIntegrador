/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos.interfaces;
import java.util.ArrayList;
import modelos.classes.Movimentacao;

/**
 *
 * @author alice
 */
public interface IMovimentacaoCRUD {
    void salvar (Movimentacao movimentacoes) throws Exception;    
    ArrayList<Movimentacao> listaDeMovimentacoes() throws Exception;
    Movimentacao buscarPorId(int idMovimentacao) throws Exception;
    void atualizar(Movimentacao movimentacoes) throws Exception;
    void remover(int idMovimentacao) throws Exception;
   }
