package com.sandman.blog.dao.mysql.user;

import com.sandman.blog.entity.user.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunpeikai on 2018/5/4.
 */
@Repository
public interface CategoryDao {
    public Category getCategoryById(Long id);
    public List<Category> getCategoryListByBloggerId(Long bloggerId);
    public boolean createCategory(Category category);
    public void updateCategory(Category category);
    public Integer findMaxOrder(@Param("bloggerId") Long bloggerId);
}
