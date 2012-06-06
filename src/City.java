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
 * �v���O�������F	City�N���X�F�X���̂��Ǘ����Ă���N���X
 * �쐬�ҁF		�H�R���I
 */

public class City {
	// ���ϐ��Q
	protected Tile[][] arrTiles; // �e�^�C���̃f�[�^
	protected int tileWidth = 32;// �^�C���̉��s�N�Z��
	protected int tileHeight = 9;// �^�C���̏c�s�N�Z��
	protected int tileGraphicHeight = 128;// �^�C���̉摜�̍����s�N�Z��
	protected Point cityCenter;// �s�s�̒��S����ۑ�

	// ���S�n���猩�āA�ǂ̎{�݂��ǂ̋������Ȃ猚������邩
	protected int levelHighArea = 10;
	protected int levelLowArea = 20;
	protected int levelHouseArea = 30;

	// �����\�b�h�Q
	// �R���X�g���N�^
	City(int cityWidth, int cityHeight, int tileWidth, int tileHeight,
			int tileGraphicHeight, int numRoads) {
		// �^�C�����X�̃T�C�Y�� ����
		arrTiles = new Tile[cityHeight][cityWidth];
		// �X�̒��S��ݒ�
		// �T�C�Y / 2 - �T�C�Y / 4 + �����_��(�T�C�Y / 4 * 2)
		// ���T�C�Y / 4 + �����_��(�T�C�Y/2)
		cityCenter = new Point((int) (getWidth() / 4.0 + Random
				.getInt(getWidth() / 2)), (int) (getHeight() / 4.0 + Random
				.getInt(getHeight()) / 2));
		System.out.println(cityCenter.x + "," + cityCenter.y + ":" + cityWidth
				+ "," + cityHeight);

		// �s�s���x���������ݒ�
		resetCityLevel();

		// ���ׂẴ^�C���ɒ��g��ݒ�
		for (int y = 0; y < cityHeight; y++)
			for (int x = 0; x < cityWidth; x++)
				setTileRandom(x, y);

		// �w�肳�ꂽ�񐔂������H�𐶐�����
		int i = 0;
		while (i++ < numRoads)
			Road.generateRandomRoad(arrTiles, getWidth(), getHeight());

		// �摜�̃T�C�Y��ݒ�
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.tileGraphicHeight = tileGraphicHeight;
	}

	// �X�̌i�F��`��
	public void draw(BCanvas canvas, int viewX, int viewY, int viewWidth,
			int viewHeight) {
		Tile tile = null;
		int isZurashi = 0;
		canvas.clear();

		// �c�P�s���ƂɃ��[�v���܂킷
		for (int y = viewY; (y - viewY - 1) * tileHeight < viewHeight; y++) {
			// �O�̂��߁AY���W�����炩�ɂ͂ݏo�����ꍇ�̓��[�v���I��������
			if (y >= arrTiles.length)
				break;

			// �Ђ��`�ł��Y��ɕ��ׂ邽�߂ɁA1��΂��Ă��炵�Ă���
			if (y % 2 == 0)
				isZurashi = tileWidth / 2;
			else
				isZurashi = 0;

			// ���P�^�C�������[�v���܂킷
			for (int x = viewX; (x - viewX - 1) * tileWidth < viewWidth; x++) {
				// �O�̂��߁AX���W�����炩�ɂ͂ݏo�����ꍇ�̓��[�v���I��������
				if (x >= arrTiles[y].length)
					break;

				// �P�^�C������
				tile = arrTiles[y][x];
				// �^�C����`�悷����W���v�Z
				int tx = x * tileWidth + isZurashi - viewX * tileWidth;
				int ty = (y + 1) * tileHeight - tileGraphicHeight - viewY
						* tileHeight;
				// �P�^�C�����̉摜��`�悷��
				canvas.drawImage(tile.getImageName(), tx - tileWidth, ty);
			}
		}
	}

	// �X�̉���
	public int getWidth() {
		return arrTiles[0].length;
	}

	// �X�̍���
	public int getHeight() {
		return arrTiles.length;
	}

	// �w�肵�����W�Ƀ^�C����ݒ�
	public void setTile(int x, int y, Tile tile) {
		arrTiles[y][x] = tile;
	}

	// �w�肳�ꂽ���W�������_���ȗv�f�ɐ؂�ւ���
	public void setTileRandom(int x, int y) {
		if (cityCenter != null) {
			setTileRandomByCityCenterAlgorithm(x, y);
		} else {
			setTileRandomByRandomAlgorithm(x, y);
		}

		// �摜�t�@�C����ǂݍ���
		arrTiles[y][x].setRandomImage();
	}

	// // �V�e�B�Z���^�[���ʂ��g���ĂP�^�C�����Z�b�g����
	private void setTileRandomByCityCenterAlgorithm(int x, int y) {
		// �w�肳�ꂽ�^�C�����璆�S�n�ւ̋�����dist�ɑ������
		double dist = Calculation.distance(cityCenter, x, y);

		if (dist < levelHighArea / 2 + Random.getInt(levelHighArea / 2)) {
			// �V�e�B�Z���^�[�Ȃ̂ŁA���w�r���𑽂߂ɂ���
			arrTiles[y][x] = new HighBuilding();
		} else if (dist < levelLowArea / 2 + Random.getInt(levelLowArea / 2)) {
			// �V�e�B�Z���^�[���炿����ƊO��Ă�̂ŁA��w�r���𑽂߂ɂ���
			arrTiles[y][x] = new LowBuilding();
		} else if (dist < levelHouseArea / 2
				+ Random.getInt(levelHouseArea / 2)) {
			// �x�O�Ȃ̂ŁA���Ƃ𑽂߂ɂ���
			arrTiles[y][x] = new House();
		} else {
			// �_�O�Ȃ̂ŁA���J���̒n�𑽂߂ɂ���
			arrTiles[y][x] = new Flat();
		}
	}

	// ���S�Ƀ����_���ȏ����łP�^�C�����Z�b�g����
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

	// �w�肵����(0.00�`1.00)���A�����ĊJ������
	public void redevelopment(double percent) {
		// ������̓I�ɂ����̃^�C�����ĊJ������̂��v�Z
		int num = (int) (percent * getWidth() * getHeight());

		// �ĊJ������^�C�������[�v
		for (int i = 0; i < num; i++) {
			// ���H�ȊO�̏ꏊ���ĊJ������܂Ŗ������[�v
			while (true) {
				int x = Random.getInt(getWidth());
				int y = Random.getInt(getHeight());
				if (!(arrTiles[y][x] instanceof Road)) {
					// �w�肵���^�C�������H�ȊO�Ȃ�ĊJ��
					setTileRandom(x, y);
					break;
				}
			}
		}
	}

	// �P�^�C���̉���(px)
	public int getTileWidth() {
		return tileWidth;
	}

	// �P�^�C���̏c��(px)
	public int getTileHeight() {
		return tileHeight;

	}

	// �s�s����i�߂�
	public void urbanize() {
		levelHighArea++;
		levelLowArea += Random.getInt(2) + 1;
		levelHouseArea += Random.getInt(3) + 1;
	}

	// �n������i�߂�
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

	// �s�s���x��(�s�s���x)�̃��Z�b�g
	public void resetCityLevel() {
		levelHighArea = (getWidth() + getHeight()) / 12;
		levelLowArea = (getWidth() + getHeight()) / 6;
		levelHouseArea = (getWidth() + getHeight()) / 4;
	}

	// �V�e�B�Z���^�[���ʂ�ON�ɂ���
	public void onCityCenter() {
		cityCenter = new Point(getWidth() / 2, getHeight() / 2);
	}

	// �V�e�B�Z���^�[���ʂ�OFF�ɂ���
	public void offCityCenter() {
		cityCenter = null;
	}

	// �V�e�B�[�Z���^�[���ʂ�ON��OFF��
	public boolean hasCityCenter() {
		return cityCenter != null;
	}

	// �V�e�B�[�Z���^�[���ʂ�؂�ւ���
	public void switchHasCityCenter() {
		if (hasCityCenter())
			offCityCenter();
		else
			onCityCenter();
	}
}
