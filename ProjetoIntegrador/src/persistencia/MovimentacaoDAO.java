/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;
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
        nomeDoArquivoNoDisco = "./src/bancodedados/BancoMovimento.txt";
    }

    @Override
    public void salvar(Movimentacao m) throws Exception {
       try {
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco, true);
            BufferedWriter bw = new BufferedWriter(fw);

            // salvar
            String str = m.getIdMovimentacao() + ";"
                      + m.getIdVeiculo() + ";"
                      + m.getIdDespesa() + ";"
                      + m.getDescricao() + ";"
                      + m.getData() + ";"
                      + m.getValor() + "\n";
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
                    int idVeiculo = Integer.parseInt(vetor[1]);
                    int idDespesa = Integer.parseInt(vetor[2]);
                    String descricao = vetor[3];
                    String data = vetor[4];
                    double valor = Double.parseDouble(vetor[5]);
                
                Movimentacao movi = new Movimentacao(idMovimentacao, idVeiculo, idDespesa, descricao, data, valor);
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

        String linha;

        while ((linha = br.readLine()) != null) {
            String vetor[] = linha.split(";");

            int idAux = Integer.parseInt(vetor[0]);

            if (idAux == idMovimentacao) {
                int idVeiculo = Integer.parseInt(vetor[1]);
                int idDespesa = Integer.parseInt(vetor[2]);
                String descricao = vetor[3];
                String data = vetor[4];
                double valor = Double.parseDouble(vetor[5]);

                br.close();
                return new Movimentacao(idAux, idVeiculo, idDespesa, descricao, data, valor);
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

            BufferedWriter bw = new BufferedWriter(new FileWriter(nomeDoArquivoNoDisco));

            for (Movimentacao obj : lista) {
                if (obj.getIdMovimentacao() != idMovimentacao) {
                    bw.write(
                        obj.getIdMovimentacao() + ";" +
                        obj.getIdVeiculo() + ";" +
                        obj.getIdDespesa() + ";" +
                        obj.getDescricao() + ";" +
                        obj.getData() + ";" +
                        obj.getValor() + "\n"
                    );
                }
            }

            bw.close();

        } catch (Exception erro) {
            throw new Exception("Persistencia - Remover: " + erro.getMessage());
        }
    }
 
}
