package General;

/*
 * プログラム名：	計算のたぐいの関数
 * 作成者：		秋山博紀
 * コメント：		授業で使ったライブラリ以外は使用禁止で、
 * 				Mathクラスを授業で使わなかったような気がするので、自分でつくった
 */

public class Calculation {

	// distance：２点間の距離を測る関数
	public static double distance(Point a, Point b) {
		return distance(a.x, a.y, b.x, b.y);
	}

	public static double distance(Point a, int x, int y) {
		return distance(a.x, a.y, x, y);
	}

	public static double distance(int x1, int y1, int x2, int y2) {
		return Math
				.sqrt((int) (Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)));
	}

	// Mathクラスが使用禁止と勘違いしていたため作った関数(sqrt,pow)
	// 速度改善したと思ったがプラシーボ効果かもしれないため、使わない
	/*
	 * //平方根に近い整数を求める関数 注意：小数第１位以下すべてを切り捨てるというおぞましい実装だが少し速い public static int
	 * sqrt(int x) { int a = 0; // ２乗した時に一番近い値を探す while (true) { a++; if (pow(a
	 * + 1, 2) > x) break; } return a; }
	 * 
	 * // 累乗を計算する関数（Math.powの代用品(int型なので少し高速)） public static int pow(int x, int
	 * n) { int tmp = x; while (--n > 0) tmp *= x; return tmp; }
	 */
}
