package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Task;
import com.examly.springapp.repository.TaskRepository;


@Service
public class TaskService{

	@Autowired
	private TaskRepository taskRepo;

	public boolean saveTask(Task task) {
		return taskRepo.save(task) != null ? true : false;
	}

	public boolean updateTask(Task t, int taskId) {
			return taskRepo.save(t) != null ? true : false;
	}
	
	public boolean updateTaskStatus(String status , int taskId) {
		if (taskRepo.existsById(taskId)) {
			Task t= taskRepo.findById(taskId).get();
			t.setStatus(status);

		return taskRepo.save(t) != null ? true : false;
		}
		return false;
     }
	
	
	public boolean deleteTask(int id) {
		if (taskRepo.existsById(id)) {
			taskRepo.deleteById(id);
			return true;
		}
		return false;
	}

	public List<Task> getAllTask() {

		List<Task> list = taskRepo.findAll();
		return list;
	}

	public Task getTaskById(int id) {

		if (taskRepo.existsById(id)) {
			Task t = taskRepo.findById(id).get();
			return t;
		}

		return null;
	}

}
