package TileSets;

import obpro.cui.Random;

/*
 * プログラム名：	おうちのクラス
 * 作成者：		秋山博紀
 */

public class House extends Tile {
	// 画像をランダムに設定する
	public void setRandomImage() {
		this.imageName = "res/h" + Random.getInt(26) + ".gif";
	}
}
