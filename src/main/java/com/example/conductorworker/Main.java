package com.example.conductorworker;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.conductor.client.automator.TaskRunnerConfigurer;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.worker.Worker;

public class Main {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(Main.class);

        TaskClient taskClient = new TaskClient();
        taskClient.setRootURI("http://localhost:8080/api/"); // Point this to the server API

        int threadCount =
                2; // number of threads used to execute workers.  To avoid starvation, should be
        // same or more than number of workers

        Worker worker1 = new SampleWorker("get_starting_params");

        

        // Create TaskRunnerConfigurer
        TaskRunnerConfigurer configurer =
                new TaskRunnerConfigurer.Builder(taskClient, Arrays.asList(worker1))
                        .withThreadCount(threadCount)
                        .build();

        // Start the polling and execution of tasks
        logger.info("Initiating Worker Manager...");
        configurer.init();
    }
}