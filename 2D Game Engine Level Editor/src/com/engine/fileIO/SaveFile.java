package com.engine.fileIO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SaveFile {

	private Map<String, String> data = new HashMap<String, String>();
	private String filePath;

	public SaveFile(String filePath) {
		this.filePath = filePath;
	}

	public void registerData(String id, String value) {
		data.put(id, value);
	}

	public String getData(String name) {
		return data.get(name);
	}

	public void loadData() {
		try {
			BufferedInputStream stream = new BufferedInputStream(getClass().getResourceAsStream(filePath));
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println("[FILE] " + line);
				String[] parts = line.split("=");
				if (parts.length > 1) {
					String key = parts[0];
					String value = parts[1];
					registerData(key, value);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void saveData() {
		Set<String> keys = data.keySet();
		for (String key : keys) {
			saveData(key, data.get(key));
		}
	}

	private void saveData(String key, String value) {
		String line = key + "=" + value;
		byte data[] = line.getBytes();
		try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
			out.write(data, 0, data.length);
		} catch (IOException x) {
			System.err.println(x);
		}

	}

}
