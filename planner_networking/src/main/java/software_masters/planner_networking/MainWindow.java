package software_masters.planner_networking;

import java.lang.ref.Cleaner.Cleanable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MainWindow extends Application
{
	
	private Stage serverStage;
	private Stage loginStage;
	private Stage templateStage;
	private Stage editStage;
	
	
	private TextField ipFiled ;
	private TextField serverNameField;
	
	private TextField usernameField;
	private TextField passwordField;
	private Registry registry;
	
	private  ArrayList<Button> yearButtons;
	
	private Client client;
	private Server server;
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		// TODO Auto-generated method stub
//		setLoginStage();
//		loginStage.show();
//		setServerStage();
//		serverStage.show();
//		setTemplateStage();
//		templateStage.show();
	}
	void editStage()
	{
		
	}
	void setServerStage()
	{
		String title = "Connect to Server";
		String label1 = "IP";
		String label2 = "Server";
		String buttonLabel = "Connect";
		serverStage = new Stage();
		serverStage.setTitle(title);
		
		ipFiled = new TextField();
		serverNameField = new TextField();
		
		Label l1 = new Label(label1);
		l1.setMinWidth(40);
		l1.setTextAlignment(TextAlignment.LEFT);
		Label l2 = new Label(label2);
		l2.setMinWidth(40);
		
		HBox box1 = new HBox(l1,ipFiled);
		HBox box2 = new HBox(l2, serverNameField);
		
		HBox.setMargin(l1, new Insets(10));
		HBox.setMargin(l2, new Insets(10));
		HBox.setMargin(ipFiled, new Insets(10));
		HBox.setMargin(serverNameField, new Insets(10));
		
		Button button = new Button(buttonLabel);
		button.setOnAction(e->connect());
		VBox box = new VBox();
		box.getChildren().addAll(box1,box2);
		BorderPane pane = new BorderPane(box);
		BorderPane subpane = new BorderPane(button);
		pane.setBottom(subpane);
	
		
		BorderPane.setMargin(box, new Insets(20));
		BorderPane.setMargin(subpane, new Insets(20));
		
		Scene scene = new Scene(pane);
		serverStage.setScene(scene);	
	}
	
	private void connect()
	{
		try
		{
			registry = LocateRegistry.getRegistry(ipFiled.getText());
			server = (Server) registry.lookup(serverNameField.getText());
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	void setLoginStage()
	{
		String title = "Login";
		String label1 = "Username";
		String label2 = "Password";
		String buttonLabel = "Login";
		loginStage = new Stage();
		loginStage.setTitle(title);
		
		usernameField = new TextField();
		passwordField = new TextField();
		
		Label l1 = new Label(label1);
		l1.setMinWidth(50);
		Label l2 = new Label(label2);
		l2.setMinWidth(50);
		
		HBox box1 = new HBox(l1,usernameField);
		HBox box2 = new HBox(l2, passwordField);
		
		HBox.setMargin(l1, new Insets(10));
		HBox.setMargin(l2, new Insets(10));
		HBox.setMargin(usernameField, new Insets(10));
		HBox.setMargin(passwordField, new Insets(10));
		
		Button button = new Button(buttonLabel);
		button.setOnAction(e->login());
		VBox box = new VBox();
		box.getChildren().addAll(box1,box2);
		VBox.setMargin(box1, new Insets(10));
		VBox.setMargin(box2, new Insets(10));
		BorderPane pane = new BorderPane(box);
		BorderPane subpane = new BorderPane(button);
		pane.setBottom(subpane);
		
		BorderPane.setMargin(box, new Insets(20));
		BorderPane.setMargin(subpane, new Insets(20));
		
	
		Scene scene = new Scene(pane);
		loginStage.setScene(scene);
	}
	
	void login()
	{
		try
		{
			client = new Client(server);
			client.login(usernameField.getText(), passwordField.getText());
		} catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void setTemplateStage()
	{
		templateStage = new Stage();
		Label chooseLabel = new Label("Choose a plane");
		//ArrayList<String> years = client.getAllYears();
		/**
		 * Note, we use a surrogate here to see the effect of the GUI
		 */
		 
		ArrayList<String> years = new ArrayList<>();
		for (int i = 0; i <10; i++)
		{
			Integer interger = i+2000;
			years.add(interger.toString());
		}
		yearButtons = new ArrayList<Button>();
		FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL);
		for (String string : years)
		{
			Button button = new Button(string);
			FlowPane.setMargin(button, new Insets(20));
			yearButtons.add(button);
		}
		
		flowPane.getChildren().addAll(yearButtons);
		
		BorderPane pane = new BorderPane();
		pane.setTop(chooseLabel);
		pane.setCenter(flowPane);
		
		BorderPane.setMargin(chooseLabel, new Insets(0, 30, 0, 150));
		Scene scene = new Scene(pane);
		templateStage.setScene(scene);
		
	}


}
