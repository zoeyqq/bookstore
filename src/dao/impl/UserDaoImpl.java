package dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import dao.UserDao;
import domain.User;
import utils.JdbcUtils;

public class UserDaoImpl implements UserDao {

	@Override
	public void add(User user) {
		// TODO Auto-generated method stub
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "insert into user(id,username,password,phone,cellphone,email,address) values(?,?,?,?,?,?,?)";
			Object params[] = {user.getId(),user.getUsername(),user.getPassword(),user.getPhone(),user.getCellphone(),user.getEmail(),user.getAddress()};
			runner.update(sql, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);			
		}
	}

	@Override
	public User find(String id) {
		// TODO Auto-generated method stub
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from user where id=?";
			return (User)runner.query(sql, id, new BeanHandler(User.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	@Override
	public User find(String username, String password) {
		// TODO Auto-generated method stub
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from user where username=? and password=?";
			Object params[]={username,password};
			return (User)runner.query(sql, params, new BeanHandler(User.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

}
