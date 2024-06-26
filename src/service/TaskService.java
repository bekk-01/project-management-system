package service;

import model.Task;
import repository.TaskRepository;
import repository.UserRepository;


import java.util.ArrayList;
import java.util.Objects;


import java.util.UUID;


public class TaskService extends BaseService<Task, TaskRepository> {

    private static final TaskService taskService = new TaskService();

    public static TaskService getInstance() {
        return taskService;
    }

    private TaskService() {
        super(TaskRepository.getInstance());
    }

    public void deleteTaskForEmployer(UUID id) {
        repository.deleteTaskForEmployer(id);
    }

    @Override
    public boolean check(Task task) {
        return false;
    }

    public ArrayList<Task> getTasksByProjectId(UUID id) {
        return repository.getTasksByProjectId(id);
    }


    public ArrayList<Task>statusCreated(){
        return repository.statusCreated();
    }
}
