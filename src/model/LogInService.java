package model;

public class LogInService {
	DataAccess dao;
	

	public String entrance(int serviceCode, String userInfo) {
		
		String result = null;
		switch (serviceCode) {

		case 1:
			result = loginCtl(userInfo);
			break;

		}

		return result;
	}

	
	
	private String loginCtl(String userInfo) {
		dao = new DataAccess();
		String result = userInfo+"false";
		String check = this.idCheck(userInfo);
		
		if(!check.equals("-1")) {
			if(	passwordCheck(check,userInfo)) {
				result = userInfo.split(",")[0];
			}
			
		}
		
		return result;
	}
	
	
	private String idCheck(String j) {
		return  dao.check(j,"0");
	}
	
	
	private boolean passwordCheck(String idIndex,String j) {
		return  dao.pwCheck(idIndex,j);
	}
	
	
	
	
	
}
