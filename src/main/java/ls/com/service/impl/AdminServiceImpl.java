package ls.com.service.impl;

import ls.com.entity.Admin;
import ls.com.entity.User;
import ls.com.mapper.AdminMapper;
import ls.com.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl  implements AdminService{
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin findByAdminname(String adminname) {
        return adminMapper.findByAdminname(adminname);
    }
    @Override
    public void deleteUser(Long userid) {
        adminMapper.deleteUser(userid);
    }
    @Override
    public List<User> getUserList() {
        return adminMapper.getUserList();
    }
}
