package org.vicrul.shop;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.vicrul.shop.model.Customer;
import org.vicrul.shop.model.PeriodData;
import org.vicrul.shop.model.Product;
import org.vicrul.shop.service.CustomerService;
import org.vicrul.shop.service.CustomerServiceImpl;

public class Run {

	public static void main(String[] args) {
		
		String command = args[0];
		String input = args[1];
		String output = args[2];
		
		System.out.println(command + " " + input + " " + output);

		CustomerService customerService = new CustomerServiceImpl();
		List<Customer> customers1 = customerService.findBySurname("Иванов");
		List<Customer> customers2 = customerService.findWhoBoughtThisProduct("Минералка", 0);
		List<Customer> customers3 = customerService.intervalCost(100, 500);
		List<Customer> customers4 = customerService.findPassiveCustomers(4);
		// customerService.createView("2020-05-20", "2020-05-24");
		// customerService.dropView();
		List<Customer> customers5 = customerService.getCustomersInPeriod();

		getList(customers1);
		System.out.println("==============================================================");
		getList(customers2);
		System.out.println("==============================================================");
		getList(customers3);
		System.out.println("==============================================================");
		getList(customers4);
		System.out.println("==============================================================");
		getList(customers5);
//=============================================================================================================
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-M-dd");
		String dateInString1 = "2020-05-20";
		String dateInString2 = "2020-05-24";
		LocalDate date1 = LocalDate.parse(dateInString1, dtf);
		LocalDate date2 = LocalDate.parse(dateInString2, dtf);

		int countDays = 0;
		for (LocalDate date = date1; date.isBefore(date2); date = date.plusDays(1)) {
			Calendar gc = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
			int day = gc.get(Calendar.DAY_OF_WEEK);
			if(day > 1 && day < 7) {
				countDays ++;
			}
	    }
		System.out.println("Ответ = " + countDays);
//=================================================================================================================	
		List<Product> purchases = customerService.getCustomerPurchses("Иванов", "Иван");
		
		long totalExpenses = 0;
		for (Product product : purchases) {
			System.out.println(product.toString());
			totalExpenses += product.getPrice();
		}
		System.out.println(totalExpenses);

//===================================================================================================================			
		List<PeriodData> allData = customerService.getAllData();
		
		long totalFullExpenses = 0;
		for (PeriodData periodData : allData) {
			totalFullExpenses += periodData.getPrice();
		}
		System.out.println("Общая сумма = " + totalFullExpenses);
		System.out.println("Средняя общая сумма = " + (double) totalFullExpenses/allData.size());
	}

	public static List<Customer> getList(List<Customer> customers) {
		for (Customer customer : customers) {
			System.out.println(customer.toString());
		}
		return customers;
	}
}