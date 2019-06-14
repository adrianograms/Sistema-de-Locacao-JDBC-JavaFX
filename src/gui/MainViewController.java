package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.scene.control.TextFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.entities.*;

public class MainViewController implements Initializable {

	String RegexCPF = "[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}";
	String RegexEMAIL = "[a-zA-Z0-9]*@(gmail.com|hotmail.com|outlook.com|unioeste.br|yahoo.com.br)";
	String RegexTELEPHONE = "[(][0-9]{2}[)] [0-9]{5}-[0-9]{4}";
	String RegexRealNumber = "[0-9]*[.][0-9]*";
	
	int countID = 0;	

	public static ObservableList<Endereco> observableAdressList = FXCollections.observableArrayList();
	public static ObservableList<String> observableNameAdressList = FXCollections.observableArrayList();
	
	public static ObservableList<Equipamento> observableEquipList = FXCollections.observableArrayList();
	public static ObservableList<String> observableNameEquipList = FXCollections.observableArrayList();
	
	public static String a = " "; 
	
	public static Stage stage = new Stage();
	
    @FXML
    private AnchorPane PanelHeader;
    
    @FXML
    private AnchorPane AGWPanel;

    @FXML
    private AnchorPane PanelMenu;
	
    @FXML
    private AnchorPane PanelAddClient;
    
    @FXML
    private AnchorPane PanelAddEquip;
    
    @FXML
    private TabPane PanelList;
    
    @FXML
    private AnchorPane PanelAloc;
    
    @FXML
    private Button ClienteButtonEditAdress;
    
    @FXML
    private Button Alocate;

    @FXML
    private Button AddClient;

    @FXML
    private Button AddEquip;

    @FXML
    private Button List;

    @FXML
    private Hyperlink HiperlinkSobre;

    @FXML
    private TextField ClientTextFieldName;

    @FXML
    private TextField ClientTextFieldCPF;

    @FXML
    private CheckBox M;

    @FXML
    private CheckBox F;

    @FXML
    private TextField ClientTextFieldEmail;

    @FXML
    private TextField ClientTextFieldNumber;

    @FXML
    private ChoiceBox<String> ClienteChoiceBoxAdress;

    @FXML
    private Button ClientNewAdress;

    @FXML
    private Button ClientNewClient;

    @FXML
    private Button EquipNewEquip;
    
    @FXML
    private ChoiceBox<Endereco> AlocChoiceBoxAdress;

    @FXML
    private TextField EquipTextFieldDiaria;

    @FXML
    private TextField EquipTextFieldDescription;
    
    @FXML
    private TextField EquipTextFieldSemanal;

    @FXML
    private TextField EquipTextFieldQuinzenal;

    @FXML
    private TextField EquipTextFieldMensal;

    @FXML
    private TextField EquipTextFieldName;
    
    @FXML
    private Label EquipLabelValue;

    @FXML
    private DatePicker AlocTextFieldInitialDate;

    @FXML
    private DatePicker AlocTextFieldFinalDate;

    @FXML
    private TextField AlocTextFieldValue;

    @FXML
    private TextField AlocTextFieldID;
    
    @FXML
    private TextField ListClientTextFieldID;
    
    @FXML
    private Button ListClientButtonModificate;
    
    @FXML
    private TextField ListEquipTextFieldID;
    
    @FXML
    private Button ListEquipButtonModificate;
    
    @FXML
    private TextField ListAlocTextFieldID;
    
    @FXML
    private Button ListAlocButtonModificate;
    
    @FXML
    private TextField ListAlocTextFieldIDPDF;
    
    @FXML
    private Button ListAlocButtonPDF;
    
    @FXML
    private TextField ListAdressTextFieldIDSearch;
    
    @FXML
    private Button ListAdressButtonSearch;
    
    @FXML
    private TextField ListAdressTextFieldID;

    @FXML
    private Button ListAdressButtonModificate;
    
    @FXML
    private Button AlocNewAloc;    

    @FXML
    private TableColumn<Cliente, Integer> ClientTableViewID;

    @FXML
    private TableColumn<Cliente, String> ClientTableViewName;

    @FXML
    private TableColumn<Cliente, String> ClientTableViewCPF;

    @FXML
    private TableColumn<Cliente, Character> ClientTableViewSex;

    @FXML
    private TableColumn<Cliente, String> ClientTableViewEmail;
    
    @FXML
    private TableColumn<Cliente, String> ClientTableViewTelephone;
    
    @FXML
    private TableView<Cliente> ClientTableView;
    
    @FXML
    private TableView<Equipamento> EquipTableView;
    
    @FXML
    private TableView<Locacao> AlocTableView;
    
    @FXML
    private TableColumn<Endereco, Integer> AdressTableViewID;

    @FXML
    private TableColumn<Endereco, String> AdressTableViewStreet;

    @FXML
    private TableColumn<Endereco, String> AdressTableViewCity;

    @FXML
    private TableColumn<Endereco, Character> AdressTableViewUF;

    @FXML
    private TableColumn<Endereco, String> AdressTableViewNumber;
    
    @FXML
    private TableColumn<Endereco, String> AdressTableViewCEP;
    
    @FXML
    private TableColumn<Endereco, String> AdressTableViewBairro;
    
    @FXML
    private TableColumn<Equipamento, String> EquipTableViewName;

    @FXML
    private TableColumn<Equipamento, Double> EquipTableViewDiaria;

    @FXML
    private TableColumn<Equipamento, Double> EquipTableViewSemanal;

    @FXML
    private TableColumn<Equipamento, Double> EquipTableViewQuinzenal;

    @FXML
    private TableColumn<Equipamento, Double> EquipTableViewMensal;
    
    @FXML
    private TableColumn<Equipamento, String> EquipTableViewDescription;
    
    @FXML
    private TableColumn<Equipamento, Integer> EquipTableViewID;
    
    @FXML
    private TableView<Endereco> AdressTableView;
        

    ObservableList<Cliente> observableList = FXCollections.observableArrayList();
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
				
		/// Setting ClientTableView
		ClientTableViewID.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("id"));
		ClientTableViewName.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
		ClientTableViewCPF.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cpf"));
		ClientTableViewEmail.setCellValueFactory(new PropertyValueFactory<Cliente, String>("email"));
		ClientTableViewSex.setCellValueFactory(new PropertyValueFactory<Cliente, Character>("sexo"));
		ClientTableViewTelephone.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefone"));
		
		/// Setting EquipTableView
		EquipTableViewDescription.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("descricao"));
		EquipTableViewDiaria.setCellValueFactory(new PropertyValueFactory<Equipamento, Double>("diaria"));
		EquipTableViewMensal.setCellValueFactory(new PropertyValueFactory<Equipamento, Double>("mensal"));
		EquipTableViewQuinzenal.setCellValueFactory(new PropertyValueFactory<Equipamento, Double>("quinzenal"));
		EquipTableViewSemanal.setCellValueFactory(new PropertyValueFactory<Equipamento, Double>("semanal"));
		EquipTableViewName.setCellValueFactory(new PropertyValueFactory<Equipamento, String>("nome"));
		EquipTableViewID.setCellValueFactory(new PropertyValueFactory<Equipamento, Integer>("id"));
		
		
		//////
		/// Restricting ClientTextFieldName, EquipTextFieldName to have only Letters
		/////
		
		Pattern pattern = Pattern.compile("[a-zA-Z ]*");
		UnaryOperator<TextFormatter.Change> filter = c -> {
		    if (pattern.matcher(c.getControlNewText()).matches()) {
		        return c ;
		    } else {
		        return null ;
		    }
		};
		TextFormatter<String> formatter = new TextFormatter<>(filter);
		TextFormatter<String> EquipName = new TextFormatter<>(filter);
		ClientTextFieldName.setTextFormatter(formatter);
		EquipTextFieldName.setTextFormatter(EquipName);
		
		EquipTextFieldDiaria.textProperty().addListener((obs, oldValue, newValue) -> {
	    	if (newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) {
	    		EquipTextFieldDiaria.setText(oldValue);
            }
	    });
		
		ClientTextFieldNumber.textProperty().addListener((obs, oldValue, newValue) -> {
	    	if (newValue != null && !newValue.matches("[\\(][0-9]")) {
	    		ClientTextFieldNumber.setText(oldValue);
            }
	    });		
				
		//////
		/// Setting Formatter for Telephone Number
		//////
		
		ClientTextFieldNumber.setTextFormatter(new TextFormatter<String>(change -> {			
			
            final int oldLength = change.getControlText().length();
            int newLength = change.getControlNewText().length();
            
            if (newLength < oldLength) return change;
            
            switch (newLength) {
            	case 0:
	                if(ClientTextFieldNumber.isPressed() && ClientTextFieldNumber.getLength() == 0) {
	                	change.setText(change.getText() + "("); 
	                	newLength++;
	                }
	                break;    
            	case 3:
                    change.setText(change.getText() + ")"); 
                    newLength++;
                    change.setText(change.getText() + " "); 
                    newLength++;
                    break;
            	case 10:
                    change.setText(change.getText() + "-"); 
                    newLength++;
                    break;
                case 16:
                    return null;
            }
            
            change.setCaretPosition(newLength);
            change.setAnchor(newLength);
            return change;
        }));
		
		//////
		/// Setting Formatter for CPF
		/////
		
		ClientTextFieldCPF.setTextFormatter(new TextFormatter<String>(change -> {

            final int oldLength = change.getControlText().length();
            int newLength = change.getControlNewText().length();
            
            if (newLength < oldLength) return change;
            
            switch (newLength) {
            	case 3: case 7:
                    change.setText(change.getText() + "."); 
                    newLength++;
                    break;
            	case 11:
                    change.setText(change.getText() + "-"); 
                    newLength++;
                    break;
                case 15:
                    return null;
            }
            change.setCaretPosition(newLength);
            change.setAnchor(newLength);
            return change;
        }));
		
	}
	
    ////
	// Handling CheckBox inside AddClientPanel
    ////
	
	@FXML
    public void handleM(){
    	if(M.isSelected()) {
    		F.setSelected(false);
    	}
    }
    
    @FXML
    public void handleF(){
    	if(F.isSelected()) {
    		M.setSelected(false);
    	}
    }
    
    ////
    // Changing the Panels
    ////
    
    @FXML
    void AddClientPanel(ActionEvent event) {    	
    	observableAdressList.clear();   	
    	observableNameAdressList.clear();
    	M.setSelected(false);
    	F.setSelected(false);
    	ClientTextFieldCPF.setText("");
		ClientTextFieldEmail.setText("");
		ClientTextFieldName.setText("");
		ClientTextFieldName.setText("");
		ClientTextFieldNumber.setText("");
    	
    	PanelAddClient.setVisible(true);
    	PanelAddClient.setDisable(false);
    	PanelAddEquip.setVisible(false);
    	PanelAddEquip.setDisable(true);
    	PanelAloc.setVisible(false);
    	PanelAloc.setDisable(true);
    	PanelList.setVisible(false);
    	PanelList.setDisable(true);
    	
    	System.out.println("Client Panel");
    	
    }
    
    @FXML
    void AddEquipPanel(ActionEvent event) {
    	    	
    	EquipTextFieldName.setText("");
    	EquipTextFieldDescription.setText("");
    	EquipTextFieldDiaria.setText("");
    	EquipTextFieldMensal.setText("");
    	EquipTextFieldQuinzenal.setText("");
    	EquipTextFieldSemanal.setText("");
    	
    	PanelAddClient.setVisible(false);
    	PanelAddClient.setDisable(true);
    	PanelAddEquip.setVisible(true);
    	PanelAddEquip.setDisable(false);
    	PanelAloc.setVisible(false);
    	PanelAloc.setDisable(true);
    	PanelList.setVisible(false);
    	PanelList.setDisable(true);
    	System.out.println("Equip Panel");
    }

    @FXML
    void AlocPanel(ActionEvent event) {
    	PanelAddClient.setVisible(false);
    	PanelAddClient.setDisable(true);
    	PanelAddEquip.setVisible(false);
    	PanelAddEquip.setDisable(true);
    	PanelAloc.setVisible(true);
    	PanelAloc.setDisable(false);
    	PanelList.setVisible(false);
    	PanelList.setDisable(true);
    	System.out.println("Aloc Panel");

    }
    
    @FXML
    void ListPanel(ActionEvent event) {
    	ClientTableView.setItems(observableList);
    	EquipTableView.setItems(observableEquipList);
    	PanelAddClient.setVisible(false);
    	PanelAddClient.setDisable(true);
    	PanelAddEquip.setVisible(false);
    	PanelAddEquip.setDisable(true);
    	PanelAloc.setVisible(false);
    	PanelAloc.setDisable(true);
    	PanelList.setVisible(true);
    	PanelList.setDisable(false);
    	System.out.println("List Panel");
    }
    
    ////
    // Changing the buttons color
    ////
    
    @FXML
    void AlocMouseEntered(MouseEvent event) {
    	Alocate.setStyle("-fx-background-color: #530D53");
    }

    @FXML
    void AlocMouseExited(MouseEvent event) {
    	if(PanelAloc.isDisable())
    		Alocate.setStyle("-fx-background-color: #663399");
    }
    
    @FXML
    void AlocMousePressed(MouseEvent event) {
    	Alocate.setStyle("-fx-background-color: #530D53");
    	AddClient.setStyle("-fx-background-color: #663399");
    	AddEquip.setStyle("-fx-background-color: #663399");
    	List.setStyle("-fx-background-color: #663399");
    }
    
    @FXML
    void ClientMouseEntered(MouseEvent event) {    	
    	AddClient.setStyle("-fx-background-color: #530D53");
    }

    @FXML
    void ClientMouseExited(MouseEvent event) {
    	if(PanelAddClient.isDisable())
    		AddClient.setStyle("-fx-background-color: #663399");
    }
    
    @FXML
    void ClientMousePressed(MouseEvent event) {
    	Alocate.setStyle("-fx-background-color: #663399");
    	AddClient.setStyle("-fx-background-color: #530D53");
    	AddEquip.setStyle("-fx-background-color: #663399");
    	List.setStyle("-fx-background-color: #663399");
    }
    
    @FXML
    void EquipMouseEntered(MouseEvent event) {
    	AddEquip.setStyle("-fx-background-color: #530D53");
    }

    @FXML
    void EquipMouseExited(MouseEvent event) {
    	if(PanelAddEquip.isDisable())
    		AddEquip.setStyle("-fx-background-color: #663399");
    }
    
    @FXML
    void EquipMousePressed(MouseEvent event) {
    	Alocate.setStyle("-fx-background-color: #663399");
    	AddClient.setStyle("-fx-background-color: #663399");
    	AddEquip.setStyle("-fx-background-color: #530D53");
    	List.setStyle("-fx-background-color: #663399");
    }
    
    @FXML
    void ListMouseEntered(MouseEvent event) {
    	List.setStyle("-fx-background-color: #530D53");
    }

    @FXML
    void ListMouseExited(MouseEvent event) {
    	if(PanelList.isDisable())
    		List.setStyle("-fx-background-color: #663399");
    }
    
    @FXML
    void ListMousePressed(MouseEvent event) {
    	Alocate.setStyle("-fx-background-color: #663399");
    	AddClient.setStyle("-fx-background-color: #663399");
    	AddEquip.setStyle("-fx-background-color: #663399");
    	List.setStyle("-fx-background-color: #530D53");
    }
    
    ////
    // Window popup in AddClientPanel to Register Adress
    ////
    
    @FXML
    void RegisterNewAdress(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/gui/RegisterAdress.fxml"));
				
		Scene scene = new Scene(root);
				
		stage.setScene(scene);
		stage.setTitle("Cadastrar Endereço");
		stage.show();	
		
		AGWPanel.setDisable(true);
		
		stage.setOnCloseRequest(event1 -> {
			AGWPanel.setDisable(false);
			if(!observableAdressList.isEmpty())
				ClienteChoiceBoxAdress.setDisable(false);
			ClienteChoiceBoxAdress.setItems(observableNameAdressList);
		});
		
    }
    
    ////
    // Registering a new Client
    ////
    
    @FXML
    void cadClient(ActionEvent event) throws Exception{
    	
    	Cliente cli;
    	Character Sex = ' ';
    	
    	// Checking the data
    	if(!ClientTextFieldEmail.getText().matches(RegexEMAIL) || !ClientTextFieldCPF.getText().matches(RegexCPF)
    			|| !ClientTextFieldNumber.getText().matches(RegexTELEPHONE) || ( !M.isSelected() && !F.isSelected() ) 
    			|| ClientTextFieldName.getText().isEmpty() || observableAdressList.isEmpty()) {     		
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(M.isSelected()) Sex = 'M';
    	else if(F.isSelected()) Sex = 'F';    	
    	
    	cli = new Cliente(countID, ClientTextFieldName.getText(), Sex, ClientTextFieldEmail.getText(), 
				ClientTextFieldCPF.getText(), ClientTextFieldNumber.getText());
		
    	cli.setEnderecos(observableAdressList);
    	
    	System.out.println(cli.getEnderecos());
    	
    	observableList.add(cli);
		
		countID++;
  		
		System.out.println("Cadastrando...");
		
		ClientTextFieldCPF.setText("");
		ClientTextFieldEmail.setText("");
		ClientTextFieldName.setText("");
		ClientTextFieldName.setText("");
		ClientTextFieldNumber.setText("");
		observableAdressList.clear();   	
    	observableNameAdressList.clear();
    	M.setSelected(false);
    	F.setSelected(false);
    }
    
    ////
    // Editing an Adress
    ////
    
    @FXML
    void editAdress(ActionEvent event) throws IOException, Exception {
    	if(ClienteChoiceBoxAdress.isDisable())
    		return;
    	
    	a = ClienteChoiceBoxAdress.getValue();
    	    	
    	Parent root = FXMLLoader.load(getClass().getResource("/gui/RegisterAdressEdit.fxml"));
		
		Scene scene = new Scene(root);
		
		stage.setScene(scene);
		stage.setTitle("Cadastrar Endereço");
		stage.show();	
		
		AGWPanel.setDisable(true);
		
		stage.setOnCloseRequest(event1 -> {
			AGWPanel.setDisable(false);
			if(!observableAdressList.isEmpty())
				ClienteChoiceBoxAdress.setDisable(false);
			ClienteChoiceBoxAdress.setItems(observableNameAdressList);
		});
    }
    
    ////
    // Registering an Equipment
    ////
    
    @FXML
    void cadEquip(ActionEvent event) throws Exception {
    	
    	Equipamento equip;
    	    	    	
    	if(!EquipTextFieldDiaria.getText().matches(RegexRealNumber) || !EquipTextFieldMensal.getText().matches(RegexRealNumber) 
    			|| !EquipTextFieldQuinzenal.getText().matches(RegexRealNumber) || !EquipTextFieldSemanal.getText().matches(RegexRealNumber)
    			|| EquipTextFieldName.getText().isEmpty() || EquipTextFieldDescription.getText().isEmpty())
    		throw new Exception("DADOS INVÁLIDOS");
    	
    	equip = new Equipamento(countID, EquipTextFieldName.getText(), Double.parseDouble(EquipTextFieldDiaria.getText()), 
    			Double.parseDouble(EquipTextFieldSemanal.getText()), Double.parseDouble(EquipTextFieldQuinzenal.getText()), 
    			Double.parseDouble(EquipTextFieldMensal.getText()), EquipTextFieldDescription.getText());
    	
    	observableEquipList.add(equip);
    	observableNameEquipList.add(EquipTextFieldName.getText());
    	
    	EquipTextFieldName.setText("");
    	EquipTextFieldDescription.setText("");
    	EquipTextFieldDiaria.setText("");
    	EquipTextFieldMensal.setText("");
    	EquipTextFieldQuinzenal.setText("");
    	EquipTextFieldSemanal.setText("");
    	
    	System.out.println("Cadastrando...");
    	
    }
    
}