package mealplanner;

import java.util.Calendar;


public class TextParser {
	public TextParser(){

	}
	public boolean SetIngrediants(Dish d, String line){
		Dish dish = d;
		String [] splitByCommas = line.split(",");
		if(splitByCommas.length==0){
			return false; //empty line is a problem
		}
		if(splitByCommas.length==1 && splitByCommas[0].equals("Nutrition Facts")){
			return false; //no ingrediants just the nutrition facts header
		}
		for(int x=0;x<splitByCommas.length;x++){
			//add the ingrediants, getting rid of any excess whitespace
			dish.addIngrediant(splitByCommas[x].trim());
		}

		return true;
	}

	public boolean setNutritionFacts(Dish d, String line){
		Dish dish = d;
		//parse and set nutrition facts
					String [] allamounts = line.split("gm"); //split up the line by the (gm) text
					String [] amounts = allamounts[(allamounts.length)-1].split(" "); //using the last thing in the array, split up the numbers by spaces
					try{
						for(int i =(amounts.length)-8;i<amounts.length;i++){ //check to make sure that this is formatted correctly
								Double.valueOf(amounts[i]);
						}
					}catch(java.lang.NumberFormatException e){ // this means that there is a problem as we should have enough doubles at the end
						//to map to nutrition facts. This happens when there are not enough doubles and a string gets included.
						return false;
					}catch(Exception any){ //again, this means there was a problem
						return false;
					}
						dish.setPortion(amounts[1].trim());
						int counter =0;
						for(int i =(amounts.length)-8;i<amounts.length;i++){
							switch(counter){
							case 0:{dish.setCalories(Double.valueOf(amounts[i]));
									break;
							}
							case 1: {dish.setFat(Double.valueOf(amounts[i]));
									break;
							}
							case 2: {dish.setSaturatedFat(Double.valueOf(amounts[i]));
									break;
							}
							case 3: {dish.setChol(Double.valueOf(amounts[i]));
									break;
							}
							case 4:{ dish.setSodium(Double.valueOf(amounts[i]));
									break;
							}
							case 5: {dish.setCarbs(Double.valueOf(amounts[i]));
									break;
							}
							case 6: {dish.setFiber(Double.valueOf(amounts[i]));
									break;
							}
							case 7: {dish.setProtein(Double.valueOf(amounts[i]));
								break;
							}

							}
							counter ++;
						}
		return true;
	}
	//returns -1 on error, 1 if this is a new date, and 0 if the date is not new
	public Calendar setDate(Dish d, String line,Calendar curdate){
          Calendar zeroDate = Calendar.getInstance();
          zeroDate.set(0, 0, 0);
		Dish dish =d;
		String [] parts =line.split("/");
		if(parts.length < 3){
			//there is a problem we can't make a date from this line
			return null;
		}
		//note that months are zero indexed for some weird reason
		Calendar date = Calendar.getInstance();

                //date.set(Integer.valueOf(parts[2]),(Integer.valueOf(parts[0])-1),Integer.valueOf(parts[1]));
                date.set(Calendar.MONTH, (Integer.valueOf(parts[0])-1));
                date.set(Calendar.DAY_OF_MONTH, Integer.valueOf(parts[1]));
                date.set(Calendar.HOUR, 0);
                date.set(Calendar.MINUTE, 0);
                date.set(Calendar.SECOND,0);
                date.set(Calendar.MILLISECOND,0);
		if(curdate.equals(zeroDate)){ //the first time through, curdate has not been set
			curdate = date;
			return curdate; //
		}else{
			if(curdate.equals(date)==true){
				//then this is the same meal
				return curdate;
			}else{
				//this is a new meal, as the date is different
				curdate = date;
				return curdate;
			}
		}
	}
}
