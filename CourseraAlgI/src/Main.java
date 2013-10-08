public class Main {
	public static void main(String[] args) {
//		boolean isOpen = false;
//		assert isOpen = true; // 如果开启了断言，会将isOpen的值改为true
//		System.out.println(isOpen);// 打印是否开启了断言
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
