package software_masters.planner_networking;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfirmationBox.
 */

public class ConfirmationBox
{
	
	/** The confirmed. */
	public boolean confirmed;
	
	/** The stage. */
	Stage stage;
	
	/** The title. */
	String title;
	
	/** The message. */
	String message;
	
	/**
	 * Instantiates a new confirmation box.
	 *
	 * @param message the message
	 * @param title the title
	 */
	public ConfirmationBox(String message, String title )
	{
		super();
		this.title = title;
		this.message = message;
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
		
		Label warningLabel = new Label();
		warningLabel.setText("Warning");
		
		Label messagLabel = new Label(message);
		BorderPane laberPane = new BorderPane(warningLabel);
		
		Button oKButton = new Button();
		oKButton.setText("Yes");
		oKButton.setOnAction(e->{stage.close();confirmed = true;});
		Button noButton = new Button();
		noButton.setText("No");
		noButton.setOnAction(e->{stage.close();confirmed = false;});
		
		int minwidth = 100;
		oKButton.setMinWidth(minwidth);
		noButton.setMinWidth(minwidth);
		
		oKButton.setOnAction(e->okButtonAction());
		noButton.setOnAction(e->noButtonAction());
		
		Region spacer = new Region();
		HBox buttonHBox = new HBox();
		buttonHBox.getChildren().addAll(oKButton,spacer,noButton);
		for (javafx.scene.Node node : buttonHBox.getChildren())
		{
			HBox.setMargin(node, new Insets(20));
		}
		HBox.setHgrow(spacer, Priority.ALWAYS);
		
		BorderPane mainPane = new BorderPane();
		mainPane.setCenter(messagLabel);
		mainPane.setTop(laberPane);
		mainPane.setBottom(buttonHBox);
		
		Scene scene = new Scene(mainPane);
		stage.setScene(scene);
		stage.showAndWait();
	}
	
	/**
	 * Ok button action.
	 */
	protected void okButtonAction()
	{
		stage.close();
		confirmed = true;
	}
	
	/**
	 * No button action.
	 */
	protected void noButtonAction()
	{
		stage.close();
		confirmed = false;
	}
}


