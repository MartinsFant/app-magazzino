package marti.magazzino.gui.product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import marti.magazzino.models.GeneralProduct;
import marti.magazzino.models.Product;


public class ProductViewController implements Initializable {

    @FXML private Label productCodeLabel;
    @FXML private Label nameLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label quantityLabel;
    @FXML private Label purchasePriceLabel;
    @FXML private Label salePriceLabel;

    /**
     * This method accepts a product to initialize the view
     * @param product
     * @param generalProduct
     */
    public void initData(Product product, GeneralProduct generalProduct) {
        productCodeLabel.setText(product.getProductCode());
        nameLabel.setText(generalProduct.getName());
        descriptionLabel.setText(generalProduct.getDescription());
        quantityLabel.setText(String.valueOf(product.getQuantity()));
        purchasePriceLabel.setText("€ " + String.valueOf(generalProduct.getPurchasePrice()));
        salePriceLabel.setText("€ " + String.valueOf(product.getSalePrice()));
    }

    /**
     * When this method is called, it will change the Scene to
     * a TableView example
     */
    public void changeScreenPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/fxml/ProductTableView.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
