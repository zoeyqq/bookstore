package dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import dao.OrderDao;
import domain.Book;
import domain.Order;
import domain.OrderItem;
import domain.User;
import utils.JdbcUtils;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void add(Order order) {
		// TODO Auto-generated method stub
		try {
			//1.��order�Ļ�����Ϣ���浽order��
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "insert into orders(id,ordertime,price,state,user_id) values(?,?,?,?,?)";
			Object params[] = {order.getId(),order.getOrdertime(),order.getPrice(),order.isState(),order.getUser().getId()};
			runner.update(sql,params);
			//2.��order�Ķ��������orderitem��
			Set<OrderItem> set = order.getOrderitems();
			for(OrderItem item:set){
				sql = "insert into orderitem(id,quantity,price,order_id,book_id) values(?,?,?,?,?)";
				params = new Object[]{item.getId(),item.getQuantity(),item.getPrice(),order.getId(),item.getBook().getId()};
				runner.update(sql,params);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	public void delete(Order order) {
		try {
			//1.�Ѷ�����Ϣ��orderitem��ɾ��
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "delete from orderitem where order_id=?";
			runner.update(sql,order.getId());			
			//2.�Ѷ�����Ϣ��orders��ɾ��			
			sql="delete from orders where id=?";
			runner.update(sql, order.getId());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public Order find(String id) {
		// 1.�ҳ������Ļ�����Ϣ
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from orders where id=?";
			Order order = (Order)runner.query(sql, id,new BeanHandler(Order.class));
			//2.�ҳ����������ж�����
			sql = "select * from orderitem where order_id=?";
			List<OrderItem> list = runner.query(sql, id, new BeanListHandler(OrderItem.class));
			for(OrderItem item : list){
				sql = "select book.* from orderitem,book where orderitem.id=? and orderitem.book_id=book.id";
				Book book = (Book)runner.query(sql, item.getId(), new BeanHandler(Book.class));
				item.setBook(book);
			}
			//���ҳ��Ķ�����Ž�order
			order.getOrderitems().addAll(list);
			//3.�ҳ����������ĸ��û�
			sql = "select user.* from orders,user where orders.id=? and orders.user_id=user.id";
			User user = (User)runner.query(sql, id, new BeanHandler(User.class));
			order.setUser(user);
			return order;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	//��̨��ȡ���ж���
	@Override
	public List<Order> getAll(boolean state) {
		// TODO Auto-generated method stub
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from orders where state=?";
			List<Order> list = (List<Order>)runner.query(sql, state, new BeanListHandler(Order.class));
			for(Order order : list){
				sql = "select user.* from user,orders where orders.id=? and orders.user_id=user.id";
				User user = (User)runner.query(sql, order.getId(), new BeanHandler(User.class));
				order.setUser(user);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Order order) {
		// TODO Auto-generated method stub
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "update orders set state=? where id=?";
			Object params[] = {order.isState(),order.getId()};
			runner.update(sql,params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		

	}

	//ǰ��ҳ���л�ȡĳ���û������ж���
	@Override
	public List<Order> getAll(boolean state, String userid) {
		// TODO Auto-generated method stub
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from orders where state=? and user_id=?";
			Object params[] ={state,userid};
			List<Order> list = (List<Order>)runner.query(sql, params, new BeanHandler(Order.class));
			for(Order order : list){
				sql = "select * from user where user.id=?";
				User user = (User)runner.query(sql, userid, new BeanHandler(User.class));
				order.setUser(user);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Order> getAllOrder(String userid) {
		// TODO Auto-generated method stub
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from orders where user_id=?";
			List<Order> list = (List<Order>)runner.query(sql, userid, new BeanListHandler(Order.class));
			for(Order order : list){
				sql = "select * from user where user.id=?";
				User user = (User)runner.query(sql, userid, new BeanHandler(User.class));
				order.setUser(user);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


}
