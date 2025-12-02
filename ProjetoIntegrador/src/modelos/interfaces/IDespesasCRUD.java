/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelos.interfaces;
import java.util.ArrayList;
import modelos.classes.Despesas;

public interface IDespesasCRUD {
  void salvar(Despesas tipoDeDespesa) throws Exception;
  ArrayList<Despesas> listaDeTiposDeDespesas() throws Exception;
  Despesas buscarPorId(int idTipoDeDespesa) throws Exception;
  void atualizar(Despesas tipoDeDespesa) throws Exception;
  void remover(int idTipoDeDespesa) throws Exception;
}
