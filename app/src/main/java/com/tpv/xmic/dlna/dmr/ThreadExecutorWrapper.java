package com.tpv.xmic.dlna.dmr;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author :  jianyu.ke
 * e-mail :  jianyu.ke@tpv-tech.com
 * date   :  2020/8/27 14:42
 * desc   :
 * version: 1.0
 */
public class ThreadExecutorWrapper {

    private static final String TAG = ThreadExecutorWrapper.class.getSimpleName();
    private UiThreadExecutor mUiExecutor;
    private BackgroundThreadExecutor mExecutor;
    private static final String TAG_THREAD_START = "thread: %s start...";
    private static final String TAG_THREAD_END = "thread: %s end";

    public ThreadExecutorWrapper() {
        mExecutor = new BackgroundThreadExecutor();
        mUiExecutor = new UiThreadExecutor();
    }

    public void execute(Runnable runnable, String name) {
        try {
            mExecutor.execute(new SimpleRunnable(runnable, name));
        } catch (RejectedExecutionException | NullPointerException e) {
            Log.e(TAG, "executor.execute exception: " + e.getMessage());
        }
    }

    public <T> void submit(Callable<T> callable, String name, boolean backOnUiThread, IResultListener<T> callback) {
        Future<T> future = mExecutor.submit(new SimpleCallable<>(callable, name));
        try {
            T result = future.get();
            if (backOnUiThread && callback != null) {
                mUiExecutor.execute(() -> callback.onCompleted(result));
            } else {
                if (callback != null) {
                    callback.onCompleted(result);
                }
            }
        } catch (CancellationException | ExecutionException | InterruptedException e) {
            Log.e(TAG, "future.get exception: " + e.getMessage());
        }
    }

    private static class SimpleCallable<T> implements Callable<T> {

        private final Callable<T> callable;
        private final String name;

        SimpleCallable(Callable<T> callable, String name) {
            this.callable = callable;
            this.name = name;
        }

        @Override
        public T call() throws Exception {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            if (name != null && !name.isEmpty()) {
                Thread.currentThread().setName(name);
            }
            Log.i(TAG, String.format(TAG_THREAD_START, Thread.currentThread().getName()));
            T result = null;
            try {
                if (callable != null) {
                    result = callable.call();
                }
            } finally {
                Log.i(TAG, String.format(TAG_THREAD_END, Thread.currentThread().getName()));
            }
            return result;
        }
    }

    private static class SimpleRunnable implements Runnable {

        private final Runnable runnable;
        private final String name;

        SimpleRunnable(Runnable runnable, String name) {
            this.runnable = runnable;
            this.name = name;
        }

        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            if (name != null && !name.isEmpty()) {
                Thread.currentThread().setName(name);
            }
            Log.i(TAG, String.format(TAG_THREAD_START, Thread.currentThread().getName()));
            try {
                if (runnable != null) {
                    runnable.run();
                }
            } finally {
                Log.i(TAG, String.format(TAG_THREAD_END, Thread.currentThread().getName()));
            }
        }
    }


    private static class UiThreadExecutor implements Executor {
        private Handler uiHandler = HandlerCompat.createAsync(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            uiHandler.post(runnable);
        }

        public void release() {
            uiHandler.removeCallbacksAndMessages(null);
            uiHandler = null;
        }
    }

    private static class BackgroundThreadExecutor implements Executor {
        private ThreadPoolExecutor mExecutor;

        public BackgroundThreadExecutor() {
            init();
        }

        private void init() {
            mExecutor = new ThreadPoolExecutor(5, 10, 30, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(15, true));
            mExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            mExecutor.allowCoreThreadTimeOut(true);
        }

        @Override
        public void execute(@NonNull Runnable runnable) {
            if (mExecutor == null) {
                init();
            }
            mExecutor.execute(runnable);
        }

        public <T> Future<T> submit(@NonNull Callable<T> callable) {
            return mExecutor.submit(callable);
        }

        public void release() {
            mExecutor.shutdown();
            try {
                if (!mExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
                    mExecutor.shutdownNow();
                    if (!mExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
                        Log.e(TAG, "Pool did not terminate");
                    }
                }
            } catch (InterruptedException ie) {
                mExecutor.shutdownNow();
                Thread.currentThread().interrupt();
            }
            mExecutor = null;
        }

    }

    public void shutdown() {
        if (mUiExecutor != null) {
            mUiExecutor.release();
            mUiExecutor = null;
        }
        if (mExecutor != null) {
            mExecutor.release();
            mExecutor = null;
        }
    }
}
