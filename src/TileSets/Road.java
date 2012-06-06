package TileSets;

import obpro.cui.Random;
import General.Point;

/*
 * プログラム名：	道路のクラス
 * 作成者：		秋山博紀
 */

public class Road extends Tile {
	// 東西南北の定数を指定
	public final static int NORTH = 0;
	public final static int EAST = 1;
	public final static int WEST = 2;
	public final static int SOUTH = 3;

	// 画像をランダムに設定する
	public void setRandomImage() {
		this.imageName = "res/road.gif";
	}

	// 北側に１タイル進んだ場所の座標を求める
	public static Point getTilePlusNorth(int x, int y) {
		Point p = new Point(x, y);
		// if (x % 2 == 1)
		// p.x++;
		// p.y--;
		if (y % 2 == 0)
			p.x++;
		p.y--;
		return p;
	}

	// 東側に１タイル進んだ場所の座標を求める
	public static Point getTilePlusEast(int x, int y) {
		Point p = new Point(x, y);
		if (y % 2 == 0)
			p.x++;
		p.y++;
		return p;
	}

	// 南側に１タイル進んだ場所の座標を求める
	public static Point getTilePlusSouth(int x, int y) {
		Point p = new Point(x, y);
		if (y % 2 == 1)
			p.x--;
		p.y++;
		return p;
	}

	// 西側に１タイル進んだ場所の座標を求める
	public static Point getTilePlusWest(int x, int y) {
		Point p = new Point(x, y);
		if (y % 2 == 1)
			p.x--;
		p.y--;
		return p;
	}

	// 東西南北を引数に持って、指定された方位に１タイルだけ移動した場合の位置を返す
	public static Point getTilePlusNEWS(int x, int y, int direction) {
		switch (direction) {
		case NORTH:
			return getTilePlusNorth(x, y);
		case EAST:
			return getTilePlusEast(x, y);
		case WEST:
			return getTilePlusWest(x, y);
		case SOUTH:
			return getTilePlusSouth(x, y);
		default:
			return null;
		}
	}

	// 道路を再帰的にどんどんつくってく関数
	public static void generateRoad(int x, int y, int direction,
			Tile[][] target, int cityWidthNum, int cityHeightNum, int turnLimit) {
		target[y][x] = new Road();
		Point p = Road.getTilePlusNEWS(x, y, direction);
		if (isPointOver(p, cityWidthNum, cityHeightNum))
			return;
		if (turnLimit != 0)
			if (Random.getInt(4) < 1) {
				generateRoad(p.x, p.y, turnRandom(direction), target,
						cityWidthNum, cityHeightNum, turnLimit - 1);
				return;
			}
		generateRoad(p.x, p.y, direction, target, cityWidthNum, cityHeightNum,
				turnLimit);
	}

	// ランダムに道路を生成する
	public static void generateRandomRoad(Tile[][] target, int cityWidthNum,
			int cityHeightNum) {
		int rnd = Random.getInt(4);
		int startX = Random.getInt(cityWidthNum);
		int startY = Random.getInt(cityHeightNum);
		switch (rnd) {
		case Road.NORTH:
		case Road.SOUTH:
			generateRoad(startX, startY, Road.NORTH, target, cityWidthNum,
					cityHeightNum, 1);
			generateRoad(startX, startY, Road.SOUTH, target, cityWidthNum,
					cityHeightNum, 1);
			break;
		case Road.EAST:
		case Road.WEST:
			generateRoad(startX, startY, Road.EAST, target, cityWidthNum,
					cityHeightNum, 1);
			generateRoad(startX, startY, Road.WEST, target, cityWidthNum,
					cityHeightNum, 1);
			break;
		}
	}

	protected static boolean isPointOver(Point p, int cityWidthNum,
			int cityHeightNum) {
		if ((p.x < 0) || (p.y < 0) || (p.x >= cityWidthNum)
				|| (p.y >= cityHeightNum))
			return true;
		else
			return false;
	}

	// 指定した方位から右にまがった時の値
	protected static int turnRight(int direction) {
		switch (direction) {
		case NORTH:
			return EAST;
		case EAST:
			return SOUTH;
		case SOUTH:
			return WEST;
		case WEST:
			return NORTH;
		default:
			return -1;
		}
	}

	// 指定した方位から左にまがった時の値
	protected static int turnLeft(int direction) {
		switch (direction) {
		case NORTH:
			return WEST;
		case EAST:
			return NORTH;
		case SOUTH:
			return EAST;
		case WEST:
			return SOUTH;
		default:
			return -1;
		}
	}

	// ランダムに左か右に曲がる
	protected static int turnRandom(int direction) {
		if (Random.getInt(2) == 0)
			return turnRight(direction);
		else
			return turnLeft(direction);
	}
}
