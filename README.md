# Sudoku
javafxを用いて開発した数独アプリ

# Demo
録画ソフトの関係でドロップダウンリストが映らなかったため画像を載せておきます

![DropDownList](https://user-images.githubusercontent.com/72292551/109939601-9191c700-7d14-11eb-8fa5-1ee58f544acb.png)
![Sudoku_demo](https://user-images.githubusercontent.com/72292551/109939308-47a8e100-7d14-11eb-818c-fc7598cd7ebb.gif)

# Features
ドロップダウンリストから数字を選択して回答する形式の数独です

# Requirement
* jdk 14.0.2
* javafx sdk 11.0.2

# Usage
* コンパイルコマンド
```bash
javac -p "保存したjavafxのlibファイルへのpath" --add-modules javafx.controls -encoding UTF-8 Sudoku.java
```
* 実行コマンド
```bash
java -p "保存したjavafxのlibファイルへのpath" --add-modules javafx.controls Sudoku
```

# Note
* 問題(input.txt)と解答(input_answer.txt)のファイルを同じディレクトリに格納してください

# Author
* https://github.com/kokugosogo
