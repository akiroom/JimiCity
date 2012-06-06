package TileSets;

/*
 * プログラム名：	１タイル分を表す関数
 * 作成者：		秋山博紀
 */

// 1タイル分を表すが、ビルとか平地とか実際に実装したもののみを使うこと！
public abstract class Tile {
	// このタイルに割り当てられた画像ファイルの名前
	protected String imageName;

	// コンストラクタ
	protected Tile() {
		this.setRandomImage();
	}

	// 画像のファイル名を指定できるコンストラクタ
	protected Tile(String imageName) {
		this.imageName = imageName;
	}

	// 画像のファイル名を取得するメソッド
	public String getImageName() {
		return imageName;
	}

	// ランダムな画像を指定するメソッド。実装は継承先でつくる。
	public abstract void setRandomImage();
}
