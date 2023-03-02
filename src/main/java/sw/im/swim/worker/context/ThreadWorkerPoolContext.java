package sw.im.swim.worker.context;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

@Slf4j
public class ThreadWorkerPoolContext {

    public ExecutorService NOTI_WORKER;
    public ExecutorService NGINX_WORKER;
    public ExecutorService DB_CONNECT_WORKER;
    public ExecutorService DB_DUMP_WORKER;
    public ExecutorService DEFAULT_WORKER;
    public ExecutorService SPEED_TEST_WORKER;

    private ThreadWorkerPoolContext() {

        final ThreadFactory[] namedThreadFactorys = {
                new ThreadFactoryBuilder().setNameFormat("NOTI_WORKER-%d")
                        .build(),
                new ThreadFactoryBuilder().setNameFormat("NGINX_WORKER-%d")
                        .build(),
                new ThreadFactoryBuilder().setNameFormat("DB_CONNECT_WORKER-%d")
                        .build(),
                new ThreadFactoryBuilder().setNameFormat("DB_DUMP_WORKER-%d")
                        .build(),
                new ThreadFactoryBuilder().setNameFormat("DEFAULT_WORKER-%d")
                        .build(),
                new ThreadFactoryBuilder().setNameFormat("SPEED_TEST_WORKER-%d")
                        .build()
        };

        NOTI_WORKER = Executors.newFixedThreadPool(2, namedThreadFactorys[0]);
        NGINX_WORKER = Executors.newFixedThreadPool(1, namedThreadFactorys[1]);
        DB_CONNECT_WORKER = Executors.newFixedThreadPool(2, namedThreadFactorys[2]);
        DB_DUMP_WORKER = Executors.newFixedThreadPool(4, namedThreadFactorys[3]);
        DEFAULT_WORKER = Executors.newFixedThreadPool(2, namedThreadFactorys[4]);
        SPEED_TEST_WORKER = Executors.newFixedThreadPool(1, namedThreadFactorys[5]);

        log.debug("WORKER POOL init complete !");

    }

    private static class SingleTone {
        public static final ThreadWorkerPoolContext INSTANCE = new ThreadWorkerPoolContext();
    }

    public static ThreadWorkerPoolContext getInstance() {
        return SingleTone.INSTANCE;
    }

}
