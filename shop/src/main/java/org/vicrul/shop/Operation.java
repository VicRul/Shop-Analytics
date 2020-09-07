package org.vicrul.shop;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.vicrul.shop.model.Customer;
import org.vicrul.shop.service.CustomerService;
import org.vicrul.shop.service.CustomerServiceImpl;

import lombok.AllArgsConstructor;

public class Operation {

	private CustomerService customerService = new CustomerServiceImpl();

	public void search(String inputFilePath, String outputFilePath) {
		JSONObject jsonObject = readFromFile(inputFilePath, outputFilePath);
		JSONArray creterias = (JSONArray) jsonObject.get("criterias");
		Iterator element = creterias.iterator();
		JSONObject result = new JSONObject();
		result.put("type", "search");
		while (element.hasNext()) {
			JSONObject innerObj = (JSONObject) element.next();
			if (innerObj.containsKey("surname")) {
				String surname = (String) innerObj.get("surname");
				if (surname.isEmpty()) {
					errorOperation("Параметр 'lastName' должен быть пустым", outputFilePath);
				} else {
					List<Customer> customers = customerService.findBySurname(surname);
					result.put("criteria", result.put("surname", surname));
					result.put("results", customers);
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
					criteria.put("productName", productName);
					criteria.put("minTimes", minTimes);

					result.put("criteria", criteria);
					result.put("result", customers);
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
					result.put("criteria", criteria);
					result.put("result", customers);
				}

			} else if (innerObj.containsKey("badCustomers")) {

				int badCustomers = (int) innerObj.get("badCustomers");
				if (badCustomers <= 0) {
					errorOperation("Параметр 'badCustomers' должен быть больше нуля", outputFilePath);
				} else {
					List<Customer> customers = customerService.findPassiveCustomers(badCustomers);
					result.put("criteria", result.put("badCustomers", badCustomers));
					result.put("results", customers);
				}

			} else {
				errorOperation("Указан некорректный критерий для выполнения операции", outputFilePath);
			}
		}
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
