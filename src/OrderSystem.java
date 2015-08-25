import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;
import javax.swing.*;


	public class OrderSystem extends JFrame
{
Connection con;
TextArea t1,t2;
Panel p1,p2,p3,p4;
Button b1,b2,b3,b4,b5,b6,b7,b8;
TableFromDatabase tb1;

public OrderSystem()
{
setLayout(new FlowLayout());
p1 = new Panel();
p2 = new Panel();
p3 = new Panel();
p4 = new Panel();

p1.setLayout(new GridLayout(3,3,20,20));
p2.setLayout(new BorderLayout());
p3.setLayout(new GridLayout(2,3,15,15));

tb1 = new TableFromDatabase();



t1 = new TextArea("Enter query",5,60);
t2 = new TextArea("Result",5,60);


b1 = new Button("Connect");
b2 = new Button("Disconnect");
b3 = new Button("Show Orders");
b4 = new Button("Update");
b5 = new Button("Insert");
b6 = new Button("Delete");
b7 = new Button("Clear query");
b8 = new Button("Clear result");

p1.add(b1);
p1.add(b2);
p2.add("Center",t1);
p3.add(b3);
p3.add(b4);
p3.add(b5);
p3.add(b6);
p3.add(b7);
p3.add(b8);
p2.add("South",p3);
p4.add(t2);
p4.add(tb1);
add(p1);
add(p2);
add(p4);
addWindowListener(new WindowAdapter()
{
public void windowClosing(WindowEvent e)
{
System.exit(0);
}
});
b1.addActionListener(new ConnectDB());
b2.addActionListener(new DisconnectDB());
b3.addActionListener(new Select());
b5.addActionListener(new Insert());
b4.addActionListener(new Update());
b6.addActionListener(new Delete());
b7.addActionListener(new ClearQuery());
b8.addActionListener(new ClearResult());
}
//}





class ConnectDB implements ActionListener
{
public void actionPerformed(ActionEvent e)
{
try
{
Class.forName("com.mysql.jdbc.Driver");
// String s = InetAddress.getLocalHost();
con = DriverManager.getConnection("jdbc:mysql://localhost/mydb","root","netbuilder");
t2.setText("Connection success");
}
catch(ClassNotFoundException e2)
{
t2.setText("Class not found");
}
catch(Exception e1)
{
t2.setText("error in connection");
}
}
}

class DisconnectDB implements ActionListener
{
public void actionPerformed(ActionEvent e)
{
try
{
con.commit();
con.close();
t2.append("Connection closed");
}
catch(Exception e1)
{
t2.setText("error in disconnection");
}
}
}

class Select implements ActionListener
{
public void actionPerformed(ActionEvent e)
{
try
{
Statement stmt;
ResultSet rs;
String query;
boolean more;
stmt = con.createStatement();
int count =0;
query = t1.getText();
rs = stmt.executeQuery(query);
more = rs.next();
if(!more)
{
t2.append("No results");
return;
}
t2.append("NO\tBName\tAname\tRate");
while(more)
{
t2.append("\n"+rs.getString("ano")+"\t"+rs.getString("bname")+"\t"+rs.getString( "aname")+"\t"+rs.getString("rate"));
count++;
more=rs.next();
}
t2.append("\n"+count+" rows selected");
rs.close();
stmt.close();
}
catch(Exception e1)
{
t2.setText("error in selection");
}
}
}

class Update implements ActionListener
{
public void actionPerformed(ActionEvent e)
{
try
{
Statement stmt;
String query;
int rows;
stmt = con.createStatement();
query = t1.getText();
rows = stmt.executeUpdate(query);
if(rows == -1)
{
t2.append("Table dropped");
}

else
{
t2.append("\n"+rows+ " rows updated");
}
con.commit();
stmt.close();
}
catch(Exception e1)
{
}
}
}
class Insert implements ActionListener
{
public void actionPerformed(ActionEvent e)
{
try
{
Statement stmt;
String query;
int rows;
stmt = con.createStatement();
query = t1.getText();
rows = stmt.executeUpdate(query);
if(rows == -1)
{
t2.append("Table dropped");
}

else
{
t2.append("\n"+rows+ " rows inserted");
}
con.commit();
stmt.close();
}
catch(Exception e1)
{

}
}
}
class Delete implements ActionListener
{
public void actionPerformed(ActionEvent e)
{
try
{
Statement stmt;
String query;
int rows;
stmt = con.createStatement();
query = t1.getText();
rows = stmt.executeUpdate(query);
if(rows == -1)
{
t2.append("Table dropped");
}

else
{
t2.append("\n"+rows+ " row deleted");
}
con.commit();
stmt.close();
}
catch(Exception e1)
{

}
}
}
class ClearQuery implements ActionListener
{
public void actionPerformed(ActionEvent e)
{
t1.setText("");
}
}

class ClearResult implements ActionListener
{
public void actionPerformed(ActionEvent e)
{
t2.setText("");
}
}
public static void main(String[] args)
{
OrderSystem c = new OrderSystem();
c.setVisible(true);
c.setSize(300,400);
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


}
/*public class OrderSystem {
	   // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/mydb";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "netbuilder";
	   
	   public static void main(String[] args) {
	   Connection conn = null;
	   Statement stmt = null;
	   try{
	      //STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 3: Open a connection
	      System.out.println("Connecting to a selected database...");
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      System.out.println("Connected database successfully...");
	      
	      //STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      stmt = conn.createStatement();

	      // Create records(DELETE)
	      System.out.println("Creating statement DELETE ORDER 9...");
	      stmt = conn.createStatement();
	      String sql4 = "DELETE FROM businessorder " + "WHERE orderID = 9";
	      stmt.executeUpdate(sql4);
	      
	      // Create records(CREATE)
	      System.out.println("Creating order 9...");
	      String sql2 = "INSERT INTO BusinessOrder " + "VALUES (9, 9, 9, null, null, null, null, null, null, null)";
	      stmt.executeUpdate(sql2);
     
	      
	      // Create records(UPDATE)
	      System.out.println("Creating statement UPDATE TO PROCESSING order 1...");
	      stmt = conn.createStatement();
	      String sql3 = "UPDATE businessorder " + "SET currentState = 'processing' WHERE orderID = 1";
	      stmt.executeUpdate(sql3);	      

	      
	      // Extract records(READ)
	      System.out.println("Fetching order...");
	      String sql = "SELECT * FROM businessorder";
	      ResultSet rs = stmt.executeQuery(sql);

	      System.out.println(rs.getFetchSize());
	      while(rs.next()){
	         //Retrieve by column name
	         int orderID  = rs.getInt("orderID");
	         int employeeID = rs.getInt("employeeID");
	         int supplierID = rs.getInt("supplierID");
	         String dateRequested = rs.getString("dateRequested");
	         String dateApproved = rs.getString("dateApproved");
	         String dateArrived = rs.getString("dateArrived");
	         int totalPrice = rs.getInt("totalPrice");
	         int totalWeight = rs.getInt("totalWeight");
	         int isApproved = rs.getInt("isApproved");
	         String currentState = rs.getString("currentState");
	         

	         //Display values
	         System.out.print(" ID: " + orderID);
	         System.out.print(", Age: " + employeeID);
	         System.out.print(", First: " + supplierID);
	         System.out.print(", DateRequested: " + dateRequested);
	         System.out.print(", DateApproved: " + dateApproved);
	         System.out.print(", DateArrived: " + dateArrived);
	         System.out.print(", TotalPrice: " + totalPrice);
	         System.out.print(", TotalWeight: " + totalWeight);
	         System.out.print(", IsApproved: " + isApproved);
	         System.out.println(", CurrentState: " + currentState);
	      }

	      
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            conn.close();
	      }catch(SQLException se){
	      }// do nothing
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	   System.out.println("Goodbye!");
	}//end main
	}//end JDBCExample
*/