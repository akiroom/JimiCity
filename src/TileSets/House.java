package TileSets;

import obpro.cui.Random;

/*
 * �v���O�������F	�������̃N���X
 * �쐬�ҁF		�H�R���I
 */

public class House extends Tile {
	// �摜�������_���ɐݒ肷��
	public void setRandomImage() {
		this.imageName = "res/h" + Random.getInt(26) + ".gif";
	}
}
