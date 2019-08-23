package gradescalculator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;


public class Students {
	
	// Variables for the Excel workbook and sheet
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	private ArrayList<Student> allStudents = new ArrayList<Student>();
	private ArrayList<ArrayList<String>> allTeams = new ArrayList<ArrayList<String>>();
	
	// Constructor - Creates and ArrayList of Student objects
	public Students(String studentsDB) throws IOException{		
				
		FileInputStream file = new FileInputStream(new File(studentsDB));
			
		//Get the workbook instance for XLSX file 
		workbook = new XSSFWorkbook(file);
			 
		//Get first sheet from the workbook
		sheet = workbook.getSheetAt(0);
		
		
		ArrayList<String> studentNames = new ArrayList<String>();
		ArrayList<String> studentIDs = new ArrayList<String>();
		ArrayList<String> studentEmailAddresses = new ArrayList<String>();
		 
		
		int numRows = 0;			// Count rows & columns
		int numColumns = 0;	
		String value = "";
		
		//Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = sheet.iterator();
		
		// Iterate through sheet, get count of rows and columns
		while(rowIterator.hasNext()){
			
			Row row = rowIterator.next();
			
			//For each row, iterate through each column
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
					
					if (numColumns == 0){
						studentNames.add(value);
					} else if (numColumns == 1){
						studentIDs.add(value);							
					} else if (numColumns == 2){
						studentEmailAddresses.add(value);
					}	
					
					numColumns += 1;					
				}				
			}
			
			numRows += 1;
					
		}  // while loop
		
		
		// Create ArrayList of Student objects, use studentNames, studentIDs and studentEmailAddresses to create
		// Student objects, and add to allStudents ArrayList		
		for (int i = 0; i<studentNames.size(); i++){
			
			Student newStudent = new Student(studentNames.get(i), studentIDs.get(i), studentEmailAddresses.get(i));
			allStudents.add(newStudent);
			newStudent = null;
		}

		studentNames = null;
		studentIDs = null;
		studentEmailAddresses = null;
		
		
		
		// ****************************************************************************
		// Populate teams		
		
		//Get second sheet from the workbook
		sheet = workbook.getSheetAt(1);
		
		numRows = 0;			// Count rows & columns
		numColumns = 0;	
		value = "";
		
		//Get iterator to all the rows in current sheet
		rowIterator = sheet.iterator();
		
		// Iterate through sheet, get count of rows and columns
		while(rowIterator.hasNext()){
			
			Row row = rowIterator.next();
			ArrayList<String> team = new ArrayList<String>();
		
			//For each row, iterate through each column
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
					
					team.add(value);
					numColumns += 1;
					
				}  // while
				
				allTeams.add(team);
				team = null;
				
			} // if
			
			numRows += 1;
			
		} // while
		
		
		// Set team property for all students
		this.setTeams();
		
		
		// Set attendance property for all students
		//this.setAttendance();

		
		file.close();
	}  // Constructor
	
	
	// Returns number of students
	public int getNumberOfStudents(){
		return allStudents.size();
	}	
	
	// Returns all students as a HashSet
	public HashSet<Student> getStudents(){
		HashSet<Student> set = new HashSet<Student>(allStudents);
		return set;
	}
	
	// Return student object for student with given name
	public Student getStudentByName(String name){
		
		Student myStudent = null;		
		
		for (Student s : allStudents){
			if (s.getName().compareTo(name) == 0){
				myStudent = s;
				break;
			}			
		}
		
		return myStudent;
	}
	
	// Return student object for student with given ID
	public Student getStudentByID(String ID){
		
		Student myStudent = null;
		
		for (Student s : allStudents){
			if (s.getGtid().compareTo(ID) == 0){
				myStudent = s;
				break;
			}
		}
		
		return myStudent;	
		
	}
	
	// Sets the 'team' property of the Student objects
	public void setTeams(){
		
		ArrayList<String> myTeam = new ArrayList<String>();
		String myID;
		Student myStudent;
		
		for (int i=0; i<allTeams.size(); i++){
			
			myTeam = allTeams.get(i);
			
			for (int j=1; j<myTeam.size(); j++){
				myID = myTeam.get(j);
				
				myStudent = this.getStudentByID(myID);
				
				myStudent.setTeam(myTeam.get(0));
				
			}
			
		}
		
		myTeam = null;
		myID = null;
		myStudent = null;		
	}
	
	// Returns HashSet of Student objects with 100% attendance
	public HashSet<Student> getStudentsWithCompleteAttendance(){		
		
		HashSet<Student> goodStudents = new HashSet<Student>();
		
		for (Student s : allStudents){
			if (s.getAttendance() == 100){
				goodStudents.add(s);				
			}			
		}
		
		return goodStudents;		
	}
	

	
} // class
		
		
		
		
		



