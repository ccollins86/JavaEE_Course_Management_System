package hw3_controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CreateSubmission")
public class CreateSubmission extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CreateSubmission() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		// get requester parameter and allocate memory for data retrieved from database
		int assignmentParam = Integer.parseInt(request.getParameter("assignment"));
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
            ResultSet rs = stmt.executeQuery("SELECT c.course_id, c.course_name, a.assignment_id, a.assignment_name\r\n"
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
		
		// display HTML create submissions view 
		request.getRequestDispatcher("/WEB-INF/CreateSubmission.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// generate time stamp for new entries
		String timeStamp = new SimpleDateFormat("MM/dd/yyyy hh:mma").format(new java.util.Date());
		
		// get requester parameters
		int assignmentIDFromParameter = Integer.parseInt(request.getParameter("assignmentID"));
		//String assignmentNameFromParameter = request.getParameter("assignmentName");
		String studentName = request.getParameter("name");
		String answer = request.getParameter("answer");
		
		// establish connection to database and update data
		Connection c = null;
        try
        {
            String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu11?useSSL=false&allowPublicKeyRetrieval=true";
            String username = "cs3220stu11";
            String password = "StUtfZGU19Bd";
            c = DriverManager.getConnection(url, username, password);
            
            String sql = "INSERT INTO submissions (assignment_id, student_name, answer, submission_date) VALUES (?, ?, ?, ?);";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, assignmentIDFromParameter);
            pstmt.setString(2, studentName);
            pstmt.setString(3, answer);
            pstmt.setString(4, timeStamp);
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

		// pass the new submission ID to destination 
		request.getSession().setAttribute("assignment", assignmentIDFromParameter);
		
		// redirect user back to submissions view
		response.sendRedirect("DisplaySubmission");		
	}
}