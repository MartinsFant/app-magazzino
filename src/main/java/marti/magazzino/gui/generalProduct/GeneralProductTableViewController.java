package marti.magazzino.gui.generalProduct;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import marti.magazzino.database.GeneralProductRepository;
import marti.magazzino.models.GeneralProduct;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GeneralProductTableViewController implements Initializable {

    //configure the table
    @FXML
    private TableView<GeneralProduct> tableView;


    @FXML
    private TableColumn<GeneralProduct, String> generalProductCodeColumn;
    @FXML
    private TableColumn<GeneralProduct, String> nameColumn;
    @FXML
    private TableColumn<GeneralProduct, String> descriptionColumn;
    @FXML
    private TableColumn<GeneralProduct, Double> purchasePriceColumn;

    //These instance variables are used to create new Item objects
    @FXML
    private TextField generalProductCodeTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField purchasePriceTextField;

    @FXML
    private Button detailedGeneralProductViewButton;


    private GeneralProductRepository generalRepository;

    public GeneralProductTableViewController() {
        this.generalRepository = new GeneralProductRepository();
    }

    /**
     * This method will allow the user to double click on a cell and update
     * the product code of the general product
     */
    public void changeGeneralProductCodeCellEvent(TableColumn.CellEditEvent edittedCell) {
        GeneralProduct generalProductSelected = tableView.getSelectionModel().getSelectedItem();
        generalProductSelected.setGeneralProductCode(edittedCell.getNewValue().toString());
    }


    /**
     * This method will enable the detailed view button once a row in the table is
     * selected
     */
    public void userClickedOnTable() {
        this.detailedGeneralProductViewButton.setDisable(false);
    }


    /**
     * This method will allow the user to double click on a cell and update
     * the name of the product
     */
    public void changeNameCellEvent(TableColumn.CellEditEvent edittedCell) {
        GeneralProduct generalProductSelected = tableView.getSelectionModel().getSelectedItem();
        generalProductSelected.setName(edittedCell.getNewValue().toString());
    }

    /**
     * This method will allow the user to double click on a cell and update
     * the description of the product
     */
    public void changeDescriptionCellEvent(TableColumn.CellEditEvent edittedCell) {
        GeneralProduct generalProductSelected = tableView.getSelectionModel().getSelectedItem();
        generalProductSelected.setDescription(edittedCell.getNewValue().toString());
    }

    /**
     * This method will allow the user to double click on a cell and update
     * the sale price of the product
     */
    public void changePurchasePriceCellEvent(TableColumn.CellEditEvent edittedCell) {
        GeneralProduct generalProductSelected = tableView.getSelectionModel().getSelectedItem();
        generalProductSelected.setPurchasePrice((Double) edittedCell.getNewValue());
    }

    /**
     * When this method is called, it will change the Scene to
     * a TableView example
     */
    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/fxml/FXMLDocument.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.setTitle("Main menu");
        window.show();
    }

    /**
     * When this method is called, it will pass the selected Item object to
     * a the detailed view
     */
    public void changeSceneToDetailedGeneralProductView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/GeneralProductView.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        //access the controller and call a method
        GeneralProductViewController controller = loader.getController();
        controller.initData(tableView.getSelectionModel().getSelectedItem());

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        //set up the columns in the table
        generalProductCodeColumn.setCellValueFactory(new PropertyValueFactory<GeneralProduct, String>(GeneralProduct.FIELD_GENERAL_PRODUCT_CODE));
        nameColumn.setCellValueFactory(new PropertyValueFactory<GeneralProduct, String>(GeneralProduct.FIELD_NAME));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<GeneralProduct, String>(GeneralProduct.FIELD_DESCRIPTION));
        purchasePriceColumn.setCellValueFactory(new PropertyValueFactory<GeneralProduct, Double>(GeneralProduct.FIELD_PURCHASE_PRICE));

        //load dummy data
        tableView.setItems(this.getGeneralProductList());

        //Update the table to allow for the product code and quantity fields
        //to be editable
        tableView.setEditable(true);
//        productCodeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
//        salePriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        //This will allow the table to select multiple rows at once
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Disable the detailed product view button until a row is selected
        this.detailedGeneralProductViewButton.setDisable(true);
    }

    /**
     * This method will remove the selected row(s) from the table
     */
    public void deleteButtonPushed() {
        ObservableList<GeneralProduct> selectedRows;

        //this gives us the rows that were selected
        selectedRows = tableView.getSelectionModel().getSelectedItems();

        //loop over the selected rows and remove the Item objects from the table
        for (GeneralProduct generalProduct : selectedRows) {
            generalRepository.delete(generalProduct);
            //TODO controllo su delete (boolean)
        }

        tableView.setItems(this.getGeneralProductList());
    }

    /**
     * This method will create a new Product object and add it to the table
     */
    public void newGeneralProductButtonPushed() {
        GeneralProduct newGeneralProduct = new GeneralProduct(generalProductCodeTextField.getText(),
                nameTextField.getText(),
                descriptionTextField.getText(),
                Double.parseDouble(purchasePriceTextField.getText()));

        //Get all the items from the table as a list, then add the new product to
        //the list

        generalRepository.insert(newGeneralProduct);
        this.resetFields();
        tableView.setItems(this.getGeneralProductList());

    }

    /**
     * This method will return an ObservableList of Products objects
     */
    public ObservableList<GeneralProduct> getGeneralProductList() {
        ObservableList<GeneralProduct> products = FXCollections.observableArrayList();

        products.addAll(generalRepository.getGeneralProductList());

        return products;
    }

    public void resetFields() {
        generalProductCodeTextField.setText("");
        nameTextField.setText("");
        descriptionTextField.setText("");
        purchasePriceTextField.setText("");


    }
}
