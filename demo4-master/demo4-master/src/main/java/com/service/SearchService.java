package com.service;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface SearchService {
    List<Map<String, Object>> search(String desc, String token);
}
