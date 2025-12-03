package Persistencia;

import java.util.ArrayList;
import modelos.classes.Veiculos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import modelos.interfaces.IVeiculosCRUD;

public class VeiculosDAO implements IVeiculosCRUD {

    private String nomeDoArquivoNoDisco = null;
    //Metodo Construtor
    public VeiculosDAO(){
      nomeDoArquivoNoDisco = "./src/bancodedados/BancoVeiculos.txt";
    }

    @Override
    public void salvar(Veiculos v) throws Exception {
        try {
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco, true);
            BufferedWriter bw = new BufferedWriter(fw);

            String str = v.getIdVeiculo() + ";"
                       + v.getPlaca() + ";"
                       + v.getMarca() + ";"
                       + v.getModelo() + ";"
                       + v.getAnoFabricacao() + ";"
                       + v.isSituacao() + "\n";

            bw.write(str);
            bw.close();

        } catch (Exception erro) {
            throw new Exception("Persistencia - Salvar: " + erro.getMessage());
        }
    }

    @Override
    public ArrayList<Veiculos> listaVeiculos() throws Exception {
        try {
            ArrayList<Veiculos> lista = new ArrayList<>();

            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);

            String linha = "";

            while ((linha = br.readLine()) != null) {
                String vet[] = linha.split(";");

                int id = Integer.parseInt(vet[0]);
                String placa = vet[1];
                String marca = vet[2];
                String modelo = vet[3];
                String ano = vet[4];
                boolean situacao = Boolean.parseBoolean(vet[5]);

                Veiculos v = new Veiculos(id, placa, marca, modelo, ano, situacao);
                lista.add(v);
            }

            br.close();
            return lista;

        } catch (Exception erro) {
            throw new Exception("Persistencia - Lista: " + erro.getMessage());
        }
    }

    @Override
    public Veiculos buscarPorId(int id) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);

            String linha = "";

            while ((linha = br.readLine()) != null) {
                String vet[] = linha.split(";");

                int idAux = Integer.parseInt(vet[0]);

                if (idAux == id) {
                    String placa = vet[1];
                    String marca = vet[2];
                    String modelo = vet[3];
                    String ano = vet[4];
                    boolean situacao = Boolean.parseBoolean(vet[5]);

                    br.close();
                    return new Veiculos(idAux, placa, marca, modelo, ano, situacao);
                }
            }

            br.close();
            return null;

        } catch (Exception erro) {
            throw new Exception("Persistencia - Buscar: " + erro.getMessage());
        }
    }

    @Override
    public void atualizar(Veiculos v) throws Exception {
        try {
            ArrayList<Veiculos> lista = this.listaVeiculos();

            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Veiculos obj : lista) {
                if (obj.getIdVeiculo() == v.getIdVeiculo()) {
                    bw.write(
                        v.getIdVeiculo() + ";" +
                        v.getPlaca() + ";" +
                        v.getMarca() + ";" +
                        v.getModelo() + ";" +
                        v.getAnoFabricacao() + ";" +
                        v.isSituacao() + "\n"
                    );
                } else {
                    bw.write(
                        obj.getIdVeiculo() + ";" +
                        obj.getPlaca() + ";" +
                        obj.getMarca() + ";" +
                        obj.getModelo() + ";" +
                        obj.getAnoFabricacao() + ";" +
                        obj.isSituacao() + "\n"
                    );
                }
            }

            bw.close();

        } catch (Exception erro) {
            throw new Exception("Persistencia - Atualizar: " + erro.getMessage());
        }
    }

    @Override
    public void remover(int id) throws Exception {
        try {
            ArrayList<Veiculos> lista = this.listaVeiculos();

            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Veiculos v : lista) {
                if (v.getIdVeiculo() != id) {
                    bw.write(
                        v.getIdVeiculo() + ";" +
                        v.getPlaca() + ";" +
                        v.getMarca() + ";" +
                        v.getModelo() + ";" +
                        v.getAnoFabricacao() + ";" +
                        v.isSituacao() + "\n"
                    );
                }
            }

            bw.close();

        } catch (Exception erro) {
            throw new Exception("Persistencia - Remover: " + erro.getMessage());
        }
    }
}
