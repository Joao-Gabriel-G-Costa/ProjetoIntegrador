package modelos.intefaces;
import java.util.ArrayList;
import modelos.classes.Veiculos;

public interface VeiculosCRUD {
  void salvar(Veiculos infoVeiculos) throws Exception;
  ArrayList<Veiculos> listaVeiculos() throws Exception;
  Veiculos buscarPorId(int idVeiculo) throws Exception;
  void atualizar(Veiculos infoVeiculos) throws Exception;
  void remover(int idVeiculos) throws Exception;
}