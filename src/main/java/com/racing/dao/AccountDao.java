package com.racing.dao;

import com.racing.model.Account;

import java.util.List;

public interface AccountDao {
    List<Account> all();
    Account get(String id);
    Account get(String email, String password);
}
