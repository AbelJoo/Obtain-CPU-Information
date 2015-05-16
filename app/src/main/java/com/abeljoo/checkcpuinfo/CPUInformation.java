package com.abeljoo.checkcpuinfo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 提供当前机器的CPU信息
 *
 * Created by abel on 15-4-28.
 */
public class CPUInformation {

    private static String CPU_ABI = "";

    private static String CPU_ABI2 = "";

    private static String CPU_FAMILY = "";

    private static String CPU_ARCHITECTURE = "";

    static {
        obtainCPUInfoFromAPI();
        obtainCPUInfoFromProc();
    }

    /**
     * from API
     */
    private static void obtainCPUInfoFromAPI() {
        CPU_ABI = android.os.Build.CPU_ABI;
        CPU_ABI2 = android.os.Build.CPU_ABI2;
    }

    /**
     * from /proc/cpuinfo
     */
    private static void obtainCPUInfoFromProc() {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            is = new FileInputStream("/proc/cpuinfo");
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                String[] KVPair= line.split(":");
                if (KVPair.length != 2) {
                    continue;
                }
                matchKV(KVPair[0], KVPair[1]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 对键值进行匹配。
     * 如果键与期望中的匹配，则将其对应的值保存至相应的本地变量。
     *
     * @param key
     * @param val
     */
    private static void matchKV(String key, String val) {

        String key_CpuFamily = "cpu family";
        String key_CpuArchitecture = "CPU architecture";

        key = key.trim();
        val = val.trim();

        if (key.equals(key_CpuFamily)) {
            CPU_FAMILY = val;
        }

        if (key.equals(key_CpuArchitecture)) {
            CPU_ARCHITECTURE = val;
        }
    }

    public static String getCPU_ABI() {
        return CPU_ABI;
    }

    public static String getCPU_ABI2() {
        return CPU_ABI2;
    }

    public static String getCPU_FAMILY() {
        return CPU_FAMILY;
    }

    public static String getCPU_ARCHITECTURE() {
        return CPU_ARCHITECTURE;
    }
}
