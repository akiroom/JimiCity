package TileSets;

import obpro.cui.Random;

/*
 * �v���O�������F	���w�r���̃N���X
 * �쐬�ҁF		�H�R���I
 */

public class HighBuilding extends Tile {
	// �摜�������_���ɐݒ肷��
	public void setRandomImage() {
		this.imageName = "res/b" + Random.getInt(11) + ".gif";
	}
}
