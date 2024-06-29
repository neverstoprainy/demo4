package com.controller;

/**
 * @author zym
 * @version 1.0
 * @description: TODO
 * @date 2024/6/27 10:14
 */

import com.entity.ResponseMessage;
import com.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/search")
public class SearchController {

    @Autowired(required = false)
    private SearchService searchService;

    @GetMapping
    public ResponseEntity<ResponseMessage> search(@RequestParam(value = "desc", required = false) String desc,
                                                  @RequestHeader("Authorization") String token) {
        try {
            List<Map<String, Object>> results = searchService.search(desc, token);
            ResponseMessage response = new ResponseMessage(0, results, "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseMessage response = new ResponseMessage(1, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
