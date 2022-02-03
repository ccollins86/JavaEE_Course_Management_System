package hw3_controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hw3_model.Assignment;
import hw3_model.Course;

@WebServlet(urlPatterns = "/DisplayCourses", loadOnStartup = 1)
public class DisplayCourses extends HttpServlet {
	private static final long serialVersionUID = 1L;
       	
    public DisplayCourses() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// allocate memory to store retrieved data from database
		List<Course> courses = new ArrayList<Course>();
    	
		// establish connection to database and retrieve data 
    	Connection c = null;
        try
        {
            String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu11?useSSL=false&allowPublicKeyRetrieval=true";
            String username = "cs3220stu11";
            String password = "StUtfZGU19Bd";
            c = DriverManager.getConnection(url, username, password);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM courses;");           

            while(rs.next())
            {           	
            	courses.add(new Course(rs.getInt(1), rs.getString(2), null));
            }
            
            rs = stmt.executeQuery("SELECT * FROM assignments;");
            while(rs.next())
            {           	
            	Assignment newAssignment = new Assignment(rs.getInt(1), rs.getString(3), rs.getInt(4), rs.getString(5), null);
            	for (Course course: courses) {
            			
        			if (course.getId() == rs.getInt(2) && course.getAssignments() == null) {
            			
        				List<Assignment> assignments = new ArrayList<Assignment>();
        				assignments.add(newAssignment);
        				course.setAssignments(assignments);
            		}
        			else if (course.getId() == rs.getInt(2) && course.getAssignments() != null){
        				
        				course.getAssignments().add(newAssignment);
        			}
        			else {
        				
        				continue;
        			}
            	}            	
            }            
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(c != null) c.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        
        // pass parameters to requester
    	request.setAttribute("courses", courses);

		// Display HTML courses view
		request.getRequestDispatcher("/WEB-INF/DisplayCourses.jsp").forward(request, response);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}