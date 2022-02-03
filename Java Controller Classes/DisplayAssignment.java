package hw3_controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

@WebServlet("/DisplayAssignment")
public class DisplayAssignment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DisplayAssignment() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// check requester source and get parameter
		int sessionID = 0;		
		if (request.getParameter("id") != null ) {
			
			sessionID = Integer.parseInt(request.getParameter("id"));
		}
		else {
			
			sessionID = (int) request.getSession().getAttribute("courseParam");
		}
		
		// allocate memory to store retrieved data from database
		List<Assignment> assignments = new ArrayList<Assignment>();
		Course userInput = new Course(sessionID, null, null);
		
		// establish connection to database and retrieve data 
    	Connection c = null;
        try
        {
            String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu11?useSSL=false&allowPublicKeyRetrieval=true";
            String username = "cs3220stu11";
            String password = "StUtfZGU19Bd";
            c = DriverManager.getConnection(url, username, password);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT a.assignment_id, a.assignment_name,\r\n"
            		+ "(SELECT COUNT(*) FROM submissions s WHERE a.assignment_id = s.assignment_id) number_of_submissions,\r\n"
            		+ "(SELECT MAX(s.submission_date) FROM submissions s WHERE a.assignment_id = s.assignment_id) latest_submission\r\n"
            		+ "FROM assignments a\r\n"
            		+ "WHERE a.course_id = '" + sessionID + "';");           

            while(rs.next())
            {           	
            	assignments.add(new Assignment(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), null));
            	String sql = "UPDATE assignments SET number_of_submissions = '" + rs.getInt(3) + "', "
            			+ "latest_submission = '" + rs.getString(4) + "' WHERE assignment_id = '" + rs.getInt(1) + "';";
                PreparedStatement pstmt = c.prepareStatement(sql);
                pstmt.executeUpdate(); 
            } 
            
            rs = stmt.executeQuery("SELECT * FROM courses;");
            while(rs.next())
            {
            	if (rs.getInt(1) == sessionID) {
            		userInput = new Course(rs.getInt(1), rs.getString(2), assignments);
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
		request.setAttribute("sessionID", sessionID);
		request.setAttribute("userInput", userInput);
		request.setAttribute("url", userInput.getCourseName());
		
		// Display HTML assignments view
		request.getRequestDispatcher("/WEB-INF/DisplayAssignment.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}