import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class script {
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private String db;

	public static void main(String args[]) {
		new script().tanigna();
		
	}

	public void tanigna() 
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sy2017_18_1stsem", "root", "root");
			st = con.createStatement();
			db = "sy2017_18_1stsem";
			for (int x = 1; x < 8; x++) {
				String query;
				query = "select * from Student where studID=" + x;
				rs = st.executeQuery(query);
				rs.next();
				System.out.println(rs.getString("studID")+" output "+rs.getString("name"));
				query = "GRANT SELECT ON " + db + ".* TO '" + rs.getString("studID") + "'@'%' IDENTIFIED BY '"
						+ rs.getString("name") + "'";
				st.executeUpdate(query);
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
}
