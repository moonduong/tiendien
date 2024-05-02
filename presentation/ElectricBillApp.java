package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import domain.ElectricBill;
import domain.ForeignCustomer;
import domain.VietnamseCustomer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class ElectricBillApp extends JFrame implements Observer {
    private ElectricBillController controller;
    private DefaultTableModel tableModel;
    private JTable billTable;
    private JFrame frame;
    private CommandProcessor commandProcessor;

    private JTextField mKHField;
    private JTextField hotenField;
    private JTextField ngayrahdField;
    private JTextField soluongKWField;
    private JTextField dongiaField;
    private JTextField dinhmucField;
    private JTextField quoctichField;
    private JComboBox<String> doituongKHComboBox;
    private JTextField monthField;

    private JTextArea outputArea;

    public ElectricBillApp(){

        controller = new ElectricBillController();
        controller.addObserver(this);
        commandProcessor=new CommandProcessor();
        frame= new JFrame("Ứng dụng hóa đơn điện");

        setTitle("Electric Bill App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setVisible(true);

        mKHField=new JTextField(15);
        hotenField=new JTextField(15);
        ngayrahdField=new JTextField(15);
        soluongKWField=new JTextField(15);
        dongiaField=new JTextField(15);
        dinhmucField=new JTextField(15);
        quoctichField=new JTextField(15);
        doituongKHComboBox=new JComboBox<>(new String[]{"Sinh hoạt", "Kinh doanh", "Sản xuất"});
        monthField=new JTextField(15);
        outputArea=new JTextArea(5, 10);
        JScrollPane scrollPane= new JScrollPane(outputArea);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Mã KH");
        tableModel.addColumn("Họ tên");
        tableModel.addColumn("Ngày ra hoá đơn");
        tableModel.addColumn("Số lượng");
        tableModel.addColumn("Đơn giá");
        tableModel.addColumn("Định mức");
        tableModel.addColumn("Đối tượng KH");
        tableModel.addColumn("Quốc tịch");
        //tableModel.addColumn("Tháng");
        billTable=new JTable(tableModel);

        JButton addButton=new JButton("Thêm hóa đơn");

        frame.setLayout(new FlowLayout());

        frame.add(addButton);

        JButton addVietnameseCustomerButton = new JButton("Thêm hóa đơn KHVN");
        addVietnameseCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mKH = mKHField.getText();
                String hoten = hotenField.getText();
                Date ngayraHD = parseDate(ngayrahdField.getText());
                double soluongKW = Double.parseDouble(soluongKWField.getText());
                double dongia = Double.parseDouble(dongiaField.getText());
                double dinhmuc = Double.parseDouble(dinhmucField.getText());
                String DoituongKH = (String) doituongKHComboBox.getSelectedItem();

                VietnamseCustomer customer=new VietnamseCustomer(mKH, hoten, ngayraHD, soluongKW, dongia, DoituongKH, dinhmuc);

                controller.addBill(customer);
                outputArea.append("Đã thêm hóa đơn khách hàng Việt Nam: " + mKH + "\n");
                addRowToTable(customer);
            }
        });
        JButton addForeignCustomerButton = new JButton("Thêm hóa đơn KHNN");
        addForeignCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mKH = mKHField.getText();
                String hoten = hotenField.getText();
                Date ngayraHD =parseDate(ngayrahdField.getText()) ;
                double soluongKW = Double.parseDouble(soluongKWField.getText());
                double dongia = Double.parseDouble(dongiaField.getText());
                String quoctich = quoctichField.getText();

                ForeignCustomer customer = new ForeignCustomer(mKH, hoten, ngayraHD, soluongKW, dongia, quoctich );

                controller.addBill(customer);
                outputArea.append("Đã thêm hóa đơn khách hàng nước ngoài: " + mKH+ "\n");
                addRowToTable(customer);
            }
        });
        JButton deleteBillButton = new JButton("Xóa");
        deleteBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = billTable.getSelectedRow();
                if (selectedRow != -1) {
                    String mKH = (String) billTable.getValueAt(selectedRow, 0);
                    controller.deleteBill(mKH);
                    tableModel.removeRow(selectedRow);
                    outputArea.append("Đã xóa hóa đơn khách hàng có mã: " + mKH + "\n");
                } else {
                    JOptionPane.showMessageDialog(ElectricBillApp.this, "Vui lòng chọn một hóa đơn để xóa");
                }
            }
        });
        JButton editBillButton = new JButton("Sửa");
        editBillButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        int selectedRow = billTable.getSelectedRow();
        if (selectedRow != -1) {
            String mKH = (String) billTable.getValueAt(selectedRow, 0);
            ElectricBill billToEdit = controller.searchBill(mKH);
            if (billToEdit != null) {
                EditBillDialog editDialog = new EditBillDialog(billToEdit);
                editDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(ElectricBillApp.this, "Không tìm thấy hóa đơn cần sửa");
            }
        } else {
            JOptionPane.showMessageDialog(ElectricBillApp.this, "Vui lòng chọn một hóa đơn để sửa");
        }
    }
});
        JButton calculateTotalButton = new JButton("Tính tổng");
        calculateTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<ElectricBill> electricBills = controller.getElectricBills();
                Map<String, Double> totalKWMap = new HashMap<>();
                for (ElectricBill bill : electricBills) {
                    String mKH = bill.getMkh();
                    double soluongKW = bill.getSoLuongkw();
        
                    if (totalKWMap.containsKey(mKH)) {
                        double totalKW = totalKWMap.get(mKH) + soluongKW;
                        totalKWMap.put(mKH, totalKW);
                    } else {
                        totalKWMap.put(mKH, soluongKW);
                    }
                }
                StringBuilder result = new StringBuilder("Tổng số lượng kW cho khách hàng:\n");
            for (Map.Entry<String, Double> entry : totalKWMap.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append(" kW\n");
        }
        JOptionPane.showMessageDialog(null, result.toString());
    }
});
        JButton calculateAverageButton = new JButton("Tính trung bình");
        frame.add(calculateAverageButton);
        calculateAverageButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        double average = controller.calculateAverageForeignCustomerTotal();
        JOptionPane.showMessageDialog(frame, "Trung bình thành tiền cho khách hàng nước ngoài: " + average);
    }
});

        JButton exportButton = new JButton("Xuất hóa đơn");
        frame.add(exportButton);
        exportButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            boolean exportSuccess = controller.exportBillsToFile("C:\\Users\\Admin\\OneDrive\\Documents.txt");
            if (exportSuccess) {
                JOptionPane.showMessageDialog(frame, "Hóa đơn đã được xuất thành công");
            } else {
                JOptionPane.showMessageDialog(frame, "Có lỗi khi xuất hóa đơn!");
            }
        }
    });

        JButton searchButton=new JButton("Tìm kiếm");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mKHToSearch = mKHField.getText(); // Lấy mã KH từ trường văn bản
                ElectricBill foundBill = controller.searchBill(mKHToSearch);
                if (foundBill != null) {
                    JOptionPane.showMessageDialog(frame, "Đã tìm thấy hóa đơn:\n" + foundBill.toString());
                } else {
                    JOptionPane.showMessageDialog(frame, "Không tìm thấy hóa đơn với mã KH: " + mKHToSearch);
                }
            }
    });
        frame.pack();
        frame.setVisible(true);

        JPanel inputPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        inputPanel.add(new JLabel("Mã khách hàng:"));
        inputPanel.add(mKHField);
        inputPanel.add(new JLabel("Họ tên:"));
        inputPanel.add(hotenField);
        inputPanel.add(new JLabel("Ngày ra hoá đơn:"));
        inputPanel.add(ngayrahdField);
        inputPanel.add(new JLabel("Số lượng:"));
        inputPanel.add(soluongKWField);
        inputPanel.add(new JLabel("Đơn giá:"));
        inputPanel.add(dongiaField);
        inputPanel.add(new JLabel("Định mức:"));
        inputPanel.add(dinhmucField);
        inputPanel.add(new JLabel("Đối tượng khách hàng:"));
        inputPanel.add(doituongKHComboBox);
        inputPanel.add(new JLabel("Quốc tịch:"));
        inputPanel.add(quoctichField);
       // inputPanel.add(new JLabel("Tháng:"));
        //inputPanel.add(monthField);

        JPanel buttonPanel=new JPanel();
        buttonPanel.add(addVietnameseCustomerButton);
        buttonPanel.add(addForeignCustomerButton);
        buttonPanel.add(deleteBillButton);
        buttonPanel.add(editBillButton);
        buttonPanel.add(calculateTotalButton);
        buttonPanel.add(searchButton);

        JPanel mainPanel= new JPanel();
        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(new JScrollPane(billTable));

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(mainPanel, BorderLayout.CENTER);
        contentPane.add(scrollPane, BorderLayout.SOUTH);

}
    private void addRowToTable(VietnamseCustomer customer) {
        Object[] row = {
            customer.getMkh(),
            customer.getHoTen(),
            customer.getNgayRahd(),
            customer.getSoLuongkw(),
            customer.getDonGia(),
            customer.getDinhmuc(),
            customer.getDoituongKH(),
        };
        tableModel.addRow(row);
    }
    private void addRowToTable(ForeignCustomer customer) {
        Object[] row = {
            customer.getMkh(),
            customer.getHoTen(),
            customer.getNgayRahd(),
            customer.getSoLuongkw(),
            customer.getDonGia(),
            "",
            "",
            customer.getQuocTich(),
        };
        tableModel.addRow(row);
    }

private void updateRowInTable(int rowIndex, ElectricBill updatedBill) {
    if (updatedBill instanceof VietnamseCustomer) {
        VietnamseCustomer customer = (VietnamseCustomer) updatedBill;
        Object[] row = {
            customer.getMkh(),
            customer.getHoTen(),
            customer.getNgayRahd(),
            customer.getSoLuongkw(),
            customer.getDonGia(),
            customer.getDinhmuc(),
            customer.getDoituongKH(),
        };
        tableModel.removeRow(rowIndex);
        tableModel.insertRow(rowIndex, row);
    } else if (updatedBill instanceof ForeignCustomer) {
        ForeignCustomer customer = (ForeignCustomer) updatedBill;
        Object[] row = {
            customer.getMkh(),
            customer.getHoTen(),
            customer.getNgayRahd(),
            customer.getSoLuongkw(),
            customer.getDonGia(),
            "",
            "",
            customer.getQuocTich()
        };
        tableModel.removeRow(rowIndex);
        tableModel.insertRow(rowIndex, row);
    }
}
    private Date parseDate (String dateString) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    try {
        return dateFormat.parse(dateString);
    } catch (ParseException e) {
        e.printStackTrace();
        return null; 
    }
}
    
    private Object[] createRowFromBill(ElectricBill bill) {
        if (bill instanceof VietnamseCustomer) {
            VietnamseCustomer customer = (VietnamseCustomer) bill;
            return new Object[]{
                customer.getMkh(),
                customer.getHoTen(),
                customer.getNgayRahd(),
                customer.getSoLuongkw(),
                customer.getDonGia(),
                customer.getDinhmuc(),
                customer.getDoituongKH(),
                "",
            };
        } else if (bill instanceof ForeignCustomer) {
            ForeignCustomer customer = (ForeignCustomer) bill;
            return new Object[]{
                customer.getMkh(),
                customer.getHoTen(),
                customer.getNgayRahd(),
                customer.getSoLuongkw(),
                customer.getDonGia(),
                "",
                "",
                customer.getQuocTich(),
            };
        }
        return new Object[]{};
    }
    
    private int findRowIndexByMkh(String mKH) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String rowMkh = (String) tableModel.getValueAt(i, 0);
            if (rowMkh.equals(mKH)) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public void update(ElectricBill bill) {
        System.out.println("Received update for khach hang: " + bill.getMkh() + ", soluongkW: " + bill.getSoLuongkw());
    }

    /**
     * 
     */
    private void addVietnameseCustomer() {
        String mKH = mKHField.getText();
        String hoten = hotenField.getText();
        Date ngayraHD = parseDate(ngayrahdField.getText());
        double soluongKW = Double.parseDouble(soluongKWField.getText());
        double dongia = Double.parseDouble(dongiaField.getText());
        double dinhmuc = Double.parseDouble(dinhmucField.getText());
        String DoituongKH = (String) doituongKHComboBox.getSelectedItem();
    
        VietnamseCustomer customer = new VietnamseCustomer(mKH, hoten, ngayraHD, soluongKW, dongia, DoituongKH, dinhmuc);

        Command addCommand = new AddBillCommand(controller, customer);
        commandProcessor.executeCommand(addCommand);

        //controller.update(newBill);
        addRowToTable(customer);

    }
     private void undoLastAction() {
        commandProcessor.undoLastCommand();

    }
   
}



    

