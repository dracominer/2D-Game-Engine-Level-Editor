package com.engine.data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class EncryptedDataFile extends DataFile {

	public EncryptedDataFile(String name, String location, List<String> lines) {
		super(name, location, lines);
	}

	public EncryptedDataFile(String name, String location) {
		super(name, location);
	}

	public EncryptedDataFile(String name) {
		super(name);
	}

	public List<String> getEncryptedData() {
		List<String> en = new ArrayList<String>();
		for (String s : lines) {
			byte[] bytes = s.getBytes();
			String bits = new BigInteger(bytes).toString(2);
			String inverted = "";
			for (int i = 0; i < bits.length(); i++) {
				if (bits.charAt(i) == '0') inverted += "1";
				if (bits.charAt(i) == '1') inverted += "0";
			}
			//			System.out.println(bits);
			//			System.out.println(inverted);
			//			System.out.println("-----------------------------");
			en.add(inverted);
		}
		return en;
	}

	public List<String> getDecryptedData() {
		List<String> de = new ArrayList<String>();
		for (String s : lines) {
			byte[] bytes = s.getBytes();
			String bits = new BigInteger(bytes).toString(2);
			String inverted = "";
			for (int i = 0; i < bits.length(); i++) {
				if (bits.charAt(i) == '0') inverted += "1";
				if (bits.charAt(i) == '1') inverted += "0";
			}
			//			System.out.println(bits);
			//			System.out.println(inverted);
			//			System.out.println("-----------------------------");
			de.add(inverted);
		}
		return de;
	}

	public boolean saveData() {
		return super.saveData(getEncryptedData());
	}

	public boolean loadDataFromFile() {
		List<String> en = new ArrayList<String>();
		super.loadDataFromFile(en);
		setLines(getDecryptedData());
		return super.loadDataFromFile();
	}

}
