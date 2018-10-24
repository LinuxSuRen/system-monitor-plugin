package io.jenkins.plugins.monitor;

import hudson.Extension;
import hudson.model.ComputerPanelBox;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.LinkedHashMap;
import java.util.Map;

@Extension
public class SystemMonitor extends ComputerPanelBox {
    public MemoryUsage getMem() {
        MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();

        return memBean.getHeapMemoryUsage();
    }

    public Map<String, String> getMemHuman() {
        Map<String, String> data = new LinkedHashMap();
        MemoryUsage mem = getMem();

        data.put("Init", getHumanSize(mem.getInit()));
        data.put("Used", getHumanSize(mem.getUsed()));
        data.put("Commited", getHumanSize(mem.getCommitted()));
        data.put("Max", getHumanSize(mem.getMax()));

        return data;
    }

    private String getHumanSize(long size) {
        if(size < K) {
            return String.format("%d", size);
        } else if(size < M) {
            return String.valueOf(size * 1f / K) + "K";
        } else if(size < G) {
            return String.valueOf(size * 1f / M) + "M";
        } else {
            return String.valueOf(size * 1f / G) + "G";
        }
    }

    private static final long K = 1024;
    private static final long M = K * 1024;
    private static final long G = M * 1024;
    private static final long T = G * 1024;
}
