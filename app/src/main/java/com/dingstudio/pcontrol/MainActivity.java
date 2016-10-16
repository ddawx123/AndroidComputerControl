package com.dingstudio.pcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import java.io.OutputStream;
import java.net.Socket;
import java.net.InetSocketAddress;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public Button btnDisConnect;  //定义断开按钮的对象名
    public Button btnConnect;  //定义连接按钮的对象名
    public Button btnCloseScreen;  //定义远程熄屏按钮的对象名
    public Button btnLockScreen; //定义远程锁屏按钮的对象名
    public Button btnReboot; //定义远程重启按钮的对象名
    public Button btnVolDn;  //定义远程音量控制（减）的按钮的对象名
    public Button btnVolUp;  //定义远程音量控制（加）的按钮的对象名
    public Button btnSoundCtl;  //定义远程音量控制（静音）的按钮的对象名
    public Button btnSendUserMessage; //定义发送自定义消息的按钮对象名
    public EditText TextAddress; //远程主机地址输入区域的对象定义
    public EditText textCommand; //定义自定义指令专用输入框对象名
    public TextView TextVersion;  //版本显示交互式功能区定义
    String mEditText = "";  //初始化TCP字符串数据包为空
    String ipAddr = "";  //初始化远程主机地址字符串为空
    public Socket sock = null; //初始化socket对象
    private int timeout = 5000;  //设置socket建立的失败超时值
    private InetSocketAddress  isa;  //指定ISA

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
                .build());


        btnConnect = (Button) findViewById(R.id.btnConnect);
        btnCloseScreen = (Button) findViewById(R.id.btnCloseScreen);
        btnLockScreen = (Button) findViewById(R.id.btnLockScreen);
        btnReboot = (Button) findViewById(R.id.btnReboot);
        btnVolDn = (Button) findViewById(R.id.btnVolDn);
        btnVolUp = (Button) findViewById(R.id.btnVolUp);
        btnSoundCtl = (Button) findViewById(R.id.btnSoundCtl);
        btnSendUserMessage = (Button) findViewById(R.id.btnSendUserMessage);
        btnDisConnect = (Button) findViewById(R.id.btnDisConnect);
        TextAddress = (EditText) findViewById(R.id.textAddress);
        TextVersion = (TextView) findViewById(R.id.textVersion);
        textCommand = (EditText) findViewById(R.id.TextCommand);

        btnSendUserMessage.setOnClickListener(new OnClickListener() {  //此程序用于传自定义参数到远端机
            @Override
            public void onClick(View v) {

                if ("".equals(textCommand.getText().toString())){
                    Toast.makeText(getApplication(), "亲，自定义参数不能为空。此功能仅供技术人员使用，普通用户请勿使用！",Toast.LENGTH_SHORT).show();
                }

                else{

                    try{
                        OutputStream outputStream = sock.getOutputStream();
                        byte buffer [] = new byte[4*1024];
                        int temp = 0 ;
                        buffer= textCommand.getText().toString().getBytes();
                        outputStream.write(buffer);
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(getApplication(), "发送失败！可能是服务器未连接或网络不稳定导致，请稍后重试。",Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        btnSoundCtl.setOnClickListener(new OnClickListener() {   //此程序用于远程静音
            @Override
            public void onClick(View v) {
                try{
                    OutputStream outputStream = sock.getOutputStream();
                    byte buffer [] = new byte[4*1024];
                    int temp = 0 ;
                    mEditText = "novol";
                    buffer= mEditText.toString().getBytes();
                    outputStream.write(buffer);
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplication(), "发送失败！可能是服务器未连接或网络不稳定导致，请稍后重试。",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnVolUp.setOnClickListener(new OnClickListener() {  //此程序用于远程增强音量
            @Override
            public void onClick(View v) {
                try{
                    OutputStream outputStream = sock.getOutputStream();
                    byte buffer [] = new byte[4*1024];
                    int temp = 0 ;
                    mEditText = "volup";
                    buffer= mEditText.toString().getBytes();
                    outputStream.write(buffer);
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplication(), "发送失败！可能是服务器未连接或网络不稳定导致，请稍后重试。",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnVolDn.setOnClickListener(new OnClickListener() {  //此程序用于远程减轻音量
            @Override
            public void onClick(View v) {
                try{
                    OutputStream outputStream = sock.getOutputStream();
                    byte buffer [] = new byte[4*1024];
                    int temp = 0 ;
                    mEditText = "voldn";
                    buffer= mEditText.toString().getBytes();
                    outputStream.write(buffer);
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplication(), "发送失败！可能是服务器未连接或网络不稳定导致，请稍后重试。",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReboot.setOnClickListener(new OnClickListener() {  //此程序用于远程重启远端机
            @Override
            public void onClick(View v) {
                try{
                    OutputStream outputStream = sock.getOutputStream();
                    byte buffer [] = new byte[4*1024];
                    int temp = 0 ;
                    mEditText = "reboot";
                    buffer= mEditText.toString().getBytes();
                    outputStream.write(buffer);
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplication(), "发送失败！可能是服务器未连接或网络不稳定导致，请稍后重试。",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLockScreen.setOnClickListener(new OnClickListener() {  //此程序用于远程锁定
            @Override
            public void onClick(View v) {
                try{
                    OutputStream outputStream = sock.getOutputStream();
                    byte buffer [] = new byte[4*1024];
                    int temp = 0 ;
                    mEditText = "lockscreen";
                    buffer= mEditText.toString().getBytes();
                    outputStream.write(buffer);
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplication(), "发送失败！可能是服务器未连接或网络不稳定导致，请稍后重试。",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCloseScreen.setOnClickListener(new OnClickListener() {  //远程关屏
            @Override
            public void onClick(View v) {
                try{
                    OutputStream outputStream = sock.getOutputStream();
                    byte buffer [] = new byte[4*1024];
                    int temp = 0 ;
                    mEditText = "closescreen";
                    buffer= mEditText.toString().getBytes();
                    outputStream.write(buffer);
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplication(), "发送失败！可能是服务器未连接或网络不稳定导致，请稍后重试。",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDisConnect.setOnClickListener(new OnClickListener() {  //断开Socket专用
            @Override
            public void onClick(View v) {
                try{

                    try{  //本次发送用于发送断开会话前最后一个握手数据包disconnect！
                        OutputStream outputStream = sock.getOutputStream();
                        byte buffer [] = new byte[4*1024];
                        int temp = 0 ;
                        mEditText = "disconnect";
                        buffer= mEditText.toString().getBytes();
                        outputStream.write(buffer);  //末端会话假设成功发出，远端机或重置socket状态，释放所有在6565端口上的连接并重启网络服务。
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(getApplication(), "发送失败！可能是服务器未连接或网络不稳定导致，请稍后重试。",Toast.LENGTH_SHORT).show();
                    }

                    sock.close();  //末端会话发送结束后关闭连接

                }
                catch (Exception e)
                {
                    Toast.makeText(getApplication(), "亲，当前没有连接主机，无需断开！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextVersion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

        btnConnect.setOnClickListener(new OnClickListener() //登录
        { // 关闭定时器按钮点击后响应接口
            public void onClick(View v)
            {
                ipAddr=TextAddress.getText().toString();
                if ("".equals(ipAddr)){
                    Toast.makeText(getApplication(), "亲，主机地址不能为空。请重试！",Toast.LENGTH_SHORT).show();
                }
                else{
                    try
                    {
                        Toast.makeText(getApplication(), "亲，我们正在与服务器建立连接，请稍候！",Toast.LENGTH_SHORT).show();
                        sock = new Socket();  //建立一个新的空socket会话
                        isa = new InetSocketAddress(ipAddr, 6565);  //定义远端机的主机地址和端口
                        sock.connect(isa, timeout);  //开始连接，并使用程序头定义的超时检测值。如果超时5秒则抛出一个异常并由异常处理程序解决！
                        Toast.makeText(getApplication(), "连接建立成功！现在可以开始发送指令给远程PC了。",Toast.LENGTH_SHORT).show();

                        try{  //假设连接成功，则发送首个握手数据包！
                            OutputStream outputStream = sock.getOutputStream(); //从Socket当中得到OutputStream
                            byte buffer [] = new byte[4*1024];
                            int temp = 0 ;
                            mEditText = "android";
                            buffer= mEditText.toString().getBytes();
                            outputStream.write(buffer);
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getApplication(), "网络状况测试专用握手数据包发送失败！可能是您或服务器的网络不稳定导致的，您依旧可以继续遥控，但是使用体验会较差，操作会有延迟。",Toast.LENGTH_SHORT).show();
                        }


                    }
                    catch(Exception e)
                    {
                        Toast.makeText(getApplication(), "亲，当前系统暂时无法连接到远端机：" + ipAddr + "。请稍后重试！（错误类型：连接超时达5000毫秒。）",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });




    }


}
