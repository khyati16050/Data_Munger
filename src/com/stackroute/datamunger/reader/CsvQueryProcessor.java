package com.stackroute.datamunger.reader;

import com.stackroute.datamunger.query.*;
import com.stackroute.datamunger.query.parser.QueryParameter;
import com.stackroute.datamunger.query.parser.Restriction;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//import java.net.http.HttpRequest;
import java.text.ParseException;
import java.util.*;

public class CsvQueryProcessor implements QueryProcessingEngine {
	/*
	 * This method will take QueryParameter object as a parameter which contains the
	 * parsed query and will process and populate the ResultSet
	 */
	public DataSet getResultSet(QueryParameter queryParameter) throws IOException, ParseException {

		/*
		 * initialize BufferedReader to read from the file which is mentioned in
		 * QueryParameter. Consider Handling Exception related to file reading.
		 */
		String fileName = queryParameter.getFileName();
		FileReader fileReader = new FileReader("data/ipl.csv");
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		/*
		 * read the first line which contains the header. Please note that the headers
		 * can contain spaces in between them. For eg: city, winner
		 */
		// header called
		Header header = new Header(fileName);


		/*
		 * read the next line which contains the first row of data. We are reading this
		 * line so that we can determine the data types of all the fields. Please note
		 * that ipl.csv file contains null value in the last column. If you do not
		 * consider this while splitting, this might cause exceptions later
		 */
		String line = bufferedReader.readLine();
		//String[] firstLine = line.split(","); //to determine data type

		/*
		 * populate the header Map object from the header array. header map is having
		 * data type <String,Integer> to contain the header and it's index.
		 */
		//System.out.println("hello");

		/*
		 * We have read the first line of text already and kept it in an array. Now, we
		 * can populate the RowDataTypeDefinition Map object. RowDataTypeDefinition map
		 * is having data type <Integer,String> to contain the index of the field and
		 * it's data type. To find the dataType by the field value, we will use
		 * getDataType() method of DataTypeDefinitions class
		 */
		RowDataTypeDefinitions rowDataTypeDefinitions = new RowDataTypeDefinitions(fileName);
		rowDataTypeDefinitions.setRowDataTypeMap(line);

		/*
		 * once we have the header and dataTypeDefinitions maps populated, we can start
		 * reading from the first line. We will read one line at a time, then check
		 * whether the field values satisfy the conditions mentioned in the query,if
		 * yes, then we will add it to the resultSet. Otherwise, we will continue to
		 * read the next line. We will continue this till we have read till the last
		 * line of the CSV file.
		 */
//		String line = bufferedReader.readLine();
//		DataSet dataSet = new DataSet();
//		long lineNumber = 0;
//		while(line != null)
//		{
//			Row row = new Row(fileName,line);
//
//			dataSet.put(lineNumber,row);
//			line = bufferedReader.readLine();
//			lineNumber++;
//		}



		/* reset the buffered reader so that it can start reading from the first line */
		//bufferedReader.reset();
BufferedReader buff = new BufferedReader(fileReader);
		/*
		 * skip the first line as it is already read earlier which contained the header
		 */
		/* read one line at a time from the CSV file till we have any lines left */
		/*
		 * once we have read one line, we will split it into a String Array. This array
		 * will continue all the fields of the row. Please note that fields might
		 * contain spaces in between. Also, few fields might be empty.
		 */
//		String fileLine = buff.readLine();
		List<Restriction> restrictions = null;
		if(queryParameter.getRestrictions()!=null)
		{
			restrictions = queryParameter.getRestrictions();
		}
		DataSet dataSet = new DataSet();

		long rowNum = 0;
		DataTypeDefinitions dataTypeDefinitions = new DataTypeDefinitions();
		Filter filter = new Filter();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String fileLine;
		while((fileLine = br.readLine()) != null)
		{
			if(rowNum!=0)
			{
				Row row = new Row(fileName,fileLine);
				boolean finalCheck = true;
				String[] fields = fileLine.split(",");
				int size = fields.length;
				String[] lineFields = new String[size];
				for(int i=0;i<size;i++)
				{
					if(fields[i]!=null)
					lineFields[i] = fields[i].trim();
				}

				if(restrictions!= null)
				{
					List<Boolean> logicalCheck = new ArrayList<>();
					for(Restriction restriction : restrictions)
					{
						boolean flag = compareWhere(restriction,header,lineFields,dataTypeDefinitions,filter);
						logicalCheck.add(flag);
					}
					if(logicalCheck.size()==1)
					{
						if(logicalCheck.get(0)==false)
						{
							finalCheck = false;
						}
					}
					else
					{
						List<String> operator = queryParameter.getLogicalOperators();
						if(operator!=null)
						{
							int operatorSize = operator.size();
							if(operatorSize<2)
							{
								for(int i=0;i<operatorSize;i++)
								{
									Boolean logicOne = logicalCheck.get(i);
									Boolean logicTwo = logicalCheck.get(i+1);
									if(operator.get(i).equals("and"))
									{
										if(logicOne==false || logicTwo==false)
											finalCheck = false;
									}
									else if(operator.get(i).equals("or"))
									{
										if(logicOne==false && logicTwo==false)
											finalCheck = false;
									}
								}

							}
							else
							{
								Boolean flag = true;
								Boolean logicOne = logicalCheck.get(0);
								Boolean logicTwo = logicalCheck.get(1);
								for(int i=0;i<operatorSize-1;i++)
								{
									if(operator.get(i).equals("and"))
									{
										if(logicOne==false || logicTwo==false)
											flag = false;
									}
									else if(operator.get(i).equals("or"))
									{
										if(logicOne==false && logicTwo==false)
											flag = false;
									}
									logicOne = flag;
									logicTwo = logicalCheck.get(i+2);

								}




							}
						}
					}
				}

				if(finalCheck)
				{
					dataSet.put(rowNum,row);
				}

			}
			rowNum++;
		}
		List<String> fields = queryParameter.getFields();
		String[] headers = header.getheader();
		DataSet resultSet = displayFields(dataSet,fields,headers);


		return resultSet;
//		return  resultSet;

		/*
		 * if there are where condition(s) in the query, test the row fields against
		 * those conditions to check whether the selected row satifies the conditions
		 */

		/*
		 * from QueryParameter object, read one condition at a time and evaluate the
		 * same. For evaluating the conditions, we will use evaluateExpressions() method
		 * of Filter class. Please note that evaluation of expression will be done
		 * differently based on the data type of the field. In case the query is having
		 * multiple conditions, you need to evaluate the overall expression i.e. if we
		 * have OR operator between two conditions, then the row will be selected if any
		 * of the condition is satisfied. However, in case of AND operator, the row will
		 * be selected only if both of them are satisfied.
		 */

		/*
		 * check for multiple conditions in where clause for eg: where salary>20000 and
		 * city=Bangalore for eg: where salary>20000 or city=Bangalore and dept!=Sales
		 */

		/*
		 * if the overall condition expression evaluates to true, then we need to check
		 * if all columns are to be selected(select *) or few columns are to be
		 * selected(select col1,col2). In either of the cases, we will have to populate
		 * the row map object. Row Map object is having type <String,String> to contain
		 * field Index and field value for the selected fields. Once the row object is
		 * populated, add it to DataSet Map Object. DataSet Map object is having type
		 * <Long,Row> to hold the rowId (to be manually generated by incrementing a Long
		 * variable) and it's corresponding Row Object.
		 */

		/* return dataset object */
	//	return null;
	//return null;
		}


	public boolean compareWhere(Restriction restriction, Header header,String[] lineFields, DataTypeDefinitions dataTypeDefinitions, Filter filter) throws IOException, ParseException {
		String headerName = restriction.getPropertyName();
		String propertyOne = restriction.getPropertyValue();
		String condition = restriction.getCondition();
		String dataType = dataTypeDefinitions.getDataType(propertyOne);
		int headerIndex = header.getHeaderMap().get(headerName);
		String propertyTwo = lineFields[headerIndex];
		return filter.evaluateExpression(dataType,propertyOne,propertyTwo,condition);


	}
	public DataSet displayFields(DataSet dataSet, List<String> fields, String[] headerlist)
	{
		DataSet resultSet = new DataSet();
		long num = 1;
		for (Map.Entry<Long,Row> entry : dataSet.entrySet())
		{
			Long key = entry.getKey();
			Row row = entry.getValue();
			Row newRow = new Row();
			if(fields.size()==1)
			{
				if(fields.get(0).equals("*"))
				{
					newRow = row;
				}
			}
			else
			{


				for(String field : fields)
				{
					String value = row.get(field);
					System.out.println(field);
					newRow.put(field,value);
				}

			}

			resultSet.put(num,newRow);


			num++;
			// now work with key and value...
		}
		return resultSet;
	}

}
