package com.kuit.conet.dao;

import com.kuit.conet.domain.user.User;
import com.kuit.conet.dto.web.request.auth.OptionTermRequestDTO;
import com.kuit.conet.jpa.domain.auth.Platform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class UserDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public User findById(Long userId) {
        String sql = "select * from member where member_id=:member_id and status=1";
        Map<String, Long> param = Map.of("member_id", userId);

        RowMapper<User> mapper = (rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getLong("member_id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setUserImgUrl(rs.getString("img_url"));
            user.setServiceTerm(rs.getBoolean("service_term"));
            user.setOptionTerm(rs.getBoolean("option_term"));
            String platform = rs.getString("platform");
            user.setPlatform(Platform.valueOf(platform));
            user.setPlatformId(rs.getString("platform_id"));
            return user;
        };

        return jdbcTemplate.queryForObject(sql, param, mapper);
    }

    public User agreeTermAndPutName(String name, Boolean optionTerm, Long userId) {
        String sql = "update member set name=:name, service_term=1, option_term=:option_term where member_id=:member_id and status=1";
        Map<String, Object> param = Map.of(
                "name", name,
                "option_term", optionTerm,
                "member_id", userId);

        jdbcTemplate.update(sql, param);

        String returnSql = "select * from member where member_id=:member_id";
        Map<String, Object> returnParam = Map.of("member_id", userId);

        RowMapper<User> returnMapper = (rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getLong("member_id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setUserImgUrl(rs.getString("img_url"));
            user.setServiceTerm(rs.getBoolean("service_term"));
            user.setOptionTerm(rs.getBoolean("option_term"));
            String platform = rs.getString("platform");
            user.setPlatform(Platform.valueOf(platform));
            user.setPlatformId(rs.getString("option_term"));
            return user;
        };

        return jdbcTemplate.queryForObject(returnSql, returnParam, returnMapper);
    }

}