package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class DataAccess {
	FileReader reader;
	BufferedReader bReader;
	FileWriter writer;
	BufferedWriter bWriter;
	StringBuffer sb;
	String[] fileInfo = { "C:\\Users\\chl\\Desktop\\workspace\\database2\\MemberShip.txt" , "C:\\Users\\chl\\Desktop\\workspace\\database2\\Account.txt"};
	String[] stockName = {"카카오","삼성전자","두산","현대차","네이버"};
	String[] item = {"kakao-corp","samsung-electronics-co-ltd","doosan","hyundai-motor","nhn-corp"};

	
	
	
	
	
	String fullData() {
		sb = new StringBuffer();
		sb.delete(0,sb.length());
		this.fileReading(1);

		String[] reading = new String[100];
		int count = 0;

		try {

			while ((reading[count] = bReader.readLine()) != null) {
				count++;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		readerClose();
		for(int i = 0 ; i<reading.length; i++) {
			if(reading[i] != null) {
		 sb.append(reading[i]+"@");
			}
		}
		sb.delete(sb.length()-1, sb.length());
		return sb.toString();
	}
	
	void manageAccount(String data) {
		
		this.fileWriter(1, false);
		try {
			bWriter.write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.writerClose();
		
	}

	private void fileReading(int fileIndex) {
		try {
			reader = new FileReader(fileInfo[fileIndex]);
			bReader = new BufferedReader(reader);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	String priceCrawling(String data) {
		int itemIndex = 0;
		String price = null;
		
		for(int i = 0 ; i < stockName.length; i++) {
			if (stockName[i].equals(data)) {
				itemIndex=i;
			}
		}
		
	 	try {
	 	
			Document doc = Jsoup.connect("https://kr.investing.com/equities/"+item[itemIndex]).get();
			price= doc.select(".instrument-price_last__KQzyA").text();
		}
	 	catch (IOException e) {
		}
		return price;
	}
	
	String stockCrawling(String data) {
		sb = new StringBuffer();
		sb.delete(0,sb.length());
		int itemIndex = 0;
		
		for(int i = 0 ; i < stockName.length; i++) {
			if (stockName[i].equals(data)) {
				itemIndex=i;
			}
		}
		
	 	try {
	 	
			Document doc = Jsoup.connect("https://kr.investing.com/equities/"+item[itemIndex]).get();
			sb.append(doc.select(".instrument-price_last__KQzyA").text()+"::");
			sb.append(doc.select(".instrument-price_change__3UTmm>span").text()+"::");
			sb.append(doc.select("trading-hours_value__2MrOn[data-test=\"volume-value\"]").text()+"::");
			sb.append(doc.select("trading-hours_value__2MrOn[data-test=\"range-value\"]").text());

		} catch (IOException e) {
		}
	 	
	 	return sb.toString();
	}
	
	String fsCrawling(String data) {
		String fsData = null;
		int itemIndex = 0;
		
		for(int i = 0 ; i < stockName.length; i++) {
			if (stockName[i].equals(data)) {
				itemIndex=i;
			}
		}
		
	 	try {
	 	
			Document doc = Jsoup.connect("https://kr.investing.com/equities/"+item[itemIndex]).get();
			fsData = doc.select("[data-test=\"key-info\"]").text();

		} catch (IOException e) {
		}
	 	
		fsData = fsData.replace("금일 변동","::금일 변동");
		fsData = fsData.replace("매출","::매출");
		fsData = fsData.replace("금일 시가","::금일 시가");
		fsData = fsData.replace("52주 변동폭","::52주 변동폭"); 
		fsData = fsData.replace("거래량","::거래량");
		fsData = fsData.replace("총 시가","::총 시가"); 
		fsData = fsData.replace("배당금","::배당금");
		fsData = fsData.replace("평균 거래량","::평균 거래량"); 
		fsData = fsData.replace("주가수익비율","::주가수익비율");
		fsData = fsData.replace("베타","::베타"); 
		fsData = fsData.replace("1년 변동률","::1년 변동률");
		fsData = fsData.replace("발행주식수","::발행주식수"); 
		fsData = fsData.replace("다음 수익일자","::다음 수익일자");
	 	return fsData;
	}
	
	String newsCrawling(String data) {
		Elements newsList = null;
		sb = new StringBuffer();
		sb.delete(0,sb.length());
		String[] particular = {"kakao-corp","samsung-electronics-co-ltd","doosan","hyundai-motor","nhn-corp"};
		int index = 0;
		int count = 0;
		for(int i = 0 ; i < stockName.length ; i++) {
			
			if(stockName[i].equals(data)) {
				index=i;
				break;
			}
			
			index=-1;
		}
		
		if(index==-1) {
	 	try {
			Document doc = Jsoup.connect("https://kr.investing.com/news/most-popular-news").get();
			newsList = doc.select(".title");

		} catch (IOException e) {
		}
	 	
	 	for(Element news : newsList ) {
	 		
	 		if(news.toString().contains("Investing.com")) {break;}
	 		count++;
	 		sb.append(count+". "+news.text()+"\n");
	 		
	 	}
	 	
		}else{
			try {
				Document doc = Jsoup.connect("https://kr.investing.com/equities/"+particular[index]+"-news").get();
				newsList = doc.select(".js-external-link");

			} catch (IOException e) {
			}
			
		 	for(Element news : newsList ) {
		 		
		 		if(!news.toString().contains("img")) {
		 			count++;
		 			sb.append(count+". "+news.text()+"\n");
		 			
		 		}
		 		
		 		
		 		
		 	}
		}
		
	 	
		
		sb.append("::!@"+count);
		
		
		return sb.toString();
	}
	
	
	
	String newsContent(String data) {
		Elements newsList = null;
		Elements newsContent = null;
		sb = new StringBuffer();
		sb.delete(0,sb.length());
		String link =null;
		
	 
		
		 	try {
				Document doc = Jsoup.connect("https://kr.investing.com/news/most-popular-news").get();
				newsList = doc.select(".title");

			} catch (IOException e) {
			}
		
	 	int count = 0;
	 	
	 	for(Element news : newsList ) {
	 		
	 		count++;
	 		if(count == Integer.parseInt(data)) {
	 			link = news.attr("href");
	 			break;
	 		}
	 		
	 		
	 	}
	 	
	 	try {
	 		
			Document doc = Jsoup.connect("https://kr.investing.com/"+link).get();
			sb.append("["+doc.select(".articleHeader").text()+"]\n\n");

		} catch (IOException e) {
		}
	 	
	 	try {
	 		
			Document doc = Jsoup.connect("https://kr.investing.com/"+link).get();
			newsContent = doc.select(".WYSIWYG > p");

		} catch (IOException e) {
		}
	
	 	
	 	int count2 = 0;
		for(Element news1 : newsContent ) {
			
			if(news1.toString().contains("<p><a")) {
	 			break;
	 		}
			
			if(count2>0) {
	 		sb.append(news1.text()+"\n");
			}

	 		count2++;
	 		
	 	
	 		
	 	}
	 		
	 		
		
		return sb.toString();
	}
	
	String newsParticular(String data) {
		String[] particular = {"kakao-corp","samsung-electronics-co-ltd","doosan","hyundai-motor","nhn-corp"};
		Elements newsList = null;
		sb = new StringBuffer();
		sb.delete(0,sb.length());
		String link =null;
		
		try {
			Document doc = Jsoup.connect("https://kr.investing.com/equities/"+particular[Integer.parseInt(data.split(":")[1])-1]+"-news").get();
			newsList = doc.select(".js-external-link");

		} catch (IOException e) {
		}
		
		System.out.println(particular[Integer.parseInt(data.split(":")[1])-1]);
		
		int count=0;
		for(Element ee : newsList) {
			
			if(!ee.toString().contains("img")) {
				count++;
				if(count==Integer.parseInt(data.split(":")[0])) {
				link=(ee.attr("href")) ;
				}
		}
		}
		
		try {
			Document doc = Jsoup.connect(link).get();
			newsList = doc.select(".cont_art");

		} catch (IOException e) {
		}
		
		

		
		  for(int i = 0 ; i < newsList.text().length() ; i +=35) {
		  try {newsList.text().substring(i,i+35);
		  sb.append(newsList.text().substring(i,i+35)+"\n");
		  }
		  catch(Exception e) {
		  sb.append(newsList.text().substring(i,newsList.text().length())+"\n");
		  }
		  }
		 
		
		
		return sb.toString();
	}
	

	private void readerClose() {

		if (bReader != null) {
			try {
				bReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 데이터 확인
	String check(String j , String col) {
		int colnum = Integer.parseInt(col);
		boolean tf = false;
		this.fileReading(0);
		String[] reading = new String[100];
		int count = 0;

		try {

			while ((reading[count] = bReader.readLine()) != null) {
				count++;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		count = -1;
		
		for (int i = 0; i < reading.length; i++) {
			if (reading[i] != null) {
				count++;
				if (reading[i].split(",")[colnum].equals(j.split(",")[colnum])) {
					tf = true;
					break;
				}

			}
		}

		readerClose();
		
		if(tf == false) {
			count = -1;
		}
		
		return count+"";
	}
	
	boolean pwCheck(String idIndex ,String j) {
		
		boolean tf = false;
		this.fileReading(0);
		String[] reading = new String[100];
		int count = 0;

		try {

			while ((reading[count] = bReader.readLine()) != null) {
				count++;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				if (reading[Integer.parseInt(idIndex)].split(",")[1].equals(j.split(",")[1])) {
					tf = true;
				}
					
		readerClose();
		return tf;
		
	}

	private void fileWriter(int fileIndex, boolean add) {
		try {
			writer = new FileWriter(fileInfo[fileIndex], add);
			bWriter = new BufferedWriter(writer);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writerClose() {

		if (bWriter != null) {
			try {
				bWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	void join(String j) {
		this.fileWriter(0, true);

		try {
			bWriter.write(j + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		writerClose();

	}
	
	void join2(String j) {
		this.fileWriter(1, true);

		try {
			bWriter.write(j.split(",")[0]+","+"10000000,"+"카카오:0_삼성전자:0_두산:0_현대차:0_네이버:0"+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		writerClose();

	}

}
