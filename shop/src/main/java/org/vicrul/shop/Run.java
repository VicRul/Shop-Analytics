package org.vicrul.shop;

import org.vicrul.shop.util.Operation;

public class Run {

	public static void main(String[] args) {
		
		String inputCommand = "", inputFilePath = "", outputFilePath = "";
		try {
			inputCommand = args[0];
			inputFilePath = args[1];
			outputFilePath = args[2];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Указаны не все входные параметры при запуске.\n "
					+ "Необходимо ввести операцию [search или stat], файл с критериями [*.json], файл для записи [*.json]..");
		}

		switch (inputCommand) {
		case "search":
			new Operation().search(inputFilePath, outputFilePath);
			break;

		case "stat":
			new Operation().stat(inputFilePath, outputFilePath);
			break;

		default:
			break;
		}
	}
}