package edu.gatech.seclass.gradescalculator;

import java.util.HashSet;

public class Course {
	
	private Students courseStudents;
	private Grades courseGrades;
	private Student myStudent;
	
	// Constructor
	public Course(Students students, Grades grades){
		courseStudents = students;
		courseGrades = grades;
		
		// Set student attendance
		this.setAttendance(courseStudents, courseGrades);		
	}
	
	// Return number of students
	public int getNumStudents(){		
		return courseStudents.getNumberOfStudents();		
	}
	
	// Return number of assignments
	public int getNumAssignments(){
		return courseGrades.getNumberOfAssignments();
	}
	
	// Return number of projects
	public int getNumProjects(){
		return courseGrades.getNumberOfProjects();
	}
	
	// Returns all students in course as a HashSet
	public HashSet<Student> getStudents(){
		return courseStudents.getStudents();
	}
	
	// Returns student object of student with given name
	public Student getStudentByName(String name){
		
		// get student object
		Student myStudent = null;				
		myStudent = courseStudents.getStudentByName(name);		
		
		return myStudent;		
	}
	
	// Returns student object of student with given ID
	public Student getStudentByID(String ID){
		return courseStudents.getStudentByID(ID);
	}
	
	// Get attendance by student ID
	public int getStudentAttendance(String studentID){
		return courseGrades.getAttendance(studentID);
	}
	
	// Return students with 100% attendance
	public HashSet<Student> getStudentsWithCompleteAttendance(){
		return courseStudents.getStudentsWithCompleteAttendance();
	} 
	
	// Returns grade average of specified team	
	public String getTeamGradeAverage(String team){
		return courseGrades.getTeamGradeAverage(team);
	}
	
	// Returns team name of team with highest grade average
	public String getTeamWithBestGrades(){
		return courseGrades.getTeamWithBestGrades();
	}
	
	// Returns grade average for student with specified student ID
	public String getStudentGradeAverage(String studentID){
		return courseGrades.getStudentGradeAverage(studentID);
	}
	
	public String getAssignmentGradeAverage(int assignmentNum){
		return courseGrades.getAssignmentGradeAverage(assignmentNum);
	}
	
	
	// Set attendance for students
	public void setAttendance(Students students, Grades grades){
		
		HashSet<Student> myStudents = students.getStudents();
		
		for (Student s : myStudents){			
			s.setAttendance(this.getStudentAttendance(s.getGtid()));
				
		}		
	}

}
