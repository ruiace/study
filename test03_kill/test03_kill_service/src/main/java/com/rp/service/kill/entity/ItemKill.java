package com.rp.service.kill.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 待秒杀商品表
 * </p>
 *
 * @author rp
 * @since 2019-11-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ItemKill implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 商品id
     */
    private Integer itemId;

    /**
     * 可被秒杀的总数
     */
    private Integer total;

    /**
     * 秒杀开始时间
     */
    private Date startTime;

    /**
     * 秒杀结束时间
     */
    private Date endTime;

    /**
     * 是否有效（1=是；0=否）
     */
    private Integer isActive;

    /**
     * 创建的时间
     */
    private Date createTime;

    @Transient
    private String name;

    @Transient
    private Integer canKill;


}
