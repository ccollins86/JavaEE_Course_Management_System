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

import hw3_model.Submission;

@WebServlet("/DisplaySubmission")
public class DisplaySubmission extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DisplaySubmission() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// check requester source and get parameter
		int assignmentParam;
		if(request.getParameter("assignment") != null) {
			
			assignmentParam = Integer.parseInt(request.getParameter("assignment"));
		}
		else {
		
			assignmentParam = (int) request.getSession().getAttribute("assignment");
		}
		
		// allocate memory to store retrieved data from database
		List<Submission> submissions = new ArrayList<Submission>();		
		int courseID = 0; 
		int  assignmentID = 0;
		String coursetURL = ""; 
		String assignmentURL = "";
		
		// establish connection to database and retrieve data 
		Connection c = null;
        try
        {
            String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu11?useSSL=false&allowPublicKeyRetrieval=true";
            String username = "cs3220stu11";
            String password = "StUtfZGU19Bd";
            c = DriverManager.getConnection(url, username, password);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM submissions WHERE assignment_id = '" + assignmentParam + "';");           

            while(rs.next())
            {           	
            	submissions.add(new Submission(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
            
            rs = stmt.executeQuery("SELECT c.course_id, c.course_name, a.assignment_id, a.assignment_name\r\n"
            		+ "FROM assignments a\r\n"
            		+ "INNER JOIN courses c ON a.course_id = c.course_id\r\n"
            		+ "WHERE a.assignment_id = '" + assignmentParam + "';");
            
            while(rs.next())
            {           	
            	courseID = rs.getInt(1);
            	coursetURL = rs.getString(2);
            	assignmentID = rs.getInt(3);
        		assignmentURL = rs.getString(4);
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
		request.setAttribute("courseID", courseID);
		request.setAttribute("coursetURL", coursetURL);
		request.setAttribute("assignmentID", assignmentID);
		request.setAttribute("assignmentURL", assignmentURL);
		request.setAttribute("submissions", submissions);
		
		// Display HTML submissions view
		request.getRequestDispatcher("/WEB-INF/DisplaySubmission.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}