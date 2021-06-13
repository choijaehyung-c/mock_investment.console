package model;

public class CrawlingService {
	DataAccess dao;
	public String entrance(int serviceCode, String data) {
		String result = null;
		switch (serviceCode) {
		
		case 1: result = priceCrawling(data);
				break;
		
		case 2: result = newsCrawling(data);
				break;
		
		case 3: result = fsCrawling(data);
				break;
				
		case 4: result = newsContent(data);
				break;
				
		case 5: result = newsParticular(data);
				break;
				
		case 6: result = stockCrawling(data);
				break;

		}

		return result;
	}
	
	
	
	
	
	private String priceCrawling(String data) {
		dao = new DataAccess();
		
		return dao.priceCrawling(data);
	}
	
	private String fsCrawling(String data) {
		dao = new DataAccess();
		
		return dao.fsCrawling(data);
	}
	
	
	private String newsCrawling(String data) {
		dao = new DataAccess();
		return dao.newsCrawling(data);
	}
	
	
	private String newsContent(String data) {
		dao = new DataAccess();
		
		return dao.newsContent(data);
	}
	private String newsParticular(String data) {
		dao = new DataAccess();
		return dao.newsParticular(data);
	}
	
	private String stockCrawling(String data) {
		dao = new DataAccess();
		return dao.stockCrawling(data);
	}
	
}

