package gui;

import model.entities.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;
import model.entities.Cliente;
import model.services.EnderecoService;

public class RegisterAdressEditController implements Initializable{

	String RegexCEP = "[0-9]{5}-[0-9]{3}";
	
	static boolean active = true;
	
	@FXML
	private AnchorPane PanelNewAdress;
	
	@FXML
    private TextField AdressTextFieldStreet;
	
    @FXML
    private TextField AdressTextFieldName;

    @FXML
    private TextField AdressTextFieldNumber;

    @FXML
    private TextField AdressTextFieldCEP;

    @FXML
    private TextField AdressTextFieldBairro;

    @FXML
    private TextField AdressTextFieldCity;

    @FXML
    private TextField AdressTextFieldUF;

    @FXML
    private Button ClientNewAdress;
    

    int index = 0;
    
    String nomeAdress;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	
		Endereco adr;
		
		for(int i = 0; i < MainViewController.observableAdressList.size(); i++) {
			if( MainViewController.observableAdressList.get(i).getNome() == MainViewController.auxAdressName) {
				adr = new Endereco(MainViewController.observableAdressList.get(i));
				AdressTextFieldName.setText(adr.getNome());
				AdressTextFieldBairro.setText(adr.getBairro());
				AdressTextFieldCEP.setText(adr.getCep());
				AdressTextFieldCity.setText(adr.getCidade());
				AdressTextFieldNumber.setText(adr.getNumero());
				AdressTextFieldStreet.setText(adr.getRua());
				AdressTextFieldUF.setText(adr.getEstado());
				index = i;
				nomeAdress = adr.getNome();
				break;
			}
		}
		
		//////
		/// Restricting AdressTextFieldNumber to have only numbers
		/////
			
		Pattern pattern = Pattern.compile("[0-9]*");
		UnaryOperator<TextFormatter.Change> filter = c -> {
		    if (pattern.matcher(c.getControlNewText()).matches()) {
		        return c ;
		    } else {
		        return null ;
		    }
		};
		TextFormatter<String> formatter = new TextFormatter<>(filter);
		AdressTextFieldNumber.setTextFormatter(formatter);

		//////
		/// Setting Formatter for Telephone Number
		//////
		
		AdressTextFieldCEP.setTextFormatter(new TextFormatter<String>(change -> {			
			
            final int oldLength = change.getControlText().length();
            int newLength = change.getControlNewText().length();
            
            if (newLength < oldLength) return change;
            
            switch (newLength) {            	 
            	case 5:
                    change.setText(change.getText() + "-"); 
                    newLength++;
                    break;
                case 10:
                    return null;
            }
            
            change.setCaretPosition(newLength);
            change.setAnchor(newLength);
            return change;
        }));
		
	}
    
	@FXML
    void cadAdress(ActionEvent event) throws Exception {
    	
		/// Checking the data
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error Dialog");
    	alert.setHeaderText("DADOS INVÁLIDOS");
    	
    	if(!AdressTextFieldCEP.getText().matches(RegexCEP)){
    		alert.setContentText("Verifique se o CEP está no formato correto.");
    		AdressTextFieldCEP.setText("");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(AdressTextFieldBairro.getText().isEmpty()){
    		alert.setContentText("Digite o nome do bairro.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(AdressTextFieldCity.getText().isEmpty()){
    		alert.setContentText("Digite o nome da cidade.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(AdressTextFieldName.getText().isEmpty()){
    		alert.setContentText("Digite um nome para este endereço.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(AdressTextFieldNumber.getText().isEmpty()){
    		alert.setContentText("Digite o número da casa.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(AdressTextFieldUF.getText().isEmpty()){
    		alert.setContentText("Digite o nome do estado.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	// Checks if the name choosed is unique
    	for(int i = 0; i < MainViewController.observableNameAdressList.size(); i++) {
    		if(AdressTextFieldName.getText().equals( MainViewController.observableNameAdressList.get(i))
    				&& !AdressTextFieldName.getText().equals(nomeAdress)) {				
    			alert.setContentText("Já existe um endereço com este nome.");
        		alert.showAndWait();
        		AdressTextFieldName.setText("");
    			throw new Exception("Já existe um endereço com este nome.");		
    		}
    	}
    	
    	MainViewController.observableAdressList.get(index).setNome(AdressTextFieldName.getText());
    	MainViewController.observableAdressList.get(index).setCidade(AdressTextFieldCity.getText());
    	MainViewController.observableAdressList.get(index).setNumero(AdressTextFieldNumber.getText());
    	MainViewController.observableAdressList.get(index).setBairro(AdressTextFieldBairro.getText());
    	MainViewController.observableAdressList.get(index).setCep(AdressTextFieldCEP.getText());
    	MainViewController.observableAdressList.get(index).setRua(AdressTextFieldStreet.getText());
    	MainViewController.observableAdressList.get(index).setEstado(AdressTextFieldUF.getText());    	
		  		
		System.out.println("Cadastrando...");
		
		MainViewController.observableNameAdressList.set(index, MainViewController.observableAdressList.get(index).getNome());
		
		MainViewController.stage.fireEvent(
			    new WindowEvent(MainViewController.stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		 
		
    }
	
    

}
