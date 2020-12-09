package com.controller;


import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.GetProcessInstancesPayloadBuilder;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.model.builders.StartProcessPayloadBuilder;
import org.activiti.api.process.model.payloads.GetProcessInstancesPayload;
import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.CompleteTaskPayloadBuilder;
import org.activiti.api.task.model.builders.GetTasksPayloadBuilder;
import org.activiti.api.task.model.payloads.CompleteTaskPayload;
import org.activiti.api.task.model.payloads.GetTasksPayload;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.task.TaskInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * activit7新增api
 */
@Slf4j
@RestController
@RequestMapping("/newTaskService")
public class NewProcessController {

    private static final String PROCESS_DEFINITION_KEY = "zx";

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private TaskRuntime taskRuntime;

    /**
     * 启动流程
     *
     * @return java.lang.String
     * @Date 2020/12/8
     **/
    @PostMapping("start")
    public String start() {

        Map variables = new HashMap();
        variables.put("applyName", "salaboy");

        String businessKey = UUID.randomUUID().toString().replace("-", "");

        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey(PROCESS_DEFINITION_KEY)
                .withBusinessKey(businessKey)
                .withVariables(variables)
                .build());

        log.info("**********开始流程************");
        log.info("流程定义key: {}", processInstance.getProcessDefinitionKey());
        log.info("流程id: {}", processInstance.getProcessDefinitionId());
        log.info("业务id: {}", processInstance.getBusinessKey());

        return processInstance.getId();
    }

    /**
     * 查询流程实例
     *
     * @return void
     * @Date 2020/12/8
     **/
    @PostMapping("query/processInstance")
    public String queryProcessInstance() {

        GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayloadBuilder()
                .withProcessDefinitionKey(PROCESS_DEFINITION_KEY)
                .build();
        Page<ProcessInstance> processInstancePage = processRuntime.processInstances(Pageable.of(0, 10), getProcessInstancesPayload);

        log.info("流程实例集合：{}", processInstancePage.getContent());

        return "流程实例集合: " + processInstancePage.getContent().stream().map(ProcessInstance::getId).collect(Collectors.joining(","));
    }

    /**
     * 查询任务
     *
     * @return java.lang.String
     * @Date 2020/12/8
     **/
    @PostMapping("query/task")
    public String queryTask() {

        GetTasksPayload getTasksPayload = new GetTasksPayloadBuilder()
                .withAssignee("salaboy")
                .build();
        Page<Task> taskPage = taskRuntime.tasks(Pageable.of(0, 10), getTasksPayload);
        log.info("任务列表: {}", taskPage.getContent());

        return "任务列表:" + taskPage.getContent().stream().map(Task::getId).collect(Collectors.joining(","));
    }

    /**
     * 办理任务
     *
     * @return void
     * @Date 2020/12/8
     **/
    @PostMapping("handle")
    public String handle() {

        GetTasksPayload getTasksPayload = new GetTasksPayloadBuilder()
                .withAssignee("salaboy")
                .build();
        Page<Task> taskPage = taskRuntime.tasks(Pageable.of(0, 10), getTasksPayload);
        log.info("待办理的任务: {}", taskPage.getContent());

        taskPage.getContent().forEach(task -> {

            Map var = new HashMap();
            var.put("num", 100);

            CompleteTaskPayload completeTaskPayload = new CompleteTaskPayloadBuilder()
                    .withTaskId(task.getId())
                    .withVariables(var)
                    .build();
            taskRuntime.complete(completeTaskPayload);
        });

        return "办理的任务: " + taskPage.getContent().stream().map(Task::getId).collect(Collectors.joining(","));
    }

}
