import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ACMVisualization
 */

public class ACMVisualization extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn;
	Statement statement;
	Statement select;
	ResultSet rs1;
	ResultSet rs2;
	ResultSet rs3;
	public void connect() throws Exception {

		String username = "azadeh"; // username;
		String password = "xpa25Rd"; // password;
		String url = "jdbc:mysql://rdc04.uits.iu.edu:3264/ACM"; // db server
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection(url, username, password); // connection
																		// clause

		select = conn.createStatement();
		System.out.println("Databae connection established");
	}


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ACMVisualization() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		java.io.PrintWriter out = response.getWriter();
		out.println("<html><head>"); // Start to print HTML page
		out.println("<title>Information</title></head><body>"); // page title
														// "Information"
		out.println("<h2>Please enter papaer ID:</h2>"); // page header
		// Create a HTML form with post method
		out.println("<form method=\"post\" action =\""
				+ request.getContextPath() + "/ACMVisualization\" >");
		// papaer title
		//out.println("<ol><li>");
		
		out.println("<table border=\"0\"><tr><td valign=\"top\">");
		out.println("Paper ID</td>  <td valign=\"top\">");
		out.println("<input type=\"text\" name=\"paperIDInput\" size=\"20\">");
		
		out.println("</td></tr><tr><td valign=\"top\">");
		out.println("<input type=\"submit\" value=\"Submit\"></td></tr>");
		out.println("</table></form>");
		out.println("</body></html>");
		

	} // end of doGet
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		//create HTML page for results
		response.setContentType("text/html");
		java.io.PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html><head>");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"style.css\" />"); //using the "style.css" page for styling the result pages 
		out.println("<script type=\"text/javascript\" src=\"d3.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"d3.layout.js\"></script>");

		out.println("<style type=\"text/css\">");
	    out.println(".node circle {\\" +
	    		"cursor: pointer;" +
	    		"fill: #fff;" +
	    		"stroke: steelblue;" +
	    		"stroke-width: 1.5px;}" +
	    		".node text {font-size: 11px;}" +
	    		"path.link {fill: none;stroke: #ccc;stroke-width: 1.5px;}");
	    out.println("</style>");
		out.println("<title>Result</title></head><body>"); //page title "Result"
//		out.println("<div style='height:30px'>");
		out.println("<h2>Paper Citation</h2>"); //page header
		out.println("<div id=\"body\">");
//		out.println("<script type=\"text/javascript\">");
//		out.println("var width = 960,height = 2200;\n var cluster = d3.layout.cluster().size([height, width - 160]);\n"+
//"var diagonal = d3.svg.diagonal().projection(function(d) { return [d.y, d.x]; });\n"+
//"var svg = d3.select('body').append('svg').attr('width', width).attr('height', height).append('g').attr('transform', 'translate(40,0)');\n"+
//"d3.json('paperCitation.json', function(error, root) {\n"+
//  "var nodes = cluster.nodes(root), links = cluster.links(nodes);\n"+
//" var link = svg.selectAll('.link').data(links).enter().append('path').attr('class', 'link').attr('d', diagonal);\n"+
//"var node = svg.selectAll('.node').data(nodes).enter().append('g').attr('class', 'node').attr('transform', function(d) { return 'translate(' + d.y + ',' + d.x + ')'; })\n"+
//"node.append('circle').attr('r', 4.5);\n"+
//"node.append('text').attr('dx', function(d) { return d.children ? -8 : 8; }).attr('dy', 3)\n"+
//".style('text-anchor', function(d) { return d.children ? 'end' : 'start'; }).text(function(d) { return d.paperId + ', ' + d.paperTitle; });\n"+
//"});\n"+
//"d3.select(self.frameElement).style('height', height + 'px');\n");
//		
//		
//		
//		out.println("</script>");
		
		
//		out.println("<div id=\"body\"><div id=\"footer\">");
//		
//		out.println("<div class=\"hint\">click or option-click to expand or collapse</div>");
//	out.println("</div>");
	out.println("<script type=\"text/javascript\" src=\"cite.js\"></script>");
	out.println("<script type=\"text/javascript\" src=\"mjtooltip.js\"></script>");

	out.println("</div>");
//	out.println("</script>");
		int firstID = Integer.parseInt(request.getParameter("paperIDInput")); // first paper ID
		

		try {
			connect();
			FileWriter fw = new FileWriter("C:/Users/Azade/workspace/ACMData/WebContent/paperCitation.json");
			BufferedWriter bw = new BufferedWriter(fw);

			createJson(firstID);
			String parent = "{" + "\"paperId\":" + firstID + ","
					+ "\"paperTitle\": \"" + getTitle(firstID)
					+ "\"," + "\"count\":" + Count(firstID) + ","
					+ "\"children\":[";
			String closing = "} ] }";
			String correction = jsonData.replace("},]", "}]");
			correction = correction.replace("]{", "]},{");
			String json2file = parent + correction + closing;
			bw.write(json2file);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		out.println("</div>");	
	out.println("</body></html>");
	
}
	
	public  ArrayList<Integer> getPaperCitation(int paperID)
			throws Exception {

		ArrayList<Integer> paperCitation = new ArrayList<Integer>();

		try {
			rs1 = select.executeQuery("SELECT * from citation WHERE paper_id=  '"
							+ paperID + "'");
			while (rs1.next()) {
				paperCitation.add(rs1.getInt(1));
			} // end of while

		} catch (SQLException e) {
			e.printStackTrace();
		} // end of catch
		return paperCitation;

	}

	public  int Count(int citationID) throws Exception {

		int count = 0;
		try {
			rs2 = select
					.executeQuery("select count(*) from ACM.citation_position where citation_id = '"
							+ citationID + "'");
			while (rs2.next()) {

				count = rs2.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} // end of catch
		return count;

	}

	public  String getTitle(int paperID) throws Exception {

		String title = null;
		try {
			//connect();
			
			// select * from ACM.paper where id = 2583;
			rs3 = select.executeQuery("select * from ACM.paper where id = '" + paperID + "'");
			while (rs3.next()) {

				title = rs3.getString(3);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} // end of catch
		return title;

	}
	
    public String jsonData = "";

    public void createJson(int paperId) throws Exception {
            for (int pid : getPaperCitation(paperId)) {
                this.jsonData += "{";
                this.jsonData += "\"paperId\":" + pid + ",";
                this.jsonData += "\"count\":" + Count(pid) + ",";
                this.jsonData += "\"paperTitle\":" + "\"" + getTitle(pid) + "\"";
                if (!getPaperCitation(pid).isEmpty()){
                	this.jsonData += ", \"children\": [";
                    createJson(pid);
                    this.jsonData += "]";
                } else {
                	this.jsonData += "},";
                }
            }
        return;
    }

}

class stackPaperRecord {
	public int paperID;
	public String paperTitle;

	public stackPaperRecord(int paperID, String paperTitle) {

		this.paperID = paperID;
		this.paperTitle = paperTitle;

	}

}

class citationRecord {
	public int paperID;
	public int citationID;
	public String paperTitle;
	public int countCitationID;

	citationRecord(int paperID, int citationID, String paperTitle, int countCitationID) {

		this.paperID = paperID;
		this.citationID = citationID;
		this.paperTitle = paperTitle;
		this.countCitationID = countCitationID;

	}

}
