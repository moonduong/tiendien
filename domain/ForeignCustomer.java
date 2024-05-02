package domain;

import java.util.Date;

public class ForeignCustomer extends ElectricBill{
    private String quoctich;

    public ForeignCustomer (String mKH, String hoten, Date ngayraHD, double soluongKW, double dongia, String quoctich){
        super(mKH, hoten, ngayraHD, soluongKW, dongia);
        this.quoctich=quoctich;
    }
    @Override
    public double calculateTotal() {
        return soluongKW * dongia;
    }
    public String getQuocTich() {
        return quoctich;
    }
    @Override
    public double getDinhmuc() {
       throw new UnsupportedOperationException("Khách hàng nước ngoài không có định mức tiêu dùng");
    }
}

