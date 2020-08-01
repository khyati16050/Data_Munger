package com.stackroute.datamunger.reader;

import com.stackroute.datamunger.query.DataSet;
import com.stackroute.datamunger.query.parser.QueryParameter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public interface QueryProcessingEngine {

	public DataSet getResultSet(QueryParameter queryParameter) throws IOException, ParseException;
	
}
