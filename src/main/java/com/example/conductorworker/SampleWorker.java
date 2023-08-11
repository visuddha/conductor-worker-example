package com.example.conductorworker;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskResult.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleWorker implements Worker {

    Logger logger = LoggerFactory.getLogger(Main.class);

    private final String taskDefName;

    public SampleWorker(String taskDefName) {
        this.taskDefName = taskDefName;
    }

    @Override
    public String getTaskDefName() {
        return taskDefName;
    }

    @Override
    public TaskResult execute(Task task) {
        TaskResult result = new TaskResult(task);
        result.setStatus(Status.COMPLETED);

        if ( "get_starting_params".compareTo( task.getReferenceTaskName() ) == 0 ) {
			processGetStartingParams( task, result );
	    } 
			
		return result;
    }

    private void processGetStartingParams(Task task,TaskResult result) {
		
		String confStarter = (String) task.getInputData().get("start_id") + task.getTaskDefName();
		
		logger.info("-----\n");
		logger.info("Running task: "+task.getTaskDefName());
		logger.info("Input: ");
		logger.info("Starter Param:   {}", (String) task.getInputData().get("start_id") );
		logger.info("Output: ");
		logger.info("Starter Configuration: {}", confStarter );
		logger.info("-----\n");
		
		//Register the output of the task
		result.getOutputData().put("conf_starter", confStarter);
	}
}