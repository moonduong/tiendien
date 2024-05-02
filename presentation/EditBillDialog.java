package presentation;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.ElectricBill;

public class EditBillDialog extends JDialog{
    private JTextField mKHField;
    private JTextField hotenField;
    private JTextField ngayrahdField;
    private JTextField soluongKWField;
    private JTextField dongiaField;
    private JTextField dinhmucField;
    private JComboBox<String> doituongKHComboBox;
    private JTextField quoctichField;
    private JTextField monthField;

    private ElectricBill billToUpdate;

    public EditBillDialog(ElectricBill bill){
        setTitle("Sửa Hóa đơn");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setModal(true);

        billToUpdate = bill;

        mKHField = new JTextField(bill.getMkh(), 15);
        hotenField = new JTextField(bill.getHoTen(), 15);
        ngayrahdField=new JTextField(bill.getNgayRahd().toString(),15);
        soluongKWField=new JTextField(Double.toString(bill.getSoLuongkw()),15);
        dongiaField=new JTextField(Double.toString(bill.getDonGia()),15);
        dinhmucField=new JTextField(Double.toString(bill.getDinhmuc()),15);
        //quoctichField=new JTextField(15);
        //doituongKHComboBox=new JComboBox<>(new String[]{"Sinh hoạt", "Kinh doanh", "Sản xuất"});
        //monthField=new JTextField(15);

        JButton saveButton = new JButton("Lưu");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                billToUpdate.setMkh(mKHField.getText());
                billToUpdate.setHoTen(hotenField.getText());
                billToUpdate.setNgayRahd(parseDate(ngayrahdField.getText()));
                billToUpdate.setSoLuongkw(Double.parseDouble(soluongKWField.getText()));
                billToUpdate.setDonGia(Double.parseDouble(dongiaField.getText()));
                billToUpdate.setDinhmuc(Double.parseDouble(dinhmucField.getText()));
                
                dispose();
            }
        });
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

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);

        JPanel mainPanel = new JPanel();
        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);

        setContentPane(mainPanel);
    }
    private Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
}

