package controll;

import model.*;

public class Controll {
	JoinService service1;
	LogInService service2;
	InvestService service3;
	CrawlingService service4;
	

	public String entrance(String classify, String data) {
		service1 = new JoinService();
		service2 = new LogInService();
		service3 = new InvestService();
		service4 = new CrawlingService();
		String result = null;
		int serviceCode = 0;
		String[] sCode = {"join","login","sPrice","myAsset","manageAccount","fStatement","news","newsContent","newsParticular","stock"};
		
		for(int i = 0 ; i < sCode.length ; i++) {
			if(sCode[i].equals(classify)) {
				serviceCode=(i+1);
				break;
			}
		}
		
		switch(serviceCode) {

		case 1:
			serviceCode=1;
			result = service1.entrance(serviceCode,data); //회원가입
			break;
			
		case 2:
			serviceCode=1;
			result = service2.entrance(serviceCode,data); //로그인
			break;
			
		case 3:
			serviceCode=1;
			result = service4.entrance(serviceCode,data); //주가크롤링
			break;
			
		case 7:
			serviceCode=2;
			result = service4.entrance(serviceCode,data); //뉴스크롤링
			break;
		
		case 8:
			serviceCode=4;
			result = service4.entrance(serviceCode,data); //뉴스내용
			break;
		case 9:
			serviceCode=5;
			result = service4.entrance(serviceCode,data); //특정기업 뉴스내용
			break;
		
		case 10:
			serviceCode=6;
			result = service4.entrance(serviceCode,data); // 특정 주식
			break;
			
		case 6:
			serviceCode=3;
			result = service4.entrance(serviceCode,data); // 재무제표
			break;
		}
		return result;
	}
	
	
	public String entrance(String classify, String idSession,String asset) {
		String result = null;
		int serviceCode = 0;
		String[] sCode = {"join","login","sPrice","myAsset","manageAccount","fStatement","news","newsContent","newsParticular"};
		
		for(int i = 0 ; i < sCode.length ; i++) {
			if(sCode[i].equals(classify)) {
				serviceCode=(i+1);
				break;
			}
		}
		
		switch(serviceCode) {

		case 4:
			serviceCode=1;
			result = service3.entrance(serviceCode,idSession,asset);// 자산확인
			break;
		
		case 5:
			serviceCode=2;
			service3.entrance(serviceCode,idSession,asset); // 자산기록
			break;
			
		}
		
		return result;
		
	}

}
