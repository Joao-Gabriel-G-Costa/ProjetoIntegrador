<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
<<<<<<< HEAD:ProjetoIntegrador/src/Persistencia/RelatoriosDAO.java
package Persistencia;
import Persistencia.VeiculosDAO;
=======
package persistencia;
>>>>>>> 8f493e8089e01e4641df975a29feac032f41a764:ProjetoIntegrador/src/persistencia/RelatoriosDAO.java
=======
package persistencia;

import modelos.interfaces.IRelatorios;
>>>>>>> de8c9888b88749e3a9d0a4a52fa57ec25b2262f6
import modelos.classes.Veiculos;
import modelos.classes.Movimentacao;
import modelos.classes.Despesas; 
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RelatoriosDAO implements IRelatorios {
    private VeiculosDAO veicDAO = new VeiculosDAO();
    private MovimentacaoDAO moviDAO = new MovimentacaoDAO(); 
    private DespesasDAO despDAO = new DespesasDAO();
<<<<<<< HEAD
    
    
  
    //LISTAR VEICULOS NA COMBOBOX
    public ArrayList<Veiculos> listarVeiculos() {
    try {
        ArrayList<Veiculos> lista = veicDAO.listaVeiculos(); // chama o DAO de veículos
        if (lista == null) {
            return new ArrayList<>(); 
        }
        return lista;
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao listar veículos: " + e.getMessage());
        return new ArrayList<>();
    }
}
=======
>>>>>>> de8c9888b88749e3a9d0a4a52fa57ec25b2262f6
    
    @Override
    public ArrayList<Veiculos> listarVeiculos() throws Exception {
        return veicDAO.listaVeiculos();
    }
    
<<<<<<< HEAD
    //DESPESAS REALIZADAS DE UM VEICULO
    //A PESSOA POE O ID DO VEICULO
    //VALIDO SE O ID EXISTE OU NAO ---> IMPORTANTE
    //MOSTRA OS RESULTADOS
    //ID; MARCA; MODELO; DESCRIÇÃO; 
    
    //o método precisa receber um id do veículo (PARAMETRO)
    public ArrayList<Movimentacao> despesasVeiculos(int idVeiculo){
        try {
            //public ArrayList<Movimentacao> listaDeMovimentacoes() 
            
            //o DAO lê o arquivo e devolve todas as movimentações ---> IDVEICULO DESPESA DESCRIÇÃO
            ArrayList<Movimentacao> lista = moviDAO.listaDeMovimentacoes();
            
            
            //nova lista
            ArrayList<Movimentacao> despesas = new ArrayList<>();//o método precisa receber um id do veículo
            
            for (Movimentacao m : lista) {
                  if (m.getIdVeiculo() == idVeiculo){
                despesas.add(m);
            }
            
            
            }     return despesas;
            
        } catch (Exception e) {
                return null;
                }
        }
    
    
//     public ArrayList<Movimentacao> despesasMes (String data){
//         try {
//             //o DAO lê o arquivo e devolve todas as movimentações ---> IDVEICULO VEICULO IDDESPESA DATA VALOR
//            ArrayList<Movimentacao> lista = moviDAO.listaDeMovimentacoes();
//            
//            
//            //nova lista
//            ArrayList<Movimentacao> despesasMes = new ArrayList<>();//o método precisa receber um id do veículo
//         } catch (Exception e) {
//         }
//            
//     }    
    
    
    
    
    
=======
    @Override
    public ArrayList<Veiculos> listarVeiculosParaCombo() throws Exception {
        return listarVeiculos(); 
    }
>>>>>>> de8c9888b88749e3a9d0a4a52fa57ec25b2262f6
    
    @Override
    public Veiculos buscarVeiculoPorId(int id) throws Exception {
        return veicDAO.buscarPorId(id);
    }
    
    @Override
    public ArrayList<Movimentacao> despesasMes(int mes) throws Exception {
        ArrayList<Movimentacao> lista = moviDAO.listaDeMovimentacoes();
        ArrayList<Movimentacao> filtradas = new ArrayList<>();
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (Movimentacao m: lista) {
            LocalDate data = LocalDate.parse(m.getData(), formato);
            if (data.getMonthValue() == mes) {
                filtradas.add(m);
            }
        }
        return filtradas;
    }
    
    @Override
    public ArrayList<Movimentacao> despesasAno(int ano) throws Exception {
        ArrayList<Movimentacao> lista = moviDAO.listaDeMovimentacoes();
        ArrayList<Movimentacao> filtradas = new ArrayList<>();
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (Movimentacao m: lista) {
            LocalDate data = LocalDate.parse(m.getData(), formato);
            if (data.getYear() == ano) {
                filtradas.add(m);
            }
        }
        return filtradas;
    }
    
    @Override
    public ArrayList<Veiculos> listarInativos() throws Exception {
        ArrayList<Veiculos> lista = veicDAO.listaVeiculos();
        ArrayList<Veiculos> inativos = new ArrayList<>(); 
        
        for (Veiculos v : lista) {
            if (!v.isSituacao()) {
                inativos.add(v);
            }
        }
        return inativos;
    }
    
    @Override
    public ArrayList<Movimentacao> despesasVeiculos(int idVeiculo) throws Exception {
        ArrayList<Movimentacao> lista = moviDAO.listaDeMovimentacoes();
        ArrayList<Movimentacao> despesas = new ArrayList<>();
        
        for (Movimentacao m : lista) {
            if (m.getIdVeiculo() == idVeiculo) {
                despesas.add(m);
            }
        }
        return despesas;
    }
    
    @Override
    public ArrayList<Movimentacao> ipvaAno(int ano) throws Exception {
        ArrayList<Movimentacao> todasAno = despesasAno(ano);
        ArrayList<Movimentacao> ipvas = new ArrayList<>();

        for (Movimentacao m : todasAno) {
            Despesas d = despDAO.buscarPorId(m.getIdDespesa());
            if (d != null && "IPVA".equalsIgnoreCase(d.getDescricao())) {
                ipvas.add(m);
            }
        }
        return ipvas;
    }

    @Override
    public ArrayList<Movimentacao> multasVeiculoAno(int idVeiculo, int ano) throws Exception {
        ArrayList<Movimentacao> todasAno = despesasAno(ano);
        ArrayList<Movimentacao> multas = new ArrayList<>();

<<<<<<< HEAD
    //Total geral de despesas da frota no mês
    //MOVIMENTACAO
//    private int idMovimentacao = 0;
//    private int idVeiculo = 0;
//    private int idDespesa = 0;
//    private String descricao = "";
//    private String data = "";
//    private double valor = 0;
// USAR --> ID VEICULO IDDESPESA (TIPO DESPESA DA CLASSE "DESPESAS") DATA VALOR
//Soma de todas as despesas de todos os veículos num mês específico.

    

    
}
    



=======
        for (Movimentacao m : todasAno) {
            if (m.getIdVeiculo() != idVeiculo) {
                continue;
            }

            Despesas d = despDAO.buscarPorId(m.getIdDespesa());
            if (d != null && "Multa".equalsIgnoreCase(d.getDescricao())) {
                multas.add(m);
            }
        }
        return multas;
    }

}
>>>>>>> de8c9888b88749e3a9d0a4a52fa57ec25b2262f6
