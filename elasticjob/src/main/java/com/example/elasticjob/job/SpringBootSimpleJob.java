package com.example.elasticjob.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringBootSimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext context) {

        log.info("JobName: {}, TaskId: {}, ShardingItem: {}, shardingParameter: {}",
                context.getJobName(),
                context.getTaskId(),
                context.getShardingItem(),
                context.getShardingParameter());

    }
}
