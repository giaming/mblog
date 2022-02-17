package com.example.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (comment)实体类
 *
 * @author giaming
 * @since 2022-02-12 18:04:11
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("comment")
public class Comment extends Model<Comment> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
	private Long id;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 头像(图片路径)
     */
    private String avatar;
    /**
     * 评论时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 评论者ip地址
     */
    private String ip;
    /**
     * 博主回复
     */
    private Integer isAdminComment;
    /**
     * 公开或非公开
     */
    private Boolean isPublished;
    /**
     * 所属的文章
     */
    private Long blogId;
    /**
     * 父评论id，-1为根评论
     */
    private Long parentCommentId;
    /**
     * 个人网站
     */
    private String website;
    /**
     * 被回复昵称
     */
    private String parentCommentNickname;
    /**
     * 如果评论昵称为QQ号，则将昵称和头像置为QQ昵称和QQ头像，并将此字段置为QQ号备份
     */
    private String qq;

}