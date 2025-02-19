package gui;

import buddy.Buddy;
import buddy.Ui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Buddy buddy;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image buddyImage = new Image(this.getClass().getResourceAsStream("/images/Buddy.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        showWelcomeMessage();
    }

    /** Injects the Buddy instance */
    public void setBuddy(Buddy b) {
        buddy = b;
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = buddy.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, buddyImage)
        );
        userInput.clear();
    }

    /** Method to show the welcome message */
    private void showWelcomeMessage() {
        String welcomeMessage = Ui.showWelcomeMessage();
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(welcomeMessage, buddyImage)
        );
    }
}
