package ls.com.service;

import ls.com.entity.Admin;
import ls.com.entity.User;

import java.util.List;

public interface AdminService {

    Admin findByAdminname(String adminname);
    void deleteUser(Long userid);

    List<User> getUserList();
}
