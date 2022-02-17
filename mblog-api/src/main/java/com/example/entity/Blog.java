package com.example.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotBlank;

/**
 * (blog)实体类
 *
 * @author giaming
 * @since 2022-02-12 18:04:11
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("blog")
public class Blog extends Model<Blog> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
	private Long id;
    /**
     * 文章标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;
    /**
     * 文章首图，用于随机文章展示
     */
    private String firstPicture;
    /**
     * 描述
     */
    @NotBlank(message = "摘要不能为空")
    private String description;
    /**
     * 文章正文
     */
    @NotBlank(message = "内容不能为空")
    private String content;
    /**
     * 创建时间
     */
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateTime;
    /**
     * 浏览次数
     */
    private Integer views;
    /**
     * 文章字数
     */
    private Integer words;
    /**
     * 文章分类id
     */
    private Long typeId;
    /**
     * 文章作者id
     */
    private Long userId;
    /**
     * status
     */
    private Integer status;

}