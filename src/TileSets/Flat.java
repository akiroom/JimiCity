package TileSets;

import obpro.cui.Random;

/*
 * プログラム名：	平地のクラス
 * 作成者：		秋山博紀
 */

public class Flat extends Tile {
	// 画像をランダムに設定する
	public void setRandomImage() {
		this.imageName = "res/grass" + Random.getInt(3) + ".gif";
	}
}
