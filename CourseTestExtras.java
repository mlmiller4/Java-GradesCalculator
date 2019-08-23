package edu.gatech.seclass.gradescalculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CourseTestExtras {
	
    Students students = null;
    Grades grades = null;
    Course course = null;
    static final String STUDENTS_DB = "DB/GradesDatabase6300-students.xlsx";
    static final String GRADES_DB = "DB/GradesDatabase6300-grades.xlsx";
    
    @Before
    public void setUp() throws Exception {
        students = new Students(STUDENTS_DB);
        grades = new Grades(GRADES_DB);
        course = new Course(students, grades);
    }

    @After
    public void tearDown() throws Exception {
        students = null;
        grades = null;
        course = null;
    }
    
    @Test
    // Get all students with 100% attendance
    public void testGetStudentsWithCompleteAttendance() {
    	HashSet<Student> goodStudents = null;
    	goodStudents = course.getStudentsWithCompleteAttendance();
    	assertEquals(4, goodStudents.size());
    }    
    
	@Test
    // Get team grade average
    public void testGetTeamGradeAverage1(){
    	assertEquals("91.60", course.getTeamGradeAverage("Team 3"));
    }
	
	@Test
	// Team grade average - test 2
	public void testGetTeamGradeAverage2(){
		assertEquals("89.80", course.getTeamGradeAverage("Team 4"));
	}
	
	@Test
	// Get team with best grade average
	public void testGetTeamWithBestGrades(){
		assertEquals("Team 1", course.getTeamWithBestGrades());
	}
	
	@Test 
	// Get a student's grade average
	public void testGetStudentGradeAverage(){
		assertEquals("93.75", course.getStudentGradeAverage("901234506"));
	}
	
	@Test
	public void testGetAssignmentGradeAverage(){
		assertEquals("99.13", course.getAssignmentGradeAverage(2));
	}
    
	@Test
	public void testGetAssignmentGradeAverage2(){
		assertEquals("77.00", course.getAssignmentGradeAverage(3));
	}
    

}
