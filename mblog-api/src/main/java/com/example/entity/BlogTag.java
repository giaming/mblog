package com.example.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author giaming
 * @date 2022/2/12 - 18:25
 * @detail
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogTag extends Model<BlogTag> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long blogId;

    private Long tagId;
}
