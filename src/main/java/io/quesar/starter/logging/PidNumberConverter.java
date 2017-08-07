package io.quesar.starter.logging;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.lang.management.ManagementFactory;

/**
 * @author dolphineor@gmail.com
 * @version 1.0
 * @date 2017-08-04
 */
public class PidNumberConverter extends ClassicConverter {

    private final String pid;

    public PidNumberConverter() {
        String processName = ManagementFactory.getRuntimeMXBean().getName();
        this.pid = processName.split("@")[0];
    }

    @Override
    public String convert(ILoggingEvent event) {
        return pid;
    }
}
