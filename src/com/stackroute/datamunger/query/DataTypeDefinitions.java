package com.stackroute.datamunger.query;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Implementation of DataTypeDefinitions class. This class contains a method getDataTypes() 
 * which will contain the logic for getting the datatype for a given field value. This
 * method will be called from QueryProcessors.   
 * In this assignment, we are going to use Regular Expression to find the 
 * appropriate data type of a field. 
 * Integers: should contain only digits without decimal point 
 * Double: should contain digits as well as decimal point 
 * Date: Dates can be written in many formats in the CSV file. 
 * However, in this assignment,we will test for the following date formats('dd/mm/yyyy',
 * 'mm/dd/yyyy','dd-mon-yy','dd-mon-yyyy','dd-month-yy','dd-month-yyyy','yyyy-mm-dd')
 */
public class DataTypeDefinitions {
	private String fileName = "data/ipl.csv";
	String[] dataTypes;

	public String[] getDataTypes()
	{
		return this.dataTypes;
	}
	public void setDataTypes(String[] dataTypes)
	{
		this.dataTypes = dataTypes;
	}

	@Override
	public String toString()
	{
		String dataTypeString = Arrays.toString(dataTypes);
		return "DataTypeDefinitions{" + "dataTypes=" + dataTypeString+ '}';
	}

	//method stub
	public DataTypeDefinitions getColumnType() throws IOException
	{
		// TODO Auto-generated method stub
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String[] splitHeaders = bufferedReader.readLine().split(",");
		int len = splitHeaders.length;
		String line = bufferedReader.readLine();
		if (line == null)
		{
			return new DataTypeDefinitions();
		}
		String[] lineSplit = line.split(",");
		List<String> listDataType = new ArrayList<>();
		for (String data : lineSplit)
		{
			// checking for Integer
			listDataType.add(getDataType(data));

		}
		while (listDataType.size() < len)
		{
			listDataType.add("java.lang.Object");
		}
		DataTypeDefinitions dataTypeDefiniton = new DataTypeDefinitions();
		int size = listDataType.size();
		Array[] dataArray = new Array[size];
		//dataArray = listDataType.toArray(new String(size));
		dataTypeDefiniton.setDataTypes(listDataType.toArray(new String[size]));

		return dataTypeDefiniton;
	}
	public String getDataType(String data)
	{
		if(!data.contains("."))
		{
			if(data.matches("[0-9]*"))
			{
				return ("java.lang.Integer");
			}
			else if((data.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")) || (data.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")))
			{
				return ("java.util.Date");
			}
			else if(data.matches("[0-9]{2}/[a-zA-z]*/[0-9]{2}") || data.matches("[0-9]{2}-[a-zA-z]*-[0-9]{4}"))
			{
				return ("java.util.Date");
			}
			else
			{
				return ("java.lang.String");
			}

		}
		else
		{
			if(data.matches("[0-9]*"))
			{
				return ("java.lang.Double");
			}
		}
		return "java.lang.Object";

	}
	

	
}
