package hw3_model;

import java.util.List;

public class Course {
	
	private int id;
	private String courseName;
	private List<Assignment> assignments;
	
	public Course(int id, String courseName, List<Assignment> assignments) {
		
		this.id = id;
		this.courseName = courseName;
		this.assignments = assignments;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public List<Assignment> getAssignments(){
		return assignments;
	}
	
	public void setAssignments(List<Assignment> assignments){
		this.assignments = assignments;
	}	
}