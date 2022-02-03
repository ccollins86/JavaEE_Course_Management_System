package hw3_controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CreateCourse")
public class CreateCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public CreateCourse() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/CreateCourse.jsp").forward(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// validate user input 
		if (request.getParameter("course") == null || request.getParameter("course").equals("")) {
			
			request.getRequestDispatcher("/WEB-INF/CreateCourse.jsp").forward(request, response);
			return;
		}
		
		// get requester parameter (course name)
		String course = request.getParameter("course");
    	
		// establish connection to database and update data 
    	Connection c = null;
        try
        {
            String url = ""; // Add your database URL here
            String username = ""; // Add your username here
            String password = ""; // Add your password here
            c = DriverManager.getConnection(url, username, password);
            
            String sql = "INSERT INTO courses (course_name) VALUES (?);";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, course);
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
        
        // redirect the user to course view 
        response.sendRedirect("DisplayCourses");
	}
}
