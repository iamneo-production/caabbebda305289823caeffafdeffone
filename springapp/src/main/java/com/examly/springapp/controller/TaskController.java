package com.examly.springapp.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Task;
import com.examly.springapp.service.TaskService;


@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@PostMapping
	public ResponseEntity<Boolean> save(@RequestBody Task task) {

		boolean s = taskService.saveTask(task);
		if (s) {
			return new ResponseEntity<>(s, HttpStatus.OK);
		}
		return new ResponseEntity<>(s, HttpStatus.ALREADY_REPORTED);
	}

	@PutMapping("/{taskId}")
	public ResponseEntity<Boolean> update(@RequestBody Task task, @PathVariable int taskId) {

		boolean s = taskService.updateTask(task, taskId);
		if (s) {
			return new ResponseEntity<>(s, HttpStatus.OK);
		}
		return new ResponseEntity<>(s, HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/{taskId}/status")
	public ResponseEntity<Boolean> updateStatus(@RequestParam String status, @PathVariable int taskId) {

		boolean s = taskService.updateTaskStatus(status, taskId);
		if (s) {
			return new ResponseEntity<>(s, HttpStatus.OK);
		}
		return new ResponseEntity<>(s, HttpStatus.NOT_FOUND);
	}
	

	@DeleteMapping("/{taskId}")
	public ResponseEntity<Boolean> delete(@PathVariable int taskId) {

		boolean s = taskService.deleteTask(taskId);
		if (s) {
			return new ResponseEntity<>(s, HttpStatus.OK);
		}
		return new ResponseEntity<>(s, HttpStatus.NOT_FOUND);
	}

	@GetMapping
	public ResponseEntity<List<Task>> getAll() {

		List<Task> tasks = taskService.getAllTask();
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}

	@GetMapping("/{taskId}")
	public ResponseEntity<Task> getById(@PathVariable int taskId) {

		Task task = taskService.getTaskById(taskId);
		return new ResponseEntity<>(task, HttpStatus.OK);
	}

}
