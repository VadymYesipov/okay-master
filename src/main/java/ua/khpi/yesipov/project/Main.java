package ua.khpi.yesipov.project;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        BusinessLogic businessLogic = new BusinessLogic();
        Scanner scanner = new Scanner(System.in);
        businessLogic.makeOrder(scanner);
        businessLogic.showOrders();
    }
}
