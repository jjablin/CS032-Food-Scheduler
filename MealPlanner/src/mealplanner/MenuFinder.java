package mealplanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuFinder {
	private Pattern _pat1;
	private Pattern _pat2;
	private Pattern _pat3;
	private Pattern _pat4;
	private Pattern _pat5;

	public MenuFinder(){ //any of five saved menus could be the current menu we want
		_pat1 = Pattern.compile("/savedmenus1/");
		_pat2 = Pattern.compile("/savedmenus2/");
		_pat3 = Pattern.compile("/savedmenus3/");
		_pat4 = Pattern.compile("/savedmenus4/");
		_pat5 = Pattern.compile("/avedmenus5/");
	}
	public String getMenu(String filepath){
		try {
			//initialize variables
			String tofind = "";
			Scanner fileScanner = new Scanner(new File(filepath));
			String line = "";
			Matcher matcher = _pat1.matcher(line);
			boolean found;
			while(fileScanner.hasNextLine()){
				line = fileScanner.nextLine();
				for(int x=1;x<6;x++){ //for every line in the text file, see if it contains any of the five matches above
					switch(x){
					case 1:
						matcher = _pat1.matcher(line);
						tofind="savedmenus1";
						break;
					case 2:
						matcher = _pat2.matcher(line);
						tofind="savedmenus2";
						break;
					case 3:
						matcher = _pat3.matcher(line);
						tofind="savedmenus3";
						break;
					case 4:
						matcher = _pat4.matcher(line);
						tofind="savedmenus4";
						break;
					case 5:
						matcher = _pat5.matcher(line);
						tofind="savedmenus5";
						break;
					}

					found = matcher.find();
					if(found==true){
						return tofind; //if you found one, stop looking and return it
					}
				}

			}
		} catch (FileNotFoundException e) {
			return null;
		}
		return null; //if you didn't find any of the five return null to show error
	}
}
