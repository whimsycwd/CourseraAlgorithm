public class Main {
	public static void main(String[] args) {
//		boolean isOpen = false;
//		assert isOpen = true; // ��������˶��ԣ��ὫisOpen��ֵ��Ϊtrue
//		System.out.println(isOpen);// ��ӡ�Ƿ����˶���
		int cnt = 0;
		outer:
			for (int i = 0;i<1000;++i){
				for (int j = 0;j<1000;++j){
					++cnt;
					break outer;
				}
			}
		System.out.println(cnt);
	}
}
