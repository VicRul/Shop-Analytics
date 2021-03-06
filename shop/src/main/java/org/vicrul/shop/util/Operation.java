package org.vicrul.shop.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.vicrul.shop.model.Customer;
import org.vicrul.shop.model.PeriodData;
import org.vicrul.shop.model.Product;
import org.vicrul.shop.service.CustomerService;
import org.vicrul.shop.service.CustomerServiceImpl;

public class Operation {

	private CustomerService customerService = new CustomerServiceImpl();

	public void search(String inputFilePath, String outputFilePath) {
		JSONObject jsonObject = readFromFile(inputFilePath, outputFilePath);
		JSONArray creterias = (JSONArray) jsonObject.get("criterias");
		Iterator element = creterias.iterator();
		JSONObject result = new JSONObject();
		JSONArray resultArray = new JSONArray();
		result.put("type", "search");

		while (element.hasNext()) {
			JSONObject innerObj = (JSONObject) element.next();
			JSONObject innerResult = new JSONObject();

			if (innerObj.containsKey("lastName")) {
				String lastName = (String) innerObj.get("lastName");
				if (lastName.isEmpty()) {
					errorOperation("Параметр 'lastName' должен быть пустым", outputFilePath);
				} else {
					List<Customer> customers = customerService.findBySurname(lastName);
					JSONObject criteria = new JSONObject();
					criteria.put("lastName", lastName.trim());
					innerResult.put("criteria", criteria);
					JSONArray customersArray = new JSONArray();

					for (Customer customer : customers) {
						JSONObject jsonCustomers = new JSONObject();
						jsonCustomers.put("lastName", customer.getLastName().trim());
						jsonCustomers.put("firstName", customer.getFirstName().trim());
						customersArray.add(jsonCustomers);
					}
					innerResult.put("results", customersArray);
				}
			} else if (innerObj.containsKey("productName") && innerObj.containsKey("minTimes")) {
				String productName = (String) innerObj.get("productName");
				long minTimes = (long) innerObj.get("minTimes");
				if (productName.isEmpty()) {
					errorOperation("Параметр 'minTimes' должен быть больше нуля", outputFilePath);
				} else if (minTimes <= 0) {
					errorOperation("Параметр 'productName' должен быть больше пустым", outputFilePath);
				} else {
					List<Customer> customers = customerService.findWhoBoughtThisProduct(productName, minTimes);
					JSONObject criteria = new JSONObject();
					criteria.put("productName", productName.trim());
					criteria.put("minTimes", minTimes);

					innerResult.put("criteria", criteria);
					JSONArray customersArray = new JSONArray();

					for (Customer customer : customers) {
						JSONObject jsonCustomers = new JSONObject();
						jsonCustomers.put("lastName", customer.getLastName().trim());
						jsonCustomers.put("firstName", customer.getFirstName().trim());
						customersArray.add(jsonCustomers);
					}
					innerResult.put("results", customersArray);
				}
			} else if (innerObj.containsKey("minExpenses") && innerObj.containsKey("maxExpenses")) {

				long minExpenses = (long) innerObj.get("minExpenses");
				long maxExpenses = (long) innerObj.get("maxExpenses");

				if (minExpenses <= 0 || maxExpenses <= 0) {
					errorOperation("Параметры 'minExpenses' и 'maxExpenses' должены быть больше нуля", outputFilePath);
				} else {
					List<Customer> customers = customerService.intervalCost(minExpenses, maxExpenses);
					JSONObject criteria = new JSONObject();
					criteria.put("minExpenses", minExpenses);
					criteria.put("maxExpenses", maxExpenses);
					innerResult.put("criteria", criteria);
					JSONArray customersArray = new JSONArray();

					for (Customer customer : customers) {
						JSONObject jsonCustomers = new JSONObject();
						jsonCustomers.put("lastName", customer.getLastName().trim());
						jsonCustomers.put("firstName", customer.getFirstName().trim());
						customersArray.add(jsonCustomers);
					}
					innerResult.put("results", customersArray);
				}

			} else if (innerObj.containsKey("badCustomers")) {

				long badCustomers = (long) innerObj.get("badCustomers");
				if (badCustomers <= 0) {
					errorOperation("Параметр 'badCustomers' должен быть больше нуля", outputFilePath);
				} else {
					List<Customer> customers = customerService.findPassiveCustomers(badCustomers);
					JSONObject criteria = new JSONObject();
					criteria.put("badCustomers", badCustomers);
					innerResult.put("criteria", criteria);
					JSONArray customersArray = new JSONArray();

					for (Customer customer : customers) {
						JSONObject jsonCustomers = new JSONObject();
						jsonCustomers.put("lastName", customer.getLastName().trim());
						jsonCustomers.put("firstName", customer.getFirstName().trim());
						customersArray.add(jsonCustomers);
					}
					innerResult.put("results", customersArray);
				}

			} else {
				errorOperation("Указан некорректный критерий для выполнения операции", outputFilePath);
			}
			resultArray.add(innerResult);
		}
		result.put("results", resultArray);
		writeInFile(result, outputFilePath);
	}

	public void stat(String inputFilePath, String outputFilePath) {

		JSONObject jsonObject = readFromFile(inputFilePath, outputFilePath);
		JSONObject result = new JSONObject();
		result.put("type", "stat");

		String startDate = (String) jsonObject.get("startDate");
		String endDate = (String) jsonObject.get("endDate");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-M-dd");
		LocalDate date1 = LocalDate.parse(startDate, dtf);
		LocalDate date2 = LocalDate.parse(endDate, dtf);

		int countDays = 0;
		for (LocalDate date = date1; date.isBefore(date2); date = date.plusDays(1)) {
			Calendar gc = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
			int day = gc.get(Calendar.DAY_OF_WEEK);
			if (day > 1 && day < 7) {
				countDays++;
			}
		}
		result.put("totalDays", countDays);

		customerService.createView(startDate, endDate);
		List<Customer> customers = customerService.getCustomersInPeriod();
		JSONArray customersArray = new JSONArray();

		for(Customer customer : customers) {
			JSONObject jsonCustomers = new JSONObject();
			JSONArray arrayPurchases = new JSONArray();
			jsonCustomers.put("name", customer.getLastName().trim() + " " + customer.getFirstName().trim());
			List<Product> purchases = customerService.getCustomerPurchses(customer.getLastName().trim(),
					customer.getFirstName().trim());
			int customerTotalExpenses = 0;
			for(Product product : purchases) {
				JSONObject jsonPurchases = new JSONObject();
				jsonPurchases.put("name", product.getProductName().trim());
				jsonPurchases.put("expenses", product.getPrice());
				arrayPurchases.add(jsonPurchases);
				customerTotalExpenses += product.getPrice();
			}
			jsonCustomers.put("purchases", arrayPurchases);
			jsonCustomers.put("totalExpenses", customerTotalExpenses);
			customersArray.add(jsonCustomers);
		}
		result.put("customers", customersArray);

		List<PeriodData> allData = customerService.getAllData();
		int totalFullExpenses = 0;
		for (PeriodData periodData : allData) {
			totalFullExpenses += periodData.getPrice();
		}
		double avgExpenses = totalFullExpenses / allData.size();
		result.put("totalExpenses", totalFullExpenses);
		result.put("avgExpenses", avgExpenses);

		customerService.dropView();
		writeInFile(result, outputFilePath);
	}

	private void errorOperation(String message, String outputFilePath) {
		JSONObject object = new JSONObject();
		object.put("type", "error");
		object.put("message", message);

		writeInFile(object, outputFilePath);
	}

	private void writeInFile(JSONObject string, String outputFilePath) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(outputFilePath);
			writer.write(string.toJSONString());
		} catch (IOException e) {
			System.out.println("Не удалось записaть в файл " + outputFilePath);
		} finally {
			try {
				if (writer != null) {
					writer.flush();
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private JSONObject readFromFile(String inputFilePath, String outputFilePath) {
		FileReader reader = null;
		JSONObject jsonObject = null;
		try {
			reader = new FileReader(inputFilePath);
			jsonObject = (JSONObject) new JSONParser().parse(reader);
		} catch (IOException | ParseException e) {
			errorOperation("Не удалось прочитать файл " + outputFilePath, outputFilePath);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return jsonObject;
	}
}
