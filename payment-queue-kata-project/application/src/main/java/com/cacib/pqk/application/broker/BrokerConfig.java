package com.cacib.pqk.application.broker;

import com.cacib.pqk.domain.partner.application.ApplicationInfo;
import com.ibm.mq.jakarta.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import jakarta.jms.JMSException;

public class BrokerConfig {

    private final ApplicationInfo info;

    public BrokerConfig(ApplicationInfo info) {
        this.info = info;
    }

    public MQQueueConnectionFactory factory() throws JMSException {
        MQQueueConnectionFactory factory = new MQQueueConnectionFactory();
        factory.setQueueManager(info.getQueueManager());
        factory.setChannel(info.getChannel());
        factory.setHostName(info.getHost());
        factory.setPort(info.getPort());
        factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        factory.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, info.getName());
        return factory;
    }

    public String getUser() {
        return info.getUser();
    }

    public String getPassword() {
        return info.getPassword();
    }

    public String getQueueName() {
        return info.getQueue();
    }
}
