package General;

/*
 * �v���O�������F	�v�Z�̂������̊֐�
 * �쐬�ҁF		�H�R���I
 * �R�����g�F		���ƂŎg�������C�u�����ȊO�͎g�p�֎~�ŁA
 * 				Math�N���X�����ƂŎg��Ȃ������悤�ȋC������̂ŁA�����ł�����
 */

public class Calculation {

	// distance�F�Q�_�Ԃ̋����𑪂�֐�
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

	// Math�N���X���g�p�֎~�Ɗ��Ⴂ���Ă������ߍ�����֐�(sqrt,pow)
	// ���x���P�����Ǝv�������v���V�[�{���ʂ�������Ȃ����߁A�g��Ȃ�
	/*
	 * //�������ɋ߂����������߂�֐� ���ӁF������P�ʈȉ����ׂĂ�؂�̂Ă�Ƃ��������܂������������������� public static int
	 * sqrt(int x) { int a = 0; // �Q�悵�����Ɉ�ԋ߂��l��T�� while (true) { a++; if (pow(a
	 * + 1, 2) > x) break; } return a; }
	 * 
	 * // �ݏ���v�Z����֐��iMath.pow�̑�p�i(int�^�Ȃ̂ŏ�������)�j public static int pow(int x, int
	 * n) { int tmp = x; while (--n > 0) tmp *= x; return tmp; }
	 */
}
