package General;

/*
 * プログラム名：	X,Y座標を表す構造体
 * 作成者：		秋山博紀
 * コメント：		授業で使ったライブラリ以外は使用禁止で、
 * 				JavaのPoint型を授業内で使ったかどうか覚えてないので、自分でつくった
 */

public class Point {
	public int x;
	public int y;

	public Point() {
		x = 0;
		y = 0;
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
