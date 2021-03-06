package com.sandman.blog.dao.mysql.system;

import com.sandman.blog.entity.system.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunpeikai on 2018/5/23.
 */
@Repository
public interface RoleDao {
    public List<Role> findByUserId(Long userId);
}
