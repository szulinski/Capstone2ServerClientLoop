package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }



    @Override
    public Account findByUserId(Long id) {
        String sql = "SELECT * FROM account WHERE user_id =  ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (rowSet.next()){
            return mapRowToAccount(rowSet);
        }
        throw new UsernameNotFoundException("User " + id + " was not found.");
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();

        account.setAccountId(rs.getLong("account_id"));
        account.setUserId(rs.getLong("user_id"));
        account.setBalance(BigDecimal.valueOf(rs.getDouble("balance")));

        return account;
    }

//    @Transactional
//    public boolean adjustAccounts(Long userIdFrom, Long userIdTo, BigDecimal amount) {
//        String sql1 = "UPDATE account SET balance -= ? FROM account WHERE account_id = ?";
//        SqlRowSet rowSet1 = jdbcTemplate.queryForRowSet(sql1, amount, userIdFrom);
//        String sql2 = "UPDATE account SET balance += ? FROM account WHERE account_id = ?";
//        SqlRowSet rowSet2 = jdbcTemplate.queryForRowSet(sql2, amount, userIdTo);
//        return true;
//    }
}
