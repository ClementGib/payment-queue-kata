package com.cacib.pqk.application.jms;

import jakarta.jms.JMSException;

@FunctionalInterface
public interface JMSCallable<T> {
    T call() throws JMSException;
}
