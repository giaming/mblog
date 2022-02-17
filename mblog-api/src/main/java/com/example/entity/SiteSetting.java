package com.example.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (site_setting)实体类
 *
 * @author giaming
 * @since 2022-02-12 20:12:26
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("site_setting")
public class SiteSetting extends Model<SiteSetting> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
	private Long id;
    /**
     * nameEn
     */
    private String nameEn;
    /**
     * nameZh
     */
    private String nameZh;
    /**
     * value
     */
    private String value;
    /**
     * 1基础设置，2页脚徽标，3资料卡，4友链信息
     */
    private Integer type;

}