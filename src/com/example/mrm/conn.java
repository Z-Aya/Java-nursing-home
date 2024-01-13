package com.example.mrm;

import java.sql.*;

public class conn {
    public static Connection connect()
    {
        Connection conn = null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/mrm", "root", "");
            System.out.println("Connexion réussie -- conn !");
            //conn.setAutoCommit(true);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return conn;
    }
    public static void disconnect(Connection conn)
    {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connexion fermée. -- conn");
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }
}
