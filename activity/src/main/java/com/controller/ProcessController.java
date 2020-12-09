package com.controller;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
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
 * 处理流程相关的类
 */
@Slf4j
@RestController
@RequestMapping("/taskService")
public class ProcessController {

    private static final String PROCESS_DEFINITION_KEY = "qj";

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

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
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY, businessKey, variables);

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

        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().processDefinitionKey(PROCESS_DEFINITION_KEY).list();
        log.info("流程实例集合：{}", list);

        return "流程实例集合: " + list.stream().map(ProcessInstance::getId).collect(Collectors.joining(","));
    }

    /**
     * 查询任务
     *
     * @return java.lang.String
     * @Date 2020/12/8
     **/
    @PostMapping("query/task")
    public String queryTask() {

        List<Task> taskList = taskService.createTaskQuery().taskAssignee("salaboy").list();
        log.info("任务列表: {}", taskList);

        return "任务列表:" + taskList.stream().map(TaskInfo::getId).collect(Collectors.joining(","));
    }

    /**
     * 办理任务
     *
     * @return void
     * @Date 2020/12/8
     **/
    @PostMapping("handle")
    public String handle() {

        List<Task> taskList = taskService.createTaskQuery().taskAssignee("salaboy").list();
        log.info("待办理的任务: {}", taskList);

        taskList.forEach(task -> {

            Map var = new HashMap();
            var.put("num", 1);
            var.put("jl", "jingli");
            taskService.complete(task.getId(), var);
        });

        return "办理的任务: " + taskList.stream().map(TaskInfo::getId).collect(Collectors.joining(","));
    }

}
