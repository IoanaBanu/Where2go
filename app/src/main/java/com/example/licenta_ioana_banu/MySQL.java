package com.example.licenta_ioana_banu;

//import models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MySQL{
    private String host = "192.168.1.1";
    private String database = "";
    private int port = 3306;
    private String userName = "root";
    private String password = "";

    public MySQL() {}

    public MySQL(String host, String database, int port, String userName, String password) {
        this.host = host;
        this.database = database;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection connection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=latin1&useConfigs=maxPerformance", userName,password); //here sonoo is database name, root is username and password
            return con;//?characterEncoding=latin1&useConfigs=maxPerformance
        }catch(Exception e){ System.out.println(e);}
        return null;
    }

    public int insertQuery(String table, String columns, String[] values) {
        Connection con = connection();
        String queryTable = "INSERT INTO " + table + "(" + columns + ") ";

        if (values != null) {
            queryTable += "VALUES (";
            for (int i = 0; i < values.length; i++) {
                queryTable += "?";
                if (values.length != 1 && i < values.length - 1)
                    queryTable += ",";
                queryTable += " ";
            }
            queryTable += ")";
        }
        System.out.println("aia e");
        System.out.println(queryTable);
        try {
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(queryTable);
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            for (int i = 0; i < values.length; i++) {
                if (pattern.matcher(values[i]).matches()) {
                    preparedStmt.setInt(i + 1, Integer.parseInt(values[i]));
                } else {
                    preparedStmt.setString(i + 1, values[i]);
                }
            }
            preparedStmt.execute();
            con.close();
            return 1;
        } catch (SQLException throwables) {
            //1062: Duplicate entry
            if (throwables.getErrorCode() == 1062) {
                return -1;
            }
            throwables.printStackTrace();
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateQuery(String table, String columnMod[][], String whereClause[][]) {
        Connection con = connection();
        String queryTable = "UPDATE " + table + " SET ";
        if (columnMod != null) {
            for (int i = 0; i < columnMod.length; i++) {
                queryTable += columnMod[i][0] + " = ?";
                if (i < columnMod.length - 1) {
                    queryTable += ", ";
                }
            }
            queryTable += " ";
        }
        if (whereClause != null) {
            queryTable += " WHERE ";
            for (int i = 0; i < whereClause.length; i++) {
                queryTable += whereClause[i][0] + " = ? " + whereClause[i][2] + " ";
            }
        }
        if (columnMod != null && whereClause != null) {

            try {
                PreparedStatement preparedStmt = con.prepareStatement(queryTable);
                Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
                int contor = 1;
                for (int i = 0; i < columnMod.length; i++) {
                    if (pattern.matcher(columnMod[i][1]).matches()) {
                        preparedStmt.setInt(contor, Integer.parseInt(String.valueOf(columnMod[i][1])));
                    } else {
                        preparedStmt.setString(contor, columnMod[i][1]);
                    }
                    contor++;
                }

                for (int i = 0; i < whereClause.length; i++) {
                    if (pattern.matcher(whereClause[i][1]).matches()) {
                        preparedStmt.setInt(contor, Integer.parseInt(String.valueOf(whereClause[i][1])));
                    } else {
                        preparedStmt.setString(contor, whereClause[i][1]);
                    }
                    contor++;
                }
                int result = preparedStmt.executeUpdate();
                con.close();
                return result;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch(NullPointerException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }
/*
    public List getQuery(String table, String columns, String whereClause[][], String groupClause[], String orderClause[], String order) {
        Connection con = connection();
        String queryTable = "SELECT " + columns + " FROM " + table + " ";
        if (whereClause != null) {
            queryTable += "WHERE ";
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            for (int i = 0; i < whereClause.length; i++) {
                for (int j = 0; j < whereClause[i].length; j++) {
                    if (j == 1) {
                        if (pattern.matcher(whereClause[i][j]).matches()) {
                            queryTable += whereClause[i][j] + " ";
                        } else {
                            queryTable += "'" + whereClause[i][j] + "'" + " ";
                        }
                    } else {
                        queryTable += whereClause[i][j] + " ";
                    }
                    if (j == 0) {
                        queryTable += "= ";
                    }
                }
            }
        }
        if (groupClause != null) {
            queryTable += "GROUP BY ";
            for (int i = 0; i < groupClause.length; i++) {
                queryTable += groupClause[i];
                if (groupClause.length != 1 && i < groupClause.length - 1) {
                    queryTable += ",";
                }
                queryTable += " ";
            }
        }
        if (orderClause != null) {
            queryTable += "ORDER BY ";
            for (int i = 0; i < orderClause.length; i++) {
                queryTable += orderClause[i];
                if (orderClause.length != 1 && i < orderClause.length - 1) {
                    queryTable += ",";
                }
                queryTable += " ";
            }
            queryTable += order;
        }
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(queryTable);
            List<Object> listaProduse = new ArrayList();
            if (table.equalsIgnoreCase("stoc")) {
                while(rs.next()) {
                    listaProduse.add(new Produs(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
                }
            } else if (table.equalsIgnoreCase("user_type")) {
                while(rs.next()) {
                    listaProduse.add(new UserType(rs.getInt(1), rs.getString(2)));
                }
            } else if (table.equalsIgnoreCase("user")) {
                while(rs.next()) {
                    listaProduse.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getDate(6), rs.getInt(7)));
                }
            } else if (table.equalsIgnoreCase("preparate")) {
                while(rs.next()) {
                    listaProduse.add(new Preparat(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
                }
            } else if (table.equalsIgnoreCase("preparat_stoc_mapping_vw")) {
                while(rs.next()) {
                    listaProduse.add(new PreparatMapp(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(6)));
                }
            } else if (table.equalsIgnoreCase("comenzi")) {
                while(rs.next()) {
                    listaProduse.add(new Comenzi(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getDate(9)));
                }
            } else if (table.equalsIgnoreCase("comenzi_temporare")) {
                while(rs.next()) {
                    listaProduse.add(new Comenzi(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8) / rs.getInt(4), rs.getInt(8), rs.getDate(9), rs.getInt(10)));
                }
            } else if (table.equalsIgnoreCase("mese")) {
                while(rs.next()) {
                    listaProduse.add(new Masa(rs.getInt(1), rs.getString((2))));
                }
            } else if (table.equalsIgnoreCase("progres_preparate")) {
                while(rs.next()) {
                    listaProduse.add(new ProgresPreparat(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDate(4)));
                }
            } else if (table.equalsIgnoreCase("costuri")) {
                while(rs.next()) {
                    listaProduse.add(new Costuri(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getDate(5)));
                }
            } else if (table.equalsIgnoreCase("tipuri_costuri")) {
                while(rs.next()) {
                    listaProduse.add(new CostType(rs.getInt(1), rs.getString(2), rs.getString(3)));
                }
            } else if (table.equalsIgnoreCase("fiscal_bon")) {
                while(rs.next()) {
                    listaProduse.add(new FiscalBon(rs.getInt(1), rs.getString(2), rs.getDate(3)));
                }
            } else {
                return null;
            }

            con.close();
            return listaProduse;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
*/

  /*
    public List betweenQuery(String table, String columns, String whereClause[][], String groupClause[], String orderClause[], String order) {
        Connection con = connection();
        String queryTable = "SELECT " + columns + " FROM " + table + " ";
        if (whereClause != null) {
            queryTable += "WHERE ";
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            for (int i = 0; i < whereClause.length; i++) {
                for (int j = 0; j < whereClause[i].length; j++) {
                    if (j == 1 || j == 3) {
//                        if (pattern.matcher(whereClause[i][j]).matches()) {
//                            queryTable += whereClause[i][j] + " ";
//                        } else {
                        queryTable += "'" + whereClause[i][j] + "'" + " ";
                        // }
                    } else {
                        queryTable += whereClause[i][j] + " ";
                    }
                    if (j == 0) {
                        queryTable += "BETWEEN ";
                    }
                }
            }
        }
        if (groupClause != null) {
            queryTable += "GROUP BY ";
            for (int i = 0; i < groupClause.length; i++) {
                queryTable += groupClause[i];
                if (groupClause.length != 1 && i < groupClause.length - 1) {
                    queryTable += ",";
                }
                queryTable += " ";
            }
        }
        if (orderClause != null) {
            queryTable += "ORDER BY ";
            for (int i = 0; i < orderClause.length; i++) {
                queryTable += orderClause[i];
                if (orderClause.length != 1 && i < orderClause.length - 1) {
                    queryTable += ",";
                }
                queryTable += " ";
            }
            queryTable += order;
        }
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(queryTable);
            List<Object> listaProduse = new ArrayList();
            if (table.equalsIgnoreCase("stoc")) {
                while(rs.next()) {
                    listaProduse.add(new Produs(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
                }
            } else if (table.equalsIgnoreCase("user_type")) {
                while(rs.next()) {
                    listaProduse.add(new UserType(rs.getInt(1), rs.getString(2)));
                }
            } else if (table.equalsIgnoreCase("user")) {
                while(rs.next()) {
                    listaProduse.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getDate(6)));
                }
            } else if (table.equalsIgnoreCase("preparate")) {
                while(rs.next()) {
                    listaProduse.add(new Preparat(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
                }
            } else if (table.equalsIgnoreCase("preparat_stoc_mapping_vw")) {
                while(rs.next()) {
                    listaProduse.add(new PreparatMapp(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(6)));
                }
            } else if (table.equalsIgnoreCase("comenzi")) {
                while(rs.next()) {
                    listaProduse.add(new Comenzi(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getDate(9)));
                }
            } else if (table.equalsIgnoreCase("comenzi_temporare")) {
                while(rs.next()) {
                    listaProduse.add(new Comenzi(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8) / rs.getInt(4), rs.getInt(8), rs.getDate(9), rs.getInt(10)));
                }
            } else if (table.equalsIgnoreCase("mese")) {
                while(rs.next()) {
                    listaProduse.add(new Masa(rs.getInt(1), rs.getString((2))));
                }
            } else if (table.equalsIgnoreCase("progres_preparate")) {
                while(rs.next()) {
                    listaProduse.add(new ProgresPreparat(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDate(4)));
                }
            } else if (table.equalsIgnoreCase("fiscal_bon")) {
                while(rs.next()) {
                    listaProduse.add(new FiscalBon(rs.getInt(1), rs.getString(2), rs.getDate(3)));
                }
            } else {
                return null;
            }

            con.close();
            return listaProduse;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
*/
    public int deleteQuery(String table, String whereClause[][]) {
        Connection con = connection();
        String queryTable = "DELETE FROM " + table;

        if (whereClause != null) {
            queryTable += " WHERE ";
            for (int i = 0; i < whereClause.length; i++) {
                queryTable += whereClause[i][0] + " = ? " + whereClause[i][2] + " ";
            }
        }

        try {
            PreparedStatement preparedStmt = con.prepareStatement(queryTable);
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            int contor = 1;

            for (int i = 0; i < whereClause.length; i++) {
                if (pattern.matcher(whereClause[i][1]).matches()) {
                    preparedStmt.setInt(contor, Integer.parseInt(String.valueOf(whereClause[i][1])));
                } else {
                    preparedStmt.setString(contor, whereClause[i][1]);
                }
                contor++;
            }
            int result = preparedStmt.executeUpdate();
            con.close();
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void close(Connection con) {
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}