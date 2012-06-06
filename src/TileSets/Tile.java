package TileSets;

/*
 * �v���O�������F	�P�^�C������\���֐�
 * �쐬�ҁF		�H�R���I
 */

// 1�^�C������\�����A�r���Ƃ����n�Ƃ����ۂɎ����������݂̂̂��g�����ƁI
public abstract class Tile {
	// ���̃^�C���Ɋ��蓖�Ă�ꂽ�摜�t�@�C���̖��O
	protected String imageName;

	// �R���X�g���N�^
	protected Tile() {
		this.setRandomImage();
	}

	// �摜�̃t�@�C�������w��ł���R���X�g���N�^
	protected Tile(String imageName) {
		this.imageName = imageName;
	}

	// �摜�̃t�@�C�������擾���郁�\�b�h
	public String getImageName() {
		return imageName;
	}

	// �����_���ȉ摜���w�肷�郁�\�b�h�B�����͌p����ł���B
	public abstract void setRandomImage();
}
