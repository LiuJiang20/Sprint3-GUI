package software_masters.planner_networking;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The Class ErrorMessage.
 * This class helps to report any error messages when make operations on the planfile
	It also handles to warn user the planfile is read-only
 */

public class ErrorMessage
{

	/** The erro message. */
	String erroMessage;
	
	/** The title. */
	String title = "Error message";
	
	/** The stage. */
	Stage stage;
	
	/**
	 * Instantiates a new error message.
	 *
	 * @param erroMessage the error message
	 */
	public ErrorMessage(String erroMessage)
	{
		this.erroMessage = erroMessage;
	}

	/**
	 * Show.
	 */
	public void show()
	{
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		//stage.setTitle(title);
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
