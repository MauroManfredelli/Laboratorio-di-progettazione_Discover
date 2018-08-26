package it.unimib.discover.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.unimib.discover.entity.MyUserAccount;

public class MyUserAccountMapper implements RowMapper<MyUserAccount> {
	 
    @Override
    public MyUserAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
 
        String id = rs.getString("id");
  
        String email = rs.getString("email");
        String userName= rs.getString("user_name");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String password = rs.getString("password");
        String imageUrl = rs.getString("image_url");
        String role = rs.getString("role");
        Integer punti = rs.getInt("punti");
        Integer livello = rs.getInt("livello");
 
        return new MyUserAccount(id, email,userName, firstName,
                lastName, password,
                imageUrl, role, punti, livello );
    }
 
}
