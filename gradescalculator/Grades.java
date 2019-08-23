package gradescalculator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;


//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;

public class Grades {
	
	// Variables for the Excel workbook and sheet
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	private ArrayList<ArrayList<String>> allGrades = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> allTeamGrades = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> allAttendance = new ArrayList<ArrayList<String>>();
	
	// Constructor
	public Grades(String gradesDB) throws IOException{		

		FileInputStream file = new FileInputStream(new File(gradesDB));
			
		//Get the workbook instance for XLS file 
		workbook = new XSSFWorkbook(file);
		
		// *****************************************************************************
		// Populate individual grades
			
		//Get first sheet from the workbook
		sheet = workbook.getSheetAt(1);	
		
		int numRows = 0;
		int numColumns = 0;
		String value = "";

		//Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = sheet.iterator();
		
		// Iterate through sheet, get count of rows and columns
		while(rowIterator.hasNext()){
			
			Row row = rowIterator.next();
			ArrayList<String> studentGrades = new ArrayList<String>();
		
			//For each row, iterate through each column (skip header row)
			if (numRows > 0){
				numColumns = 0;
				Iterator<Cell> cellIterator = row.cellIterator();
				while(cellIterator.hasNext()){
		
					Cell cell = cellIterator.next();
					
					switch(cell.getCellType()){
						case Cell.CELL_TYPE_NUMERIC:
							value = NumberToTextConverter.toText(cell.getNumericCellValue());
							break;
						case Cell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;
					}
					
					studentGrades.add(value);
					numColumns += 1;
					
				}  // while
				
				allGrades.add(studentGrades);
				studentGrades = null;				
				
			}  // if
			
			numRows += 1;
			
		}// while
		
		
		
		
		// ********************************************************************
		// Populate teamGrades
		
		//Get fourth sheet from the workbook
		sheet = workbook.getSheetAt(3);
		
		numRows = 0;
		numColumns = 0;
		value = "";
		
		//Get iterator to all the rows in current sheet		
		rowIterator = sheet.iterator();
		
		// Iterate through sheet, get count of rows and columns
		while(rowIterator.hasNext()){
			
			Row row = rowIterator.next();
			ArrayList<String> teamGrades = new ArrayList<String>();
		
			//For each row, iterate through each column (skip header row)
			if (numRows > 0){
				numColumns = 0;
				Iterator<Cell> cellIterator = row.cellIterator();
				while(cellIterator.hasNext()){
		
					Cell cell = cellIterator.next();
					
					switch(cell.getCellType()){
						case Cell.CELL_TYPE_NUMERIC:
							value = NumberToTextConverter.toText(cell.getNumericCellValue());
							break;
						case Cell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;
					}
					
					teamGrades.add(value);
					numColumns += 1;
					
				} // while
				
				allTeamGrades.add(teamGrades);
				teamGrades = null;				
				
			} //if
			
			numRows += 1;
			
		}//while	
		
		
		// *************************************************************************************
		// Populate Attendance
		// Get first sheet from the workbook
		sheet = workbook.getSheetAt(0);
		
		numRows = 0;
		numColumns = 0;
		value = "";
		
		//Get iterator to all the rows in current sheet		
		rowIterator = sheet.iterator();
		
		// Iterate through sheet, get count of rows and columns
		while(rowIterator.hasNext()){
			
			Row row = rowIterator.next();
			ArrayList<String> studentAttendance = new ArrayList<String>();
		
			//For each row, iterate through each column (skip header row)
			if (numRows > 0){
				numColumns = 0;
				Iterator<Cell> cellIterator = row.cellIterator();
				while(cellIterator.hasNext()){
		
					Cell cell = cellIterator.next();
					
					switch(cell.getCellType()){
						case Cell.CELL_TYPE_NUMERIC:
							value = NumberToTextConverter.toText(cell.getNumericCellValue());
							break;
						case Cell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;
					}
					
					studentAttendance.add(value);
					numColumns += 1;
					
				} // while
				
				allAttendance.add(studentAttendance);
				studentAttendance = null;
				
			} // if
			
			numRows += 1;
			
		} // while
		
	
		
		
		file.close();
	} // Constructor
	
	
	// Returns number of assignments
	public int getNumberOfAssignments(){		
		return allGrades.get(0).size() - 1;		// Get number of columns in IndividualGrades sheet - Subtract 1 for column with Student IDs
	}
	
	// Returns number of projects
	public int  getNumberOfProjects(){
		return allTeamGrades.get(0).size() - 1;		// Get number of columns in TeamGrades sheet - Subtract 1 for column with team numbers
	}
	
	//Return attendance of student with given student ID
	public int getAttendance(String studentID){
		
		ArrayList<String> myAtt = new ArrayList<String>();
		String attendance = "";
		
		for (int i=0; i<allAttendance.size(); i++){
			
			myAtt = allAttendance.get(i);
			
			//if (myAtt.get(0) == studentID){
			if (myAtt.get(0).compareTo(studentID) == 0){
				attendance = myAtt.get(1);				
				break;
			}			
		}		
				
		return Integer.parseInt(attendance);
	}
	
	
	// Return grade average for specified team - Grade will always have two decimal places, even if zeros
	public String getTeamGradeAverage(String team){
		
		double gradeAvg = 0;
		double gradeTotal = 0;
		
		ArrayList<String> currentTeam = new ArrayList<String>();
		DecimalFormat df = new DecimalFormat("#.00");
		
		for (int i=0; i<allTeamGrades.size(); i++){		
			
			currentTeam = allTeamGrades.get(i);
			
			if (currentTeam.get(0).compareTo(team) == 0){
				
				gradeTotal = 0;
				
				for (int j=1; j<currentTeam.size(); j++){					
					gradeTotal += Double.parseDouble(currentTeam.get(j));
				}
				
				gradeAvg = gradeTotal / (this.getNumberOfProjects());
				
			}			
		}		

		return df.format(gradeAvg);
	}
	
	
	// Returns team with best grade average
	public String getTeamWithBestGrades(){
		
		double gradeAvg = 0;		
		String bestTeam = "";
		double topGrade = 0;
			
		ArrayList<String> currentTeam = new ArrayList<String>();		
		
		for (int i=0; i<allTeamGrades.size(); i++){
			
			currentTeam = allTeamGrades.get(i);
			
			gradeAvg = Double.parseDouble(this.getTeamGradeAverage(currentTeam.get(0)));
			
			if (Double.compare(gradeAvg, topGrade) > 0){
				bestTeam = currentTeam.get(0);
				topGrade = gradeAvg;
			}						
		}
		
		return bestTeam;		
	}
	
	// Returns grade average of student with specified student ID
	public String getStudentGradeAverage(String studentID){
		
		double gradeAvg = 0;
		double gradeTotal = 0;
		
		ArrayList<String> currentStudent = new ArrayList<String>();		
		DecimalFormat df = new DecimalFormat("#.##");
		
		for (int i=0; i<allGrades.size(); i++){
			
			currentStudent = allGrades.get(i);
			
			if (currentStudent.get(0).compareTo(studentID) == 0){
				
				gradeTotal = 0;
				
				for (int j=1; j<currentStudent.size(); j++){
					gradeTotal += Double.parseDouble(currentStudent.get(j));
				}
				
				gradeAvg = gradeTotal / (this.getNumberOfAssignments());
				
			}
		}
		
		return df.format(gradeAvg);
		
	}

	// Return grade average for specified assignment - Grade will always have two decimal places, even if zeros
	public String getAssignmentGradeAverage(int assignmentNum){		
		
		ArrayList<String> currentGrade = new ArrayList<String>();
		DecimalFormat df = new DecimalFormat("#.00");
		int grade = 0;
		int gradeTotal = 0;
		double numAssignments = 0;
		double gradeAvg = 0;
		
		for (int i=0; i<allGrades.size(); i++){
			
			currentGrade = allGrades.get(i);			
			grade = Integer.parseInt(currentGrade.get(assignmentNum));			
			gradeTotal += grade;	
			numAssignments += 1;			
		}
		
		gradeAvg = gradeTotal / numAssignments;
		
		return df.format(gradeAvg);
	}


} // class
	



