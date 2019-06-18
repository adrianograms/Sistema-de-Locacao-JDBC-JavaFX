package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.Alert.AlertType;
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
import model.services.EquipamentoService;

public class MainViewController implements Initializable {

	/// Variáveis dispensáveis

	int countID = 0;
	int AlocIndex = 0;

    ObservableList<Cliente> observableList = FXCollections.observableArrayList();
	
	public static ObservableList<Endereco> observableAdressList = FXCollections.observableArrayList();
	public static ObservableList<String> observableNameAdressList = FXCollections.observableArrayList();
	
	public static ObservableList<Equipamento> observableEquipList = FXCollections.observableArrayList();
	public static ObservableList<String> observableNameEquipList = FXCollections.observableArrayList();
	public static ObservableList<String> auxNameList = FXCollections.observableArrayList();
	public static ObservableList<Equipamento> obsEquipAlocList = FXCollections.observableArrayList();
	
	public static ObservableList<Locacao> observableAlocList = FXCollections.observableArrayList();
	ObservableList<Endereco> aux = FXCollections.observableArrayList();
	
	/// Auxiliar in editing an Adress in ClientPanel
	public static String a = " "; 
	
	///
	
	String RegexCPF = "[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}";
	String RegexEMAIL = "[a-zA-Z0-9]*@(gmail.com|hotmail.com|outlook.com|unioeste.br|yahoo.com.br)";
	String RegexTELEPHONE = "[(][0-9]{2}[)] [0-9]{5}-[0-9]{4}";
	String RegexRealNumber = "[0-9]*[.][0-9]*";
	String RegexDate = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
	
	public static Stage stage = new Stage();
	
	private EquipamentoService equipamentoService;
	
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
    private Button AlocCalcValue;
    
    @FXML
    private Hyperlink HiperlinkSobre;

    @FXML
    private TextField ClientTextFieldName;

    @FXML
    private TextField ClientTextFieldCPF;
    
    @FXML
    private Button AlocSearchID;

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
    private ChoiceBox<String> AlocChoiceBoxAdress;
    
    @FXML
    private ChoiceBox<String> AlocChoiceBoxEquip;

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
    private Button AlocAddEquip;
    
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
    private TableColumn<Endereco, String> AdressTableViewUF;

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
    private TableColumn<Locacao, Integer> AlocTableViewID;

    @FXML
    private TableColumn<Locacao, String> AlocTableViewClient;

    @FXML
    private TableColumn<Locacao, String> AlocTableViewAdress;

    @FXML
    private TableColumn<Locacao, String> AlocTableViewDI;

    @FXML
    private TableColumn<Locacao, String> AlocTableViewDF;
    
    @FXML
    private TableColumn<Locacao, Double> AlocTableViewValue;
    
    @FXML
    private TableView<Endereco> AdressTableView;


    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		setEquipamentoService(new EquipamentoService());
				
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
		
		/// Setting AlocTableView
		AlocTableViewID.setCellValueFactory(new PropertyValueFactory<Locacao, Integer>("id"));
		AlocTableViewClient.setCellValueFactory(new PropertyValueFactory<Locacao, String>("nomeCliente"));
		AlocTableViewAdress.setCellValueFactory(new PropertyValueFactory<Locacao, String>("nomeEndereco"));
		AlocTableViewValue.setCellValueFactory(new PropertyValueFactory<Locacao, Double>("valor"));
		AlocTableViewDI.setCellValueFactory(new PropertyValueFactory<Locacao, String>("data_inicio"));
		AlocTableViewDF.setCellValueFactory(new PropertyValueFactory<Locacao, String>("data_final"));
				
		/// Settin AdressTableView
		AdressTableViewID.setCellValueFactory(new PropertyValueFactory<Endereco, Integer>("id"));
		AdressTableViewBairro.setCellValueFactory(new PropertyValueFactory<Endereco, String>("bairro"));
		AdressTableViewCEP.setCellValueFactory(new PropertyValueFactory<Endereco, String>("cep"));
		AdressTableViewCity.setCellValueFactory(new PropertyValueFactory<Endereco, String>("cidade"));
		AdressTableViewNumber.setCellValueFactory(new PropertyValueFactory<Endereco, String>("numero"));
		AdressTableViewStreet.setCellValueFactory(new PropertyValueFactory<Endereco, String>("rua"));
		AdressTableViewUF.setCellValueFactory(new PropertyValueFactory<Endereco, String>("estado"));
		
		//////
		/// Restricting ClientTextFieldName to have only Letters
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
		ClientTextFieldName.setTextFormatter(formatter);
		
		//////
		/// Restricting EquipTextFields to have only Doubles or Integers
		/////
		
		EquipTextFieldDiaria.textProperty().addListener((obs, oldValue, newValue) -> {
	    	if (newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) {
	    		EquipTextFieldDiaria.setText(oldValue);
            }
	    });
		
		EquipTextFieldMensal.textProperty().addListener((obs, oldValue, newValue) -> {
	    	if (newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) {
	    		EquipTextFieldMensal.setText(oldValue);
            }
	    });
		
		EquipTextFieldQuinzenal.textProperty().addListener((obs, oldValue, newValue) -> {
	    	if (newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) {
	    		EquipTextFieldQuinzenal.setText(oldValue);
            }
	    });
			
		EquipTextFieldSemanal.textProperty().addListener((obs, oldValue, newValue) -> {
	    	if (newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) {
	    		EquipTextFieldSemanal.setText(oldValue);
            }
	    });
		
		AlocTextFieldID.textProperty().addListener((obs, oldValue, newValue) -> {
	        if (newValue != null && !newValue.matches("\\d*")) {
	        	AlocTextFieldID.setText(oldValue);
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
    	
    	/// Clears the screen every time it enters
    	observableAdressList.clear();   	
    	observableNameAdressList.clear();
    	M.setSelected(false);
    	F.setSelected(false);
    	ClientTextFieldCPF.setText("");
		ClientTextFieldEmail.setText("");
		ClientTextFieldName.setText("");
		ClientTextFieldName.setText("");
		ClientTextFieldNumber.setText("");
    	
		/// Disable/Enable panels
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
    	    	
    	/// Clears the screen every time it enters
    	EquipTextFieldName.setText("");
    	EquipTextFieldDescription.setText("");
    	EquipTextFieldDiaria.setText("");
    	EquipTextFieldMensal.setText("");
    	EquipTextFieldQuinzenal.setText("");
    	EquipTextFieldSemanal.setText("");
    	
    	/// Disable/Enable panels
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
    	    
    	// Clear the List used to adding Equipamentos to the Locacao
    	obsEquipAlocList.clear();
    	
    	// Tag to control if it has been found the ClienteID
    	AlocIndex = -1;
    	
    	EquipLabelValue.setText("");
    	
    	AlocChoiceBoxAdress.setDisable(true);
    	AlocChoiceBoxAdress.setItems(FXCollections.observableArrayList());
    	auxNameList.clear();
    	if(!observableNameEquipList.isEmpty()) {
    		auxNameList.addAll(observableNameEquipList);
    		AlocChoiceBoxEquip.setItems(auxNameList);
    		AlocChoiceBoxEquip.setDisable(false);
    	} else AlocChoiceBoxEquip.setDisable(true);
    	
    	AlocTextFieldID.setText("");    	
    	
    	/// Disable/Enable panels
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
    	AlocTableView.setItems(observableAlocList);
    	AdressTableView.setItems(observableAdressList);
    	
    	/// Disable/Enable panels
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
    // Window popup in AddClientPanel to Register Adress
    ////
    
    @FXML
    void RegisterNewAdress(ActionEvent event) throws IOException {
    	
    	// Opening the a new window
    	Parent root = FXMLLoader.load(getClass().getResource("/gui/RegisterAdress.fxml"));
				
		Scene scene = new Scene(root);
				
		stage.setScene(scene);
		stage.setTitle("Cadastrar Endereço");
		stage.show();	
		
		// Disables the principal window
		AGWPanel.setDisable(true);
		
		// Enables the principal window on closure and updates the ChoiceBoxAdress (for editing)
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
    	
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error Dialog");
    	alert.setHeaderText("DADOS INVÁLIDOS");
    	
    	if(!ClientTextFieldEmail.getText().matches(RegexEMAIL)){
    		alert.setContentText("Verifique se o email está no formato correto.");
    		ClientTextFieldEmail.setText("");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(!ClientTextFieldCPF.getText().matches(RegexCPF)){
    		alert.setContentText("Verifique se o CPF está no formato correto.");
    		ClientTextFieldCPF.setText("");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(!ClientTextFieldNumber.getText().matches(RegexTELEPHONE)){
    		alert.setContentText("Verifique se o Telefone está no formato correto.");
    		ClientTextFieldNumber.setText("");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(!M.isSelected() && !F.isSelected()){
    		alert.setContentText("Selecione o sexo do cliente.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(ClientTextFieldName.getText().isEmpty()){
    		alert.setContentText("Digite o nome do cliente.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(observableAdressList.isEmpty()){
    		alert.setContentText("Adicione um endereço para o cliente.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	
//    	// Checking the data
//    	if(!ClientTextFieldEmail.getText().matches(RegexEMAIL) || !ClientTextFieldCPF.getText().matches(RegexCPF)
//    			|| !ClientTextFieldNumber.getText().matches(RegexTELEPHONE) || ( !M.isSelected() && !F.isSelected() ) 
//    			|| ClientTextFieldName.getText().isEmpty() || observableAdressList.isEmpty()) {     		
//    		throw new Exception("DADOS INVÁLIDOS");
//    	}
    	
    	if(M.isSelected()) Sex = 'M';
    	else if(F.isSelected()) Sex = 'F';
    	
    	/// Registering Cliente    	
    	cli = new Cliente(countID, ClientTextFieldName.getText(), Sex, ClientTextFieldEmail.getText(), 
				ClientTextFieldCPF.getText(), ClientTextFieldNumber.getText());
		
    	/// Adding the adresses to the Cliente
    	cli.setEnderecos(observableAdressList);
    	
    	/// Updating the ObservableList that will appear in the ListPanel
    	observableList.add(cli);
		
		countID++;
  		
		System.out.println("Cliente Cadastrado.");
		
		///Clears the data in the screen
		ClientTextFieldCPF.setText("");
		ClientTextFieldEmail.setText("");
		ClientTextFieldName.setText("");
		ClientTextFieldName.setText("");
		ClientTextFieldNumber.setText("");
		observableAdressList.clear();   	
    	observableNameAdressList.clear();
    	M.setSelected(false);
    	F.setSelected(false);
    	ClienteChoiceBoxAdress.setDisable(true);
    }
    
    ////
    // Editing an Adress
    ////
    
    @FXML
    void editAdress(ActionEvent event) throws IOException, Exception {
    	
    	/// If the ChoiceBox is disable (empty), do nothing
    	if(ClienteChoiceBoxAdress.isDisable() || ClienteChoiceBoxAdress.getValue() == null)
    		return;
    	
    	// a is an Auxiliar to fill the data that will be changed in the new window
    	a = ClienteChoiceBoxAdress.getValue();
    	    	
    	/// Opening the window
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
		
		System.out.println("Endereço editado");
    }
    
    ////
    // Registering an Equipment
    ////
    
    @FXML
    void cadEquip(ActionEvent event) throws Exception {
    	
    	Equipamento equip;    	
    	
    	/// Checking the data
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error Dialog");
    	alert.setHeaderText("DADOS INVÁLIDOS");
    	
    	if(EquipTextFieldName.getText().isEmpty()){
    		alert.setContentText("Digite o nome do equipamento.");
    		EquipTextFieldName.setText("");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(EquipTextFieldDiaria.getText().isEmpty() || EquipTextFieldSemanal.getText().isEmpty() || 
    			EquipTextFieldQuinzenal.getText().isEmpty() || EquipTextFieldMensal.getText().isEmpty()){
    		alert.setContentText("Digite os valores do equipamento.");
    		EquipTextFieldName.setText("");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(EquipTextFieldDescription.getText().isEmpty()){
    		alert.setContentText("Digite uma descrição para o equipamento.");
    		EquipTextFieldDescription.setText("");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
//    	/// Checking the data
//    	if(!diaria.matches(RegexRealNumber) || !mensal.matches(RegexRealNumber) 
//    			|| !quinzenal.matches(RegexRealNumber) || !semanal.matches(RegexRealNumber)
//    			|| EquipTextFieldName.getText().isEmpty() || EquipTextFieldDescription.getText().isEmpty())
//    		throw new Exception("DADOS INVÁLIDOS");    	
    	
    	// Setting the format of the string stored in the TextFields that should contain a double value
    	String diaria = String.format("%.2f", Double.parseDouble(EquipTextFieldDiaria.getText()));
    	String semanal = String.format("%.2f", Double.parseDouble(EquipTextFieldSemanal.getText()));
    	String quinzenal = String.format("%.2f", Double.parseDouble(EquipTextFieldQuinzenal.getText()));
    	String mensal = String.format("%.2f", Double.parseDouble(EquipTextFieldMensal.getText()));
    	diaria = diaria.replace(',', '.');
    	semanal = semanal.replace(',', '.');
    	quinzenal = quinzenal.replace(',', '.');
    	mensal = mensal.replace(',', '.');
    	
    	/// Registering the Equipamento
    	equip = new Equipamento(countID, EquipTextFieldName.getText(), Double.parseDouble(diaria), 
    			Double.parseDouble(semanal), Double.parseDouble(quinzenal), 
    			Double.parseDouble(mensal), EquipTextFieldDescription.getText());
    	
    	/// Adding it to the ObservableList used in ListPanel
    	observableEquipList.add(equip);
    	equip.setId(null);
    	equipamentoService.saveOrUpdate(equip);
    	/// This ObservableList is used to fill the ChoiceBoxes with only the name of the Equipamento
    	observableNameEquipList.add(EquipTextFieldName.getText());
    	
    	/// Clears the screen
    	EquipTextFieldName.setText("");
    	EquipTextFieldDescription.setText("");
    	EquipTextFieldDiaria.setText("");
    	EquipTextFieldMensal.setText("");
    	EquipTextFieldQuinzenal.setText("");
    	EquipTextFieldSemanal.setText("");
    	
    	System.out.println("Equipamento Cadastrado");
    	
    }
    
    @FXML
    void cadAloc(ActionEvent event) throws Exception {
    	
    	Locacao aloc;
    	
    	//////// --------> NAO CONSIGO LIMPAR O TEXTFIELD DAS DATAS QUANDO SE SAI E ENTRA NA TELA
    	
    	/// Checking the data
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error Dialog");
    	alert.setHeaderText("DADOS INVÁLIDOS");
    	
    	if(AlocIndex == -1){
    		alert.setContentText("Digite o ID do cliente.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(AlocTextFieldFinalDate.getValue() == null){
    		alert.setContentText("Selecione uma data para a locação do cliente.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(AlocTextFieldInitialDate.getValue() == null){
    		alert.setContentText("Selecione uma data para a locação do cliente.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if((AlocChoiceBoxAdress.getValue() == null && observableEquipList.isEmpty())){
    		alert.setContentText("É preciso cadastrar e selecionar ao menos um equipamento.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(AlocChoiceBoxAdress.getValue() == null || obsEquipAlocList.isEmpty()){
    		alert.setContentText("É preciso adicionar ao menos um equipamento.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	   		
    	/// Registering the adress
    	Endereco adr = searchAdress(observableList.get(AlocIndex), AlocChoiceBoxAdress.getValue());    	// Returns the adress based on it's name
    	aloc = new Locacao(countID, AlocTextFieldInitialDate.getValue(), AlocTextFieldFinalDate.getValue(), 
    			adr, observableList.get(AlocIndex));
       		
    	
    	aloc.setEquipamentos(obsEquipAlocList);
    	
    	aloc.setValor(calcValue(aloc.getData_inicio(), aloc.getData_final(), aloc));
    	
    	AlocTextFieldID.setText("");
    	
    	observableAlocList.add(aloc);
    	
    	obsEquipAlocList.clear();
    	
    	
    }
    
    //////
    // Calculation of the value based in the Date given in AlocPanel
    /////    
    
    private double calcValue(LocalDate ini, LocalDate fin, Locacao loc) {
    	
    	double value;
    			    	
    	double diaria = 0, semanal = 0, quinzenal = 0, mensal = 0;
    			
    	for(int i = 0 ; i < loc.getEquipamentos().size() ; i++) {
    				
    		diaria += loc.getEquipamentos().get(i).getDiaria();
    		semanal += loc.getEquipamentos().get(i).getSemanal();
    		quinzenal += loc.getEquipamentos().get(i).getQuinzenal();
    		mensal += loc.getEquipamentos().get(i).getMensal();
    				
    	}
    			
    	int diastotal = (fin.getYear() - ini.getYear()) * 365 + (fin.getMonthValue() - ini.getMonthValue()) * 30 + 
    				    (fin.getDayOfMonth() - ini.getDayOfMonth());
    		
    	int meses = diastotal/30;
    	int quinzenas = (diastotal - meses*30)/15;
    	int semanas = (diastotal - meses*30 - quinzenas*15)/7;
    	int dias = diastotal - meses*30 - quinzenas*15 - semanas*7;    	
    			
    	return value = meses*mensal + quinzenas*quinzenal + semanas*semanal + dias*diaria;    	
    	
    }   
    
    //////
    // Searches for the Cliente that matches to the ID in AlocPanel
    /////
    
    @FXML
    void searchID(ActionEvent event) throws Exception{

    	int index = 0;
    	
    	for (index = 0 ; index < observableList.size() ; index++) {
    		if(observableList.get(index).getId() == Integer.parseInt(AlocTextFieldID.getText()))
    			break;
    	}
    	    	
    	if(index == observableList.size()) {
    		AlocChoiceBoxAdress.setDisable(true);
        	AlocChoiceBoxAdress.setItems(FXCollections.observableArrayList());
    		throw new Exception("CLIENTE NÃO ENCONTRADO");
    	}
    	else
    		AlocIndex = index;
    	
    	aux = FXCollections.observableArrayList(observableList.get(index).getEnderecos());
    	ObservableList<String> AdressNameList = FXCollections.observableArrayList();
    	
    	for( index = 0 ; index < aux.size() ; index++) {
    		AdressNameList.add(aux.get(index).getNome());
    	}    	
    	
    	AlocChoiceBoxAdress.setItems(AdressNameList);
    	AlocChoiceBoxAdress.setDisable(false);
    	
    }
    
    //////
    // Searches for the Endereco that matches to the AdressName in the ChoiceBox in AlocPanel
    /////
    
    Endereco searchAdress(Cliente cli, String AdressName) {
    	
    	Endereco adr;
    	
    	for(int i = 0 ; i < cli.getEnderecos().size() ; i++) {
    		if(cli.getEnderecos().get(i).getNome() == AdressName) {
    			adr = cli.getEnderecos().get(i);
    			return adr;
    		}
    	}
    	
    	return null;
    	
    }
    
    //////
    // Searches for the Equipamento that matches to the EquipName in the ChoiceBox in AlocPanel
    /////
    
    Equipamento searchEquip(String EquipName) {
    	for( int i = 0 ; i < observableEquipList.size() ; i++) {
    		if(observableEquipList.get(i).getNome().equals(EquipName)) {
    			auxNameList.remove(observableEquipList.get(i).getNome());
    			break;
    		}    			
    	}
    	
    	for( int i = 0 ; i < observableEquipList.size() ; i++) {
    		if(observableEquipList.get(i).getNome().equals(EquipName)) {
    			return observableEquipList.get(i);
    		}    			
    	}
    	
    	return null;
    	
    }
    
    //////
    // Adds the equipament
    /////
    
    @FXML
    void AddEquipAloc(ActionEvent event) {
    	
    	Equipamento equip = searchEquip(AlocChoiceBoxEquip.getValue());
    	obsEquipAlocList.add(equip);
    	if(auxNameList.isEmpty())
    		AlocChoiceBoxEquip.setDisable(true);
    	
    	System.out.println(equip);
    	
    }
    
    /////
    // Shows the value
    ////
    
    
    @FXML
    void changeValueLabel(ActionEvent event) throws Exception {
    	Locacao aloc;
    	
    	/// Checking the data
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error Dialog");
    	alert.setHeaderText("DADOS INVÁLIDOS");
    	
    	if(AlocIndex == -1){
    		alert.setContentText("Digite o ID do cliente.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(AlocTextFieldFinalDate.getValue() == null){
    		alert.setContentText("Selecione uma data para a locação do cliente.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(AlocTextFieldInitialDate.getValue() == null){
    		alert.setContentText("Selecione uma data para a locação do cliente.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if((AlocChoiceBoxAdress.getValue() == null && observableEquipList.isEmpty())){
    		alert.setContentText("É preciso cadastrar e selecionar ao menos um equipamento.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(AlocChoiceBoxAdress.getValue() == null || obsEquipAlocList.isEmpty()){
    		alert.setContentText("É preciso adicionar ao menos um equipamento.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	    		
    	Endereco adr = searchAdress(observableList.get(AlocIndex), AlocChoiceBoxAdress.getValue());    		
    	aloc = new Locacao(countID, AlocTextFieldInitialDate.getValue(), AlocTextFieldFinalDate.getValue(), 
    			adr, observableList.get(AlocIndex));
       	    	
    	
    	aloc.setEquipamentos(obsEquipAlocList);
    	
    	String valor = String.format("%.2f R$", calcValue(aloc.getData_inicio(), aloc.getData_final(), aloc));
    	    	
    	EquipLabelValue.setText(valor);
    	
    	System.out.println("Locado");
    	
    }

	public void setEquipamentoService(EquipamentoService equipamentoService) {
		this.equipamentoService = equipamentoService;
	}
    
}