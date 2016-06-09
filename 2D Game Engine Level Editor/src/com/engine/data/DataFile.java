package com.engine.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataFile {

	protected String name;

	protected String location;

	protected boolean doesFileHaveData = false;

	protected List<String> lines = new ArrayList<String>();

	public DataFile(String name, String location, List<String> lines) {
		this.name = name;
		this.location = location;
		this.lines.addAll(lines);
	}

	public DataFile(String name, String location) {
		this.name = name;
		this.location = location;
	}

	public DataFile(String name) {
		this.name = name;
		this.location = System.getProperty("user.home") + "/temp.dna";
	}

	public void init() {
		System.out.println("initializing file: " + name);
		System.out.println("[" + name + "] file location: " + location);
		boolean dirs = makeParentDirectories();
		boolean file = makeFile();
		String message = "nothing happened";
		if (dirs && file) message = "created both the file and all parent folders";
		if (!dirs && file) message = "created the file. Parent folders already exist";
		if (!dirs && !file) message = "both the file and the parent folders already exist. OR this FAILED";
		if (dirs && !file) message = "Somehow the file existed but the parent folders didn't. How could this be???";
		System.out.print("[" + name + "] ");
		System.out.println(message);
		doesFileHaveData = !file;
	}

	public File getFile() {
		return new File(location);
	}

	public File getParent() {
		return getFile().getParentFile();
	}

	public boolean makeFile() {
		boolean flag = false;
		try {
			flag = getFile().createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean makeParentDirectory() {
		boolean flag = false;
		flag = getParent().mkdir();
		return flag;
	}

	public boolean makeParentDirectories() {
		boolean flag = false;
		flag = getParent().mkdirs();
		return flag;
	}

	public boolean doesFileExist() {
		return getFile().exists();
	}

	public boolean saveData() {
		try {
			FileWriter fw = null;
			File file = getFile();
			if (!file.exists()) {
				File parent = file.getParentFile();
				if (!parent.exists()) {
					parent.mkdirs();
				}
				file.createNewFile();
			}
			fw = new FileWriter(file);
			if (fw != null) {
				BufferedWriter w = new BufferedWriter(fw);

				for (String line : lines) {
					if (line == null) continue;
					w.write(line);
					w.newLine();
				}
				w.flush();
				w.close();
			}
			doesFileHaveData = true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean loadDataFromFile() {
		try {
			FileReader fr = null;
			File file = getFile();
			if (!file.exists()) { return false; }
			fr = new FileReader(file);
			if (fr != null) {
				BufferedReader reader = new BufferedReader(fr);
				String line = "";
				lines.clear();
				while (line != null) {
					line = reader.readLine();
					lines.add(line);
				}
				reader.close();
			}
			clearOutNullLines();
			doesFileHaveData = !lines.isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean saveData(List<String> lines) {
		try {
			FileWriter fw = null;
			File file = getFile();
			if (!file.exists()) {
				File parent = file.getParentFile();
				if (!parent.exists()) {
					parent.mkdirs();
				}
				file.createNewFile();
			}
			fw = new FileWriter(file);
			if (fw != null) {
				BufferedWriter w = new BufferedWriter(fw);
				for (String line : lines) {
					if (line == null) continue;
					w.write(line);
					w.newLine();
				}
				w.flush();
				w.close();
			}
			doesFileHaveData = true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<String> loadDataFromFile(List<String> lines) {
		try {
			FileReader fr = null;
			File file = getFile();
			fr = new FileReader(file);
			if (fr != null) {
				BufferedReader reader = new BufferedReader(fr);
				String line = "";
				lines.clear();
				while (line != null) {
					line = reader.readLine();
					lines.add(line);
				}
				reader.close();
			}
			clearOutNullLines();
			doesFileHaveData = !lines.isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}

	public void clearOutNullLines() {
		List<String> newLines = new ArrayList<String>();
		for (String l : lines) {
			if (l == null) continue;
			if (l.equals("")) continue;
			newLines.add(l);
		}
		this.lines = newLines;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the lines
	 */
	public List<String> getLines() {
		return lines;
	}

	/**
	 * @param lines the lines to set
	 */
	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	/**
	 * @param index
	 * @param element
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	public void add(int index, String element) {
		lines.add(index, element);
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(String e) {
		return lines.add(e);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends String> c) {
		return lines.addAll(c);
	}

	/**
	 * @param index
	 * @param c
	 * @return
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int index, Collection<? extends String> c) {
		return lines.addAll(index, c);
	}

	/**
	 * 
	 * @see java.util.List#clear()
	 */
	public void clear() {
		lines.clear();
	}

	/**
	 * @param index
	 * @return
	 * @see java.util.List#get(int)
	 */
	public String get(int index) {
		return lines.get(index);
	}

	/**
	 * @return
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty() {
		return lines.isEmpty();
	}

	/**
	 * @param index
	 * @return
	 * @see java.util.List#remove(int)
	 */
	public String remove(int index) {
		return lines.remove(index);
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		return lines.remove(o);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c) {
		return lines.removeAll(c);
	}

	/**
	 * @param index
	 * @param element
	 * @return
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public String set(int index, String element) {
		return lines.set(index, element);
	}

	/**
	 * @return
	 * @see java.util.List#size()
	 */
	public int size() {
		return lines.size();
	}

}
