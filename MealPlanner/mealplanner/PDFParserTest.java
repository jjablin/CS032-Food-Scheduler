/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mealplanner;

import java.util.ArrayList;
import org.junit.Test;

/**
 *
 * @author kpdurfee
 */
public class PDFParserTest {
    public PDFParserTest() {
    }

    /**
     * Test of clearOld method, of class PDFParser.
     */
    @Test
    public void testGetCurrentEateryInfo() {
            PDFParser instance = new PDFParser();
            ArrayList result = new ArrayList();
            result = instance.getCurrentEateryInfo();
            assert(result.size()>0);
            assert(result.contains(null)==false);
    }
    @Test
    public void testGetEateryReaderNoSpaces() {
    //test a file that is not spaced out correctly
        EateryReader eread = new EateryReader();
        ArrayList list = new ArrayList();
        list = eread.ReadInEatery("test1.txt", "ivy");
        //incorrectly spaced filed should not return any dishes
        assert(list.size()==0);
    }
        @Test
    public void testGetEateryReader() {
    //test a small but correctly formatted file
        EateryReader eread = new EateryReader();
        ArrayList list = new ArrayList();
        list = eread.ReadInEatery("test2.txt", "ivy");
        //incorrectly spaced filed should not return any dishes
        assert(list.size()>0);
        assert(((Dish)(list.get(0))).getName().equals("COUNTRY WEDDING SOUP"));
        assert(((Dish)(list.get(0))).getIngredients().size()>5);
        assert(((Dish)(list.get(0))).getChol()==10.6);
    }
    @Test
    public void testGetEateryReaderNoIngrediants() {
    //test a small but correctly formatted file that leaves out the ingrediants (sometimes happens)
        EateryReader eread = new EateryReader();
        ArrayList list = new ArrayList();
        list = eread.ReadInEatery("test3.txt", "ivy");
        //incorrectly spaced filed should not return any dishes
        assert(list.size()>0);
        assert(((Dish)(list.get(0))).getName().equals("COUNTRY WEDDING SOUP"));
        assert(((Dish)(list.get(0))).getIngredients().size()==0);
        assert(((Dish)(list.get(0))).getChol()==10.6);
    }
        @Test
    public void testGetEateryReaderIncompleteNutrition() {
    //test a small but correctly formatted file that leaves out the nutrition info (sometimes happens)
        EateryReader eread = new EateryReader();
        ArrayList list = new ArrayList();
        list = eread.ReadInEatery("test4.txt", "ivy");
        //incorrectly spaced filed should not return any dishes
        assert(list.size()>0);
        assert(((Dish)(list.get(0))).getName().equals("COUNTRY WEDDING SOUP"));
        assert(((Dish)(list.get(0))).getIngredients().size()>0);
        assert(((Dish)(list.get(0))).getChol()==-1);
    }
    @Test
    public void testGetEateryReaderNoFile() {

        EateryReader eread = new EateryReader();
        ArrayList list = new ArrayList();
        list = eread.ReadInEatery("/tmp/gibberish.txt", "ivy");
        //this should throw an error message and return null
        assert(list==null);

    }

        @Test
    public void testGetEateryReaderNewFormat() {

        EateryReader eread = new EateryReader();
        ArrayList list = new ArrayList();
        list = eread.ReadInEatery("test5.txt", "ivy");
        //this should return null
        assert(list==null);

    }


}