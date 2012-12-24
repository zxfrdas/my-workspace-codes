package com.konka.kkimplements.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class IniReader {
	private Map<String, Properties> sections;
	private String secion;
	private Properties properties;

	
	/**
	 * read and parse the INI file
	 * @param filename
	 * @throws IOException
	 */
	public IniReader(String filename) throws IOException {
		sections = new HashMap<String, Properties>();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		read(reader);
		reader.close();
	}

	/**
	 * ready the INI file line by line
	 * @param reader
	 * @throws IOException
	 */
	private void read(BufferedReader reader) throws IOException {
		String line;
		while ((line = reader.readLine()) != null) {
			parseLine(line);
		}
	}

	/**
	 * parse one of the line of INI file
	 * @param line
	 */
	private void parseLine(String line) {
		line = line.trim();
		if (line.matches("\\[.*\\]") == true) {
			secion = line.replaceFirst("\\[(.*)\\]", "$1");
			properties = new Properties();
			sections.put(secion, properties);
		} else if (line.matches(".*=.*") == true) {
			if (properties != null) {
				int i = line.indexOf('=');
				String name = line.substring(0, i);
				String value = line.substring(i + 1);
				name  = name.trim();
				value = value.trim();
				properties.setProperty(name, value);
			}
		}
	}

	/**
	 * get the value by the key in the specified section
	 * @param section
	 * @param key
	 * @return
	 * the value
	 */
	public String getValue(String section, String key) {
		Properties p = sections.get(section);
		if (p == null) {
			System.out.println("[wf] get the section is null");
			return null;
		}
		String value = p.getProperty(key);
		return value;
	}

}
