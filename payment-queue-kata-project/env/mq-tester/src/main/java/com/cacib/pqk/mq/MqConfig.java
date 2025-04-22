package com.cacib.pqk.mq;

import com.ibm.mq.jakarta.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import jakarta.jms.JMSException;

import java.io.InputStream;
import java.util.Properties;

public class MqConfig {

    private final Properties props;

    public MqConfig() throws Exception {
        props = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("mq.properties")) {
            if (in == null) throw new IllegalStateException("mq.properties not found");
            props.load(in);
        }
    }

    public MQQueueConnectionFactory factory() throws JMSException {
        MQQueueConnectionFactory factory = new MQQueueConnectionFactory();
        factory.setQueueManager(props.getProperty("mq.queueManager"));
        factory.setChannel(props.getProperty("mq.channel"));
        factory.setHostName(props.getProperty("mq.host"));
        factory.setPort(Integer.parseInt(props.getProperty("mq.port")));
        factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        factory.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, props.getProperty("mq.applicationName"));
        return factory;
    }

    public String getUser() {
        return props.getProperty("mq.user");
    }

    public String getPassword() {
        return props.getProperty("mq.password");
    }

    public String getQueueName() {
        return props.getProperty("mq.queue");
    }
}
