package com.racing.service;

import com.racing.dao.AccountDao;
import com.racing.model.Account;
import com.racing.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceDynamo implements UserDetailsService {
    @Autowired
    AccountDao accountDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Account account = accountDao.getByEmail(username);

        if(account != null) {
            return new RacingUserDetails(account.getEmail(), account.getPassword(), Constants.ADMIN);
        }

        return null;
    }
}
