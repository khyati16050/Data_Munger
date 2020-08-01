package com.stackroute.datamunger.query;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//Contains the row object as ColumnName/Value. Hence, HashMap is being used
public class Row extends HashMap<String, String>
{
	private String fileName;

	public Row(String fileName, String line) throws IOException {
		this.fileName = fileName;
		setRow(line);
	}
	public Row()
	{

	}
	private String[] getRowValues(String line) throws IOException
	{
		if(line!=null)
		{
			String[] Rowvalues = line.split(",");
			int length = Rowvalues.length;
			String[] finalRowValues = new String[length+1];
			for(int i=0;i<length;i++)
			{
				finalRowValues[i] = Rowvalues[i];
				finalRowValues[i+1] = "";
			}
			return  finalRowValues;
		}
		else
		{
			return null;
		}
	}
	private void setRow(String line) throws IOException {

		Header header = new Header(fileName);
		String[] headerlist  = header.getheader();
		String[] Rowvalues = getRowValues(line);
//		if(headerlist==null)
//			System.out.println("yo");

		int size = Rowvalues.length;
		for(int i=0;i<size;i++)
		{
//			System.out.println(headerlist[i]);
////			System.out.println(Rowvalues[i]);
			this.put(headerlist[i],Rowvalues[i]);
		}


	}






	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
