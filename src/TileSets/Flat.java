package TileSets;

import obpro.cui.Random;

/*
 * �v���O�������F	���n�̃N���X
 * �쐬�ҁF		�H�R���I
 */

public class Flat extends Tile {
	// �摜�������_���ɐݒ肷��
	public void setRandomImage() {
		this.imageName = "res/grass" + Random.getInt(3) + ".gif";
	}
}
