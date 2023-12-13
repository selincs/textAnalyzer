package P1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FinalProject extends Application {
	MainLinkedList mainLinkedList;

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Create panes and set scene
		BorderPane root = new BorderPane();
		BorderPane innerBP = new BorderPane();
		StackPane textPane = new StackPane();
		GridPane userInputGrid = new GridPane();
		BorderPane userInputPane = new BorderPane();

		Scene scene = new Scene(root, 800, 600);
		primaryStage.setMaximized(true);

		// Create the menu bar & File Menu
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");

		// Create Menu Items
		MenuItem selectText = new MenuItem("Select Text to Analyze");
		MenuItem saveText = new MenuItem("Save Output Text");
		MenuItem exitProgram = new MenuItem("Exit Program");

		// Create file chooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

		//Create user input UI elements
		String backgroundGreen = "-fx-background-color:rgba(2, 48, 32, .7); ";
		Label keywordLabel = new Label("Keyword from Analyzed Text :");
		TextArea keywordInput = new TextArea();
		keywordInput.setPrefRowCount(1);
		Button generateButton = new Button("Generate Paragraph");
		TextArea paragraphOutput = new TextArea();
		paragraphOutput.setEditable(false);
		paragraphOutput.setWrapText(true);
		paragraphOutput.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(.5))));
		Label wordCountLabel = new Label("Paragraph Size:");
		TextArea wordCountTA = new TextArea();
		wordCountTA.setPrefRowCount(1);
		wordCountTA.setPrefColumnCount(3);
		generateButton.setDisable(true);
		paragraphOutput.setText("Begin generating text by selecting a text file");

		// TextArea that will display .txt for user
		TextArea generatedText = new TextArea();
		generatedText.setWrapText(true); 
		
        // Bind the disable property of the text area to the selected property of the check box
		CheckBox checkBox = new CheckBox("Enable Text Scrolling for Selected File Displayed Above"); 
		generatedText.disableProperty().bind(checkBox.selectedProperty().not());
		 
		// Set up the userInputGrid
		userInputGrid.add(checkBox, 1, 0);
		GridPane.setColumnSpan(checkBox, 4);
		userInputGrid.add(keywordLabel, 0, 1);
		userInputGrid.add(keywordInput, 1, 1);
		userInputGrid.add(wordCountLabel, 2, 1);
		userInputGrid.add(wordCountTA, 3, 1);
		userInputGrid.add(generateButton, 4, 1);
		userInputGrid.setHgap(10);
		userInputGrid.setVgap(10);
		userInputGrid.setPadding(new Insets(10));
		userInputGrid.setAlignment(Pos.CENTER);
		userInputGrid.setBackground(new Background(new BackgroundFill(Color.GHOSTWHITE, null, null)));
		userInputGrid.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(.5))));

		// Place input grid inside border pane
		userInputPane.setTop(userInputGrid);
		userInputPane.setCenter(paragraphOutput);
		
		// Text Area UI Elements
		Label welcome = new Label("Welcome! Please select a Text File from the File Menu to begin generating paragraphs");
		textPane.getChildren().addAll(generatedText, welcome);
		textPane.setPrefSize(100, 100);
		textPane.setStyle(backgroundGreen);
		textPane.setPadding(new Insets(30));
		innerBP.setStyle(backgroundGreen);

		// Add the Open menu item to the File menu
		fileMenu.getItems().add(selectText);
		fileMenu.getItems().add(saveText);
		fileMenu.getItems().add(exitProgram);

		// Add the File menu to the menu bar
		menuBar.getMenus().add(fileMenu);

		// Add the menu bar to the root pane
		root.setTop(menuBar);

		// Pane organization
		root.setCenter(innerBP);
		innerBP.setPadding(new Insets(30));


		innerBP.setCenter(textPane);
		innerBP.setBottom(userInputPane);

		//Event Handlers
		selectText.setOnAction(e->{
				File file = fileChooser.showOpenDialog(primaryStage);
				generateButton.setDisable(false);
				if (file != null) {
					try {
						String filePath = file.getAbsolutePath();
						String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
						mainLinkedList = analyzeText(filePath);
						welcome.setVisible(false);
						generatedText.setText(content);

						// Display the results in your GUI
					} catch (IOException ioe) {
						Alert alert = new Alert(AlertType.ERROR, "Error reading file");
						alert.showAndWait();
					}
				}
			
		});

		generateButton.setOnAction(e -> {
			String keyword = keywordInput.getText();
			String wordCountStr = wordCountTA.getText();
			int wordCount;
			try {
				wordCount = Integer.parseInt(wordCountStr);
			} catch (NumberFormatException nfe) {
				Alert alert = new Alert(AlertType.ERROR, "Please enter a valid number for paragraph size.");
				alert.showAndWait();
				return;
			}

			MainLink userInput = null;
			for (MainLink mainLink : mainLinkedList.getMainLinkedList()) {
				if (mainLink.getKeyword().equalsIgnoreCase(keyword)) {
					userInput = mainLink;
					break;
				}
			}
			if (userInput != null) {
				paragraphOutput.setText(generateParagraph(userInput, wordCount));
			} else {
				paragraphOutput.setText("No matching keyword found");
			}
		});


		saveText.setOnAction(e -> {
			// create the output directory if it doesn't exist
			File outputDir = new File("output");
			if (!outputDir.exists()) {
				outputDir.mkdir();
			}

			// create the file chooser dialog
			FileChooser saveChooser = new FileChooser();
			saveChooser.setTitle("Save Output");
			saveChooser.setInitialDirectory(outputDir);
			saveChooser.setInitialFileName("output.txt");

			// show the file chooser dialog
			File outputFile = saveChooser.showSaveDialog(primaryStage);
			if (outputFile != null) {
				try {
					// write the generated String to the output file
					FileWriter writer = new FileWriter(outputFile);
					writer.write(paragraphOutput.getText());
					writer.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		});

		exitProgram.setOnAction(e -> {
			primaryStage.close();
		});

		// Set scene
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static String generateParagraph(MainLink keywordInput, int paragraphSize) {
		StringBuilder sb = new StringBuilder();
		sb.append(keywordInput.getKeyword());
		
		for (int i = 1; i < paragraphSize; i++) {
			int random = new Random().nextInt(keywordInput.getFollowingWords().getBabyLinkedList().size());
			sb.append(keywordInput.getFollowingWords().getBabyLinkedList().get(random)); // random babylink based on list size
		}
//		System.out.println(sb);
		return sb.toString();
	}

	public static MainLinkedList analyzeText(String filePath) {
		MainLinkedList mainList = new MainLinkedList();

		try {
			// create a file object for the myFile.txt file in the data folder
			File file = new File(filePath);
			// create a FileReader object to read the contents of the file
			FileReader reader = new FileReader(file);
			// create a buffer to store the contents of the file
			BufferedReader buffer = new BufferedReader(reader);
			// read the contents of the file line by line and print them to the console
			String line;
			String lastKeyword = null; // keep track of the previous word

			while ((line = buffer.readLine()) != null) {
				String[] words = line.split("[\\p{Punct}\\s\\d]+");

				for (int i = 0; i < words.length - 1; i++) {
					boolean linkExists = false;
					String keyword = words[i].toLowerCase();
					String followingWord = words[i + 1].toLowerCase();
					MainLink newLink = new MainLink(keyword, followingWord);

					if (lastKeyword != null && i == 0) {
						mainList.getMainLinkedList().getLast().getFollowingWords().add(new BabyLink(keyword));
					} else {
						for (MainLink mainLink : mainList.getMainLinkedList()) {
							if (mainLink.getKeyword().equalsIgnoreCase(keyword)) {
								mainLink.getFollowingWords().add(new BabyLink(followingWord));
								linkExists = true;
								break;
							}
						}

						if (!linkExists) {
							mainList.add(newLink);
						} // Check if this is the last word of the line
						if (i == words.length - 2 || words.length == 2) {
							lastKeyword = words[i + 1].toLowerCase();
							MainLink lastLink = new MainLink(lastKeyword);
							mainList.add(lastLink);
						}
					}
				}

			}
			buffer.close();
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return mainList;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
