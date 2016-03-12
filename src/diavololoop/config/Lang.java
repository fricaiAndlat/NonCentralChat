package diavololoop.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Lang {
	
	private static Lang CURRENT_LANGUAGE;
	
	static{
		try {
			Lang.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Lang getCurrent(){
		return Lang.CURRENT_LANGUAGE;
	}
	public static void init() throws IOException{
		Lang.CURRENT_LANGUAGE = new Lang();
		Lang.CURRENT_LANGUAGE.load(new BufferedReader(new InputStreamReader(Lang.class.getResourceAsStream("language/Deutsch"))));
	}
	
	private HashMap<String, String> map = new HashMap<String, String>();
	
	public void load(BufferedReader input) throws IOException{
		for(String line = input.readLine(); line != null; line = input.readLine()){
			String[] keyValue = line.replaceAll("\t", "").split(":");
			map.put(keyValue[0], keyValue[1]);
		}
		
		
	}
	
	public String get(String key){
		return map.get(key);
	}
	
	public static void main(String[] args) throws IOException{
		Lang.init();
	}
	

}
