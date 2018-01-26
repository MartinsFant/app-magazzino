package marti.magazzino.gui.generalProduct;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GeneralProductViewController implements Initializable{
    private GeneralProduct selectedGeneralProduct;

    @FXML private Label generalProductCodeLabel;
    @FXML private Label nameLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label purchasePriceLabel;



    /**
     * This method accepts a product to initialize the view
     * @param generalProduct
     */
    public void initData(GeneralProduct generalProduct) {
        selectedGeneralProduct = generalProduct;
        generalProductCodeLabel.setText(selectedGeneralProduct.getGeneralProductCode());
        nameLabel.setText(selectedGeneralProduct.getName());
        descriptionLabel.setText(selectedGeneralProduct.getDescription());
        purchasePriceLabel.setText("€ " + String.valueOf(selectedGeneralProduct.getPurchasePrice()));
    }

    /**
     * When this method is called, it will change the Scene to
     * a TableView example
     */
    public void changeScreenPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/fxml/GeneralProductTableView.fxml"));
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
