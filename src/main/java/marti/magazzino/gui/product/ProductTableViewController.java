package marti.magazzino.gui.product;

import javafx.collections.FXCollections;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import marti.magazzino.database.GeneralProductRepository;
import marti.magazzino.database.ProductRepository;
import marti.magazzino.database.Repository;
import marti.magazzino.models.GeneralProduct;
import marti.magazzino.models.Product;


public class ProductTableViewController implements Initializable {

    //configure the table
    @FXML
    private TableView<Product> tableView;

    @FXML
    private TableColumn<Product, String> productCodeColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, Double> salePriceColumn;

    //These instance variables are used to create new Item objects
    @FXML
    private TextField quantityTextField;
    @FXML
    private TextField salePriceTextField;

    @FXML
    private Button detailedProductViewButton;

    @FXML
    private ComboBox comboBox;

    private ProductRepository repository;

    public ProductTableViewController() {
        if (null != Repository.repository) {
            this.repository = new ProductRepository(Repository.repository);
        }
    }

    /**
     * This method will allow the user to double click on a cell and update
     * the product code of the product
     */
    public void changeProductCodeCellEvent(CellEditEvent edittedCell) {
        Product productSelected = tableView.getSelectionModel().getSelectedItem();
        productSelected.setProductCode(edittedCell.getNewValue().toString());
    }

    /**
     * This method will enable the detailed view button once a row in the table is
     * selected
     */
    public void userClickedOnTable() {
        this.detailedProductViewButton.setDisable(false);
    }


    /**
     * This method will allow the user to double click on a cell and update
     * the quantity of the product
     */
    public void changeQuantityCellEvent(CellEditEvent edittedCell) {
        Product productSelected = tableView.getSelectionModel().getSelectedItem();
        productSelected.setQuantity((Integer) edittedCell.getNewValue());
    }

    /**
     * This method will allow the user to double click on a cell and update
     * the sale price of the product
     */
    public void changeSalePriceCellEvent(CellEditEvent edittedCell) {
        Product productSelected = tableView.getSelectionModel().getSelectedItem();
        productSelected.setSalePrice((Double) edittedCell.getNewValue());
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
    public void changeSceneToDetailedProductView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/ProductView.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        //access the controller and call a method
        Product product = tableView.getSelectionModel().getSelectedItem();
        GeneralProduct generalProduct = new GeneralProductRepository().find(product.getProductCode());

        ProductViewController controller = loader.getController();
        controller.initData(product, generalProduct);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void comboBoxProductCodeWasUpdated() {
        System.out.println("SELECTED: " + comboBox.getValue().toString());
    }

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        //set up the columns in the table
        productCodeColumn.setCellValueFactory(new PropertyValueFactory<Product, String>(Product.FIELD_PRODUCT_CODE));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>(Product.FIELD_QUANTITY));
        salePriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>(Product.FIELD_SALE_PRICE));

        //load dummy data
        tableView.setItems(this.getProductList());

        //Update the table to allow for the product code and quantity fields
        //to be editable
        tableView.setEditable(true);

        //This will allow the table to select multiple rows at once
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Disable the detailed product view button until a row is selected
        this.detailedProductViewButton.setDisable(true);

        //INIT COMBO BOX
        comboBox.getItems().addAll(this.getGeneralProductsId());
        comboBox.setVisibleRowCount(3);
        comboBox.setEditable(true);
        comboBox.setPromptText("Product Id");
    }

    /**
     * This method will remove the selected row(s) from the table
     */
    public void deleteButtonPushed() {
        ObservableList<Product> selectedRows;

        //this gives us the rows that were selected
        selectedRows = tableView.getSelectionModel().getSelectedItems();

        //loop over the selected rows and remove the Item objects from the table
        for (Product product : selectedRows) {
            repository.delete(product);

        }

        tableView.setItems(this.getProductList());
    }

    /**
     * This method will create a new Product object and add it to the table
     */
    public void newProductButtonPushed() {
        Product newProduct = new Product(comboBox.getValue().toString(),
                Integer.parseInt(quantityTextField.getText()),
                Double.parseDouble(salePriceTextField.getText()));

        //Get all the items from the table as a list, then add the new product to
        //the list

        Boolean res = repository.update(newProduct);
        System.out.println(res.toString());

        repository.insert(newProduct);
        this.resetFields();
        tableView.setItems(this.getProductList());

    }

    /**
     * This method will return an ObservableList of Products objects
     */
    public ObservableList<Product> getProductList() {
        ObservableList<Product> products = FXCollections.observableArrayList();

        products.addAll(repository.getProductList());

        return products;
    }

    public void resetFields() {
        comboBox.setValue("");
        quantityTextField.setText("");
        salePriceTextField.setText("");
    }

    public List<String> getGeneralProductsId() {
        List<String> productIdList = new ArrayList<String>();

        List<GeneralProduct> generalProducts = new GeneralProductRepository().getGeneralProductList();
        for (GeneralProduct p : generalProducts) {
            productIdList.add(p.getGeneralProductCode());
        }

        return productIdList;
    }
}
