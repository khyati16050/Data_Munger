package com.stackroute.datamunger.query.parser;

import java.util.List;
/* 
 * This class will contain the elements of the parsed Query String such as conditions,
 * logical operators,aggregate functions, file name, fields group by fields, order by
 * fields, Query Type
 * */
public class QueryParameter {
	//select city,winner,team1,team2,player_of_match from data/ipl.csv where season >= 2008 or toss_decision != bat and city = bangalore
	private String file; //select city,winner,team1,team2,player_of_match from data/ipl.csv
	private String baseQuery; //select city,winner,team1,team2,player_of_match from data/ipl.csv
	private List<String> fields;
	private List<Restriction> restriction;
	private List<String> operator;
	private List<AggregateFunction> aggregate;
	private List<String> orderByFields;
	private List<String> groupByFields;


	public String getFileName()
	{
		return this.file;
	}
	public void setFileName(String file)
	{
		this.file = file;
	}
	public List<String> getFields()
	{
		return this.fields;
	}
	public void setFields(List<String> fields)
	{
		this.fields = fields;
	}
	public String getBaseQuery()
	{
		return this.baseQuery;
	}
	public void setBaseQuery(String baseQuery)
	{
		this.baseQuery = baseQuery;
	}
	public List<Restriction> getRestrictions()
	{
		return this.restriction;
	}
	public void setRestrictions(List<Restriction> restriction)
	{
		this.restriction = restriction;
	}
	public List<String> getLogicalOperators()
	{
		return this.operator;
	}
	public void setLogicalOperators(List<String> operator)
	{
		this.operator = operator;
	}
	public List<String> getOrderByFields()
	{
		return this.orderByFields;
	}
	public void setOrderByFields(List<String> orderByFields)
	{
		this.orderByFields = orderByFields;
	}
	public List<String> getGroupByFields()
	{
		return this.groupByFields;
	}
	public void setGroupByFields(List<String> groupByFields)
	{
		this.groupByFields = groupByFields;
	}
	public List<AggregateFunction> getAggregateFunctions()
	{
		return this.aggregate;
	}
	public void setAggregateFunctions(List<AggregateFunction> aggregate)
	{
		this.aggregate = aggregate;
	}

	public String getQUERY_TYPE() {
		// TODO Auto-generated method stub
		return null;
	}

		

	
}
