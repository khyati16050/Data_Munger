package com.stackroute.datamunger.query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//This class contains methods to evaluate expressions
public class Filter {
	
	/* 
	 * The evaluateExpression() method of this class is responsible for evaluating 
	 * the expressions mentioned in the query. It has to be noted that the process 
	 * of evaluating expressions will be different for different data types. there 
	 * are 6 operators that can exist within a query i.e. >=,<=,<,>,!=,= This method 
	 * should be able to evaluate all of them. 
	 * Note: while evaluating string expressions, please handle uppercase and lowercase 
	 * 
	 */
		public boolean evaluateExpression(String dataType, String propertyOne, String propertyTwo,String condition) throws ParseException {
			if (dataType.equals("java.lang.Integer")) {
				int a = Integer.parseInt(propertyOne);
				int b = Integer.parseInt(propertyTwo);

				if (condition.equals(">=")) {
					if (b >= a)
						return true;
				} else if (condition.equals("<=")) {
					if (b <= a)
						return true;
				} else if (condition.equals(">"))
				{
					if (b > a)
					{
						return true;

					}

				} else if (condition.equals("<")) {
					if (b < a)
						return true;
				} else if (condition.equals("!=")) {
					if (b != a)
						return true;
				} else if (condition.equals("=")) {
					if (a == b)
						return true;

				}
				return false;
			} else if (dataType.equals("java.lang.String")) {
				String a = propertyOne;
				String b = propertyTwo;
				if (condition.equals("=")) {
					if (a.equals(b))
						return true;
				} else if (condition.equals("!=")) {
					if (!a.equals(b))
						return true;
				}
				return false;
			} else if (dataType.equals("java.lang.Double")) {
				Double a = Double.valueOf(propertyOne);
				Double b = Double.valueOf(propertyTwo);


				if (condition.equals(">=")) {
					if (b >= a)
						return true;
				} else if (condition.equals("<=")) {
					if (b <= a)
						return true;
				} else if (condition.equals(">"))
				{
					if (b > a)
						return true;
				} else if (condition.equals("<")) {
					if (b < a)
						return true;
				} else if (condition.equals("!=")) {
					if (b != a)
						return true;
				} else if (condition.equals("=")) {
					if (a == b)
						return true;

				}
				return false;
			} else if (dataType.equals("java.util.Date")) {
				if (propertyOne.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					Date date1 = sdf.parse(propertyOne);
					Date date2 = sdf.parse(propertyTwo);
					int check = date1.compareTo(date2);
					if (condition.equals(">=")) {
						if (check == 0 || check > 0)
							return true;
					} else if (condition.equals("<=")) {
						if (check == 0 || check < 0)
							return true;
					} else if (condition.equals(">")) {
						if (check > 0)
							return true;
					} else if (condition.equals("<")) {
						if (check < 0)
							return true;
					} else if (condition.equals("!=")) {
						if (check < 0 || check > 0)
							return true;
					} else if (condition.equals("=")) {
						if (check == 0)
							return true;
					}
					return false;

				} else if (propertyOne.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Date date1 = sdf.parse(propertyOne);
					Date date2 = sdf.parse(propertyTwo);
					int check = date1.compareTo(date2);
					if (condition.equals(">=")) {
						if (check == 0 || check > 0)
							return true;
					} else if (condition.equals("<=")) {
						if (check == 0 || check < 0)
							return true;
					} else if (condition.equals(">")) {
						if (check > 0)
							return true;
					} else if (condition.equals("<")) {
						if (check < 0)
							return true;
					} else if (condition.equals("!=")) {
						if (check < 0 || check > 0)
							return true;
					} else if (condition.equals("=")) {
						if (check == 0)
							return true;
					}
					return false;

				}

				return false;
			}
			return false;
		}


	//Method containing implementation of equalTo operator
	
	
	
	
	
	//Method containing implementation of notEqualTo operator
	
	
	
	
	
	
	
	//Method containing implementation of greaterThan operator
	
	
	
	
	
	
	
	//Method containing implementation of greaterThanOrEqualTo operator
	
	
	
	
	
	
	//Method containing implementation of lessThan operator
	  
	
	
	
	
	//Method containing implementation of lessThanOrEqualTo operator
	
}
