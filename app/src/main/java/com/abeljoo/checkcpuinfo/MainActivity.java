package com.abeljoo.checkcpuinfo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private TextView mTextViewCPUInfo;

    private String mCPUInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initViews();
        errorTest();
    }

    private void initData() {
        StringBuilder sb = new StringBuilder();
        sb.append("android.os.Build.CPU_ABI: " + CPUInformation.getCPU_ABI() + "\n");
        sb.append("android.os.Build.CPU_ABI2: " + CPUInformation.getCPU_ABI2() + "\n");
        sb.append("CPU_FAMILY: " + CPUInformation.getCPU_FAMILY() + "\n");
        sb.append("CPU_ARCHITECTURE: " + CPUInformation.getCPU_ARCHITECTURE() + "\n");
        mCPUInfo = sb.toString();
    }

    private void initViews() {
        mTextViewCPUInfo = (TextView) findViewById(R.id.cpuinfo);
        mTextViewCPUInfo.setText(mCPUInfo);
    }

    /**
     * 错误测试。通过这个测试方法可以得知，不同的CPU架构会有什么类型的信息。
     * 已通过云测，全部机型通过。
     */
    private void errorTest() {

        /**
         * FAMILY和ARCHITECTURE都没有
         */
        if (CPUInformation.getCPU_FAMILY().equals("")
                && CPUInformation.getCPU_ARCHITECTURE().equals("")) {
            int a = 1/0;
        }

        /**
         * FAMILY和ARCHITECTURE都有
         */
        if (!CPUInformation.getCPU_FAMILY().equals("")
                && !CPUInformation.getCPU_ARCHITECTURE().equals("")) {
            int a = 1/0;
        }

        /**
         * 主架构为armeabi时，却有FAMILY
         */
        if (CPUInformation.getCPU_ABI().contains("arm")
                && !CPUInformation.getCPU_FAMILY().equals("")) {
            int a = 1/0;
        }

        /**
         * 主架构为x86时，却有ARCHITECTURE
         */
        if (CPUInformation.getCPU_ABI().contains("86")
                && !CPUInformation.getCPU_ARCHITECTURE().equals("")) {
            int a = 1/0;
        }
    }
}
