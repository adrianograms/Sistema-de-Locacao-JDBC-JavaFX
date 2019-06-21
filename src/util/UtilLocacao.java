package util;

import java.util.List;

import model.entities.Cliente;
import model.entities.Endereco;
import model.entities.Equipamento;

public class UtilLocacao {
	
	public static Cliente findingCliente(List<Cliente> clientes, Integer id) {
		for(Cliente obj : clientes) {
			if(obj.getId() == id) {
				return obj;
			}
		}
		return null;
	}
	
	public static Endereco findingEndereco(Cliente cliente, Integer id) {
		for(Endereco obj : cliente.getEnderecos()) {
			if(obj.getId() == id) {
				return obj;
			}
		}
		return null;
	}
	
	public static Equipamento findingEquipamento(List<Equipamento> equipamentos, Integer id) {
		for(Equipamento obj : equipamentos) {
			if(obj.getId() == id) {
				return obj;
			}
		}
		return null;
	}

}
