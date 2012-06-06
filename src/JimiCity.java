import obpro.gui.BCanvas;
import obpro.gui.BWindow;

/*
 * プログラム名：	地味シティの本体クラス（実際に実行するクラス）
 * 作成者：		秋山博紀
 */

public class JimiCity {
	// ★定数群
	// ↓『人にやさしいプログラミングの哲学』では変数の先頭は小文字にするという指示があったが、定数について書かれていなかった。
	// 下記URLのJavaのドキュメントを参考に一般的な命名規則に沿って定数のフィールド名を大文字にしたので、問題ない表記と考えている。
	// http://java.sun.com/docs/books/jls/third_edition/html/names.html#9367
	protected static final int WINDOW_WIDTH = 800; // ウィンドウの横幅の定数
	protected static final int WINDOW_HEIGHT = 600; // ウィンドウの高さの定数

	// ★変数群
	protected BCanvas canvas; // メインの描画を扱うキャンバス
	protected City city; // 街のインスタンス
	protected int nowX = 0; // 現在の視点のX座標
	protected int nowY = 0; // 現在の視点のY座標

	// ★メソッド群
	// 起動処理
	public static void main(String[] args) {
		JimiCity jimiCity = new JimiCity();
		jimiCity.main();
	}

	// メイン
	private void main() {
		initialize();
		routineWork();
	}

	// 初期化処理
	protected void initialize() {
		// ウィンドウを生成する
		BWindow window = new BWindow();
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.show();
		canvas = window.getCanvas();
		resetCity();
	}

	// 都市の初期化
	protected void resetCity() {
		city = new City(60, 80, 64, 18, 256, 8);
	}

	// 基本的な処理の流れ
	protected void routineWork() {
		int redevelopmentCounter = 0; // この街を再開発してから経過した時間のカウンター

		// とりあえず最初に再描画しておく
		redraw();

		// 無限ループ
		while (true) {
			// 毎回のループで再描画が必要かどうかチェックする
			boolean isNeedRedraw = false;

			// キーボードの入力処理をチェック
			isNeedRedraw = checkKeyboard();

			// 再開発カウンタを＋１して、
			redevelopmentCounter++;
			// もし400溜まっていたら、一部地域の再開発を実施する
			if (redevelopmentCounter > 400) {
				redevelopmentCounter = 0;
				city.redevelopment(0.001); // 0.1％の地域を再開発する
				isNeedRedraw = true; // 再開発後の景色を再描画
			}
			// 再描画が必要な場合は再描画
			if (isNeedRedraw)
				redraw();
		}
	}

	// 再描画
	protected void redraw() {
		// 街の景色を描画する
		city.draw(canvas, nowX, nowY, WINDOW_WIDTH, WINDOW_HEIGHT);

		// 東西南北を表すコンパスを描画する
		canvas.drawImage("res/news.gif", 0, 0);

		// 現在の視点の座標位置を描画する
		String s = "(" + nowX + "," + nowY + ")";

		/*
		 * (本当は色を指定して影をつけたかったが、使用許可されたライブラリの一覧に
		 * java.awt.*がなく、java.awt.colorが使用禁止されているため、nullを指定して、白色のみで文字列を描画した)
		 */
		canvas.drawText(null, s, 31, 61);
		canvas.drawText(null, s, 30, 60);
		canvas.update();
	}

	// キーボードのキーが押されている時の処理(再描画が必要かどうかを値として返す)
	protected boolean checkKeyboard() {
		// ＊再描画が必要かどうかのフラグ
		boolean isNeedRedraw = false;

		// ＊十字キーで視点を移動する
		if (canvas.getKeyCode() == 37) {// [←]キー
			isNeedRedraw = true;
			nowX--; // 左に移動
			if (nowX < 0)
				nowX = 0;
		}
		if (canvas.getKeyCode() == 38) {// [↑]キー
			isNeedRedraw = true;
			nowY -= 2;// 上に移動
			if (nowY < 0)
				nowY = 0;
		}
		if (canvas.getKeyCode() == 39) {// [→]キー
			isNeedRedraw = true;
			nowX++;// 右に移動
			if (nowX > city.getWidth() - 2 - WINDOW_WIDTH / city.getTileWidth())
				nowX = city.getWidth() - 2 - WINDOW_WIDTH / city.getTileWidth();
		}
		if (canvas.getKeyCode() == 40) {// [↓]キー
			isNeedRedraw = true;
			nowY += 2;// 下に移動
			if (nowY > city.getHeight() - 1 - WINDOW_HEIGHT
					/ city.getTileHeight())
				nowY = city.getHeight() - 1 - WINDOW_HEIGHT
						/ city.getTileHeight();
		}

		// ＊複数同時に実行してほしくない処理はswitchで判定
		switch (canvas.getKeyCode()) {
		case 32:// [SPACE]キー
			// 都市化する
			city.urbanize(); // 都市化
			city.redevelopment(0.1);// 10%の区画を整理する
			isNeedRedraw = true;
			break;
		case 66:// [B]キー
		case 98:// [b]キー
			// 地方化する
			city.localize();// 地方化
			city.redevelopment(0.1);// 10%の区画を整理する
			isNeedRedraw = true;
			break;
		case 87:// [W]キー
		case 119:// [w]キー
			// 都市レベルの初期化
			city.resetCityLevel();
			break;
		case 82:// [R]キー
		case 114:// [r]キー
			// 区画整理
			city.redevelopment(0.1);
			isNeedRedraw = true;
			break;
		case 47:// [/]キー
			// シティセンター効果の有無を切り替える
			city.switchHasCityCenter();
			break;
		case 81:// [Q]キー
		case 113:// [q]キー
			// 都市の初期化
			resetCity();
			isNeedRedraw = true;
			break;
		}

		// 再描画が必要かどうか返却
		return isNeedRedraw;
	}
}
