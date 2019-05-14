package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemCliente;
	
	@FXML
	private MenuItem menuItemEquipamento;
	
	@FXML
	private MenuItem menuItemSobre;
	
	@FXML
	public void onMenuItemClienteAction() {
		System.out.println("onMenuItemClienteAction");
	}
	
	@FXML
	public void onMenuItemEquipamentoAction() {
		System.out.println("onMenuItemEquipamentoAction");
	}
	
	@FXML
	public void onMenuItemSobreAction() {
		System.out.println("onMenuItemSobreAction");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
