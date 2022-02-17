package com.example.common.lang.vo;

import lombok.Data;

import java.util.List;

/**
 * @author giaming
 * @date 2022/2/12 - 18:22
 * @detail
 */
@Data
public class PageResult {
    private List<BlogInfo> records;
    private int total;
    private int size;
    private int current;
    private List<String> orders;
    private boolean searchCount;
    private int pages;
}
