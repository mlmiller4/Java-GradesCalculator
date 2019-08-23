package edu.gatech.seclass.gradescalculator;

public class Student {
	
	private String studentGTID;
	private String studentName;
	private String studentEmail;
	private int attendance;
	private String team;
	
	// Constructor
	public Student(String name, String ID, String email){
		studentGTID = ID;
		studentName = name;
		studentEmail = email;
	}
	
	public void setStudentName(String Name){
		studentName = Name;		
	}
	
	public void setStudentGTID(String ID){
		studentGTID = ID;
	}
	
	public String getName(){
		return studentName;
	}
	
	public String getGtid(){
		return studentGTID;
	}
	
	public void setStudentEmail(String email){
		studentEmail = email;
	}
	
	public String getStudentEmail(){
		return studentEmail;
	}
	
	public int getAttendance(){		
		return attendance;		
	}

	public void setAttendance(int att){
		attendance = att;
	}
	
	public void setTeam(String t){
		team = t;
	}
	
	public String getTeam(){
		return team;
	}
	
}
