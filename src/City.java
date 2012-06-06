import obpro.cui.Random;
import obpro.gui.BCanvas;
import General.Calculation;
import General.Point;
import TileSets.Flat;
import TileSets.HighBuilding;
import TileSets.House;
import TileSets.LowBuilding;
import TileSets.Road;
import TileSets.Tile;

/*
 * プログラム名：	Cityクラス：街自体を管理しているクラス
 * 作成者：		秋山博紀
 */

public class City {
	// ★変数群
	protected Tile[][] arrTiles; // 各タイルのデータ
	protected int tileWidth = 32;// タイルの横ピクセル
	protected int tileHeight = 9;// タイルの縦ピクセル
	protected int tileGraphicHeight = 128;// タイルの画像の高さピクセル
	protected Point cityCenter;// 都市の中心部を保存

	// 中心地から見て、どの施設がどの距離内なら建立されるか
	protected int levelHighArea = 10;
	protected int levelLowArea = 20;
	protected int levelHouseArea = 30;

	// ★メソッド群
	// コンストラクタ
	City(int cityWidth, int cityHeight, int tileWidth, int tileHeight,
			int tileGraphicHeight, int numRoads) {
		// タイルを街のサイズ分 生成
		arrTiles = new Tile[cityHeight][cityWidth];
		// 街の中心を設定
		// サイズ / 2 - サイズ / 4 + ランダム(サイズ / 4 * 2)
		// →サイズ / 4 + ランダム(サイズ/2)
		cityCenter = new Point((int) (getWidth() / 4.0 + Random
				.getInt(getWidth() / 2)), (int) (getHeight() / 4.0 + Random
				.getInt(getHeight()) / 2));
		System.out.println(cityCenter.x + "," + cityCenter.y + ":" + cityWidth
				+ "," + cityHeight);

		// 都市レベルを初期設定
		resetCityLevel();

		// すべてのタイルに中身を設定
		for (int y = 0; y < cityHeight; y++)
			for (int x = 0; x < cityWidth; x++)
				setTileRandom(x, y);

		// 指定された回数だけ道路を生成する
		int i = 0;
		while (i++ < numRoads)
			Road.generateRandomRoad(arrTiles, getWidth(), getHeight());

		// 画像のサイズを設定
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.tileGraphicHeight = tileGraphicHeight;
	}

	// 街の景色を描く
	public void draw(BCanvas canvas, int viewX, int viewY, int viewWidth,
			int viewHeight) {
		Tile tile = null;
		int isZurashi = 0;
		canvas.clear();

		// 縦１行ごとにループをまわす
		for (int y = viewY; (y - viewY - 1) * tileHeight < viewHeight; y++) {
			// 念のため、Y座標が明らかにはみ出した場合はループを終了させる
			if (y >= arrTiles.length)
				break;

			// ひし形でも綺麗に並べるために、1個飛ばしてずらしていく
			if (y % 2 == 0)
				isZurashi = tileWidth / 2;
			else
				isZurashi = 0;

			// 横１タイルずつループをまわす
			for (int x = viewX; (x - viewX - 1) * tileWidth < viewWidth; x++) {
				// 念のため、X座標が明らかにはみ出した場合はループを終了させる
				if (x >= arrTiles[y].length)
					break;

				// １タイルを代入
				tile = arrTiles[y][x];
				// タイルを描画する座標を計算
				int tx = x * tileWidth + isZurashi - viewX * tileWidth;
				int ty = (y + 1) * tileHeight - tileGraphicHeight - viewY
						* tileHeight;
				// １タイル分の画像を描画する
				canvas.drawImage(tile.getImageName(), tx - tileWidth, ty);
			}
		}
	}

	// 街の横幅
	public int getWidth() {
		return arrTiles[0].length;
	}

	// 街の高さ
	public int getHeight() {
		return arrTiles.length;
	}

	// 指定した座標にタイルを設定
	public void setTile(int x, int y, Tile tile) {
		arrTiles[y][x] = tile;
	}

	// 指定された座標をランダムな要素に切り替える
	public void setTileRandom(int x, int y) {
		if (cityCenter != null) {
			setTileRandomByCityCenterAlgorithm(x, y);
		} else {
			setTileRandomByRandomAlgorithm(x, y);
		}

		// 画像ファイルを読み込む
		arrTiles[y][x].setRandomImage();
	}

	// // シティセンター効果を使って１タイルをセットする
	private void setTileRandomByCityCenterAlgorithm(int x, int y) {
		// 指定されたタイルから中心地への距離をdistに代入する
		double dist = Calculation.distance(cityCenter, x, y);

		if (dist < levelHighArea / 2 + Random.getInt(levelHighArea / 2)) {
			// シティセンターなので、高層ビルを多めにする
			arrTiles[y][x] = new HighBuilding();
		} else if (dist < levelLowArea / 2 + Random.getInt(levelLowArea / 2)) {
			// シティセンターからちょっと外れてるので、低層ビルを多めにする
			arrTiles[y][x] = new LowBuilding();
		} else if (dist < levelHouseArea / 2
				+ Random.getInt(levelHouseArea / 2)) {
			// 郊外なので、お家を多めにする
			arrTiles[y][x] = new House();
		} else {
			// 論外なので、未開発の地を多めにする
			arrTiles[y][x] = new Flat();
		}
	}

	// 完全にランダムな条件で１タイルをセットする
	private void setTileRandomByRandomAlgorithm(int x, int y) {
		switch (Random.getInt(4)) {
		case 0:
			arrTiles[y][x] = new HighBuilding();
			break;
		case 1:
			arrTiles[y][x] = new LowBuilding();
			break;
		case 2:
			arrTiles[y][x] = new House();
			break;
		case 3:
			arrTiles[y][x] = new Flat();
			break;
		}

	}

	// 指定した％(0.00～1.00)分、区画を再開発する
	public void redevelopment(double percent) {
		// ％が具体的にいくつのタイルを再開発するのか計算
		int num = (int) (percent * getWidth() * getHeight());

		// 再開発するタイル分ループ
		for (int i = 0; i < num; i++) {
			// 道路以外の場所を再開発するまで無限ループ
			while (true) {
				int x = Random.getInt(getWidth());
				int y = Random.getInt(getHeight());
				if (!(arrTiles[y][x] instanceof Road)) {
					// 指定したタイルが道路以外なら再開発
					setTileRandom(x, y);
					break;
				}
			}
		}
	}

	// １タイルの横幅(px)
	public int getTileWidth() {
		return tileWidth;
	}

	// １タイルの縦幅(px)
	public int getTileHeight() {
		return tileHeight;

	}

	// 都市化を進める
	public void urbanize() {
		levelHighArea++;
		levelLowArea += Random.getInt(2) + 1;
		levelHouseArea += Random.getInt(3) + 1;
	}

	// 地方化を進める
	public void localize() {
		levelHighArea--;
		if (levelHighArea < 2)
			levelHighArea = 2;
		levelLowArea--;
		if (levelLowArea < 2)
			levelLowArea = 2;
		levelHouseArea--;
		if (levelHouseArea < 2)
			levelHouseArea = 2;
	}

	// 都市レベル(都市化度)のリセット
	public void resetCityLevel() {
		levelHighArea = (getWidth() + getHeight()) / 12;
		levelLowArea = (getWidth() + getHeight()) / 6;
		levelHouseArea = (getWidth() + getHeight()) / 4;
	}

	// シティセンター効果をONにする
	public void onCityCenter() {
		cityCenter = new Point(getWidth() / 2, getHeight() / 2);
	}

	// シティセンター効果をOFFにする
	public void offCityCenter() {
		cityCenter = null;
	}

	// シティーセンター効果がONかOFFか
	public boolean hasCityCenter() {
		return cityCenter != null;
	}

	// シティーセンター効果を切り替える
	public void switchHasCityCenter() {
		if (hasCityCenter())
			offCityCenter();
		else
			onCityCenter();
	}
}
