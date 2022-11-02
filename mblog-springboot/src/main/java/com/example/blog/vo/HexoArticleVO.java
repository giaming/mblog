package com.example.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author: ljm
 * @Date: 2022/8/16 13:15
 * @Description: <p>
 *
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HexoArticleVO extends ArticleVO{
    private LocalDateTime createTime;
}
