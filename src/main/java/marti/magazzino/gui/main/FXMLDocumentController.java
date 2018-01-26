package marti.magazzino.gui.main;

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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import marti.magazzino.database.Repository;


public class FXMLDocumentController implements Initializable {


    //These items are for the RadioButton
    @FXML private RadioButton warehouseButton;
    @FXML private RadioButton barButton;
    @FXML private RadioButton generalProductListButton;

    @FXML private Label radioButtonLabel;
    private ToggleGroup favLangToggleGroup;




    /**
     * When this method is called, it will change the Scene to
     * a TableView example
     */

    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
        String option = this.radioButtonLabel.getText();
        Repository.repository = option;
        String layout = "/fxml/ProductTableView.fxml";

        if(option.equals(Repository.REPO_GENERAL)){
            layout = "/fxml/GeneralProductTableView.fxml";
        }

        Parent tableViewParent = FXMLLoader.load(getClass().getResource(layout));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle(option);
        window.setScene(tableViewScene);
        window.show();
    }


    /**
     * This method will update the radioButtonLabel when ever a different
     * radio button is pushed
     */
    public void radioButtonChanged() {

        if (this.favLangToggleGroup.getSelectedToggle().equals(this.warehouseButton))
            radioButtonLabel.setText(Repository.REPO_WAREHOUSE);

        if (this.favLangToggleGroup.getSelectedToggle().equals(this.barButton))
            radioButtonLabel.setText(Repository.REPO_BAR);

        if (this.favLangToggleGroup.getSelectedToggle().equals(this.generalProductListButton))
            radioButtonLabel.setText(Repository.REPO_GENERAL);

    }



    public void initialize(URL url, ResourceBundle rb) {

        //These items are for configuring the RadioButtons
        radioButtonLabel.setText("");
        favLangToggleGroup = new ToggleGroup();

        this.warehouseButton.setToggleGroup(favLangToggleGroup);
        this.barButton.setToggleGroup(favLangToggleGroup);
        this.generalProductListButton.setToggleGroup(favLangToggleGroup);


    }

}
