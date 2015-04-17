package test;

import java.sql.*;
import java.io.*;

public class UploadXML {
    
    public static String unityid = "djain2";
    public static String password = "200063439";
    public static String tablename = "TestData";
    
    //Level that is used as root level.
    //For nama.xml, use level = 1;
    //For XMark outputs, use level = 2;
    public static int level = 2;
    public static String filename = "output.xml";
    
    public static void main(String[] args) {
        
        ResultSet rs = null;
        Statement stmt = null;
        Connection conn = null;
        PreparedStatement ps;
        Clob clob;
        
        String sql = "";
        
        BufferedReader reader = null;
        int id = 0;
        String xml = "";
        
        int linecount = 0;
        try{
            File file = new File(filename);
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
            lineNumberReader.skip(Long.MAX_VALUE);
            linecount = lineNumberReader.getLineNumber();
            lineNumberReader.close();
        }catch(Exception e){
            e.printStackTrace();
            return;
        }
        if (linecount<=0)
            return;
        System.out.println("# lines in file: "+linecount);
        int step = linecount / 100;
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl", unityid, password);
            
            sql = "drop table " + tablename;
            stmt = conn.createStatement();
            try{
                stmt.execute(sql);
                System.out.println("Table '" +tablename+ "' is dropped.");
            }catch(Exception e){
                System.out.println("Table '"+tablename+"' didn't exist in database.");
            }
            sql = "create table "+tablename+" (XMLID number, XMLData XMLTYPE, primary key (XMLID))";
            stmt.executeQuery(sql);
            
            System.out.println("Table '"+tablename+"' is created new.");

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename))); 
            xml = "";
            
            int lcount = 0;
            String line = null;
            String tag = "";
            String[] tags = new String[level];
            
            int curlevel = 0;
            
            while((line=reader.readLine())!=null){
                
                lcount ++;
                if (lcount % step == 0){
                    int x = (int)(100.0 * (double)lcount / (double)linecount);
                    System.out.println(""+x+"% of the file has been read.");
                }
                
                line = line.trim();
                
                if (tag.trim().equals("")){
                    if (line.contains("<")&&line.contains(">"))
                        tag = line.substring(line.indexOf("<")+1, line.indexOf(">")).trim();
                    if (tag.startsWith("?")||tag.startsWith("!"))
                        tag = "";
                    if (tag.endsWith("/"))
                        tag = "";

                    if (tag.contains(" "))
                        tag = tag.split(" ")[0];
                    
                    if (curlevel>= 1&&tag.equals("/"+tags[curlevel-1])){
                        curlevel--;
                        tag = "";
                    }
                    
                    if (!tag.equals("")){
                        if (curlevel < level){
                            tags[curlevel] = tag;
                            tag = "";
                            curlevel ++;
                        }
                        else
                            xml = line+"\r\n";
                    }
                }
                else{
                    xml += line +"\r\n";
                    
                    if (line.contains("</"+tag+">")){
                        tag = "";
                        xml = xml.replaceAll("&#.+;", "");
                        
                        sql = "insert into "+tablename+" values (?, XMLType(?))";
                        ps = conn.prepareStatement(sql);
                        ps.setInt(1, id);
                        clob = conn.createClob();
                    
                        xml = xml.replaceAll("&#.+;", "");
                    
                        clob.setString(1, xml);
                        ps.setClob(2, clob);
                        ps.execute();
                        clob.free();
                        ps.close();
                        id ++;
                        if (id % 20 == 0){
                            System.out.println(""+id+" XML nodes have been inserted.");
                            //break;
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            //System.out.println(xml);
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                if(rs != null) {
                    rs.close();
                    rs = null;
                }
                if(stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                if(conn != null) {
                    conn.close();
                    conn = null;
                }
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Total number of XML nodes inserted: "+id);
    }
}
