package presentation;

import domain.ElectricBill;

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(ElectricBill bill);
}
