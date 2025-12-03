/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;
import modelos.classes.Veiculos;
import modelos.classes.Movimentacao;
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
    public ArrayList<Movimentacao> despesasVeiculos(){
        try {
            //public ArrayList<Movimentacao> listaDeMovimentacoes() 
            ArrayList<Movimentacao> lista = moviDAO.listaDeMovimentacoes();
            
            ArrayList<Veiculos> veiculos = veicDAO.listaVeiculos();
            //nova lista
            ArrayList<Movimentacao> despesas = new ArrayList<>();
            
            
            for (Movimentacao m : lista) {
                  despesas.add(m);
            
            
            }     return despesas;
            
        } catch (Exception e) {
                return null;
                }
        }
    
    }
    
    
    



