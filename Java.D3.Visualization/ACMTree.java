

		import java.sql.Connection;
		import java.sql.DriverManager;
		import java.sql.SQLException;
		import java.sql.Statement;
		import java.sql.ResultSet;
		import java.util.ArrayList;
		import java.util.List;

		import java.io.BufferedWriter;
		import java.io.File;
		import java.io.FileWriter;
		import java.io.IOException;

		public class ACMTree {
			// define variable for connect to database
			static Connection conn;
			static Statement statement = null;
			static Statement select = null;
			static ResultSet rs1;
			static ResultSet rs2;
			static ResultSet rs3;

			public static void connect() throws Exception {

				String username = "azadeh"; // username;
				String password = "xpa25Rd"; // password;
				String url = "jdbc:mysql://rdc04.uits.iu.edu:3264/ACM"; // db server
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection(url, username, password); // connection
																				// clause

				select = conn.createStatement();
				System.out.println("Databae connection established");
			}

			public static ArrayList<Integer> getPaperCitation(int paperID)
					throws Exception {

				ArrayList<Integer> paperCitation = new ArrayList<Integer>();

				try {
					rs1 = select
							.executeQuery("SELECT * from citation WHERE paper_id=  '"
									+ paperID + "'");
					while (rs1.next()) {

						paperCitation.add(rs1.getInt(1));

					} // end of while

				} catch (SQLException e) {
					e.printStackTrace();
				} // end of catch
				return paperCitation;

			}

			public static int Count(int citationID) throws Exception {

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

			public static String getTitle(int paperID) throws Exception {

				String title = null;
				try {
					rs3 = select.executeQuery("select * from ACM.paper where id = '"
							+ paperID + "'");
					while (rs3.next()) {

						title = rs3.getString(3);
					}

				} catch (SQLException e) {
					e.printStackTrace();
				} // end of catch
				return title;

			}

			public static void main(String args[]) throws Exception {

				ArrayList<paperRecord> paperRecords = new ArrayList<paperRecord>();
				ArrayList<Integer> paperCitation = new ArrayList<Integer>();
				ArrayList<stackPaperRecord> stackPaperRecords = new ArrayList<stackPaperRecord>();

				connect(); // connect to database

				int firstID = 2583;
				String firstTitle = getTitle(firstID);
				stackPaperRecords.add(new stackPaperRecord(firstID, firstTitle));
				try {
					FileWriter fw = new FileWriter("/Users/Azade/paperCitation.nwb");
					BufferedWriter bw = new BufferedWriter(fw);

					for (int k = 0; k < stackPaperRecords.size(); k++) {

						paperCitation = getPaperCitation(stackPaperRecords.get(k).paperID);

						for (int i = 0; i < paperCitation.size(); i++) {
							int paperID = stackPaperRecords.get(k).paperID;
							int citationID = paperCitation.get(i);
							int countCitationID = Count(citationID);

							paperRecords.add(new paperRecord(paperID, citationID,
									countCitationID));

							// if (countCitationID != 0){
							String paperTitle = getTitle(citationID);

							stackPaperRecords.add(new stackPaperRecord(citationID,
									paperTitle));
							// }

						} // end of for i

						

						paperCitation.clear();
						// paperRecords.clear();

					}// end of for k
					
					for (int z = 0; z < stackPaperRecords.size(); z++) {
						System.out.print(stackPaperRecords.get(z).paperID);
						System.out.print(" , ");
						System.out.print(stackPaperRecords.get(z).paperTitle);
						System.out.print(" , ");
						System.out.println("Paper");

						
					}

					
					for (int m = 0; m < paperRecords.size(); m++) {

						System.out.print(paperRecords.get(m).paperID);
						System.out.print(" , ");
						System.out.print(paperRecords.get(m).citationID);
						System.out.print(" , ");
						System.out.print(paperRecords.get(m).countCitationID);
						System.out.println();
						
					}


				} catch (IOException e) {
					e.printStackTrace();

				}

			}// end of main
		}// end of class ACM

		class paperRecord {
			public int paperID;
			public int citationID;
			public int countCitationID;

			paperRecord(int paperID, int citationID, int countCitationID) {

				this.paperID = paperID;
				this.citationID = citationID;
				this.countCitationID = countCitationID;

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


		public class Tree<T> {
		    private Node<T> root;

		    public Tree(T rootData) {
		        root = new Node<T>();
		        root.data = rootData;
		        root.children = new ArrayList<Node<T>>();
		    }

		    public static class Node<T> {
		        private T data;
		        private Node<T> parent;
		        private List<Node<T>> children;
		    }
		}