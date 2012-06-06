package TileSets;

import obpro.cui.Random;

/*
 * プログラム名：	低層ビルのクラス
 * 作成者：		秋山博紀
 */

public class LowBuilding extends Tile {
	// 画像をランダムに設定する
	public void setRandomImage() {
		this.imageName = "res/l" + Random.getInt(6) + ".gif";
	}
}
