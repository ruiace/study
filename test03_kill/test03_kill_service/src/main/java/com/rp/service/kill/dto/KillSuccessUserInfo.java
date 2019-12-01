package com.rp.service.kill.dto;

import com.rp.service.kill.entity.ItemKillSuccess;
import lombok.Data;

import java.io.Serializable;

/**
 *
 **/
@Data
public class KillSuccessUserInfo extends ItemKillSuccess implements Serializable {

    private String userName;

    private String phone;

    private String email;

    private String itemName;

    @Override
    public String toString() {
        return super.toString() + "\nKillSuccessUserInfo{" +
                "userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", itemName='" + itemName + '\'' +
                '}';
    }
}