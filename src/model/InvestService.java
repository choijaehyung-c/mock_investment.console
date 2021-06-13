package model;

public class InvestService {
	DataAccess dao;

	public String entrance(int serviceCode, String idSession, String asset) {
		dao = new DataAccess();
		
		String result = null;
		switch (serviceCode) {
		
		case 1:
			result = userData(idSession);
			break;
			
		case 2:
			investConfirm(idSession,asset);
			break;

		}

		return result;
	}

	
	private String userData(String idSession) {
		
		String stringData = dao.fullData();
		String[] fullData = new String[stringData.split("@").length];
		String data = null;
		
		for(int i=0; i<fullData.length; i++) {
			fullData[i]=stringData.split("@")[i];	
		}
		
		
		for(int i = 0 ; i < fullData.length ; i++) {
			if(fullData[i] != null) {
			if(fullData[i].split(",")[0].equals(idSession)) {
				data = fullData[i];
			}
			}
		}
		

		return data;
	}

	
	private void investConfirm(String idSession , String asset) {
		StringBuffer sb = new StringBuffer();
		sb.delete(0,sb.length());
		String stringData = dao.fullData();
		String[][] fullData = new String[stringData.split("@").length][3];
		
		for(int i = 0 ; i < stringData.split("@").length ; i++) {
			
			for(int ii = 0 ; ii < 3 ; ii++) {
				
				fullData[i][ii]=stringData.split("@")[i].split(",")[ii];
				
			}
			
		}
		
		for(int i = 0 ; i < fullData.length ; i++) {
			
			if(fullData[i][0].equals(idSession)) {
				fullData[i][1] = asset.split(",")[1];
				fullData[i][2] = asset.split(",")[2];
			}
			
		}
		
		
		for(int i = 0 ; i < fullData.length ; i++) {
			
			for(int ii = 0 ; ii < 3 ; ii++) {
				sb.append(fullData[i][ii]+",");
			}
			
			sb.delete(sb.length()-1, sb.length());
			sb.append("\n");
		}
		
		
		String data = sb.toString();
		dao.manageAccount(data);
		
	}


}