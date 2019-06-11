package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.entities.*;

public class MainViewController implements Initializable {

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
    private ChoiceBox<?> ClienteChoiceBoxAdress;

    @FXML
    private Button ClientNewAdress;

    @FXML
    private Button ClientNewClient;

    @FXML
    private Button NewEquip;

    @FXML
    private TextField EquipTextFieldDiaria;

    @FXML
    private TextField EquipTextFieldSemanal;

    @FXML
    private TextField EquipTextFieldQuinzenal;

    @FXML
    private TextField EquipTextFieldMensal;

    @FXML
    private TextField EquipTextFieldName;

    @FXML
    private DatePicker AlocTextFieldInitialDate;

    @FXML
    private DatePicker AlocTextFieldFinalDate;

    @FXML
    private TextField AlocTextFieldValue;

    @FXML
    private TextField AlocTextFieldID;

    @FXML
    private Button NewAloc;    

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
    private TableView<Endereco> AdressTableView;
        

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
		
		Cliente a = new Cliente(1, "Wilson",'M',"wilsonjnr665@gmail.com","xxx.xxx.xxx-xx", "998106854");
		
		ObservableList<Cliente> observableList = FXCollections.observableArrayList(
				new Cliente(1, "Wilson",'M',"wilsonjnr665@gmail.com","xxx.xxx.xxx-xx", "998106854")
		);
		
		observableList.add(a);
		
		ClientTableView.setItems(observableList);
		ClientTableView.setEditable(true);
		
	}
    
	
		
	
	
    @FXML
    public void handleM(){
    	if(M.isSelected()) {
    		F.setSelected(false);
    	}
    }
    
    @FXML
    void AddClientPanel(ActionEvent event) {
    	
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
    
    @FXML
    public void handleF(){
    	if(F.isSelected()) {
    		M.setSelected(false);
    	}
    }
    
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
    
    @FXML
    void RegisterNewAdress(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/gui/RegisterAdress.fxml"));
				
		Scene scene = new Scene(root);
		
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Cadastrar Endereço");
		stage.show();	
		
		AGWPanel.setDisable(true);
		
		stage.setOnCloseRequest(event1 -> {
			AGWPanel.setDisable(false);
		});
		
    }

    

}