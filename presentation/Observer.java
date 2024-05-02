package presentation;

import domain.ElectricBill;

public interface Observer {
    void update(ElectricBill bill);
}
