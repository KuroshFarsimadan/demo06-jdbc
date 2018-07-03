package ir.kuroshfarsimadan.demo06;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PersonListDatabaseSearchServlet
 */
@WebServlet("/person_list")
public class PersonListDatabaseSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PersonListDatabaseSearchServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// We are using the printwriter so that we can write text to the
		// response
		PrintWriter wout = response.getWriter();

		// Print the page html tags (note that this is a demo and you will not
		// actually rended your pages like this)
		wout.println("<!DOCTYPE html>");
		wout.println("<html>");
		wout.println("<body>");

		// Datebase search
		String username = "demo06";
		String password = "secretPassword";
		String url = "jdbc:mariadb://localhost/demo06";

		Connection yhteys = null;

		try {
			// OPEN THE CONNECTION AND THE SEARCH
			// Load the driver
			Class.forName("org.mariadb.jdbc.Driver").newInstance();
			// Open the connection
			yhteys = DriverManager.getConnection(url, username, password);

			// Execute the search
			String sql = "select id, firstname, lastname from person";
			Statement search = yhteys.createStatement();
			ResultSet result = search.executeQuery(sql);

			// Go through the database search results
			while (result.next()) {
				int id = result.getInt("id");
				String firstname = result.getString("firstname");
				String lastname = result.getString("lastname");

				// Print the retrieved database search results into the response
				wout.println(id + ". " + firstname + " " + lastname + "<br/>");
			}

		} catch (Exception e) {
			// AN ERROR OCCURED
			e.printStackTrace();
			wout.println("<p style=\"color:red\">Database search resulted in a failure.</p>");
		} finally {
			// AND FINALLY, ALWAYS REMEMBER TO CLOSE THE CONNECTION
			try {
				if (yhteys != null && !yhteys.isClosed())
					yhteys.close();
			} catch (Exception e) {
				System.out.println("The database connection does not close for some reason.");
			}
		}

		// Close the websites html tags
		wout.println("</body></html>");
	}

}
