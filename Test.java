import javax.swing.SwingUtilities;

import presentation.ElectricBillApp;
public class Test {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()  ->{
            ElectricBillApp app = new ElectricBillApp();
        app.setVisible(true);
    });
}
}
