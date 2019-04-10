package software_masters.planner_networking;



import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The Class MainWindowView.
 */
public class MainWindowView extends Application implements Observer
{
	
	/** The save button. */
	Button saveButton;
	
	/** The add button. */
	Button addButton;
	
	/** The del button. */
	Button delButton;
	
	/** The push button. */
	Button pushButton;
	
	/** The exit button. */
	Button exitButton;
	
	/** The year label. */
	Label yearLabel;
	
	/** The year field. */
	TextField yearField;
	
	/** The tree view. */
	TreeView<Node> treeView;
	
	/** The title field. */
	TextField titleField;
	
	/** The content field. */
	TextField contentField;
	
	/** The edit and tree view border pane. */
	BorderPane editAndTreeViewBorderPane = new BorderPane();
	
	/** The stage. */
	Stage stage;
	
	/** The controller. */
	Controller controller;
	
	/** The curr node. */
	Node currNode;
	
	/** The is editable. */
	boolean isEditable;
	
	/** The is pushed. */
	boolean isPushed;
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args)
	{
		launch(args);
	}
	
	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception
	{ 
		/*
		 * Initialize all Buttons and set on their actions
		 * */		
		stage = primaryStage;
		isPushed = true;
		controller = new Controller();
		controller.register(this);
		//setTreeView(new VMOSA().getRoot());
		saveButton = new Button("Save");
		addButton = new Button("Add Child");
		delButton = new Button("Delete");
		exitButton = new Button("Exit");
		pushButton = new Button("Push");
		
		yearLabel = new Label("Year");
		if (controller.getYear() != null)
		{
			yearField = new TextField(controller.getYear());
		}
		else 
		{
			yearField = new TextField("2019");
		}
		
		yearField.setPrefWidth(80);
		
		
		isEditable = controller.editable();
		/*
		 * Sets on actions only if the plan is editable*/
		if(isEditable)
		{
			saveButton.setOnAction(e->saveAction());
			addButton.setOnAction(e->addAction());
			delButton.setOnAction(e->deleteAction());
			pushButton.setOnAction(e->pushAction());
			
			Tooltip saveTooltip = new Tooltip("Save the current section");
			saveButton.setTooltip(saveTooltip);
			
			Tooltip deleteTooltip = new Tooltip("Delete the section selected at tree view");
			delButton.setTooltip(deleteTooltip);
			
			Tooltip addTooltip = new Tooltip("Add a branch under current section\n if you want to add a child at same level as current section,\\ go to its parent section ");
			addButton.setTooltip(addTooltip);
			
			pushButton.setTooltip(new Tooltip("Push this file to the server"));
		
		}
		else 
		{
			yearField.setDisable(true);
		}
		exitButton.setOnAction(e->exitAction());
		Tooltip exiTooltip = new Tooltip("Exit editing window");
		exitButton.setTooltip(exiTooltip);
		
		/*
		 * Get everything laid properly*/
		int minWidth = 1000;
		titleField = new TextField();
		titleField.setMinWidth(minWidth);
		titleField.setMinHeight(100);
		titleField.setFont(new Font(50));
		contentField = new TextField();
		contentField.setAlignment(Pos.TOP_LEFT);
		contentField.setMinWidth(minWidth);
		contentField.setMinHeight(minWidth);
		
		setTreeView(controller.getRootNode());
		
		
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
		
		editAndTreeViewBorderPane.setCenter(editBorderPane);
		editAndTreeViewBorderPane.setLeft(treeView);
		
		BorderPane windBorderPane = new BorderPane();
		windBorderPane.setCenter(editAndTreeViewBorderPane);
		windBorderPane.setTop(topBorderPane);
		
		Scene scene = new Scene(windBorderPane);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		if (!isEditable)
		{
			new ErrorMessage("This file is read-only. Any modification will not work!").show();
		}
		
	}

		
	/**
	 * Sets the tree view.
	 *
	 * @param root the new tree view
	 */
	private void setTreeView(Node root)
	{
		treeView = new TreeView<Node>(convertTree(root));
		editAndTreeViewBorderPane.setLeft(treeView);
		setTreeItemAction();
		expand(treeView.getRoot());
		currNode = root;
		titleField.setText(root.getName());
		contentField.setText(root.getData());
	}
	
	/**
	 * Sets the tree item action.
	 */
	private void setTreeItemAction()
	{
		treeView.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue)->
		{
			if (isEditable)
			{
				checkSave();
			}
			changeSection(newValue);}
		);
	}
	
	/**
	 * Expand.
	 *
	 * @param root the root
	 */
	private void expand(TreeItem<Node> root)
	{
		root.setExpanded(true);
		for (TreeItem<Node> child : root.getChildren())
		{
			expand(child);
		}
	}
	
	/**
	 * Convert tree.
	 *
	 * @param root the root
	 * @return the tree item
	 */
	private TreeItem<Node> convertTree(Node root)
	{
		TreeItem<Node> newRoot = new TreeItem<Node>(root);
		for(int i = 0; i < root.getChildren().size();i++) 
		{
			newRoot.getChildren().add(convertTree(root.getChildren().get(i)));
		}
		return newRoot;
	}

	/**
	 * Save action.
	 */
	private void saveAction()
	{
		currNode.setName(titleField.getText());
		currNode.setData(contentField.getText());
		isPushed = false;
	}
	
	/**
	 * Change section.
	 *
	 * @param item the item
	 */
	private void changeSection(TreeItem<Node> item)
	{
		currNode = item.getValue();
		titleField.setText(currNode.getName());
		contentField.setText(currNode.getData());
	}
	
	/**
	 * Check save.
	 */
	private void checkSave()
	{
		boolean changed = false;
		System.out.println("CheckSave: "+currNode);
		if (titleField.getText() == null)
		{
			if (currNode.getName()!= null)
			{
				changed = true;
			}
		}
		if (!titleField.getText().equals(currNode.getName()))
		{
			changed = true;
		}
		
		if (contentField.getText() == null)
		{
			if (currNode.getData() != null)
			{
				changed = true;
			}
		}
		else if (!contentField.getText().equals(currNode.getData()))
		{
			changed = true;
		}
		
		System.out.println(changed);
		if (changed)
		{
			//ask user if he wants to save
			String title = "Warning";
			String text = "The section you are editing is not saved.\n Do you want to save it?";
			ConfirmationBox box = new ConfirmationBox(text, title);
			box.show();
			if (box.confirmed)
			{
				saveAction();
			}
		}
	}

	/**
	 * Adds the action.
	 */
	private void addAction()
	{
		String message = controller.addNode(currNode);
		if (message != null)
		{
			//Display the error message
			new ErrorMessage(message).show();;
		}
	}
	
	/**
	 * Delete action.
	 */
	private void deleteAction()
	{
		String message = controller.deleteNode(currNode);
		System.out.println("Deleting the node!");
		if (message != null)
		{
			new ErrorMessage(message).show();
		}
//		else 
//		{
//			//Change the display to removed node's parent
//			currNode = currNode.getParent();
//			titleField.setText(currNode.getName());
//			contentField.setText(currNode.getData());			
//		}
	}
	
	/**
	 * Push action.
	 */
	private void pushAction()
	{
		String message = controller.pushToServer();
		if (message != null)
		{
			new ErrorMessage(message).show();
		}
	}
	
	/**
	 * Exit action.
	 */
	private void exitAction()
	{
		checkSave();
		if (!isPushed)
		{
			ConfirmationBox box = new ConfirmationBox("The file has not been pushed to the server.\nDo you want to push it to the server?", "");
			box.show();
			if (box.confirmed)
			{
				pushAction();
			}
		}
		stage.close();
	}


	/* (non-Javadoc)
	 * @see software_masters.planner_networking.Observer#respond(software_masters.planner_networking.Node)
	 */
	@Override
	public void respond(Node node)
	{
		setTreeView(node);
		isPushed = false;
		
	}
	
	
}
