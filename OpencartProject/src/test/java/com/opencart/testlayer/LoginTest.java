package com.opencart.testlayer;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.opencart.testbase.Testbase;
import com.opencart.utility.ExcelUtils;


public class LoginTest extends Testbase {

	@Test
	public void verifyLoginTestWithValidDetails() throws IOException
	{
		String exppected_result = "My Account";
		
		homepage_obj.clickOnMyAccounntLink();
		homepage_obj.clickOnLoginLink();
		logger.info("on login page");
		
		loginpage_obj.enterEmail(excel_obj.readData("LoginData", 1, 1));
		loginpage_obj.enterPassword(excel_obj.readData("LoginData", 1, 2));
		loginpage_obj.clickOnLoginButton();
		logger.info("login details entered");
		
		String actual_result = driver.getTitle();
		System.out.println(actual_result);
		Assert.assertEquals(actual_result, exppected_result);
	}
	
	@Test
	public void verifyLoginTestWithInValidDetails()
	{
		String exppected_result = "Warning: No match for E-Mail Address and/or Password.";
		
		homepage_obj.clickOnMyAccounntLink();
		homepage_obj.clickOnLoginLink();
		logger.info("on login page");
		
		loginpage_obj.enterEmail("davidtest14@gmail.com");
		loginpage_obj.enterPassword("Test");
		loginpage_obj.clickOnLoginButton();
		logger.info("login details entered");
		
		String actual_result = common_obj.getErrorMessage();
		System.out.println(actual_result);
		
		Assert.assertEquals(actual_result, exppected_result);
	}
	
	@DataProvider (name = "loginDataProvider")
	public String[][] loginDataSet() throws IOException
	{
		ExcelUtils excel_obj1= new ExcelUtils();
		int row_count = excel_obj1.getRowCount("LoginData");
		int cell_count = excel_obj1.getCellCount("LoginData", 1);
		
		System.out.println("Total Rows are " + (row_count+1) + " and Total Cells are " + cell_count);
		
		String data[][] = new String[row_count][cell_count];
		
		for(int i= 1; i<=row_count;i++)
		{
			for(int j=0; j<cell_count; j++)
			{
				data[i-1][j] = excel_obj1.readData("LoginData", i, j);
			}
		}	
		return data;
	}
	
	@Test(dataProvider = "loginDataProvider")
	public void test(String sr, String email, String password, String status)
	{
		String exppected_result = "My Account";
		
		homepage_obj.clickOnMyAccounntLink();
		homepage_obj.clickOnLoginLink();
		logger.info("on login page");
		
		loginpage_obj.enterEmail(email);
		loginpage_obj.enterPassword(password);
		loginpage_obj.clickOnLoginButton();
		logger.info("login details entered");
		
		String actual_result = driver.getTitle();
		System.out.println(actual_result);
		Assert.assertEquals(actual_result, exppected_result);
	}
	
}
