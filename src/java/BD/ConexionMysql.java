/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.bd;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Daniel
 */
public class ConexionMysql {
    // Objeto de la conexión con la base package org.utl.dsm.bd;
    Connection conn;
    
    public Connection open(){
        String user = "root";
        String password ="4435";
        String url = "jdbc:mysql://127.0.0.1:3306/zarape";
        String parametros = "?useSSL=false&useUnicode=true&characterEncoding=utf-8";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url+parametros, user, password);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public void close(){
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
    }
}
