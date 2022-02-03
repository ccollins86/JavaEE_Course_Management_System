package hw3_controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CreateAssignment")
public class CreateAssignment extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CreateAssignment() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// get requester parameter
		int courseParam = Integer.parseInt(request.getParameter("id"));
		String requestCourseName = "";
		
		// establish connection to database and retrieve data
		Connection c = null;
        try
        {
            String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu11?useSSL=false&allowPublicKeyRetrieval=true";
            String username = "cs3220stu11";
            String password = "StUtfZGU19Bd";
            c = DriverManager.getConnection(url, username, password);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT course_name FROM courses WHERE course_id = '" + courseParam + "';");           

            while(rs.next())
            {           	
            	//requestCourseID = rs.getInt(1);
            	requestCourseName = rs.getString(1);
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
		request.setAttribute("courseParam", courseParam);
		request.setAttribute("url", requestCourseName);
		
		// Display HTML create assignment view
		request.getRequestDispatcher("/WEB-INF/CreateAssignment.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
								
		// get request parameter
		int courseParam = Integer.parseInt(request.getParameter("courseParam"));
		String courseName = request.getParameter("courseName");
				
		// validate user input
		if (request.getParameter("assignment") == null || request.getParameter("assignment").equals("")) {
			
			request.setAttribute("courseParam", courseParam);
			request.setAttribute("url", courseName);
			request.getRequestDispatcher("/WEB-INF/CreateAssignment.jsp").forward(request, response);
			return;
		}
				
		// get requester parameter (assignment name)
		String newAssignment = request.getParameter("assignment");
		
		// establish connection to database and update data
    	Connection c = null;
        try
        {
            String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu11?useSSL=false&allowPublicKeyRetrieval=true";
            String username = "cs3220stu11";
            String password = "StUtfZGU19Bd";
            c = DriverManager.getConnection(url, username, password);
            
            String sql = "INSERT INTO assignments (course_id, assignment_name) VALUES (?, ?);";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, courseParam);
            pstmt.setString(2, newAssignment);
            pstmt.executeUpdate();           
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
		request.setAttribute("id", courseParam);
		request.getSession().setAttribute("courseParam", courseParam);
		
		// redirect user to the assignment list
		response.sendRedirect("DisplayAssignment");	
	}
}