package modelos.interfaces;
import java.util.ArrayList;
import modelos.classes.Veiculos;

public interface IVeiculosCRUD {
  void salvar(Veiculos infoVeiculos) throws Exception;
  ArrayList<Veiculos> listaVeiculos() throws Exception;
  Veiculos buscarPorId(int idVeiculo) throws Exception;
  void atualizar(Veiculos infoVeiculos) throws Exception;
  void remover(int idVeiculos) throws Exception;
}