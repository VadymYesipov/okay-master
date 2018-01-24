package ua.khpi.yesipov.project.persistence.dao;

import ua.khpi.yesipov.project.persistence.domain.Order;

import java.util.List;

public interface OrderDAO {

    public int insertOrder(Order order);

    public boolean deleteOrder(Order order);

    public Order findOrder(int id);

    public boolean updateOrder(Order order);

    public List<Order> selectFutureOrders();

    public List<Order> selectPastOrders();

    public List<Order> selectOrders(int id);

    public int selectCount();
}
