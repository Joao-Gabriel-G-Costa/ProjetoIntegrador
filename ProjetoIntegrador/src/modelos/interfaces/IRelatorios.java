
package modelos.interfaces;

import java.util.ArrayList;
import modelos.classes.Veiculos;
import modelos.classes.Movimentacao;

public interface IRelatorios {
    // Listagens básicas
    ArrayList<Veiculos> listarVeiculos() throws Exception;
    ArrayList<Veiculos> listarInativos() throws Exception;
    Veiculos buscarVeiculoPorId(int id) throws Exception;
    
    // Relatórios por período
    ArrayList<Movimentacao> despesasMes(int mes) throws Exception;
    ArrayList<Movimentacao> despesasAno(int ano) throws Exception;
    
    // Relatórios específicos
    ArrayList<Movimentacao> despesasVeiculos(int idVeiculo) throws Exception;
    ArrayList<Movimentacao> ipvaAno(int ano) throws Exception;
    ArrayList<Movimentacao> multasVeiculoAno(int idVeiculo, int ano) throws Exception;
    
    // Métodos auxiliares (para a tela)
    ArrayList<Veiculos> listarVeiculosParaCombo() throws Exception;
}