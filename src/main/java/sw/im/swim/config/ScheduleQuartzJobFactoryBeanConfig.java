package sw.im.swim.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class ScheduleQuartzJobFactoryBeanConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

        ScheduleAutowireJobFactory scheduleAutowireJobFactory = new ScheduleAutowireJobFactory();

        scheduleAutowireJobFactory.setApplicationContext(applicationContext);
        schedulerFactoryBean.setJobFactory(scheduleAutowireJobFactory);

        return schedulerFactoryBean;
    }

}
