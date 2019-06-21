package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import com.mysql.jdbc.UpdatableResultSet;

import db.DbException;
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
import javafx.scene.control.ButtonType;
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
import model.services.ClienteService;
import model.services.EquipamentoService;
import model.services.LocacaoService;

public class MainViewController implements Initializable {

	// ID`s counters
	
	int countID = 0;
	int countEquipID = 0;	
	int countAlocID = 0;
	public static int countAdressID = 0; // kinda useless
	
	// Auxiliar to see if an client is found and to keep the index of it
	int AlocIndex = 0;
	
	// Used to modify all the data in the ListPanel
	int GLOBALIDMODIFY = 0;

	//////////////////////////////////////////////////////////
	///     OBSERVABLE LISTS WITH >>> ALL <<< THE DATA     ///
	//////////////////////////////////////////////////////////
	//
	// Keeps all the Clients
    ObservableList<Cliente> observableList = FXCollections.observableArrayList();
	//
    // Keeps all the Adresses >>TEMPORARILY<<
    // observableName is used to tag the ChoiceBoxes for better visualization
	public static ObservableList<Endereco> observableAdressList = FXCollections.observableArrayList();
	public static ObservableList<String> observableNameAdressList = FXCollections.observableArrayList();
	//
	// observableEquipList keeps all the Equips
	// observableName is used to tag the ChoiceBoxes for better visualization
	// auxNameList is TEMPORARILY and its used in the AlocPanel to control the equips that are Added (Alocado ou não)
	// obsEquipAlocList is TEMPORARILY and its used to keep the equips that are added in the AlocPanel to then add to its Locacao
	public static ObservableList<Equipamento> observableEquipList = FXCollections.observableArrayList();
	public static ObservableList<String> observableNameEquipList = FXCollections.observableArrayList();
	public static ObservableList<String> auxNameList = FXCollections.observableArrayList();
	public static ObservableList<Equipamento> obsEquipAlocList = FXCollections.observableArrayList();
	//
	// observableAlocList keeps all the Locacoes
	public static ObservableList<Locacao> observableAlocList = FXCollections.observableArrayList();	
	//////////////////////////////////////////////////////////
	///                                                    ///
	//////////////////////////////////////////////////////////
	
	/// Auxiliar in editing an Adress in ClientPanel
	public static String auxAdressName = "";
	
	/// Regular Expressions to check the data
	
	String RegexCPF = "[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}";
	String RegexEMAIL = "[a-zA-Z0-9]*@(gmail.com|hotmail.com|outlook.com|unioeste.br|yahoo.com.br)";
	String RegexTELEPHONE = "[(][0-9]{2}[)] [0-9]{5}-[0-9]{4}";
	String RegexRealNumber = "[0-9]*[.][0-9]*";
	String RegexDate = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
	
	public static Stage stage = new Stage();
	

	private EquipamentoService equipamentoService;
	
	private ClienteService clienteService;
	
	private LocacaoService locacaoService;

	@FXML
	private Button ListClientButtonDelete;
	
	@FXML
	private Button AlocRemoveEquip;
	
	@FXML
	private AnchorPane PanelSobre;

	
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
    

	public void setLocacaoService(LocacaoService locacaoService) {
		this.locacaoService = locacaoService;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		setEquipamentoService(new EquipamentoService());
		setClienteService(new ClienteService());
		setLocacaoService(new LocacaoService());
				
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
				
		//////
		/// Restricting ClientTextFieldName to have only Letters
		/////
		
		observableEquipList.addAll(equipamentoService.findAll());
		observableList.addAll(clienteService.findAll());
		observableAlocList.addAll(locacaoService.findAll(observableList, observableEquipList));
		for(Locacao locacao : observableAlocList) {
			locacao.setNomeCliente(locacao.getCliente().getNome());
			locacao.setNomeEndereco(locacao.getNomeEndereco());
			for(Equipamento equip : locacao.getEquipamentos()) {
				System.out.println(equip.getNome());
			}
		}
		for(Cliente cliente : observableList) {
			for(Endereco endereco : cliente.getEnderecos()) {
				observableAdressList.add(endereco);
			}
		}
		for(Endereco endereco : observableAdressList) {
			observableNameAdressList.add(endereco.getNome());
		}

		
		List<String> equipNomes = new ArrayList<>();
		
		for(Equipamento equip : observableEquipList) {
			equipNomes.add(equip.getNome());
		}
		
		observableNameEquipList.addAll(equipNomes);
		
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
    // Changing the buttons color (Quando o mouse passar por eles e quando se muda a Tela)
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
	// Handling CheckBox inside AddClientPanel (Masculino e Feminino ñ podem estar selecionados ao mesmo tempo)
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
    	ClientNewClient.setText("Cadastrar Cliente");
    	observableAdressList.clear();   	
    	observableNameAdressList.clear();
    	M.setSelected(false);
    	F.setSelected(false);
    	ClientTextFieldCPF.setText("");
		ClientTextFieldEmail.setText("");
		ClientTextFieldName.setText("");
		ClientTextFieldName.setText("");
		ClientTextFieldNumber.setText("");
		countAdressID = 0; // kinda useless aswell
    	
		/// Disable/Enable panels
    	PanelAddClient.setVisible(true);
    	PanelAddClient.setDisable(false);
    	PanelAddEquip.setVisible(false);
    	PanelAddEquip.setDisable(true);
    	PanelAloc.setVisible(false);
    	PanelAloc.setDisable(true);
    	PanelList.setVisible(false);
    	PanelList.setDisable(true);
    	PanelSobre.setVisible(false);
    	PanelSobre.setDisable(true);
    	
    	System.out.println("Client Panel");
    	
    }
    
    @FXML
    void AddEquipPanel(ActionEvent event) {
    	    	
    	/// Clears the screen every time it enters
    	EquipNewEquip.setText("Cadastrar Equipamento");
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
    	PanelSobre.setVisible(false);
    	PanelSobre.setDisable(true);
    	
    	System.out.println("Equip Panel");
    }

    @FXML
    void AlocPanel(ActionEvent event) {
    	    
    	// Clears the auxiliar List used to adding Equipamentos to the Locacao
    	obsEquipAlocList.clear();
    	
    	updateAlocNameList();
    	
    	// Tag to control if it has been found the ClienteID
    	AlocIndex = -1;
    	
    	EquipLabelValue.setText(""); // Label that contains que value when calculated
    	
    	// Clears the Panel    	
    	AlocChoiceBoxAdress.setDisable(true);
    	AlocChoiceBoxAdress.setItems(FXCollections.observableArrayList());
    	auxNameList.clear();
    	if(!observableNameEquipList.isEmpty()) {
    		auxNameList.addAll(observableNameEquipList);
    		AlocChoiceBoxEquip.setItems(auxNameList);
    		AlocChoiceBoxEquip.setDisable(false);
    	} else AlocChoiceBoxEquip.setDisable(true);    	
    	AlocTextFieldID.setText("");    	
    	
    	// dates
    	AlocTextFieldInitialDate.setValue(null);
    	AlocTextFieldFinalDate.setValue(null);
    	
    	/// Disable/Enable panels
    	PanelAddClient.setVisible(false);
    	PanelAddClient.setDisable(true);
    	PanelAddEquip.setVisible(false);
    	PanelAddEquip.setDisable(true);
    	PanelAloc.setVisible(true);
    	PanelAloc.setDisable(false);
    	PanelList.setVisible(false);
    	PanelList.setDisable(true);
    	PanelSobre.setVisible(false);
    	PanelSobre.setDisable(true);
    	
    	System.out.println("Aloc Panel");

    }
    
    private void updateAlocNameList() {
    	observableNameEquipList.clear();
		for(int i =0; i< observableEquipList.size(); i++) {
			observableNameEquipList.add(observableEquipList.get(i).getNome());
		}
	}

	@FXML
    void ListPanel(ActionEvent event) {
    	
    	// Clears the Panel  
    	ListEquipTextFieldID.setText("");
    	ListEquipTextFieldID.setText("");
    	ListAlocTextFieldID.setText("");
    	ListAlocTextFieldIDPDF.setText("");
    	observableAdressList.clear();
    	observableNameAdressList.clear();
    	
    	// Sets the observable lists that contains ALL the data to the tableviews that will show that data
    	ClientTableView.setItems(observableList);
    	EquipTableView.setItems(observableEquipList);
    	AlocTableView.setItems(observableAlocList);
    	
    	/// Disable/Enable panels
    	PanelAddClient.setVisible(false);
    	PanelAddClient.setDisable(true);
    	PanelAddEquip.setVisible(false);
    	PanelAddEquip.setDisable(true);
    	PanelAloc.setVisible(false);
    	PanelAloc.setDisable(true);
    	PanelList.setVisible(true);
    	PanelList.setDisable(false);
    	PanelSobre.setVisible(false);
    	PanelSobre.setDisable(true);
    	
    	System.out.println("List Panel");
    }    
    
    /////
    // Sobre
    /////
    
    @FXML
    void sobrePanel(ActionEvent event) {
    	
    	/// Disable/Enable panels
    	PanelAddClient.setVisible(false);
    	PanelAddClient.setDisable(true);
    	PanelAddEquip.setVisible(false);
    	PanelAddEquip.setDisable(true);
    	PanelAloc.setVisible(false);
    	PanelAloc.setDisable(true);
    	PanelList.setVisible(false);
    	PanelList.setDisable(true);
    	PanelSobre.setVisible(true);
    	PanelSobre.setDisable(false);
    	
    	/// Button colors
    	Alocate.setStyle("-fx-background-color: #663399");
    	AddClient.setStyle("-fx-background-color: #663399");
    	AddEquip.setStyle("-fx-background-color: #663399");
    	List.setStyle("-fx-background-color: #663399");
    	
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
    	
    	// Pops an alert every time a exception is throwed
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
    	
    	if(M.isSelected()) Sex = 'M';
    	else if(F.isSelected()) Sex = 'F';
    	
    	/// Registering Cliente 
    	// if -> in the case that its adding a new cliente
    	// else -> in the case that its modifying a cliente
    	
    	if(ClientNewClient.getText().equals("Cadastrar Cliente")) {    	
    		cli = new Cliente(countID, ClientTextFieldName.getText(), Sex, ClientTextFieldEmail.getText(), 
				ClientTextFieldCPF.getText(), ClientTextFieldNumber.getText());
    		
    		/// Adding the adresses to the Cliente
        	cli.setEnderecos(observableAdressList);
        	
        	/// Updating the ObservableList that will appear in the ListPanel
        	observableList.add(cli);
        	cli.setId(null);
        	clienteService.saveOrUpdate(cli);
    		
    		countID++;
      		
    		System.out.println("Cliente Cadastrado.");
    		
    	} else {
    		
    		cli = observableList.get(GLOBALIDMODIFY);
    		cli.setCpf(ClientTextFieldCPF.getText());
    		cli.setEmail(ClientTextFieldEmail.getText());
    		cli.getEnderecos().clear();
    		cli.setEnderecos(observableAdressList);
    		cli.setNome(ClientTextFieldName.getText());
    		cli.setSexo(Sex);
    		cli.setTelefone(ClientTextFieldNumber.getText());
    		
    		// Updates the ObservableList that contains the ID
    		for(int i = 0 ; i < observableList.size() ; i++) {
        		if(cli.getId() == observableList.get(i).getId()) {
        			observableList.set(i, cli);
        			break;
        		}
    		}    		
    		
    		System.out.println("Cliente Modificado.");
    		
    	}
		
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
    	ClientNewClient.setText("Cadastrar Cliente");
    	countAdressID = 0;
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
    	auxAdressName = ClienteChoiceBoxAdress.getValue();
    	    	
    	/// Opening the window with the data of the selected adress
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
    	// if -> in the case that its adding a new Equipamento
    	// else -> in the case that its modifying a Equipamento
    	if(EquipNewEquip.getText().equals("Cadastrar Equipamento")) {
    		equip = new Equipamento(countEquipID, EquipTextFieldName.getText(), Double.parseDouble(diaria), 
    			Double.parseDouble(semanal), Double.parseDouble(quinzenal), 
    			Double.parseDouble(mensal), EquipTextFieldDescription.getText());
    	
    		
    		/// Adding it to the ObservableList used in ListPanel
    		observableEquipList.add(equip);
        	equip.setId(null);
        	equipamentoService.saveOrUpdate(equip);
    		/// This ObservableList is used to fill the ChoiceBoxes with only the name of the Equipamento
    		observableNameEquipList.add(EquipTextFieldName.getText());
    	} else {
    		equip = observableEquipList.get(GLOBALIDMODIFY);
    		
    		equip.setDescricao(EquipTextFieldDescription.getText());
    		equip.setNome(EquipTextFieldName.getText());
    		equip.setDiaria(Double.parseDouble(diaria));
    		equip.setSemanal(Double.parseDouble(semanal));
    		equip.setQuinzenal(Double.parseDouble(quinzenal));
    		equip.setMensal(Double.parseDouble(mensal));
			equipamentoService.saveOrUpdate(equip);
    		EquipNewEquip.setText("Cadastrar Equipamento");
    		
    		// Updates the ObservableList that contains the ID
    		for(int i = 0 ; i < observableEquipList.size() ; i++) {
        		if(equip.getId() == observableEquipList.get(i).getId()) {
        			observableEquipList.set(i, equip);
        			break;
        		}
    		}    		
    		
    		System.out.println("Equipamento Modificado.");
    	}
    	
    	
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
    	
    	if((observableEquipList.isEmpty())){
    		alert.setContentText("É preciso cadastrar e selecionar ao menos um equipamento.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(obsEquipAlocList.isEmpty()){
    		alert.setContentText("É preciso adicionar ao menos um equipamento.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(AlocChoiceBoxAdress.getValue() == null){
    		alert.setContentText("É preciso adicionar ao menos um endereço.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	
    	/// Registering the Locacao
    	// if -> in the case that its adding a new Locacao
    	// else -> in the case that its modifying a Locacao
    	if(AlocNewAloc.getText().equals("Alocar")) {
    		
    		/// Registering the adress
        	Endereco adr = searchAdress(observableList.get(AlocIndex), AlocChoiceBoxAdress.getValue());    	// Returns the adress based on its name
        	aloc = new Locacao(countAlocID, AlocTextFieldInitialDate.getValue(), AlocTextFieldFinalDate.getValue(), 
        			adr, observableList.get(AlocIndex));     
        	
        	aloc.setEquipamentos(obsEquipAlocList);
        	
        	aloc.setValor(calcValue(aloc.getData_inicio(), aloc.getData_final(), aloc));
        	
        	AlocTextFieldID.setText("");
        	
        	countAlocID++;
        	
        	observableAlocList.add(aloc);
        	aloc.setId(null);
        	locacaoService.saveOrUpdate(aloc);
    	
    	} else {
    		
    		aloc = observableAlocList.get(GLOBALIDMODIFY);
    		
    		aloc.setCliente(observableList.get(AlocIndex));
    		aloc.setData_final(AlocTextFieldFinalDate.getValue());
    		aloc.setData_inicio(AlocTextFieldInitialDate.getValue());
    		aloc.setEndereco(searchAdress(observableList.get(AlocIndex), AlocChoiceBoxAdress.getValue()));
    		aloc.getEquipamentos().clear();
    		aloc.setEquipamentos(obsEquipAlocList);
    		aloc.setNomeCliente(observableList.get(AlocIndex).getNome());
    		aloc.setNomeEndereco(aloc.getEndereco().getNome());
    		aloc.setValor(calcValue(aloc.getData_inicio(), aloc.getData_final(), aloc));
    		
    		AlocTextFieldID.setText("");
    		
    		// Updates the ObservableList that contains the ID
    		for(int i = 0 ; i < observableAlocList.size() ; i++) {
        		if(aloc.getId() == observableAlocList.get(i).getId()) {
        			observableAlocList.set(i, aloc);
        			System.out.println("entrou!");
        			break;
        		}
    		}    		
    		locacaoService.saveOrUpdate(aloc);
    		
    		System.out.println("Alocação Modificado.");
    		
    	}   	
    	
    	// Clears the auxiliar List used to adding Equipamentos to the Locacao
    	obsEquipAlocList.clear();
    	
    	// Tag to control if it has been found the ClienteID
    	AlocIndex = -1;
    	
    	EquipLabelValue.setText(""); // Label that contains que value when calculated
    	
    	// Clears the Panel    	
    	AlocChoiceBoxAdress.setDisable(true);
    	AlocChoiceBoxAdress.setItems(FXCollections.observableArrayList());
    	auxNameList.clear();
    	if(!observableNameEquipList.isEmpty()) {
    		auxNameList.addAll(observableNameEquipList);
    		AlocChoiceBoxEquip.setItems(auxNameList);
    		AlocChoiceBoxEquip.setDisable(false);
    	} else AlocChoiceBoxEquip.setDisable(true);    	
    	AlocTextFieldID.setText("");   
    	
    	// dates
    	AlocTextFieldInitialDate.setValue(null);
    	AlocTextFieldFinalDate.setValue(null);
    	
    }
    
    //////
    // Calculation of the value based in the Date given in AlocPanel and in the Equipments added to the aloc
    /////    
    
    private double calcValue(LocalDate ini, LocalDate fin, Locacao loc) {
    	    			    	
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
    			
    	return (meses*mensal + quinzenas*quinzenal + semanas*semanal + dias*diaria);    	
    	
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
    		AlocIndex = index; // sets the global AlocIndex that will get the adresses from the cliente (if its -1 then nothing was found)
    	
    	ObservableList<Endereco> aux = FXCollections.observableArrayList();
    	
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
    
    Equipamento searchEquip(String EquipName) throws Exception{
    	    	
    	System.out.println(EquipName);
    	System.out.println(observableEquipList);
    	
    	for( int i = 0 ; i < observableEquipList.size() ; i++) {
    		if(observableEquipList.get(i).getNome().equals(EquipName)) {
    			return observableEquipList.get(i);
    		}    			
    		if(i == observableEquipList.size() - 1) {
    			/// Checking the data
    	    	Alert alert = new Alert(AlertType.ERROR);
    	    	alert.setTitle("Error Dialog");
    	    	alert.setHeaderText("ERRO NA LOCAÇÃO");
    	    	alert.setContentText("O Equipamento escolhido já está alocado");
        		alert.showAndWait();
    			throw new Exception("ID NÃO ENCONTRADO");    	
    		}
    	}
    	
    	
    	return null;
    	
    }
    
    //////
    // Adds the equipament
    /////
    
    @FXML
    void AddEquipAloc(ActionEvent event) throws Exception {
    	
    	Equipamento equip = searchEquip(AlocChoiceBoxEquip.getValue());
    	obsEquipAlocList.add(equip);
    	equip.setStatus(true);
    	
    	for( int i = 0 ; i < auxNameList.size() ; i++) {
    		if(equip.getNome().equals(auxNameList.get(i))) {
    			auxNameList.set(i, auxNameList.get(i).concat(" - Alocado"));
    			System.out.println("entrou alocado = " + auxNameList.get(i));
    			
    		}
    	}
    	//AlocChoiceBoxEquip.setItems(auxNameList);
    	
    	System.out.println(equip);
    	
    }
    
    //////
    // Removes the equipament
    /////
    
    @FXML
    void RemoveEquipAloc(ActionEvent event) throws Exception {
    	
    	if(AlocChoiceBoxEquip.getValue().contains("Alocado")){
    		String equipName = AlocChoiceBoxEquip.getValue().replace(" - Alocado", "");
    		for(int i = 0; i < obsEquipAlocList.size() ; i++) {
    			if(obsEquipAlocList.get(i).getNome().equals(equipName))
    				obsEquipAlocList.remove(i);
    		}  
    		
    		for(int i = 0; i < auxNameList.size() ; i++) {
    			if(auxNameList.get(i).equals(AlocChoiceBoxEquip.getValue()))
    				auxNameList.set(i, AlocChoiceBoxEquip.getValue().replace(" - Alocado", ""));
    		}
    	}
    	
    	else {
    		/// The element in the ChoiceBox isn't alocated yet
	    	Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("Error Dialog");
	    	alert.setHeaderText("ERRO NA LOCAÇÃO");
	    	alert.setContentText("O Equipamento escolhido não está alocado");
    		alert.showAndWait();
			throw new Exception("O Equipamento escolhido não está alocado"); 
    	}
    	
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
    	
    	System.out.println(AlocChoiceBoxAdress.getValue());
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
    
    /////
    // Edição
    ////
    
    @FXML
    void modifyClient(ActionEvent event) throws Exception{
    	
    	/// Checking the ID
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error Dialog");
    	alert.setHeaderText("DADO INVÁLIDO!");
    	
    	if(observableList.isEmpty()) {
    		alert.setContentText("Não existem clientes cadastrados.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(!ListClientTextFieldID.getText().matches("[0-9]*")) {
    		alert.setContentText("Digite um ID válido.");
    		alert.showAndWait();
    		ListClientTextFieldID.setText("");
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	int id = Integer.parseInt(ListClientTextFieldID.getText());
    	
    	for(int i = 0 ; i < observableList.size() ; i++) {
    		if(id == (observableList.get(i).getId())) {
    			// Cliente found  
    			Cliente cli = observableList.get(i);
    			listEditCliente(cli);
    			GLOBALIDMODIFY = i;
    			return;
    		} 
    		
    		// No matches
    		if(i == observableList.size() - 1) {
    			alert.setContentText("Não foi encontrado nenhum Cliente com o ID especificado.");
        		alert.showAndWait();
        		ListClientTextFieldID.setText("");
        		throw new Exception("DADOS INVÁLIDOS");
    		}
    			
    	}   	
    	
    }
    
    void listEditCliente(Cliente cli) {
    	
    	/// Update the data based on the Cliente found
    	observableAdressList.clear();
    	observableAdressList.addAll(cli.getEnderecos());
    	observableNameAdressList.clear();    	
    	for(int i = 0 ; i < cli.getEnderecos().size() ; i++) {
    		observableNameAdressList.add(cli.getEnderecos().get(i).getNome());
    	}
    	
    	if(cli.getSexo() == 'M')	M.setSelected(true);
    	else   	F.setSelected(false);
    	
    	ClientTextFieldCPF.setText(cli.getCpf());
		ClientTextFieldEmail.setText(cli.getEmail());
		ClientTextFieldName.setText(cli.getNome());
		ClientTextFieldNumber.setText(cli.getTelefone());
		ClienteChoiceBoxAdress.setDisable(false);
		ClienteChoiceBoxAdress.setItems(observableNameAdressList);
    	
		/// Disable/Enable panels
    	PanelAddClient.setVisible(true);
    	PanelAddClient.setDisable(false);
    	PanelAddEquip.setVisible(false);
    	PanelAddEquip.setDisable(true);
    	PanelAloc.setVisible(false);
    	PanelAloc.setDisable(true);
    	PanelList.setVisible(false);
    	PanelList.setDisable(true);
    	
    	/// Buttons style
    	Alocate.setStyle("-fx-background-color: #663399");
    	AddClient.setStyle("-fx-background-color: #530D53");
    	AddEquip.setStyle("-fx-background-color: #663399");
    	List.setStyle("-fx-background-color: #663399");
    	
    	ClientNewClient.setText("Modificar Cliente");
    	
    	System.out.println("Client Panel");
    }
    
    @FXML
    void modifyEquip(ActionEvent event) throws Exception{
    	
    	/// Checking the ID
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error Dialog");
    	alert.setHeaderText("DADO INVÁLIDO!");
    	
    	if(observableEquipList.isEmpty()) {
    		alert.setContentText("Não existem equipamentos cadastrados.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(!ListEquipTextFieldID.getText().matches("[0-9]*")) {
    		alert.setContentText("Digite um ID válido.");
    		alert.showAndWait();
    		ListEquipTextFieldID.setText("");
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	int id = Integer.parseInt(ListEquipTextFieldID.getText());
    	
    	for(int i = 0 ; i < observableEquipList.size() ; i++) {
    		if(id == (observableEquipList.get(i).getId())) {
    			// Equip found  
    			Equipamento equip = observableEquipList.get(i);
    			listEditEquip(equip);
    			GLOBALIDMODIFY = i;
    			return;
    		} 
    		
    		// No matches
    		if(i == observableEquipList.size() - 1) {
    			alert.setContentText("Não foi encontrado nenhum Equipamento com o ID especificado.");
        		alert.showAndWait();
        		ListEquipTextFieldID.setText("");
        		throw new Exception("DADOS INVÁLIDOS");
    		}
    			
    	}
    	    	
    }
    
    void listEditEquip(Equipamento equip) {
    	
		EquipTextFieldName.setText(equip.getNome());
    	EquipTextFieldDescription.setText(equip.getDescricao());
		EquipTextFieldDiaria.setText(String.valueOf(equip.getDiaria()));
		EquipTextFieldSemanal.setText(String.valueOf(equip.getSemanal()));
		EquipTextFieldQuinzenal.setText(String.valueOf(equip.getQuinzenal()));
		EquipTextFieldMensal.setText(String.valueOf(equip.getMensal()));
    	
		/// Disable/Enable panels
    	PanelAddClient.setVisible(false);
    	PanelAddClient.setDisable(true);
    	PanelAddEquip.setVisible(true);
    	PanelAddEquip.setDisable(false);
    	PanelAloc.setVisible(false);
    	PanelAloc.setDisable(true);
    	PanelList.setVisible(false);
    	PanelList.setDisable(true);
    	
    	/// Buttons style
    	Alocate.setStyle("-fx-background-color: #663399");
    	AddClient.setStyle("-fx-background-color: #663399");
    	AddEquip.setStyle("-fx-background-color: #530D53");
    	List.setStyle("-fx-background-color: #663399");
    	
    	EquipNewEquip.setText("Modificar Equipamento");
    	
    	System.out.println("Equip Edit Panel");
    }
    
    @FXML
    void modifyAloc(ActionEvent event) throws Exception{

    	/// Checking the ID
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error Dialog");
    	alert.setHeaderText("DADO INVÁLIDO!");
    	
    	if(observableAlocList.isEmpty()) {
    		alert.setContentText("Não existem locações cadastradas.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	if(!ListAlocTextFieldID.getText().matches("[0-9]*")) {
    		alert.setContentText("Digite um ID válido.");
    		alert.showAndWait();
    		ListAlocTextFieldID.setText("");
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	// Getting the ID
    	int id = Integer.parseInt(ListAlocTextFieldID.getText());
    	
    	// Searching for the Locacao with the ID selected
    	for(int i = 0 ; i < observableAlocList.size() ; i++) {
    		if(id == (observableAlocList.get(i).getId())) {
    			// Aloc found  
    			Locacao aloc = observableAlocList.get(i);
    			listEditAloc(aloc);
    			GLOBALIDMODIFY = i;
    			return;
    		} 
    		
    		// No matches
    		if(i == observableAlocList.size() - 1) {
    			alert.setContentText("Não foi encontrado nenhuma Locacao com o ID especificado.");
        		alert.showAndWait();
        		ListAlocTextFieldID.setText("");
        		throw new Exception("DADOS INVÁLIDOS");
    		}
    			
    	}   	
    	
    }
    
    void searchAlocIndex(int id) {
    	for(int index = 0; index< observableList.size(); index++) {
    		if(observableList.get(index).getId() == id) {
    			AlocIndex = index;
    			return;
    		}
    	}
    }
    
    void listEditAloc(Locacao aloc) {    	

    	// Clears and get the info based on the aloc
    	searchAlocIndex(aloc.getCliente().getId());
    	observableAdressList.clear();
    	observableAdressList.addAll(aloc.getEndereco());
    	observableNameAdressList.clear();
    	observableNameAdressList.add(aloc.getEndereco().getNome());    	
    	obsEquipAlocList.clear();
    	obsEquipAlocList.addAll(aloc.getEquipamentos());
    	auxNameList.clear();
    	AlocTextFieldInitialDate.setValue(aloc.getData_inicio());
    	AlocTextFieldFinalDate.setValue(aloc.getData_final());
    	
    	
    	
    	// Checa se um equipamento foi alocado baseado na observableEquipList (que contém todos os equips)
    	// Se foi alocado, concatena (" - Alocado") a lista do ChoiceBox que contém somente os nomes dos equipamentos
    	for(int i = 0; i < observableEquipList.size(); i++) {
    		for(int j = 0; j < obsEquipAlocList.size(); j++) {
    			if(observableEquipList.get(i).equals(obsEquipAlocList.get(j))) {
    				auxNameList.add(observableEquipList.get(i).getNome().concat(" - Alocado"));
    				break;
    			}
    			if(j == obsEquipAlocList.size() - 1)
    				auxNameList.add(observableEquipList.get(i).getNome());    				
    		}
    	}
    	
    	//System.out.println("observableEquipList:");
    	//System.out.println(observableEquipList);
    	//System.out.println("obsEquipAlocList:");
    	//System.out.println(obsEquipAlocList);
    	//System.out.println("auxNameList:");
    	//System.out.println(auxNameList);
    	
    	AlocTextFieldID.setText(String.valueOf(aloc.getCliente().getId()));
		AlocChoiceBoxAdress.setItems(observableNameAdressList);
		AlocChoiceBoxAdress.setDisable(false);
		AlocTextFieldInitialDate.setValue(aloc.getData_inicio());
		AlocTextFieldFinalDate.setValue(aloc.getData_final());
		AlocChoiceBoxEquip.setItems(auxNameList);
		AlocChoiceBoxEquip.setDisable(false);
		
		/// Disable/Enable panels
    	PanelAddClient.setVisible(false);
    	PanelAddClient.setDisable(true);
    	PanelAddEquip.setVisible(false);
    	PanelAddEquip.setDisable(true);
    	PanelAloc.setVisible(true);
    	PanelAloc.setDisable(false);
    	PanelList.setVisible(false);
    	PanelList.setDisable(true);
    	
    	/// Buttons style
    	Alocate.setStyle("-fx-background-color: #530D53");
    	AddClient.setStyle("-fx-background-color: #663399");
    	AddEquip.setStyle("-fx-background-color: #663399");
    	List.setStyle("-fx-background-color: #663399");
    	
    	AlocNewAloc.setText("Modificar Alocação");
    	    	
    	System.out.println("Equip Edit Panel");
    }
    
    @FXML
    void deleteClient(ActionEvent event) throws Exception{
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText("Excluir Cliente");
    	alert.setContentText("Você tem certeza disso?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    	    // ... user chose OK
    		try {
    			delCli();
    			ClientTableView.setItems(observableList);
    		} catch(DbException e) {
    			Alert alertException = new Alert(AlertType.ERROR);
    			alertException.setTitle("Error Dialog");
    			alertException.setHeaderText("Excluir Cliente");
    			alertException.setContentText("Não é possivel excluir esse cliente pois ele está relacionado a uma "
    					+ "locação.\n"
    					+ "Caso deseje continua, você deve excluir a alocação relacionada a esse cliente primeiro.");
    			alertException.show();
    		}
    	} else {
    	    // ... user chose CANCEL or closed the dialog
    	}
    	
    }
    
    void delCli() throws Exception{
    	
    	/// Checking the ID
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error Dialog");
    	alert.setHeaderText("DADO INVÁLIDO!");
    	
    	if(observableList.isEmpty()) {
    		alert.setContentText("Não existem clientes cadastrados.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	for(int i = 0; i < observableList.size(); i++) {
    		if(observableList.get(i).getId() == Integer.parseInt(ListClientTextFieldID.getText())) {
    			clienteService.remove(observableList.get(i));
    			observableList.remove(i);
    			return;
    		}
    		
    		if(i == observableList.size() - 1) {
    			alert.setContentText("Não foi encontrado nenhum Cliente com o ID especificado.");
        		alert.showAndWait();
        		ListClientTextFieldID.setText("");
        		throw new Exception("DADOS INVÁLIDOS");
    		}
    	}
    }
    
    @FXML
    void deleteEquip(ActionEvent event) throws Exception{
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText("Excluir Equipamento");
    	alert.setContentText("Você tem certeza disso?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    	    // ... user chose OK
	    	try {
	    		delEquip();
	    		EquipTableView.setItems(observableEquipList);
	    	} catch(DbException e) {
	    		Alert alertException = new Alert(AlertType.ERROR);
	    		alertException.setTitle("Error Dialog");
	    		alertException.setHeaderText("Excluir Equipamento");
	    		alertException.setContentText("Não é possivel excluir, pois esse equipamento está "
	    				+ "relacionado a uma locação\n"
	    				+ "Caso queira continuar, apague primeiro a locação a a qual o equipamento está relacionado");
	    		alertException.show();
	    	}
    	} else {
    	    // ... user chose CANCEL or closed the dialog
    	}
    	
    }
    
    void delEquip() throws Exception{
    	/// Checking the ID
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error Dialog");
    	alert.setHeaderText("DADO INVÁLIDO!");
    	
    	if(observableEquipList.isEmpty()) {
    		alert.setContentText("Não existem equipamentos cadastrados.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	for(int i = 0; i < observableEquipList.size(); i++) {
    		if(observableEquipList.get(i).getId() == Integer.parseInt(ListEquipTextFieldID.getText())) {
    			equipamentoService.remove(observableEquipList.get(i));
    			observableEquipList.remove(i);
    			return;
    		}
    		
    		if(i == observableEquipList.size() - 1) {
    			alert.setContentText("Não foi encontrado nenhum Equipamento com o ID especificado.");
        		alert.showAndWait();
        		ListEquipTextFieldID.setText("");
        		throw new Exception("DADOS INVÁLIDOS");
    		}
    	}
    }
    
    @FXML
    void deleteAloc(ActionEvent event) throws Exception{
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText("Excluir Locação");
    	alert.setContentText("Você tem certeza disso?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    	    // ... user chose OK
    		delAloc();
    		AlocTableView.setItems(observableAlocList);
    	} else {
    	    // ... user chose CANCEL or closed the dialog
    	}
    	
    }
    
    void delAloc() throws Exception{
    	/// Checking the ID
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error Dialog");
    	alert.setHeaderText("DADO INVÁLIDO!");
    	
    	if(observableList.isEmpty()) {
    		alert.setContentText("Não existem locações cadastradas.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	for(int i = 0; i < observableAlocList.size(); i++) {
    		if(observableAlocList.get(i).getId() == Integer.parseInt(ListAlocTextFieldID.getText())) {
    			locacaoService.remove(observableAlocList.get(i));
    			observableAlocList.remove(i);
    			return;
    		}
    		
    		if(i == observableAlocList.size() - 1) {
    			alert.setContentText("Não foi encontrado nenhuma Locação com o ID especificado.");
        		alert.showAndWait();
        		ListAlocTextFieldID.setText("");
        		throw new Exception("DADOS INVÁLIDOS");
    		}
    	}
    }
    
    /////
    // Gerar PDF
    /////
    
    @FXML
    void generatePDF(ActionEvent event) throws Exception{
    	
    	Locacao aloc;
    	
    	/// Checking the ID
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error Dialog");
    	alert.setHeaderText("DADO INVÁLIDO!");
    	
    	if(observableList.isEmpty()) {
    		alert.setContentText("Não existem locações cadastradas.");
    		alert.showAndWait();
    		throw new Exception("DADOS INVÁLIDOS");
    	}
    	
    	for(int i = 0; i < observableAlocList.size(); i++) {
    		if(observableAlocList.get(i).getId() == Integer.parseInt(ListAlocTextFieldIDPDF.getText())) {
    			aloc = observableAlocList.get(i); 
    			break;
    		}
    		
    		if(i == observableAlocList.size() - 1) {
    			alert.setContentText("Não foi encontrado nenhuma Locação com o ID especificado.");
        		alert.showAndWait();
        		ListAlocTextFieldID.setText("");
        		throw new Exception("CLIENTE NÃO ENCONTRADO");
    		}
    	}
    	
    	// aloc contém a locação selecionada com todos os valores
    	
    	
    }
    
  
    
    
}