package com.hehua.Handler_01;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

    private Button button;
    private TextView textView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textViewId);
        button = (Button) findViewById(R.id.buttonId);

        button.setOnClickListener(new ButtonLister());

        WorkerThread wt = new WorkerThread();
        wt.start();

    }

    class ButtonLister implements OnClickListener {
        @Override
        public void onClick(View v) {
            System.out.println("onClick:" +Thread.currentThread().getName());
            String s = "从网络当中获取的数据";
            Message msg = handler.obtainMessage();
            msg.obj = s;
            handler.sendMessage(msg);
        }
    }

    class WorkerThread extends Thread {
        @Override
        public void run() {
            //super.run();

            Looper.prepare();
            //在workerThread当中生成一个Handler对象
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    System.out.println("handleMessage :"+Thread.currentThread().getName());
                    System.out.println("收到了消息对象");
                    //super.handleMessage(msg);
                    String s = (String) msg.obj;
                    textView.setText(s);
                }
            };
            //调用looper的loop()方法之后，looper对象将不断的从消息队列当中取出消息对象,然后调用handler的handleMessage()方法，处理该消息对象
            Looper.loop();


        }
    }


}
