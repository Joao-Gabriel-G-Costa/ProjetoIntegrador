/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;
import Persistencia.VeiculosDAO;
import modelos.classes.Veiculos;
import modelos.classes.Movimentacao;
import modelos.classes.Despesas; 
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author heloi
 */
public class RelatoriosDAO {
    //Dentro da classe Relatorios, eu tenho um objeto PARA ACESSAR (LER) O TXT, o arquivo de veículos 
    private VeiculosDAO veicDAO = new VeiculosDAO();
    private MovimentacaoDAO moviDAO = new MovimentacaoDAO(); 
    private DespesasDAO despDAO = new DespesasDAO();
    
    
  
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
    
    
   //INATIVOS
    public ArrayList<Veiculos> listarInativos(){
        try {
             ArrayList<Veiculos> lista = veicDAO.listaVeiculos();
    
             ArrayList<Veiculos> inativos = new ArrayList<>(); 
    
             
             
             
             for (Veiculos v : lista) {
             
                 
                 //SE FOR FALSO ---> O VEICULO É INATIVO
                 if (!v.isSituacao()){
                     inativos.add(v);
                 }
             }
             
             return inativos;
             }
         catch (Exception e) {
        
            return null;
        }
        
    }
    
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
    



