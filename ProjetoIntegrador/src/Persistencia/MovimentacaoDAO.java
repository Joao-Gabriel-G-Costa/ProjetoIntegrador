/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;
import java.util.ArrayList;
import modelos.classes.Movimentacao;
import modelos.interfaces.IMovimentacaoCRUD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;


/**
 *
 * @author alice
 */
public class MovimentacaoDAO implements IMovimentacaoCRUD {
    
    private String nomeDoArquivoNoDisco = null;

    public MovimentacaoDAO() {
        nomeDoArquivoNoDisco = "./src/bancodedados/BancoMovimentos.txt";
    }

    @Override
    public void salvar(Movimentacao movimentacoes) throws Exception {
       try {
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco, true);
            BufferedWriter bw = new BufferedWriter(fw);

            String str = movimentacoes.getIdMovimentacao() + ";"
                       + movimentacoes.getDescricao()+ ";"
                       + movimentacoes.getData()+ ";"
                       + movimentacoes.getValor()+ "\n";

            bw.write(str);
            bw.close();

        } catch (Exception erro) {
            throw new Exception("Persistencia - Salvar: " + erro.getMessage());
        }  
    }

    @Override
    public ArrayList<Movimentacao> listaDeMovimentacoes() throws Exception {
        try {
            ArrayList<Movimentacao> lista = new ArrayList<>();

            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);

            String linha = "";

            while ((linha = br.readLine()) != null) {
                String vetor[] = linha.split(";");

                int idMovimentacao = Integer.parseInt(vetor[0]);
                String descricao = vetor[1];
                String data = vetor[2];
                double valor = Double.parseDouble(vetor[3]);
                
                Movimentacao movi = new Movimentacao(idMovimentacao, descricao, data, valor);
                lista.add(movi);
            }

            br.close();
            return lista;

        } catch (Exception erro) {
            throw new Exception("Persistencia - Lista: " + erro.getMessage());
}
    }

    @Override
    public Movimentacao buscarPorId(int idMovimentacao) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);

            String linha = "";

            while ((linha = br.readLine()) != null) {
                String vetor[] = linha.split(";");

                int idAux = Integer.parseInt(vetor[0]);

                if (idAux == idMovimentacao) {
                    String descricao = vetor[1];
                    String data = vetor[2];
                    double valor = Double.parseDouble(vetor[3]);
   
                    br.close();
                    return new Movimentacao(idMovimentacao, descricao, data, valor);
                }
            }

             br.close();
             return null;

        } catch (Exception erro) {
            throw new Exception("Persistencia - Buscar: " + erro.getMessage());
        }
    }
    @Override
    public void atualizar(Movimentacao movimentacoes) throws Exception {
        try {
            ArrayList<Movimentacao> lista = this.listaDeMovimentacoes();

            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Movimentacao obj : lista) {
                if (obj.getIdMovimentacao()== movimentacoes.getIdMovimentacao()) {
                    bw.write(
                        movimentacoes.getIdMovimentacao() + ";" +
                        movimentacoes.getDescricao()+ ";" + 
                        movimentacoes.getData()+ ";" +
                        movimentacoes.getValor()+ "\n"
                    );
                } else {
                    bw.write(
                        obj.getIdMovimentacao()+ ";" +
                        obj.getDescricao() + ";" +
                        obj.getData()+ ";" +
                        obj.getValor()+ ";" + "\n"
                    );
                }
            }

            bw.close();

        } catch (Exception erro) {
            throw new Exception("Persistencia - Atualizar: " + erro.getMessage());
        }}

    @Override
    public void remover(int idMovimentacao) throws Exception {
         try {
            ArrayList<Movimentacao> lista = this.listaDeMovimentacoes();

            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Movimentacao movimentacoes : lista) {
                if (movimentacoes.getIdMovimentacao() != idMovimentacao) {
                    bw.write(
                        movimentacoes.getIdMovimentacao() + ";" +
                        movimentacoes.getDescricao()+ ";" + 
                        movimentacoes.getData()+ ";" +
                        movimentacoes.getValor()+ "\n"
                    );
                }
            }

            bw.close();

        } catch (Exception erro) {
            throw new Exception("Persistencia - Remover: " + erro.getMessage());
        }
    }
 
}
