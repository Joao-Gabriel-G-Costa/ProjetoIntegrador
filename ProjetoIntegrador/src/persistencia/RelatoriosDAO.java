/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;
import persistencia.VeiculosDAO;
import modelos.classes.Veiculos;
import modelos.classes.Movimentacao;
import modelos.classes.Despesas; 
import java.util.ArrayList;
import javax.swing.JOptionPane;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    
    //BUSCAR POR ID
    public Veiculos buscarVeiculoPorId(int id) {
    for (Veiculos v : listarVeiculos()) {
        if (v.getIdVeiculo() == id) return v;
    }
    return null;
}
    
    //PEGAR MES
    public ArrayList<Movimentacao> despesasMes (int mes){
        try {
            ArrayList<Movimentacao> lista = moviDAO.listaDeMovimentacoes();
            ArrayList<Movimentacao> filtradas = new ArrayList<>();
            
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            
            for (Movimentacao m: lista) {
                LocalDate data = LocalDate.parse(m.getData(), formato);
                
                //adicionar na lista
                if (data.getMonthValue() == mes){
                    filtradas.add(m);
                }
            }
            
            return filtradas;
            
        } catch (Exception e) {
            
            return null;
        }
    }

    
    //PEGAR ANO
    public ArrayList<Movimentacao> despesasAno (int ano){
        try {
            ArrayList<Movimentacao> lista = moviDAO.listaDeMovimentacoes();
            ArrayList<Movimentacao> filtradas = new ArrayList<>();
            
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            
            for (Movimentacao m: lista) {
                LocalDate data = LocalDate.parse(m.getData(), formato);
                
                //adicionar na lista
                if (data.getYear() == ano){
                    filtradas.add(m);
                }
            }
            
            return filtradas;
            
        } catch (Exception e) {
            
            return null;
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
    
    // Soma de todas as despesas de todos os veículos num mês específico.
     public ArrayList<Movimentacao> despesasMes (String mes){
         try {
             
             int mesFiltro = Integer.parseInt(mes); //NUMERO DO MES
             
             
             
            //o DAO lê o arquivo e devolve todas as movimentações ---> IDVEICULO VEICULO IDDESPESA DATA VALOR
            ArrayList<Movimentacao> lista = moviDAO.listaDeMovimentacoes();
            
      
            //nova lista
            ArrayList<Movimentacao> despesasMes = new ArrayList<>();//o método precisa receber um id do veículo
            
            //FORMATO DATA
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            
            
            
            
             for (Movimentacao m : lista) {
                 LocalDate data = LocalDate.parse(m.getData(), formatter);
                 
                 
                 
                  if (data.getMonthValue() == mesFiltro){
               despesasMes.add(m);
            }
            
            
         } return despesasMes;
         
         
         
         } catch (Exception e) {
             //duvida ?
                 return new ArrayList<>();
                 }
         }
     
     
     
     

       
     

    }
    
    
    



