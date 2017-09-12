package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * https://segmentfault.com/a/1190000004279679
 * ThreadMode: POSTING    这时候订阅者执行的线程与事件的发布者所在的线程为同一个线程。也就是说事件由哪个线程发布的，订阅者就在哪个线程中执行。这个也是EventBus默认的线程模式，也就是说在上面的例子中用的就是这种ThreadMode。由于没有线程的切换，也就意味消耗的资源也是最小的。如果一个任务不需要多线程的，也是推荐使用这种ThreadMode的
 * ThreadMode.MAIN:     从它的名字就很容易可以看出，他是在Android的主线程中运行的。如果提交的线程也是主线程，那么他就和ThreadMode.POSTING一样了。当然在这里由于是在主线程中运行的，所以在这里就不能执行一些耗时的任务
 *  ThreadMode: BACKGROUND   这种模式下，我们的订阅者将会在后台线程中执行。如果发布者是在主线程中进行的事件发布，那么订阅者将会重新开启一个子线程运行，若是发布者在不是在主线程中进行的事件发布，那么这时候订阅者就在发布者所在的线程中执行任务
 *
 *  ThreadMode: ASYNC  在这种模式下，订阅者将会独立运行在一个线程中。不管发布者是在主线程还是在子线程中进行事件的发布，订阅者都是在重新开启一个线程来执行任务
 */
public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        textView = (TextView) findViewById(R.id.show);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(String message) {
        textView.setText(message);

    }

    @Subscribe(priority = 6,threadMode = ThreadMode.POSTING)
    public void handleEvent1(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
        Log.e("tag","priority = 6");
        EventBus.getDefault().cancelEventDelivery(string);//只有在 ThreadMode.POSTING模式下取消事件传递有效
    }

    @Subscribe(priority = 4,threadMode = ThreadMode.POSTING)
    public void handleEvent2(String string) {
        Toast.makeText(this, string + "22", Toast.LENGTH_LONG).show();
        Log.e("tag","priority = 4");
//        EventBus.getDefault().cancelEventDelivery(string);
    }

    public void post(View view) {
//       Toast.makeText(this,"test",Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {

            @Override
            public void run() {
                EventBus.getDefault().post("post===data");
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
