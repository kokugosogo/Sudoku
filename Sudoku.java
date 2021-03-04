// Sudoku kokugosogo
//=====================================================================================
//
// コンパイルコマンド : javac_fx Sudoku.java
// 実行コマンド : java_fx Sudoku
//=====================================================================================

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.event.*;
import java.io.*;
import java.util.*;
import javafx.collections.*;
import javafx.scene.input.*;

public class Sudoku extends Application {
    private static int[][] board = new int[9][9]; // 問題用
    private static int[][] board_answer = new int[9][9]; // 答え用
    private static boolean[][] board_isCorrect = new boolean[9][9]; // 問題と答えが一致しているか
    // private TextField[][] tf = new TextField[9][9];
    private static String fname; // 問題ファイル名
    private static String fname_answer; // 答えファイル名
    private Label lb;
    private int cnt_notCorrect = 0; // 答えと一致していない数をカウント
    private int cnt_correct = 0; // 答えと一致している数をカウント
    private ArrayList<ComboBox<String>> cb_arraylist = new ArrayList<ComboBox<String>>();
    private ObservableList<String> ol;
    private int field_size = 9; // 数独の縦横サイズ
    private int room_size = 3; // 数独の大きい9マスの中に入るComboBoxの縦横

    public static void main(String[] args) {
        fname = "input.txt";
        fname_answer = "input_answer.txt";
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        lb = new Label("             空いているマスに1桁の数字を入力してください\n※1マス入力するごとにEnterを押さないと、入力が反映されません");

        // 問題を読み取る
        Scanner sc = new Scanner(new File(fname));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = sc.nextInt();
                board_isCorrect[i][j] = true;
                if (board[i][j] <= 0 || 9 < board[i][j]) {
                    board[i][j] = 0;
                    board_isCorrect[i][j] = false;
                    cnt_notCorrect++;
                }
            }
        }

        // 答えを読み取る
        sc = new Scanner(new File(fname_answer));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board_answer[i][j] = sc.nextInt();
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ComboBox<String> cb = new ComboBox<String>();
                if (board[i][j] == 0) {
                    int idx_i = i;
                    int idx_j = j;
                    ol = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9");
                    cb.setItems(ol);
                    cb.setOnAction((ActionEvent) -> {
                        if (cb_arraylist.get(idx_i * 9 + idx_j).getValue() != null) {
                            int tmp = Integer.parseInt(cb_arraylist.get(idx_i * 9 + idx_j).getValue().toString());
                            // System.out.println(tmp);
                            // 入力した値が答えと一致しているなら正解数を増やす
                            if (board_answer[idx_i][idx_j] == tmp && !board_isCorrect[idx_i][idx_j]) {
                                cnt_correct++;
                                board_isCorrect[idx_i][idx_j] = true;
                            }
                            // 入力した値が答えと一致していないなら正解数を減らす
                            if (board_answer[idx_i][idx_j] != tmp && board_isCorrect[idx_i][idx_j]) {
                                cnt_correct--;
                                board_isCorrect[idx_i][idx_j] = false;
                            }
                            // System.out.println(cnt_correct);
                            if (cnt_correct == cnt_notCorrect) {
                                lb.setText("ゲームクリア! Congratulations!");
                            }
                        }
                    });
                } else {
                    String str = String.valueOf(board[i][j]);
                    // ol = FXCollections.observableArrayList(str);
                    cb.setValue(str);
                    // cb.setText(str);
                    cb.setEditable(false);
                    cb.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
                }
                cb.setMaxWidth(40);
                // cb.setFont(Font.font("MonoSpace", 20));
                cb_arraylist.add(cb);
            }
        }

        GridPane gp_2 = new GridPane(); // 9個のブロック
        gp_2.setHgap(15);
        gp_2.setVgap(15);
        for (int i = 0; i < 9; i++) {
            GridPane gp_1 = new GridPane(); // ブロックに重ねる9マス
            gp_1.setHgap(5);
            gp_1.setVgap(5);
            for (int j = 0; j < 9; j++) {
                int pivot = ((i / room_size) * room_size * field_size) + ((i % room_size) * room_size);
                gp_1.add(cb_arraylist.get(pivot + ((j / room_size) * field_size) + (j % room_size)), j % room_size,
                        j / room_size);
            }
            gp_1.setAlignment(Pos.CENTER);
            gp_2.add(gp_1, i % 3, i / 3);
        }
        gp_2.setAlignment(Pos.CENTER);

        BorderPane bp = new BorderPane();
        bp.setTop(gp_2);
        bp.setCenter(lb);

        Button bt = new Button("リセット");
        bt.setOnAction((ActionEvent) -> {
            lb.setText("             空いているマスに1桁の数字を入力してください\n※1マス入力するごとにEnterを押さないと、入力が反映されません");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (board[i][j] <= 0 || 9 < board[i][j]) {
                        board[i][j] = 0;
                        int idx = i * 9 + j;
                        cb_arraylist.get(idx).setValue(null);
                        board_isCorrect[i][j] = false;
                    }
                }
            }
            cnt_correct = 0;
        });
        BorderPane.setAlignment(bt, Pos.BOTTOM_CENTER);
        bp.setBottom(bt);
        Scene scene = new Scene(bp, 600, 600);
        stage.setScene(scene);
        stage.setTitle("数独");
        stage.show();
    }
}