package TileSets;

import obpro.cui.Random;

/*
 * �v���O�������F	��w�r���̃N���X
 * �쐬�ҁF		�H�R���I
 */

public class LowBuilding extends Tile {
	// �摜�������_���ɐݒ肷��
	public void setRandomImage() {
		this.imageName = "res/l" + Random.getInt(6) + ".gif";
	}
}
