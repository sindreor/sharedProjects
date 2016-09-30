package application;

import java.beans.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class Controller implements Initializable {

	@FXML
	AnchorPane main;
	@FXML
	Button btnB;
	@FXML
	Label labA;
	@FXML
	Polygon f2;
	@FXML
	Rectangle f1;
	@FXML
	Polygon b1;
	@FXML
	Polygon v1;
	@FXML
	Polygon h1;
	@FXML
	Rectangle v2;
	@FXML
	Rectangle h2;
	@FXML
	Rectangle v4;
	@FXML
	Arc v3;
	@FXML
	Arc h3;
	TestKlient k;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		main.setOnKeyPressed(new javafx.event.EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke){
				if(ke.getText().equals("d")){
					k=new TestKlient("192.168.0.112");
					k.send("h\r\n");
					k.close();
					
					
					
					
					h2.setVisible(true);
					h1.setVisible(true);
					h3.setVisible(true);
					v4.setVisible(true);
					
				}
				if(ke.getText().equals("a")){
					k=new TestKlient("192.168.0.112");
					k.send("v\r\n");
					k.close();
					
					
					
					v2.setVisible(true);
					v1.setVisible(true);
					v3.setVisible(true);
					v4.setVisible(true);
				}
				if(ke.getText().equals("w")){
					k=new TestKlient("192.168.0.112");
					k.send("f\r\n");
					k.close();
					
					f1.setVisible(true);
					f2.setVisible(true);
				}
				if(ke.getText().equals("s")){
					k=new TestKlient("192.168.0.112");
					k.send("b\r\n");
					k.close();
					
					f1.setVisible(true);
					b1.setVisible(true);
				}
			}
		});
		
		main.setOnKeyReleased(new javafx.event.EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke){
				if(ke.getText().equals("d")){
					k=new TestKlient("192.168.0.112");
					k.send("d\r\n");
					k.close();
					
					h2.setVisible(false);
					h1.setVisible(false);
					h3.setVisible(false);
					v4.setVisible(false);
					
					
				}
				if(ke.getText().equals("a")){
					k=new TestKlient("192.168.0.112");
					k.send("a\r\n");
					k.close();
					
					v2.setVisible(false);
					v1.setVisible(false);
					v3.setVisible(false);
					v4.setVisible(false);
					
					
				}
				if(ke.getText().equals("w")){
					k=new TestKlient("192.168.0.112");
					k.send("w\r\n");
					k.close();
					
					f1.setVisible(false);
					f2.setVisible(false);
				}
				if(ke.getText().equals("s")){
					k=new TestKlient("192.168.0.112");
					k.send("s\r\n");
					k.close();
					
					f1.setVisible(false);
					b1.setVisible(false);
				}
			}
		});
		
		
		
	}
	
	

}
