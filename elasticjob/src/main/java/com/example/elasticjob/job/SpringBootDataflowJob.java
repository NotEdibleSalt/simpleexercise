package com.example.elasticjob.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Range;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.dataflow.job.DataflowJob;
import org.apache.zookeeper.cli.LsCommand;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@Component
public class SpringBootDataflowJob implements DataflowJob<String> {

    private int num = 0;

    private List<String> list = Arrays.asList("1"," 2", "3", "4", "5", "6");

    /**
     * 如果开启流式处理，则作业只有在 fetchData 方法的返回值为 null 或集合容量为空时，才停止抓取，否则作业将一直运行下去
     * @param context
     * @return
     */
    @Override
    public List<String> fetchData(ShardingContext context) {

        // 获取数据
        long id = Thread.currentThread().getId();
        log.info("currentThread: {}, taskId: {}, fetchData: 第{}次, ShardingItem: {}, ShardingParameter: {}",
                id, context.getTaskId(), num, context.getShardingItem(), context.getShardingParameter());

        num++;
        if (num == 1){
            return list.stream().limit(3).collect(Collectors.toList());
        } else if (num == 2){
            return list.stream().skip(3).limit(3).collect(Collectors.toList());
        } else {
            num = 0;
            return null;
        }

    }

    /**
     *
     * @param context
     * @param data
     */
    @Override
    public void processData(ShardingContext context, final List<String> data) {

        // 处理数据
        long id = Thread.currentThread().getId();
        log.info("currentThread: {}, taskId: {}, processData: 第{}次, ShardingItem: {}, ShardingParameter: {}",
                id, context.getTaskId(), num, context.getShardingItem(), context.getShardingParameter());

        data.stream().forEach(System.out::println);

    }
}
