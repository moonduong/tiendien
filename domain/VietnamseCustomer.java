package domain;
import java.util.Date;
public class VietnamseCustomer extends ElectricBill{
    public String DoituongKH;
    public double dinhmuc;

    public VietnamseCustomer(String mKH, String hoten, Date ngayraHD, double soluongKW, double dongia, String DoituongKH, double dinhmuc ){
        super(mKH, hoten, ngayraHD, soluongKW, dongia);
        this.DoituongKH=DoituongKH;
        this.dinhmuc=dinhmuc;
    }
    @Override
    public double calculateTotal() {
        double total;
        if (soluongKW <= dinhmuc) {
            total = soluongKW *dongia;
        } else {
            total = (dinhmuc * dongia) + ((soluongKW - dinhmuc) * dongia * 2.5);
        }
        return total;
    }
    public double getDinhmuc() {
        return dinhmuc;
    }
    public String getDoituongKH() {
        return DoituongKH;
    }
}

