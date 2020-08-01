package com.stackroute.datamunger.query;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//This class will be used to store the column data types as columnIndex/DataType
public class RowDataTypeDefinitions extends HashMap<Integer, String>{
	String fileName;
	public RowDataTypeDefinitions(String fileName)
	{
		this.fileName = fileName;
	}
	DataTypeDefinitions dataTypeDefinitions = new DataTypeDefinitions();

	public void setRowDataTypeMap(String line) throws IOException
	{
		String[] fieldSplit = line.split(",");
		int size = fieldSplit.length;
		for(int i=0;i<size;i++)
		{
			this.put(i,dataTypeDefinitions.getDataType(fieldSplit[i]));
		}

	}
	/**
	 * 
	 */
	//String[] datatypedefinitions = DataTypeDefinitions.getDataType()
	private static final long serialVersionUID = 1L;
	
}
