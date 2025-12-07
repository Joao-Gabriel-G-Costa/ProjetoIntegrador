package persistencia;
import java.util.ArrayList;
import modelos.classes.Despesas;
// Bibliotecas para IO em arquivo texto
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import modelos.interfaces.IDespesasCRUD;


public class DespesasDAO implements IDespesasCRUD{
  //Atributos
  private String nomeDoArquivoNoDisco = null;
  //Metodo Construtor
  public DespesasDAO(){
    nomeDoArquivoNoDisco = "./src/bancodedados/BancoDespesas.txt";
  }
  @Override
  public void salvar(Despesas tipoDeDespesa) throws Exception {
    try{
      //cria o arquivo
      FileWriter fw = new FileWriter(nomeDoArquivoNoDisco,true);//se trirar o true apaga o que esta no arquivo e substitui
      //Criar o buffer do arquivo
      BufferedWriter bw =new BufferedWriter(fw);
      //Escreve no arquivo
      String str = tipoDeDespesa.getID() + ";";
      str += tipoDeDespesa.getDescricao() + "\n";
      bw.write(str);
      //fecha o arquivo
      bw.close();		
    }catch(Exception erro){
      String msg = "Persistencia - Metodo Salvar - "+erro.getMessage();
      throw new Exception(msg);
    }
  }

  @Override
  public ArrayList<Despesas> listaDeTiposDeDespesas() throws Exception {
    try{
      ArrayList<Despesas> listaDeTiposDeDespesas = new ArrayList<>();
      //abrir um arquivo existente
      FileReader fr = new FileReader(nomeDoArquivoNoDisco);
      //Criar o buffer do arquivo
      BufferedReader br  = new BufferedReader(fr);
      String linha = "";
      while((linha=br.readLine())!=null){
        String vetorStr[] = linha.split(";");
        Despesas objTipoDeDespesa = null;
        int idTipoDeDespesaAux = Integer.parseInt(vetorStr[0]);
        String descricao = vetorStr[1];
        objTipoDeDespesa = new Despesas(idTipoDeDespesaAux,descricao);
        listaDeTiposDeDespesas.add(objTipoDeDespesa);
      }
      br.close();
      return listaDeTiposDeDespesas;
    }catch(Exception erro){
      String msg = "Persistencia - Metodo Lista - "+erro.getMessage();
      throw new Exception(msg);
    }  
  }

  @Override
  public Despesas buscarPorId(int idTipoDeDespesa) throws Exception {
    try{
      //abrir um arquivo existente
      FileReader fr = new FileReader(nomeDoArquivoNoDisco);
      //Criar o buffer do arquivo
      BufferedReader br  = new BufferedReader(fr);
      String linha = "";
      while((linha=br.readLine())!=null){
        String vetorStr[] = linha.split(";");
        int idTipoDeDespesaAux = Integer.parseInt(vetorStr[0]);
        if(idTipoDeDespesaAux == idTipoDeDespesa){
          String descricao = vetorStr[1];
          Despesas objTipoDeDespesa = null;
          objTipoDeDespesa = new Despesas(idTipoDeDespesaAux,descricao);
          br.close();
          return objTipoDeDespesa;
        }
      }
      br.close();
      return null;
    }catch(Exception erro){
      String msg = "Persistencia - Metodo Buscar - "+erro.getMessage();
      throw new Exception(msg);
    }   
  }

  @Override
  public void atualizar(Despesas tipoDeDespesa) throws Exception {
  try {
      ArrayList<Despesas> listagem = null;
      listagem = this.listaDeTiposDeDespesas();
      //cria o arquivo
      FileWriter fw = new FileWriter(nomeDoArquivoNoDisco);
      //Criar o buffer do arquivo
      BufferedWriter bw =new BufferedWriter(fw);
      for(Despesas obj : listagem){
        if(obj.getID() == tipoDeDespesa.getID()){
          String str = tipoDeDespesa.getID() + ";";
          str += tipoDeDespesa.getDescricao() + "\n";
          bw.write(str);
        }else{
          String str = obj.getID() + ";";
          str += obj.getDescricao() + "\n";
          bw.write(str);
        }
      }
      bw.close();
    }catch(Exception erro){
      String msg = "Persistencia - Metodo Atualizar - "+erro.getMessage();
      throw new Exception(msg);
    } 
  }

  @Override
  public void remover(int idTipoDeDespesa) throws Exception {
    try {
      ArrayList<Despesas> listagem = null;
      listagem = this.listaDeTiposDeDespesas();
      //cria o arquivo
      FileWriter fw = new FileWriter(nomeDoArquivoNoDisco);
      //Criar o buffer do arquivo
      BufferedWriter bw =new BufferedWriter(fw);
      for(Despesas obj : listagem){
        if(obj.getID() != idTipoDeDespesa){
          String str = obj.getID() + ";";
          str += obj.getDescricao() + "\n";
          bw.write(str);
        }
      }
      bw.close();
    }catch(Exception erro){
      String msg = "Persistencia - Metodo Remover - "+erro.getMessage();
      throw new Exception(msg);
    } 
  }

}
  
 
