package sw.im.swim.worker.context;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Slf4j
public class ThreadWorkderPoolContext {

    public ExecutorService NOTI_WORKER;
    public ExecutorService NGINX_WORKER;
    public ExecutorService DB_WORKER;

    private ThreadWorkderPoolContext() {

        final ThreadFactory[] namedThreadFactorys = {
                new ThreadFactoryBuilder().setNameFormat("NOTI_WORKER-%d")
                        .build(),
                new ThreadFactoryBuilder().setNameFormat("NGINX_WORKER-%d")
                        .build(),
                new ThreadFactoryBuilder().setNameFormat("DB_WORKER-%d")
                        .build()
        };

        NOTI_WORKER = Executors.newFixedThreadPool(4, namedThreadFactorys[0]);
        NGINX_WORKER = Executors.newFixedThreadPool(1, namedThreadFactorys[1]);
        DB_WORKER = Executors.newFixedThreadPool(4, namedThreadFactorys[2]);

        log.debug("WORKER POOL init complete !");

    }

    private static class SingleTone {
        public static final ThreadWorkderPoolContext INSTANCE = new ThreadWorkderPoolContext();
    }

    public static ThreadWorkderPoolContext getInstance() {
        return SingleTone.INSTANCE;
    }

}
