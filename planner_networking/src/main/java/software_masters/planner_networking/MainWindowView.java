package software_masters.planner_networking;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainWindowView extends Application implements Observer
{
	Button saveButton;
	Button addButton;
	Button delButton;
	Button pushButton;
	Button exitButton;
	Label yearLabel;
	TextField yearField;
	
	TreeView<Node> treeView;
	TextField titleField;
	TextField contentField;
	
	Stage stage;
	Controller controller;
	Node currNode;
	boolean isEditable;
	public static void main(String[] args)
	{
		launch(args);
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		// TODO Auto-generated method stub
		stage = primaryStage;
		controller = new Controller();
		setTreeView(controller.getRootNode());
		//setTreeView(new VMOSA().getRoot());
		setTreeItemAction();
		saveButton = new Button("Save");
		addButton = new Button("Add Child");
		delButton = new Button("Delete");
		exitButton = new Button("Exit");
		pushButton = new Button("Push");
		
		yearLabel = new Label("Year");
		yearField = new TextField("2019");
		yearField.setPrefWidth(80);
		
		
		isEditable = controller.editable();
		
		if (isEditable)
		{
			saveButton.setOnAction(e->saveAction());
			addButton.setOnAction(e->addAction());
			delButton.setOnAction(e->deleteAction());
			exitButton.setOnAction(e->exitAction());
			pushButton.setOnAction(e->pushAction());
		}
		
		int minWidth = 1000;
		titleField = new TextField();
		titleField.setMinWidth(minWidth);
		titleField.setMinHeight(100);
		titleField.setFont(new Font(50));
		contentField = new TextField();
		contentField.setAlignment(Pos.TOP_LEFT);
		contentField.setMinWidth(minWidth);
		contentField.setMinHeight(minWidth);
		
		HBox buttonBox = new HBox(saveButton,addButton,delButton);
		for (int i = 0; i < buttonBox.getChildren().size(); i++)
		{
			HBox.setMargin(buttonBox.getChildren().get(i), 	new Insets(0, 10, 0, 0));
		}
		HBox yearBox = new HBox(pushButton,yearLabel,yearField,exitButton);
		HBox.setMargin(yearField, new Insets(0, 40, 0, 0));
		HBox.setMargin(yearLabel, new Insets(0, 0, 0, 10));
		
		BorderPane topBorderPane = new BorderPane();
		yearBox.setAlignment(Pos.CENTER);
		topBorderPane.setLeft(buttonBox);
		topBorderPane.setRight(yearBox);
		

		
		BorderPane editBorderPane = new BorderPane();
		editBorderPane.setTop(titleField);
		editBorderPane.setCenter(contentField);
		
		BorderPane editAndTreeViewBorderPane = new BorderPane();
		editAndTreeViewBorderPane.setCenter(editBorderPane);
		editAndTreeViewBorderPane.setLeft(treeView);
		
		BorderPane windBorderPane = new BorderPane();
		windBorderPane.setCenter(editAndTreeViewBorderPane);
		windBorderPane.setTop(topBorderPane);
		
		Scene scene = new Scene(windBorderPane);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	private void setTreeItemAction()
	{
		treeView.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue)->
		{
			checkSave();
			changeSection(newValue);}
		);
	}
		
	private void setTreeView(Node root)
	{
		treeView = new TreeView<Node>(convertTree(root));
	}
	
	private TreeItem<Node> convertTree(Node root)
	{
		TreeItem<Node> newRoot = new TreeItem<Node>(root);
		for(int i = 0; i < root.getChildren().size();i++) 
		{
			newRoot.getChildren().add(convertTree(root.getChildren().get(i)));
		}
		return newRoot;
	}

	private void saveAction()
	{
		currNode.setName(titleField.getText());
		currNode.setData(contentField.getText());
	}
	
	private void changeSection(TreeItem<Node> item)
	{
		currNode = item.getValue();
		titleField.setText(currNode.getName());
		contentField.setText(currNode.getData());
	}
	
	private void checkSave()
	{
		boolean changed = false;
		if (!titleField.getText().equals(currNode.getName()))
		{
			changed = true;
		}
		
		if (!contentField.getText().equals(currNode.getData()))
		{
			changed = true;
		}
		
		if (changed)
		{
			//ask user if he wants to save
			String title = "Warning";
			String text = "The section you are editing is not saved.\n Do you want to save it?";
			ConfirmationBox box = new ConfirmationBox(text, title);
			if (box.confirmed)
			{
				saveAction();
			}
		}
	}

	private void addAction()
	{
		String message = controller.addNode(currNode);
		if (message != null)
		{
			//Display the error message
			new ErrorMessage(message);
		}
	}
	
	private void deleteAction()
	{
		String message = controller.deleteNode(currNode);
		if (message != null)
		{
			new ErrorMessage(message);
		}
		else 
		{
			//Change the display to removed node's parent
			currNode = currNode.getParent();
			titleField.setText(currNode.getName());
			contentField.setText(currNode.getData());
			
		}
	}
	
	private void pushAction()
	{
		String message = controller.pushToServer();
		if (message != null)
		{
			new ErrorMessage(message);
		}
	}
	
	private void exitAction()
	{
		checkSave();
		stage.close();
	}


	/* (non-Javadoc)
	 * @see software_masters.planner_networking.Observer#respond(software_masters.planner_networking.Node)
	 */
	@Override
	public void respond(Node node)
	{
		setTreeView(node);
		
	}
	
	
}
