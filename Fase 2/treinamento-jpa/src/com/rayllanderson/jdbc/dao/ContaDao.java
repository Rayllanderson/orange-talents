package com.rayllanderson.jdbc.dao;

import com.rayllanderson.jdbc.entities.Conta;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContaDao {

    private Connection conn;

    public ContaDao() {
        try {
            String url = "jdbc:postgresql://localhost:5432/treinamento-jpa";
            String user = "postgres";
            String password = "root";
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("The database has been successfully connected");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void adiciona(Conta conta) {
        try {
            String sql = "insert into conta(agencia, numero, titular) values (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, conta.getAgencia());
            ps.setLong(2, conta.getNumero());
            ps.setString(3, conta.getTitular());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
