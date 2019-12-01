package com.rp.service.kill.service;

public interface RabbitMqSendService {


    void killSuccessSendEmail(String orderNo);

    public void killSuccessSendExpireOrder(String orderNo);
}
