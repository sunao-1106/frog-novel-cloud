package com.sw.common.enums;

/**
 * @author sunao
 * @since 2022/9/16
 * description:
 */
public enum RoleEnum {

    NORMAL_USER(3L, "普通用户", "user.normal"),
    VIP_USER(2L, "会员用户", "user.vip"),
    ADMIN(1L, "管理员", "admin");

    private Long id;
    private String name;
    private String sign;

    RoleEnum(Long id, String name, String sign) {
        this.id = id;
        this.name = name;
        this.sign = sign;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSign() {
        return sign;
    }
}
