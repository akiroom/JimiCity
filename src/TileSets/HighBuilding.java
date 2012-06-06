package TileSets;

import obpro.cui.Random;

/*
 * プログラム名：	高層ビルのクラス
 * 作成者：		秋山博紀
 */

public class HighBuilding extends Tile {
	// 画像をランダムに設定する
	public void setRandomImage() {
		this.imageName = "res/b" + Random.getInt(11) + ".gif";
	}
}
