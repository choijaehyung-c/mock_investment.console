package view;

import java.io.IOException;
import java.util.Scanner;

import controll.*;

public class View {
	StringBuffer sb;
	Scanner sc;
	Controll ctl;
	String title = setFrame("\t\tmock_investment");

	public View() {
		ctl = new Controll();
		sc = new Scanner(System.in);
		this.controll();
	}

	public void controll() {

		int page = 0;
		String idSession = null;

		while (true) {

			switch (page) {

			case 0:

				page = case0();
				//EWEW\//fdsfdsdsf
				break;

			case 1: // 회원가입

				page = case1(); // 페이지 : 성공시2 , 실패시 1 or 2 선택

				break;

			case 2: // 로그인

				String resultnpage = case2(); // 페이지 , 아이디세션

				page = Integer.parseInt(resultnpage.split(",")[0]); // 페이지 : 성공시 3 , 실패시 2

				idSession = resultnpage.split(",")[1];

				break;

			case 3:

				page = mainPage();

				break;

			case 4:
				page = inform();
				break;
			case 5:
				page = trade();
				break;
			case 6:
				page = this.myAsset(idSession);
				break;

			case 7:
				page = this.fStatements();
				break;
			case 8:
				page = news();
				break;

			case 9:
				page = buying(idSession);
				break;

			case 10:
				page = selling(idSession);
				break;
			}

			if (page == 99) { // 종료

				show("프로그램 종료");
				break;
			}
		}

	}

	private int case0() {

		String selec;
		int page = 0;
		selec = startPage(); // 1. 로그인 2. 회원가입 3. 종료 중 답변
		if (selec.equals("2")) { // 만약에 답이 2번이라면 회원가입
			page = 1;
		}

		if (selec.equals("1")) { // 1번이면 로그인
			page = 2;
		}
		if (selec.equals("3")) {
			show(this.setFrame("\t시스템을 종료합니다."));
			page = 99;
		}
		return page;
	}

	private int case1() {

		String data = null;
		String join;
		int page = 0;
		show(title);
		data = join();
		join = ctl.entrance("join", data);

		if (join.equals("true")) { // 성공
			show(this.setFrame("\t" + data.split(",")[0] + "님의 회원가입을 축하합니다."));
			page = 2;
			
		}

		if (join.equals("false")) {// 실패
			String yn1;
			show(this.setFrame("\t아이디가 이미 존재합니다."));

			while (true) {
				yn1 = userInput("로그인 하시겠습니까? y/n");
				if (orValidity(yn1, "y", "n")) {
					break;
				}
				show("잘못입력하셨습니다.");
			}

			page = 1;

			if (yn1.equals("y")) {
				page = 2;

			}

		}

		return page;

	}

	private String case2() {
		int page = 0;

		show(title);

		String login = login_Data();
		String idSession = runLogin(login); // 아이디

		if (!idSession.equals(login.split(",")[0])) {// 실패
			show(this.setFrame("\t로그인에 실패했습니다 다시 시도해주세요."));
			page = 2;
		}

		if (idSession.equals(login.split(",")[0])) {// 성공
			page = 3;
			show(this.setFrame("\t" + idSession + "님께서 로그인하셨습니다."));
		}

		return page + "," + idSession;
	}

	private int mainPage() {
		int page = 0;
		this.show(this.setFrame("\t\t메뉴선택"));
		String selec = null;

		while (!numValidity(selec, 4)) {
			selec = this.userInput(this.setFrame("    1.주식정보    2.매매    3.내 자산    4.종료"));
		}

		if (selec.equals("1")) {
			page = 4;
		}

		if (selec.equals("2")) {
			page = 5;
		}

		if (selec.equals("3")) {
			page = 6;
		}

		if (selec.equals("4")) {
			page = 99;
		}

		return page;

	}

	private int trade() {
		String selec;
		int page = 0;
		selec = "";

		this.show(this.setFrame("\t\t 2.매매Page"));
		while (!this.numValidity(selec, 3)) {
			selec = this.userInput("1.매수\n2.매도\n3.메인페이지로");
		}
		if (selec.equals("1")) {
			page = 9;
		}
		if (selec.equals("2")) {
			page = 10;
		}
		if (selec.equals("3")) {
			page = 3;
		}

		return page;
	}

	private int buying(String idSession) {
		String asset = ctl.entrance("myAsset", idSession, "null");
		String buyCount = null;
		int page = 0;
		String selec = null;
		String yn = "a";
		String[] stockName = { "", "카카오", "삼성전자", "두산", "현대차", "네이버" };
		this.show(this.setFrame("\t\t 매수"));

		while (!this.numValidity(selec, 7)) {
			selec = this.userInput("1.카카오\n2.삼성전자\n3.두산\n4.현대차\n5.네이버\n6.이전페이지로\n7.메인페이지로\n\n");
		}
		int selecInt = Integer.parseInt(selec);
		if (this.numValidity(selec, 5)) {

			show(stockName[selecInt] + "/1주 : " + ctl.entrance("sPrice", stockName[selecInt]).replace(".00", "") + "원");

			// ctl.entrance("stock", stockName[Integer.parseInt(selec)]);

			show("현금 : " + asset.split(",")[1] + "원 보유");

			while (!this.numValidity2(buyCount)) {
				buyCount = this.userInput("\n\n몇주를 구매하시겠습니까?\n");
			}

			long stockPrice = (Long
					.parseLong(ctl.entrance("sPrice", stockName[selecInt]).replace(".00", "").replace(",", ""))
					* Integer.parseInt(buyCount));

			while (!this.orValidity(yn, "y", "n")) {
				yn = this.userInput(buyCount + "주 : " + stockPrice + "원 구매확정 y/n ");
			}

			if (yn.equals("y")) {

				long cash = ((Long.parseLong(asset.split(",")[1])) - stockPrice);
				String userStock = asset.split(",")[2].split("_")[selecInt - 1];

				if (!(cash < 0)) {
					asset = asset.replace(asset.split(",")[1], cash + "");
					asset = asset.replace(userStock, stockName[selecInt] + ":"
							+ (Integer.parseInt((userStock.split(":")[1])) + Integer.parseInt(buyCount)));

					ctl.entrance("manageAccount", idSession, asset);

					show(this.setFrame("매수 성공"));

					page = 6;

				} else {

					show("보유현금이 부족합니다. 구매실패\n");
					page = 6;

				}

			} else {
				show("\n 매수페이지로 돌아갑니다.\n");
				page = 9;
			}
		}

		if (selec.equals("6")) {
			page = 5;
		}
		if (selec.equals("7")) {
			page = 3;
		}

		return page;
	}

	private int selling(String idSession) {
		String asset = ctl.entrance("myAsset", idSession, "null");
		String[] stockName = { "", "카카오", "삼성전자", "두산", "현대차", "네이버" };
		String sellCount = null;
		int page = 0;
		String selec = "";
		this.show(this.setFrame("\t\t 매도"));
		String yn = "";

		while (!this.numValidity(selec, 7)) {
			selec = this.userInput("1.카카오\n2.삼성전자\n3.두산\n4.현대차\n5.네이버\n6.이전페이지로\n7.메인페이지로");
		}
		int selecInt = Integer.parseInt(selec);

		if (this.numValidity(selec, 5)) {

			if (!(asset.split(",")[2].split("_")[selecInt- 1].split(":")[1].equals("0"))) {
				show(stockName[selecInt] + " : " + asset.split(",")[2].split("_")[selecInt - 1].split(":")[1]
						+ "주 보유\n");

				while (!this.numValidity2(sellCount)) {
					sellCount = this.userInput("몇주 판매 하시겠습니까?\n");
				}

				long stockPrice = (Long
						.parseLong(ctl.entrance("sPrice", stockName[selecInt]).replace(".00", "").replace(",", ""))
						* Integer.parseInt(sellCount));

				while (!this.orValidity(yn, "y", "n")) {
					yn = this.userInput(sellCount + "주 : " + stockPrice + "원 판매확정 y/n ");
				}

				if (yn.equals("y")) {

					long cash = ((Long.parseLong(asset.split(",")[1])) + stockPrice);
					String userStock = asset.split(",")[2].split("_")[selecInt - 1];

					if ((Integer.parseInt((userStock.split(":")[1])) - Integer.parseInt(sellCount)) >= 0) {

						asset = asset.replace(asset.split(",")[1], cash + "");
						asset = asset.replace(userStock, stockName[selecInt] + ":"
								+ (Integer.parseInt((userStock.split(":")[1])) - Integer.parseInt(sellCount)));

						ctl.entrance("manageAccount", idSession, asset);

						show(this.setFrame("매도 성공"));

						page = 6;

					} else {

						show("\n보유 주식이 부족합니다. 판매실패\n");
						page = 6;

					}
				} else {
					show("\n 매도페이지로 돌아갑니다.\n");
					page = 10;

				}

			} else {
				show("\n보유한 주식이 없습니다.\n");
				page = 6;
			}
		}

		if (selec.equals("5")) {
			page = 5;
		}
		if (selec.equals("6")) {
			page = 3;
		}

		// 주식갯수가 늘어날수있음

		return page;
	}

	private int inform() {
		int page = 0;
		String select = null;
		this.show(this.setFrame("\t\t주식정보"));
		select = "aa";

		while (!numValidity(select, 3)) {
			select = this.userInput(this.setFrame("1. 재무재표  2. 뉴스  3. 메인 page로 돌아가기  "));
		}

		if (select.equals("1")) {
			page = 7;
		}
		if (select.equals("2")) {
			page = 8;
		}
		if (select.equals("3")) {
			page = 3;
		}

		return page;

	}

	private int fStatements() {
		int page = 0;
		String select = "null";
		String select2 = "null";
		this.show(this.setFrame("\t\t 재무제표"));

		while (!numValidity(select, 7)) {
			select = this.userInput("1.카카오\n2.삼성전자\n3.두산\n4.현대차\n5.네이버\n6.이전페이지\n7.메인으로 돌아가기");
		}

		if (select.equals("1")) {
			this.show(this.setFrame("\t카카오\t 재무제표"));
			this.fsDetail("카카오");
		}
		if (select.equals("2")) {
			this.show(this.setFrame("\t삼성전자\t 재무제표"));
			this.fsDetail("삼성전자");
		}
		if (select.equals("3")) {
			this.show(this.setFrame("\t두산\t 재무제표"));
			this.fsDetail("두산");
		}
		if (select.equals("4")) {
			this.show(this.setFrame("\t현대차\t 재무제표"));
			this.fsDetail("현대차");
		}
		if (select.equals("5")) {
			this.show(this.setFrame("\t네이버\t 재무제표"));
			this.fsDetail("네이버");
		}

		if (Integer.parseInt(select) >= 1 && Integer.parseInt(select) <= 5) {

			while (!numValidity(select2, 3)) {
				select2 = this.userInput("\n 1.다른 기업보기\n 2.이전페이지\n 3.메인으로 돌아가기");
			}

			if (select2.equals("1")) {
				page = 7;
			}
			if (select2.equals("2")) {
				page = 4;
			}
			if (select2.equals("3")) {
				page = 3;
			}
		}

		if (select.equals("6")) {
			page = 4;
		}
		if (select.equals("7")) {
			page = 3;
		}

		return page;
	}

	private void fsDetail(String sName) {

		String fsData = ctl.entrance("fStatement", sName);

		for (int i = 0; i < fsData.split("::").length; i++) {

			show(fsData.split("::")[i] + "\n\n");

		}

	}

	private int news() {
		int page = 0;
		String select = null;

		String news;
		this.show(this.setFrame("\t\t [뉴스]"));

		while (!numValidity(select, 4)) {
			select = this.userInput("1.뉴스보기\n2.특정기업 뉴스보기\n3.이전페이지\n4.메인으로 돌아가기\n\n");
		}
		if (select.equals("1")) {
			String select2 = null;
			String select3 = null;

			while (true) {
				news = ctl.entrance("news", "null");
				show(news.split("::!@")[0]);

				while (!numValidity(select2, Integer.parseInt(news.split("::!@")[1]))) {
					select2 = this.userInput("\n[뉴스 선택] : ");
				}

				show("\n\n" + this.setFrame("\t[뉴스]") + "\n");
				show(newsContent(select2) + "\n");

				while (!numValidity(select3, 3)) {
					select3 = this.userInput("1.다른 뉴스보기\n 2.이전페이지\n 3.메인으로 돌아가기\n");
				}

				if (select3.equals("2")) {
					page = 4;
					break;
				}

				if (select3.equals("3")) {
					page = 3;
					break;
				}

				select2 = null;
				select3 = null;
			}
		}

		if (select.equals("2")) {
			String select2 = null;
			String select3 = null;
			String select4 = null;
			String[] stockName = { "카카오", "삼성전자", "두산", "현대차", "네이버" };

			while (!numValidity(select4, 5)) {
				select4 = this.userInput("\n1.카카오\n2.삼성전자\n3.두산\n4.현대차\n5.네이버\n");
			}

			while (true) {
				news = ctl.entrance("news", stockName[Integer.parseInt(select4) - 1]);
				show(news.split("::!@")[0]);

				while (!numValidity(select2, Integer.parseInt(news.split("::!@")[1]))) {
					select2 = this.userInput("\n[뉴스 선택] : ");
				}

				show("\n\n" + this.setFrame("\t[뉴스]") + "\n");
				show(newsParticular(select2 + ":" + select4) + "\n");

				while (!numValidity(select3, 3)) {
					select3 = this.userInput("1.다른 뉴스보기\n 2.이전페이지\n 3.메인으로 돌아가기\n");
				}

				if (select3.equals("2")) {
					page = 4;
					break;
				}

				if (select3.equals("3")) {
					page = 3;
					break;
				}

				select2 = null;
				select3 = null;
			}
		}

		if (select.equals("3")) {
			page = 4;
		}

		if (select.equals("4")) {
			page = 3;
		}

		return page;
	}

	private String newsContent(String data) {
		return ctl.entrance("newsContent", data);
	}

	private String newsParticular(String data) {
		return ctl.entrance("newsParticular", data);
	}

	private int myAsset(String idSession) {
		int page = 0;
		String selec = null;
		String asset = ctl.entrance("myAsset", idSession, "null");
		long cash = Long.parseLong(asset.split(",")[1]);
		long totalAsset = 0;
		
		show(this.setFrame("\t\t내 자산")+"\n");
		
		show("현금 자산 : " + cash + "원\n\n");

		// show(ctl.entrance(3,asset.split(",")[2].split("_")[0].split(":")[0]) );
		// show(ctl.entrance(3,asset.split(",")[2].split("_")[0].split(":")[0]).split(".")[0]);
		// show(ctl.entrance(3,asset.split(",")[2].split("_")[0].split(":")[0]).replace(".",""));

		for (int i = 0; i < asset.split(",")[2].split("_").length; i++) {
			totalAsset += (Long.parseLong(ctl.entrance("sPrice", asset.split(",")[2].split("_")[i].split(":")[0])
					.replace(".00", "").replace(",", ""))
					* Long.parseLong(asset.split(",")[2].split("_")[i].split(":")[1]));
		}
		
		show("총자산 : " + (totalAsset + cash) + "원\n\n");
		show("-----보유 주식-----\n\n");
		for (int i = 0; i < asset.split(",")[2].split("_").length; i++) {

			if (!asset.split(",")[2].split("_")[i].split(":")[1].equals("0")) {

				show(asset.split(",")[2].split("_")[i].split(":")[0] + "_"
						+ asset.split(",")[2].split("_")[i].split(":")[1] + "주  현재 가치 : "
						+ Long.parseLong(ctl.entrance("sPrice", asset.split(",")[2].split("_")[i].split(":")[0])
								.replace(".00", "").replace(",", ""))
						* Long.parseLong(asset.split(",")[2].split("_")[i].split(":")[1])
						+ "원\n\n");

			}

		}

		while (!numValidity(selec, 3)) {
			selec = this.userInput("   1.주식 정보   2.매매   3.메인페이지 ");
		}

		if (selec.equals("1")) {
			page = 4;
		}

		if (selec.equals("2")) {
			page = 5;
		}

		if (selec.equals("3")) {
			page = 3;
		}

		return page;
	}

	private String runLogin(String logData) {
		return ctl.entrance("login", logData);
	}

	private String login_Data() {
		String id, pw, data;
		id = userInput("아이디를 입력해주세요.");
		pw = userInput("패스워드를 입력해주세요.");

		data = id + "," + pw;
		return data;
	}

	private String startPage() {
		show(title);
		String selec = null;

		while (!this.numValidity(selec, 2)) {
			selec = userInput(" 로그인 1번 , 회원가입 2번 : ");
		}

		return selec;
	}

	// 회원가입 job
	private String join() {

		String id, pw, account, data;

		show(this.setFrame("\t\t회원가입"));

		id = (this.userInput("\t[ID]입력\n\t(글자수 5~20 & 영문,숫자) : "));

		while (!this.idValidity(id)) {
			show("잘못 입력하셨습니다. 5자 이상 20자이하, 영문,숫자 조합으로 입력해주세요.");
			id = (this.userInput("[ID]입력 : "));
		}

		pw = (this.userInput("\t[Password]입력\n\t(글자수 6~30 & 영문대소문,숫자 2개 이상) : "));
		while (!this.PasswordValidity(pw)) {
			show("잘못 입력하셨습니다. 6자 이상 30자이하, 영어 대문자,소문자,숫자 중 2개 이상 조합으로 입력해주세요.");
			pw = (this.userInput("[Password]입력 : "));
		}

		account = (this.userInput("\t[계좌] 입력\n\t('-'제외한 8자리 숫자) : "));
		while (!this.accountValidity(account)) {
			show("잘못 입력하셨습니다. 숫자만 8자리 입력해주세요.");
			account = (this.userInput("[계좌]입력 : "));
		}

		data = id + "," + pw + "," + account;

		return data;

	}

	private String setFrame(String text) {
		sb = new StringBuffer();
		String title;
		sb.delete(0, sb.length());
		sb.append("┌────────────────────────────────────────────────┐\n");
		sb.append(text);
		sb.append("\n└────────────────────────────────────────────────┘\n");
		title = sb.toString();
		sb.delete(0, sb.length());
		return title;
	}

	// 아이디 유효성 검사 : 글자수 5자 이상 20자 이하 & 영어 대소문,숫자만
	private boolean idValidity(String input) {
		boolean result = false;
		int[] countTerms = new int[input.length()];
		int count = 0;

		for (int i = 0; i < input.length(); i++) {

			try {
				Integer.parseInt(input.substring(i, i + 1));
				countTerms[i] = 1;
			} catch (Exception e) {

			}

			if (Character.isUpperCase(input.charAt(i))) {
				countTerms[i] = 1;
			}

			if (Character.isLowerCase(input.charAt(i))) {
				countTerms[i] = 1;
			}

		}

		for (int i = 0; i < input.length(); i++) {
			count += countTerms[i];
		}

		if (input.length() > 4 && input.length() < 21 && count == input.length()) {
			result = true;
		}

		return result;
	}

	// 계좌 유효성 검사 : 글자수 8자 & 숫자
	private boolean accountValidity(String input) {
		boolean result = false;
		boolean Terms = true;

		for (int i = 0; i < input.length(); i++) {
			try {
				Integer.parseInt(input.substring(i, i + 1));
			} catch (Exception e) {
				Terms = false;
				break;
			}

		}

		if (input.length() == 8 && Terms) {
			result = true;
		}
		return result;
	}

	// 패스워드 유효성 검사 : 글자수 6자 이상 30자 이하 & 영어 대소문,숫자 2종류 이상
	private boolean PasswordValidity(String input) {
		boolean result = false;
		int[] countTerms0 = new int[3];

		for (int i = 0; i < input.length(); i++) {

			try {
				Integer.parseInt(input.substring(i, i + 1));
				countTerms0[0] = 1;
			} catch (Exception e) {

			}

			if (Character.isUpperCase(input.charAt(i))) {
				countTerms0[1] = 1;
			}

			if (Character.isLowerCase(input.charAt(i))) {
				countTerms0[2] = 1;
			}

		}

		int[] countTerms = new int[input.length()];
		int count = 0;

		for (int i = 0; i < input.length(); i++) {

			try {
				Integer.parseInt(input.substring(i, i + 1));
				countTerms[i] = 1;
			} catch (Exception e) {

			}

			if (Character.isUpperCase(input.charAt(i))) {
				countTerms[i] = 1;
			}

			if (Character.isLowerCase(input.charAt(i))) {
				countTerms[i] = 1;
			}

		}

		for (int i = 0; i < input.length(); i++) {
			count += countTerms[i];
		}

		int Terms = countTerms0[0] + countTerms0[1] + countTerms0[2];

		if (input.length() > 5 && input.length() < 31 && Terms > 1 && count == input.length()) {
			result = true;
		}

		return result;
	}

	// A or B 유효성 검사
	private boolean orValidity(String input, String A, String B) {

		boolean result = false;
		if (input.equals(A) || input.equals(B)) {
			result = true;
		}

		return result;

	}

	private boolean numValidity(String num, int range) {

		boolean result = false;

		try {
			if (Integer.parseInt(num) >= 1 && Integer.parseInt(num) <= range) {
				result = true;
			}
		}

		catch (Exception e) {
		}

		return result;
	}

	private boolean numValidity2(String num) {

		boolean result = false;

		try {
			Integer.parseInt(num);
			result = true;

		}

		catch (Exception e) {
			if (num != null) {
				show("숫자를 입력해주세요.\n");
			}
		}

		return result;
	}

	// 멘트 + 사용자 입력값을 받고 리턴
	private String userInput(String text) {
		this.show(text);
		return sc.next();
	}

	// 출력을 담당하는 메서드
	private void show(String text) {
		System.out.print(text);
	}

}