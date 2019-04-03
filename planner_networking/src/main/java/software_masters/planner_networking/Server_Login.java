package software_masters.planner_networking;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Server_Login
{

	String label1;
	String label2;
	String buttonLabel;
	String title;
	Stage stage;
	
	public Server_Login(String label1, String label2, String buttonLabel, String title)
	{
		super();
		this.label1 = label1;
		this.label2 = label2;
		this.buttonLabel = buttonLabel;
		this.title = title;
	}


	public void show()
	{
		stage = new Stage();
		stage.setTitle(title);
		stage.setMinHeight(200);
		stage.setMinWidth(400);
		
		TextField field1 = new TextField();
		TextField field2 = new TextField();
		
		Label l1 = new Label(label1);
		l1.setMinWidth(40);
		Label l2 = new Label(label2);
		l2.setMinWidth(40);
		
		HBox box1 = new HBox(l1,field1);
		HBox box2 = new HBox(l2, field2);
		
		Button button = new Button(buttonLabel);
		VBox box = new VBox();
		box.getChildren().addAll(box1,box2);
		BorderPane pane = new BorderPane(box);
		BorderPane subpane = new BorderPane(button);
		pane.setBottom(subpane);
	
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}
}
