import java.sql.*;

public class dbConnect {

	private Connection con;
	private Statement st;
	private ResultSet rs;
	private String db;
	public dbConnect(String user,String pass, String dataBase) throws Exception 
	{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://10.4.42.185:3306/"+dataBase, user, pass);
			st = con.createStatement();
			db=dataBase;
	}

	public void createDatabase() {
		try {
			rs = st.executeQuery("select month(now())");
			rs.next();
			int mo = Integer.parseInt(rs.getString("month(now())"));

			rs = st.executeQuery("select Year(now())");
			rs.next();
			int year = Integer.parseInt(rs.getString("year(now())"));

			String query = "create database ";
			if (mo == 6) {
				String dbName = "sy" + year + "_" + (year + 1) % 2000 + "_1stsem;";
				st.execute(query + dbName);
				createTable(dbName);
			} else if (mo == 10) {
				String dbName = "sy" + year + "_" + (year + 1) % 2000 + "_2ndsem;";
				st.execute(query + dbName);
				createTable(dbName);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void createTable(String dName) {
		try {
			String tableMaker = "use " + dName;
			st.execute(tableMaker);
			tableMaker = "create table Student ( studId int not null auto_increment, name text, addr text, course text, yrlevel text, Primary key (studID) )";
			st.execute(tableMaker);
			tableMaker = "create table grades (gId int not null auto_increment, prelim int, midterm int, final int, primary key (gId))";
			st.execute(tableMaker);
			tableMaker = "create table teacher(tId int not null auto_increment, tName text, tDept text, primary key (tID) )";
			st.execute(tableMaker);
			tableMaker = "create table subject (subjId int not null auto_increment, subjCode text,sched text, tId int, primary key (subjId), foreign key (tId) references teacher(tId))";
			st.execute(tableMaker);
			tableMaker = "create table enrolled( eId int not null auto_increment, subjId int, studId int, primary key (eId), foreign key (subjId) references subject(subjId), foreign key(studId) references student(studId))";
			st.execute(tableMaker);

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public String[][] getSubjData() {
		try {

			ResultSet rss;
			rss = st.executeQuery("SELECT COUNT(subjID) AS pila from subject");
			rss.next();
			String[][] x = new String[Integer.parseInt(rss.getString("pila"))][5];
			int z = 0;
			String query = "SELECT subject.*,COUNT(enrolled.subjId) AS Enrollees FROM enrolled right JOIN subject ON enrolled.subjId=subject.subjId GROUP BY subjId;";
			rs = st.executeQuery(query);
			while (rs.next()) {
				x[z][0] = rs.getString("subjID");
				x[z][1] = rs.getString("subjCode");
				x[z][2] = rs.getString("sched");
				x[z][3] = rs.getString("tID");
				x[z][4] = rs.getString("Enrollees");
				z++;
			}
			return x;
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}

	}

	public String[][] getEnData(String StudID) {
		try {
			ResultSet rss;
			rss = st.executeQuery("SELECT COUNT(*) AS EnrolledS from enrolled WHERE studid=" + StudID);
			rss.next();
			String[][] x = new String[Integer.parseInt(rss.getString("EnrolledS"))][3];
			String query = "SELECT subject.subjID,subject.subjCode,subject.sched,enrolled.studID FROM enrolled INNER JOIN subject ON subject.subjId=enrolled.subjId where enrolled.studId ="
					+ StudID;
			rs = st.executeQuery(query);
			int z = 0;
			while (rs.next()) {
				x[z][0] = rs.getString("subjID");
				x[z][1] = rs.getString("subjCode");
				x[z][2] = rs.getString("sched");
				z++;
			}
			return x;
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
	}

	public String getData(String name) {
		try {
			String query = "select * from STUDENT where name='" + name + "'";
			rs = st.executeQuery(query);
			System.out.println("Records from Database");
			while (rs.next()) {
				return rs.getString("studID");
			}
		} catch (Exception ex) {

			System.out.println(ex);
			return "0";
		}
		return "0";
	}

	public void insertData(String name, String address, String Corse, String year) {
		try {
			String query = "insert into STUDENT values(NULL, '" + name + "','" + address + "','" + Corse + "','" + year
					+ "' )";
			st.executeUpdate(query);
			System.out.println("Records inserted");
			query = "select MAX(studID) FROM student";
			rs = st.executeQuery(query);
			rs.next();
			query="GRANT SELECT on "+db+".* on "+rs.getString("studID")+"@'%' IDENTIFIED BY 'pass'";
			st.executeUpdate(query);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void enrollStud(String subID, String sID) {
		try {
			String query = "select * from Enrolled where subjID='" + subID + "' AND studID='" + sID + "'";
			rs = st.executeQuery(query);
			if (rs.next()) {
				System.out.println("lmfao");
				throw new SQLException();
			} else {
				query = "select COUNT(*) from Enrolled where subjID='" + subID + "'";
				rs = st.executeQuery(query);
				rs.next();
				if (rs.getString("COUNT(*)").equals("40")) {
					System.out.println("Rlul");
					throw new SQLException();
				} else {
					query = "insert into enrolled values(NULL, '" + subID + "','" + sID + "')";
					st.executeUpdate(query);
					System.out.println("Records inserted");
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void deleteData(String name, String address, String Corse, String gender, String year) {
		try {
			String query = "delete from STUDENT where name='" + name + "'";
			st.executeUpdate(query);
			System.out.println("Records deleted");
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void updateData(String name, String address, String Corse, String gender, String year) {
		try {
			String query = "update student set addr='" + address + "',course='" + Corse + "',gender='" + gender
					+ "',yrlevel='" + year + "' where name='" + name + "'";
			st.executeUpdate(query);
			System.out.println("Records updated");
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

}
