package com.stackroute.datamunger.query;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//Header class containing a Collection containing the headers
public class Header extends HashMap<String, Integer> {
	private String fileName;
	/*
	 * this class should contain a member variable which is a String array, to hold
	 * the headers and should override toString() method.
	 */
	String[] headers;
	public Header(String fileName)
	{
		this.fileName = fileName;
	}
//	//public String[] getHeaders()
//	{
//		return this.headers;
//	}

	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	@Override
	public String toString()
	{
		String headerString = Arrays.toString(headers);
		return "Header{" + "headers=" + headerString + '}';
	}
	public String[] getheader() throws IOException {
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = bufferedReader.readLine();
		if (line != null)
		{
			String[] headername = line.split(",");
			int size = headername.length;
			String[] finalheader = new String[size];
			for(int i=0;i<size;i++)
			{
				finalheader[i] = headername[i].trim();
			}
			setHeaders(finalheader);
			return headers;
		}
		else
		{
			return null;
		}
	}
	public Map<String,Integer> getHeaderMap() throws IOException
	{
		Map<String,Integer> map = new HashMap<>();
		headers = getheader();
		int size = headers.length;
		for(int i=0;i<size;i++)
		{
			map.put(headers[i],i);
		}
		return map;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
