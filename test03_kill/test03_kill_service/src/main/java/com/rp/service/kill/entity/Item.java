package com.rp.service.kill.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author rp
 * @since 2019-11-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 商品名
     */
    private String name;

    /**
     * 商品编号
     */
    private String code;

    /**
     * 库存
     */
    private Long stock;

    /**
     * 采购时间
     */
    private LocalDate purchaseTime;

    /**
     * 是否有效（1=是；0=否）
     */
    private Integer isActive;

    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
