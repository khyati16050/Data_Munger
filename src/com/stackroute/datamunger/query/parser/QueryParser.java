package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.List;

public class QueryParser {


	/*
	 * This method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
	private QueryParameter queryParameter = new QueryParameter();

	/*
	 * This method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
public static void main(String[] args)
{
//	QueryParameter queryParameter = new QueryParameter();
	new QueryParser().parseQuery("select city,winner,team1,team2,player_of_match from data/ipl.csv where season >= 2008 or toss_decision != bat and city = bangalore");
//	System.out.println(queryParameter);
}
	public QueryParameter parseQuery(String queryString) {
		String file = getFileName(queryString);
		String base = getBaseQuery(queryString);
		ArrayList fields = getFields(queryString);
		List<String> orderFields = getOrderByFields(queryString);
		ArrayList group = getGroupByFields(queryString);
		List<Restriction> restriction = getRestriction(queryString);
		List<AggregateFunction> aggregate = getAggregateFunctions(queryString);
		ArrayList<String> logical = getLogicalOperators(queryString);


		queryParameter.setFileName(file);
		queryParameter.setBaseQuery(base);
		queryParameter.setFields(fields);
		queryParameter.setOrderByFields(orderFields);
		queryParameter.setGroupByFields(group);
		queryParameter.setRestrictions(restriction);
		queryParameter.setAggregateFunctions(aggregate);
		queryParameter.setLogicalOperators(logical);

		return queryParameter;

	}

	/*
	 * Extract the name of the file from the query. File name can be found after the
	 * "from" clause.
	 */
	public String getFileName(String queryString) {
		String file = "";
		String[] splitString = queryString.toLowerCase().split(" ");
		int size = splitString.length;
		for (int i = 0; i < size; i++) {

			if (splitString[i].equals("from")) {
				file = splitString[i + 1];
			}
		}
		return file;
	}
	/*
	 *
	 * Extract the baseQuery from the query.This method is used to extract the
	 * baseQuery from the query string. BaseQuery contains from the beginning of the
	 * query till the where clause
	 */

	public String getBaseQuery(String queryString) {
		String baseQuery = "";
		if (queryString == null) {
			return null;
		}
		String[] splitString = queryString.toLowerCase().split(" ");
		int count = 0;
		int max = splitString.length - 1;
		while (count < max) {
			if (splitString[count].equals("where") || splitString[count].equals("group")) {
				break;
			} else if (splitString[count + 1].equals("where")) {
				baseQuery = baseQuery + (splitString[count++]);
			} else if (splitString[count + 1].equals("group")) {
				baseQuery = baseQuery + (splitString[count++]);
			} else {
				baseQuery = baseQuery + (((splitString[count++]) + (" ")));
			}
		}
		return baseQuery;
	}

	/*
	 * extract the order by fields from the query string. Please note that we will
	 * need to extract the field(s) after "order by" clause in the query, if at all
	 * the order by clause exists. For eg: select city,winner,team1,team2 from
	 * data/ipl.csv order by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one order by fields.
	 */
	public ArrayList<String> getOrderByFields(String queryString) {
		if (queryString == null) {
			return null;
		} else {
			if (queryString.contains("order by")) {
				String[] splitStrings = queryString.split(" ");
				ArrayList orderByFields = new ArrayList<String>();
				int size = splitStrings.length;
				for (int i = 2; i < size; i++) {
					if (splitStrings[i - 1].equals("by")) {
						if (splitStrings[i - 2].equals("order")) {
							orderByFields.add(splitStrings[i]);
						}
					}
				}
				return orderByFields;
			} else {
				return null;
			}
		}

	}

	/*
	 * Extract the group by fields from the query string. Please note that we will
	 * need to extract the field(s) after "group by" clause in the query, if at all
	 * the group by clause exists. For eg: select city,max(win_by_runs) from
	 * data/ipl.csv group by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one group by fields.
	 */
	public ArrayList<String> getGroupByFields(String queryString) {
		if (queryString == null) {
			return null;
		} else {
			if (queryString.contains("group by")) {
				String[] splitStrings = queryString.split(" ");
				ArrayList groupByFields = new ArrayList<String>();
				int size = splitStrings.length;
				for (int i = 2; i < size; i++) {
					if (splitStrings[i - 1].equals("by")) {
						if (splitStrings[i - 2].equals("group")) {
							groupByFields.add(splitStrings[i]);
						}
					}
				}
				return groupByFields;
			} else {
				return null;
			}
		}


	}

	/*
	 * Extract the selected fields from the query string. Please note that we will
	 * need to extract the field(s) after "select" clause followed by a space from
	 * the query string. For eg: select city,win_by_runs from data/ipl.csv from the
	 * query mentioned above, we need to extract "city" and "win_by_runs". Please
	 * note that we might have a field containing name "from_date" or "from_hrs".
	 * Hence, consider this while parsing.
	 */
	public ArrayList<String> getFields(String queryString) {
		if (queryString == null) {
			return null;
		}
		ArrayList<String> fields = new ArrayList<String>();
		String[] splitString = queryString.split(" ");
		if (splitString[0].equals("select")) {
			int i = 1;
			while (!splitString[i].equals("from")) {
				String substrings = splitString[i];
				if (substrings.contains(",")) {
					String[] subFields = substrings.split(",");
					int len = subFields.length;
					for (int k = 0; k < len; k++)
						fields.add(subFields[k]);
				} else {
					fields.add(splitString[i]);
				}
				i++;
			}

		}
		return fields;

	}

	public String getConditionsPartQuery(String queryString) {

		String conditions = " ";
		String[] splitString = queryString.split(" ");
		int size = splitString.length;
		int index = 0;
		for (int i = 0; i < size; i++) {
			if (splitString[i].equals("where")) {
				index = i + 1;
				break;
			}
//"select city,winner,player_of_match from data/ipl.csv where season > 2014
		}
		if (index == 0) {
			return null;
		}
		while (index < size) {
			if (!splitString[index].equals("group") && !splitString[index].equals("order")) {
				conditions = conditions + splitString[index] + " ";
			} else {
				break;
			}
			index++;
		}
		return conditions.trim();
	}
//
	public String[] getConditions(String queryString) {

		if (queryString == null) {
			return null;
		}
		if (queryString.isEmpty()) {
			return null;
		}
		String Partquery = getConditionsPartQuery(queryString);
		if (Partquery == null) {
			return null;
		}
		if (Partquery.contains("and")) {
			String[] splitString = Partquery.split(" and ");
			ArrayList<String> andList = new ArrayList<String>();
			for (String s : splitString) {
				if (s.contains("or")) {
					String[] orString = s.split(" or ");
					for (String str : orString) {
						andList.add(str);
					}
				} else {
					andList.add(s);
				}
			}
			int len = andList.size();
			String[] conditions = andList.toArray(new String[len]);
			return conditions;
		} else if (Partquery.contains("or")) {
			String[] splitString = Partquery.split(" or ");
			ArrayList<String> orList = new ArrayList<String>();
			for (String s : splitString) {
				orList.add(s);

			}
			int len = orList.size();
			String[] conditions = orList.toArray(new String[len]);
			return conditions;

		} else {
			return new String[]{Partquery};
		}

	}

	/*
	 * Extract the conditions from the query string(if exists). for each condition,
	 * we need to capture the following: 1. Name of field 2. condition 3. value
	 *
	 * For eg: select city,winner,team1,team2,player_of_match from data/ipl.csv
	 * where season >= 2008 or toss_decision != bat
	 *
	 * here, for the first condition, "season>=2008" we need to capture: 1. Name of
	 * field: season 2. condition: >= 3. value: 2008
	 *
	 * the query might contain multiple conditions separated by OR/AND operators.
	 * Please consider this while parsing the conditions.
	 *
	 */
	public List<Restriction> getRestriction(String queryString) {
		if (queryString == null) {
			return null;
		}
		String[] conditions = getConditions(queryString);
		if (conditions == null) {
			return null;
		}
		String logic = "";
		ArrayList<Restriction> restrictArray = new ArrayList<Restriction>();
		for (String s : conditions) {
			 if (s.contains(">=")) {
				logic = ">=";
			} else if (s.contains("<=")) {
				logic = "<=";
			} else if (s.contains(">")) {
				logic = ">";
			} else if (s.contains("<")) {
				logic = "<";
			} else if (s.contains("!=")) {
				logic = "!=";
			}
			else if (s.contains("=")) {
				logic = "=";
			}
			String[] conditionSplit = s.split(logic);
			if (conditionSplit[1].contains("'")) {
				conditionSplit[1] = conditionSplit[1].replace("'", "");
			}
			String condition1 = conditionSplit[0].trim();
			String condition2 = conditionSplit[1].trim();
			restrictArray.add(new Restriction(condition1, condition2, logic));


		}
		return restrictArray;
	}

	/*
	 * Extract the logical operators(AND/OR) from the query, if at all it is
	 * present. For eg: select city,winner,team1,team2,player_of_match from
	 * data/ipl.csv where season >= 2008 or toss_decision != bat and city =
	 * bangalore
	 *
	 * The query mentioned above in the example should return a List of Strings
	 * containing [or,and]
	 */
	public ArrayList<String> getLogicalOperators(String queryString) {
		if (queryString == null) {
			return null;
		}
		if (!queryString.contains("where")) {
			return null;
		}
		ArrayList<String> emptyArr = new ArrayList<String>();
		if ((!queryString.contains("and") && !queryString.contains("or"))) {
			return emptyArr;
		} else {
			String[] logicalOperators = queryString.split(" ");
			ArrayList<String> logics = new ArrayList();
			for (String s : logicalOperators) {
				if (s.equals("and") || s.equals("or")) {
					logics.add(s);
				}
			}
			return logics;
		}
	}

	/*
	 * Extract the aggregate functions from the query. The presence of the aggregate
	 * functions can determined if we have either "min" or "max" or "sum" or "count"
	 * or "avg" followed by opening braces"(" after "select" clause in the query
	 * string. in case it is present, then we will have to extract the same. For
	 * each aggregate functions, we need to know the following: 1. type of aggregate
	 * function(min/max/count/sum/avg) 2. field on which the aggregate function is
	 * being applied.
	 *
	 * Please note that more than one aggregate function can be present in a query.
	 *
	 *
	 */
	public List<AggregateFunction> getAggregateFunctions(String queryString) {
		if (queryString == null || queryString.isEmpty()) {
			return null;
		} else {
			ArrayList<String> fields = getFields(queryString);
			if (fields == null) {
				return null;
			}
			ArrayList<String> aggregateList = new ArrayList<String>();
			int size = fields.size();
			for (int i = 0; i < size; i++) {
				if (fields.get(i).contains("avg") || fields.get(i).contains("min") || fields.get(i).contains("max") || fields.get(i).contains("count") || fields.get(i).contains("sum")) {
					aggregateList.add(fields.get(i));
				}
			}
			if (aggregateList.size() == 0) {
				return null;
			}
			ArrayList<AggregateFunction> answer = new ArrayList<AggregateFunction>();
			String aggregateField = "";
			String aggregateFunction = "";
			for (String a : aggregateList) {
				String[] splitFields = a.split("[()]");
				for (String s : splitFields) {
					if (s.contains("avg") || s.contains("min") || s.contains("max") || s.contains("count") || s.contains("sum")) {
						aggregateFunction = s;
					} else {
						aggregateField = s;
					}
				}
				//System.out.println(aggregateField+" fields");
				//System.out.println(aggregateFunction+" function");
				answer.add(new AggregateFunction(aggregateField, aggregateFunction));
			}
			if (answer == null) {
				return null;
			}
			return answer;

		}
	}
}


		/*
		 * extract the name of the file from the query. File name can be found after the
		 * "from" clause.
		 */


		/*
		 * extract the order by fields from the query string. Please note that we will
		 * need to extract the field(s) after "order by" clause in the query, if at all
		 * the order by clause exists. For eg: select city,winner,team1,team2 from
		 * data/ipl.csv order by city from the query mentioned above, we need to extract
		 * "city". Please note that we can have more than one order by fields.
		 */




		/*
		 * extract the group by fields from the query string. Please note that we will
		 * need to extract the field(s) after "group by" clause in the query, if at all
		 * the group by clause exists. For eg: select city,max(win_by_runs) from
		 * data/ipl.csv group by city from the query mentioned above, we need to extract
		 * "city". Please note that we can have more than one group by fields.
		 */


		/*
		 * extract the selected fields from the query string. Please note that we will
		 * need to extract the field(s) after "select" clause followed by a space from
		 * the query string. For eg: select city,win_by_runs from data/ipl.csv from the
		 * query mentioned above, we need to extract "city" and "win_by_runs". Please
		 * note that we might have a field containing name "from_date" or "from_hrs".
		 * Hence, consider this while parsing.
		 */




		/*
		 * extract the conditions from the query string(if exists). for each condition,
		 * we need to capture the following:
		 * 1. Name of field
		 * 2. condition
		 * 3. value
		 *
		 * For eg: select city,winner,team1,team2,player_of_match from data/ipl.csv
		 * where season >= 2008 or toss_decision != bat
		 *
		 * here, for the first condition, "season>=2008" we need to capture:
		 * 1. Name of field: season
		 * 2. condition: >=
		 * 3. value: 2008
		 *
		 * the query might contain multiple conditions separated by OR/AND operators.
		 * Please consider this while parsing the conditions.
		 *
		 */



		/*
		 * extract the logical operators(AND/OR) from the query, if at all it is
		 * present. For eg: select city,winner,team1,team2,player_of_match from
		 * data/ipl.csv where season >= 2008 or toss_decision != bat and city =
		 * bangalore
		 *
		 * the query mentioned above in the example should return a List of Strings
		 * containing [or,and]
		 */



