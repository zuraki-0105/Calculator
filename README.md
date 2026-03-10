# Calculator (JavaFX)

JavaFX と Maven を使用して作成された、シンプルな電卓アプリケーションです。
基本的な四則演算（＋、－、×、÷）をサポートしています。

## 概要

* **言語:** Java 21
* **GUI フレームワーク:** JavaFX 21
* **ビルドツール:** Maven
* **UI デザイン:** FXML (Scene Builder)

## クローンからの実行方法 (Eclipse 向け)

このプロジェクトは Maven で管理されているため、JavaFX の SDK を手動でダウンロードしてパスを設定する必要はありません。

以下の手順で、自動的に必要なライブラリがダウンロードされ、実行できます。

### 1. プロジェクトのクローン
コマンドライン、または Eclipse の Git 機能などからリポジトリをクローンします。

```bash
git clone https://github.com/あなたのユーザー名/Calculator.git
```

### 2. Eclipse へのインポート
1. Eclipse を起動し、メニューから **ファイル (File)** > **インポート (Import)** を選択します。
2. **Maven** > **既存の Maven プロジェクト (Existing Maven Projects)** を選択し、「次へ」をクリックします。
3. 「ルート・ディレクトリー (Root Directory)」に、今クローンした `Calculator` フォルダを選択します。
4. `pom.xml` にチェックが入っていることを確認し、「完了 (Finish)」をクリックします。

*(※ 自動的に JavaFX のライブラリがダウンロードされ、ビルドエラーが解消されるまで数秒〜数十秒お待ちください。)*

### 3. アプリケーションの実行
1. プロジェクト・エクスプローラー（またはパッケージ・エクスプローラー）で `Calculator` プロジェクトを展開します。
2. `src` > `application` フォルダの中にある **`Main.java`** を探します。
3. `Main.java` を右クリックし、 **実行 (Run As)** > **Java アプリケーション (Java Application)** を選択します。

電卓の画面が立ち上がれば成功です！

---

### コマンドラインから実行する場合 (Maven)
Maven がインストールされている環境であれば、ターミナルから以下のコマンドだけで実行することも可能です。

```bash
mvn clean javafx:run
```
