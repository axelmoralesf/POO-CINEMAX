package com.example.cine;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class Conjuro implements Initializable {

//Variable de BD
    Connection cnx=null;
    String Base="ba6lsahuwfzy9sxmmsyu";
    String srv="ba6lsahuwfzy9sxmmsyu-mysql.services.clever-cloud.com";
    String usr="uyfjzr04djov72zc";
    String pass="sKZ6IUuWdAIZIVj1VA8t";
    String cxnString="jdbc:mysql://"+srv+"/"+Base+"?useSSL=false";
//Variables Normales
    ArrayList<Integer> id = new ArrayList<Integer>(); 
    ArrayList<Double> Costo = new ArrayList<Double>();
    ArrayList<String> Horario = new ArrayList<String>();
    ArrayList<Integer> Asientos = new ArrayList<Integer>();
    ArrayList<Integer> Boletos = new ArrayList<Integer>();
    int AsientosT; 
    int AsientosTT;
    int idR;
    Double CostoT;

    @FXML
    private ComboBox <String> comBoleto;

    @FXML
    private TextField txtTotal;

    @FXML
    private ListView<String> listaHorario;

    @FXML
    private ListView<String> listaOrden;

    @FXML
    private void botonCancelar_action() throws IOException{
        listaOrden.getItems().clear();
        txtTotal.clear();
        comBoleto.getItems().clear();
        comBoleto.getItems().add("Escoja un horario");
    }
    @FXML
    private void botonAgregar_action() throws IOException{
        if(comBoleto.getSelectionModel().getSelectedItem()=="Escoja un horario"){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Esa no es una cantidad de boletos valida");
            alert.showAndWait(); 
        }
        if(comBoleto.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Esa no es una cantidad de boletos valida");
            alert.showAndWait(); 
        }
        if(comBoleto.getSelectionModel().getSelectedItem()!="Escoja un horario"&&comBoleto.getSelectionModel().getSelectedItem()!=null){
            listaOrden.getItems().add(comBoleto.getSelectionModel().getSelectedItem()+" Boletos a las "+listaHorario.getSelectionModel().getSelectedItem());
            AsientosT=Integer.parseInt(comBoleto.getSelectionModel().getSelectedItem());
            CostoT=(double)AsientosT*Costo.get(listaHorario.getSelectionModel().getSelectedIndex());
            txtTotal.setText("$"+CostoT);
            AsientosTT=Asientos.get(listaHorario.getSelectionModel().getSelectedIndex())-AsientosT;
            idR=listaHorario.getSelectionModel().getSelectedIndex()+1;
        }
    }

    @FXML
    private void botonAceptar_action() throws IOException{
            if(listaOrden.getItems().isEmpty()){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No a seleccionado nada");
                alert.showAndWait();
            }
            else{
                Eliminar();
                id.clear();
                Asientos.clear();
                Costo.clear();
                Horario.clear();
                comBoleto.getItems().clear();
                listaOrden.getItems().clear();
                txtTotal.clear();
                Leer();
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Confirmado");
                alert.setHeaderText(null);
                alert.setContentText("Se confirmo la compra");
                alert.showAndWait();
            }
    }
    @FXML //Este boton regresa a la pagina anterior
    private void botonRegresar_action() throws IOException{
        App.setRoot("primary");
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Conectarse();
        Leer();
        comBoleto.getItems().add("Escoja un horario");
        listaHorario.getItems().addAll(Horario);
        listaHorario.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                comBoleto.getItems().clear();
                int i=Asientos.get(listaHorario.getSelectionModel().getSelectedIndex());
                for(int j=0;j<=i;j++){
                    comBoleto.getItems().add(""+j);
                }
            }
        });
    }

    //Establece Conexion con BD
    public void Conectarse(){

        String msg="";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            cnx=DriverManager.getConnection(cxnString, usr, pass);
        }
        catch(Exception ex){
            msg="Error";
            //COmprobar exito
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informacion");
            alert.setHeaderText(null);
            alert.setContentText(msg);
            alert.showAndWait();
        }
    }
    //Leer Datos de BD
    public void Leer(){
        try{
            String query ="SELECT * FROM ba6lsahuwfzy9sxmmsyu.`pelicula`;";
            Statement cmd=cnx.createStatement();
            ResultSet Lugares=cmd.executeQuery(query);
            while(Lugares.next()){
                id.add(Lugares.getInt("id"));
                Asientos.add(Lugares.getInt("Asientos"));
                Costo.add(Lugares.getDouble("Precio"));
                Horario.add(Lugares.getString("Hora"));
            }
            Lugares.close();
            cmd.close();
            cnx.close();
            System.out.println(""+id+Asientos+Costo+Horario);
        } catch(Exception ex){

        }
    }
    //Eliminar de BD
    public void Eliminar(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            cnx=DriverManager.getConnection(cxnString, usr, pass);
            PreparedStatement stmt;
            stmt = cnx.prepareStatement("UPDATE `ba6lsahuwfzy9sxmmsyu`.`pelicula` SET `Asientos` = '"+AsientosTT+"' WHERE (`id` = '"+idR+"');");
            int retorno = stmt.executeUpdate();
        } catch(Exception ex){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error el eliminar");
            alert.showAndWait();
        }
    }
}