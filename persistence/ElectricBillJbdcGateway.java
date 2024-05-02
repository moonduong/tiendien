package persistence;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import domain.ElectricBill;

public class ElectricBillJbdcGateway implements ElectricBillGateway{
    private Connection connection;

    public ElectricBillJbdcGateway (){
        
        String dbUrl="jdbc:sqlserver://THUYDUONG:1433;databaseName=QLHDD";
        String username="sa";
        String password="123456789";
        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void saveElectricBill(ElectricBill electricBill) {
        String insertQuery = "INSERT INTO ElectricBill (mKH, hoTen, ngayRahd, soluongKW, dongia, dinhmuc, doituongKH, quoctich) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, electricBill.getMkh());
            preparedStatement.setString(2, electricBill.getHoTen());
            preparedStatement.setDate(3, new java.sql.Date(electricBill.getNgayRahd().getTime()));
            preparedStatement.setDouble(4, electricBill.getSoLuongkw());
            preparedStatement.setDouble(5, electricBill.getDonGia());
            preparedStatement.setDouble(6, electricBill.getDinhmuc());
            //preparedStatement.setString(7, electricBill.g);
            //preparedStatement.setString(8, electricBill.getQuocTich());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ElectricBill getElectricBillByMkh(String mKH) {
        String selectQuery = "SELECT * FROM ElectricBill WHERE mKH = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, mKH);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
             
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ElectricBill> getAllElectricBills() {
        List<ElectricBill> electricBills = new ArrayList<>();
        String selectQuery = "SELECT * FROM ElectricBill";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            while (resultSet.next()) {
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return electricBills;
    }
}