package com.epam.trainee.model.entities;

import com.epam.trainee.model.dao.jdbc.JdbcRoleDao;

public enum Role {

    STOREKEEPER, CHEF, GUEST;

    static {
        JdbcRoleDao dao = JdbcRoleDao.getInstance();
        for (Role r : values()) {
            dao.merge(r);
        }
    }

    public Integer getId() {
        return ordinal();
    }
}
