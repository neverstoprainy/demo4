package com.service.impl;

import com.mapper.RecycleMapper;
import com.service.RecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/24 19:26
 */
@Service
public class RecycleServiceImpl implements RecycleService {
        @Autowired(required = false)
        private RecycleMapper recycleMapper;

        @Override
        @Transactional
        public void recycleFile(String fileId, String parentFolderId, String userId, String token) throws Exception {
            // 实现回收文件逻辑，更新文件状态到数据库
            // 注意处理事务和异常
            recycleMapper.recycleFile(fileId, parentFolderId, userId, token);
        }
}
