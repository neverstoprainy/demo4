package ls.com.mapper;

import ls.com.entity.Admin;
import ls.com.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("SELECT * FROM admin WHERE adminname = #{adminname}")
    Admin findByAdminname(@Param("adminname") String adminname);

    @Delete("DELETE FROM user WHERE id = #{id}")
    void deleteUser(Long id);
    @Select("SELECT username, email FROM user")
    List<User> getUserList();

}
