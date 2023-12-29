package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Optional;

public class CustomATM extends Application {

    private BankAccount bankAccount;

    public CustomATM() {
        // You can initialize your BankAccount here if needed
        this.bankAccount = new BankAccount(1000.00);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("ATM");

        Label titleLabel = new Label("ATM");
        titleLabel.setFont(Font.font("Verdana", 24));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-underline: true;");

        Button withdrawButton = new Button("Withdraw");
        Button depositButton = new Button("Deposit");
        Button checkBalanceButton = new Button("Check Balance");
        Button exitButton = new Button("Exit");

        withdrawButton.setOnAction(e -> showTransactionDialog("Withdraw"));
        depositButton.setOnAction(e -> showTransactionDialog("Deposit"));
        checkBalanceButton.setOnAction(e -> showBalanceDialog());
        exitButton.setOnAction(e -> primaryStage.close());

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getChildren().addAll(titleLabel, withdrawButton, depositButton, checkBalanceButton, exitButton);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void showTransactionDialog(String transactionType) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(transactionType);
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the amount to " + transactionType.toLowerCase() + ":");

        processTransaction(dialog, transactionType);
    }

    private void showBalanceDialog() {
        showAlert("Your current balance is: $" + bankAccount.getBalance());
    }

    private void processTransaction(TextInputDialog dialog, String transactionType) {
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(amount -> {
            try {
                double transactionAmount = Double.parseDouble(amount);
                if (transactionType.equals("Withdraw") && bankAccount.getBalance() < transactionAmount) {
                    showAlert("Insufficient funds.");
                } else {
                    if (transactionType.equals("Withdraw")) {
                        bankAccount.withdraw(transactionAmount);
                    } else {
                        bankAccount.deposit(transactionAmount);
                    }
                    showAlert("Transaction successful. Your balance is now: $" + bankAccount.getBalance());
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid input. Please enter a valid number.");
            }
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Simple ATM");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
