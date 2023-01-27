package com.example.cine;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class PrimaryController implements Initializable {

   

    @FXML
    private Rectangle rectangulo1;

    @FXML
    private Rectangle rectangulo2;

    @FXML
    private void btn1_action() throws IOException{
        App.setRoot("Conjuro");
    }

    @FXML
    private void btn2_action() throws IOException{
        App.setRoot("Spider");
    }

    @FXML
    private void btn3_action() throws IOException{
        App.setRoot("Jhon");
    }

    @FXML
    private void btnAyuda_action() throws IOException{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Ayuda");
            alert.setHeaderText(null);
            alert.setContentText("1.- Selecione la pelicula \n 2.- Escoja su horario \n 3.- Escoja la cantidad de voletos \n 4.- Acepte \n Nota: NO HAY DEVOLUCIONES");
            alert.showAndWait();
    }

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}