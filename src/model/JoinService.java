package model;


public class JoinService {
	DataAccess dao;


	public String entrance(int serviceCode, String userInfo) {
		String result = null;
		switch (serviceCode) {
		case 1:
			result = this.joinCtl(userInfo);
		}

		return result;
	}

	// 회원가입 컨트롤
	private String joinCtl(String j) {
		dao = new DataAccess();
		String result = "false";

		if (this.check(j).equals("-1")) {

			this.join(j);
			result = "true";

		}

		return result;

	}

	// 중복된 아이디 검사
	private String check(String j) {
		return dao.check(j, "0");
	}

	// 회원가입
	private void join(String j) {
		dao.join(j);
		dao.join2(j);
	}

}
