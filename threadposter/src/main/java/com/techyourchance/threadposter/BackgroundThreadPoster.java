package com.techyourchance.threadposter;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BackgroundThreadPoster {

    private final ThreadPoolExecutor mThreadPoolExecutor;

    public BackgroundThreadPoster() {
        mThreadPoolExecutor = newThreadPoolExecutor();
    }

    /**
     * Execute {@link Runnable} on a random background thread.
     * @param runnable {@link Runnable} instance containing the code that should be executed
     */
    public final void post(Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }

    /**
     * Get the underlying {@link ThreadPoolExecutor}.
     * In general, this method shouldn't be used and is provided only for the purpose of
     * integration with existing libraries and frameworks.
     */
    protected final ThreadPoolExecutor getThreadPoolExecutor() {
        return mThreadPoolExecutor;
    }

    /**
     * Get the underlying {@link ThreadFactory}.
     * In general, this method shouldn't be used and is provided only for the purpose of
     * integration with existing libraries and frameworks.
     */
    protected final ThreadFactory getThreadFactory() {
        return getThreadPoolExecutor().getThreadFactory();
    }

    /**
     * This factory method constructs the instance of {@link ThreadPoolExecutor} that is used by
     * {@link BackgroundThreadPoster} internally.
     * Override only if you're absolutely sure that you know what you're doing.
     */
    protected ThreadPoolExecutor newThreadPoolExecutor() {
        // copied from Executors.newCachedThreadPool(), but changed core pool size to 1 in order
        // to achieve better average response time
        return new ThreadPoolExecutor(
                1,
                Integer.MAX_VALUE,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>()
        );
    }

}
