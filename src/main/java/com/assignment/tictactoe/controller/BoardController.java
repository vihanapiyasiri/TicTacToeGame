package com.assignment.tictactoe.controller;

import com.assignment.tictactoe.service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements BoardUI{
    private HumanPlayer humanPlayer;
    private AIPlayer aiPlayer;
    private BoardImpl board;

    @FXML
    private Button but1;

    @FXML
    private Button but2;

    @FXML
    private Button but3;

    @FXML
    private Button but4;

    @FXML
    private Button but5;

    @FXML
    private Button but6;

    @FXML
    private Button but7;

    @FXML
    private Button but8;

    @FXML
    private Button but9;

    public BoardController() {
        board = new BoardImpl(this);
        humanPlayer = new HumanPlayer(board);
        aiPlayer = new AIPlayer(board);
    }

    @FXML
    void btOnAction(ActionEvent event) {
        String[][] Piece = new String[3][3];

        Button button = (Button) event.getSource();
        String id = button.getId();

        String cell = id.substring(3);

        int row = -1;
        int col = -1;

        int count = 1;

        L1:for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (count == Integer.parseInt(cell)) {
                    row = i;
                    col = j;
                    break L1;
                }
                count++;
            }
        }
        humanPlayer.move(row, col);
        aiPlayer.findBestMove();
        board.printBoard();
        updateUi();

        if (board.checkWinner() != null) {
            notifyWinner(board.checkWinner().getWinnerPiece());

        }else if (board.isBoardFull()){
            System.out.println("Tie");
            showAlert("Tie");
        }
    }


    @Override
    public void update(int col, int row, Piece piece) {

        Button[][] button = {{but1, but2, but3}, {but4, but5, but6}, {but7, but8, but9}};

        for (int i = 0; i < button.length; i++) {
            for (int j = 0; j < button[i].length; j++) {
                if (piece == Piece.X) {
                    button[row][col].setText("X");
                } else if (piece == Piece.O) {
                    button[row][col].setText("O");
                }else{
                    button[row][col].setText(" ");
                }
            }
        }
    }

    public void updateUi(){
        for (int i = 0; i < board.getPieces().length; i++) {
            for (int j = 0; j < board.getPieces()[i].length; j++) {
                update(j,i,board.getPieces()[i][j]);
            }
        }
    }

    @Override
    public void notifyWinner(Piece winner) {
        if(winner == Piece.X){
            System.out.println("X is winner");
            showAlert("X is winner");
        }else if(winner == Piece.O){
            System.out.println("O is winner");
            showAlert("O is winner");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.setOnCloseRequest((DialogEvent event) -> {
            board.initializeBoard();
            updateUi();
        });
        alert.showAndWait();
    }

}
