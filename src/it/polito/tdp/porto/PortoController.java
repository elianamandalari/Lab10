package it.polito.tdp.porto;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Paper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;
    Model model;
    @FXML
    void handleCoautori(ActionEvent event) {
     
     if(model.displayNeighbours(boxPrimo.getValue())!=null){
    	 txtResult.appendText("L'autore " +boxPrimo.getValue()+" ha scritto almeno un articolo con i seguenti coautori : \n");
    	for(Author a:model.displayNeighbours(boxPrimo.getValue()))
            txtResult.appendText(""+a.getId()+" "+a.getFirstname()+" "+a.getLastname()+"\n");
     }
     boxSecondo.getItems().clear();
     boxSecondo.getItems().addAll(model.getNonCoautore(boxPrimo.getValue()));
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	for(Paper a:model.getListaArticoli(boxPrimo.getValue(), boxSecondo.getValue()))
    	{
    	 if(model.getArticolo(a.getEprintid())!=null){
    		Paper b=model.getArticolo(a.getEprintid());
    	 txtResult.appendText(""+b.getEprintid()+" "+b.getTitle()+"\n");
    	 }
    }

    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";
        

    }

	public void setModel(Model model) {
		this.model=model;
		boxPrimo.getItems().addAll(model.getAutori());
		
		
	}
}
