package com.service.impl;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/27 10:31
 */


import com.mapper.SearchMapper;
import com.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired(required = false)
    private SearchMapper searchMapper;

    @Override
    public List<Map<String, Object>> search(String desc, String token) {
        return searchMapper.search(desc);
    }
}
