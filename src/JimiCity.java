import obpro.gui.BCanvas;
import obpro.gui.BWindow;

/*
 * �v���O�������F	�n���V�e�B�̖{�̃N���X�i���ۂɎ��s����N���X�j
 * �쐬�ҁF		�H�R���I
 */

public class JimiCity {
	// ���萔�Q
	// ���w�l�ɂ₳�����v���O���~���O�̓N�w�x�ł͕ϐ��̐擪�͏������ɂ���Ƃ����w�������������A�萔�ɂ��ď�����Ă��Ȃ������B
	// ���LURL��Java�̃h�L�������g���Q�l�Ɉ�ʓI�Ȗ����K���ɉ����Ē萔�̃t�B�[���h����啶���ɂ����̂ŁA���Ȃ��\�L�ƍl���Ă���B
	// http://java.sun.com/docs/books/jls/third_edition/html/names.html#9367
	protected static final int WINDOW_WIDTH = 800; // �E�B���h�E�̉����̒萔
	protected static final int WINDOW_HEIGHT = 600; // �E�B���h�E�̍����̒萔

	// ���ϐ��Q
	protected BCanvas canvas; // ���C���̕`��������L�����o�X
	protected City city; // �X�̃C���X�^���X
	protected int nowX = 0; // ���݂̎��_��X���W
	protected int nowY = 0; // ���݂̎��_��Y���W

	// �����\�b�h�Q
	// �N������
	public static void main(String[] args) {
		JimiCity jimiCity = new JimiCity();
		jimiCity.main();
	}

	// ���C��
	private void main() {
		initialize();
		routineWork();
	}

	// ����������
	protected void initialize() {
		// �E�B���h�E�𐶐�����
		BWindow window = new BWindow();
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.show();
		canvas = window.getCanvas();
		resetCity();
	}

	// �s�s�̏�����
	protected void resetCity() {
		city = new City(60, 80, 64, 18, 256, 8);
	}

	// ��{�I�ȏ����̗���
	protected void routineWork() {
		int redevelopmentCounter = 0; // ���̊X���ĊJ�����Ă���o�߂������Ԃ̃J�E���^�[

		// �Ƃ肠�����ŏ��ɍĕ`�悵�Ă���
		redraw();

		// �������[�v
		while (true) {
			// ����̃��[�v�ōĕ`�悪�K�v���ǂ����`�F�b�N����
			boolean isNeedRedraw = false;

			// �L�[�{�[�h�̓��͏������`�F�b�N
			isNeedRedraw = checkKeyboard();

			// �ĊJ���J�E���^���{�P���āA
			redevelopmentCounter++;
			// ����400���܂��Ă�����A�ꕔ�n��̍ĊJ�������{����
			if (redevelopmentCounter > 400) {
				redevelopmentCounter = 0;
				city.redevelopment(0.001); // 0.1���̒n����ĊJ������
				isNeedRedraw = true; // �ĊJ����̌i�F���ĕ`��
			}
			// �ĕ`�悪�K�v�ȏꍇ�͍ĕ`��
			if (isNeedRedraw)
				redraw();
		}
	}

	// �ĕ`��
	protected void redraw() {
		// �X�̌i�F��`�悷��
		city.draw(canvas, nowX, nowY, WINDOW_WIDTH, WINDOW_HEIGHT);

		// ������k��\���R���p�X��`�悷��
		canvas.drawImage("res/news.gif", 0, 0);

		// ���݂̎��_�̍��W�ʒu��`�悷��
		String s = "(" + nowX + "," + nowY + ")";

		/*
		 * (�{���͐F���w�肵�ĉe���������������A�g�p�����ꂽ���C�u�����̈ꗗ��
		 * java.awt.*���Ȃ��Ajava.awt.color���g�p�֎~����Ă��邽�߁Anull���w�肵�āA���F�݂̂ŕ������`�悵��)
		 */
		canvas.drawText(null, s, 31, 61);
		canvas.drawText(null, s, 30, 60);
		canvas.update();
	}

	// �L�[�{�[�h�̃L�[��������Ă��鎞�̏���(�ĕ`�悪�K�v���ǂ�����l�Ƃ��ĕԂ�)
	protected boolean checkKeyboard() {
		// ���ĕ`�悪�K�v���ǂ����̃t���O
		boolean isNeedRedraw = false;

		// ���\���L�[�Ŏ��_���ړ�����
		if (canvas.getKeyCode() == 37) {// [��]�L�[
			isNeedRedraw = true;
			nowX--; // ���Ɉړ�
			if (nowX < 0)
				nowX = 0;
		}
		if (canvas.getKeyCode() == 38) {// [��]�L�[
			isNeedRedraw = true;
			nowY -= 2;// ��Ɉړ�
			if (nowY < 0)
				nowY = 0;
		}
		if (canvas.getKeyCode() == 39) {// [��]�L�[
			isNeedRedraw = true;
			nowX++;// �E�Ɉړ�
			if (nowX > city.getWidth() - 2 - WINDOW_WIDTH / city.getTileWidth())
				nowX = city.getWidth() - 2 - WINDOW_WIDTH / city.getTileWidth();
		}
		if (canvas.getKeyCode() == 40) {// [��]�L�[
			isNeedRedraw = true;
			nowY += 2;// ���Ɉړ�
			if (nowY > city.getHeight() - 1 - WINDOW_HEIGHT
					/ city.getTileHeight())
				nowY = city.getHeight() - 1 - WINDOW_HEIGHT
						/ city.getTileHeight();
		}

		// �����������Ɏ��s���Ăق����Ȃ�������switch�Ŕ���
		switch (canvas.getKeyCode()) {
		case 32:// [SPACE]�L�[
			// �s�s������
			city.urbanize(); // �s�s��
			city.redevelopment(0.1);// 10%�̋��𐮗�����
			isNeedRedraw = true;
			break;
		case 66:// [B]�L�[
		case 98:// [b]�L�[
			// �n��������
			city.localize();// �n����
			city.redevelopment(0.1);// 10%�̋��𐮗�����
			isNeedRedraw = true;
			break;
		case 87:// [W]�L�[
		case 119:// [w]�L�[
			// �s�s���x���̏�����
			city.resetCityLevel();
			break;
		case 82:// [R]�L�[
		case 114:// [r]�L�[
			// ��搮��
			city.redevelopment(0.1);
			isNeedRedraw = true;
			break;
		case 47:// [/]�L�[
			// �V�e�B�Z���^�[���ʂ̗L����؂�ւ���
			city.switchHasCityCenter();
			break;
		case 81:// [Q]�L�[
		case 113:// [q]�L�[
			// �s�s�̏�����
			resetCity();
			isNeedRedraw = true;
			break;
		}

		// �ĕ`�悪�K�v���ǂ����ԋp
		return isNeedRedraw;
	}
}
