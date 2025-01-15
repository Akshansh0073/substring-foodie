package com.substring.foodie.substring_foodie.example.utils;

import com.substring.foodie.substring_foodie.example.entity.CategoryForRowMapper;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper implements RowMapper<CategoryForRowMapper> {


    //logic to convert table row to category object:
    @Override
    public CategoryForRowMapper mapRow(ResultSet rs, int rowNum) throws SQLException {
        //object category: blank
        CategoryForRowMapper category = new CategoryForRowMapper();
        // table se data nakalna hai aur category mein set karna hai.
        category.setId(rs.getInt("id"));
        category.setTitle(rs.getString("title"));
        category.setDescription(rs.getString("description"));
        return category;
    }


}
