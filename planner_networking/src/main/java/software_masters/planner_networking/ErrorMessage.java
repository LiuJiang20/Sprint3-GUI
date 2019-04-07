package software_masters.planner_networking;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorMessage
{

	String erroMessage;
	String title = "Error message";
	Stage stage;
	
	public ErrorMessage(String erroMessage)
	{
		this.erroMessage = erroMessage;
	}

	public void show()
	{
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		stage.setMinWidth(250);
		
		Label label = new Label(erroMessage);
		Button confirmButton = new Button("OK");
		confirmButton.setOnAction(e->stage.close());
		
		BorderPane pane = new BorderPane();
		pane.setCenter(label);
		pane.setBottom(confirmButton);
		
		BorderPane.setMargin(label, new Insets(20));
		BorderPane.setMargin(confirmButton, new Insets(20));
		
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.showAndWait();
		
		
	}
	
}
