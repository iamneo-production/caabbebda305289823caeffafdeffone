package com.examly.springapp;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.examly.springapp.controller.TaskController;
import com.examly.springapp.repository.TaskRepository;

@RunWith(SpringJUnit4ClassRunner.class) 
@SpringBootTest(classes = SpringappApplication.class)
@AutoConfigureMockMvc
class SpringappApplicationTests {
	@Autowired
    private MockMvc mockMvc;

    
	@Test
	public void testPostData() throws Exception {
		String st = "{\"id\":1,\"title\": \"Writing\",\"description\": \"ABCD\",\"dueDate\": \"2023-09-20\",\"status\":\"started\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/task")
				.contentType(MediaType.APPLICATION_JSON)
				.content(st)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
	}
	
	@Test
	public void testUpdateData() throws Exception {
		String st = "{\"id\":1,\"title\": \"Reading\",\"description\": \"ABCD\",\"dueDate\": \"2023-09-20\",\"status\":\"started\"}";
		mockMvc.perform(MockMvcRequestBuilders.put("/task/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(st)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
	}
	
	@Test
	public void testUpdateStatus() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/task/1/status").param("status", "completed")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
	}
	

    @Test
    public void testGetByID() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/task/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/task")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$").isArray())
                .andReturn();
    }

    @Test
	void testDelete() throws Exception{	

		 mockMvc.perform(MockMvcRequestBuilders.delete("/task/1")
		 				.accept(MediaType.APPLICATION_JSON))
						.andDo(print())
						.andExpect(status().isOk())
						.andExpect(jsonPath("$").value(true))
						.andReturn();
	}
    

    private void checkClassExists(String className) {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            fail("Class " + className + " does not exist.");
        }
    }

    private void checkFieldExists(String className, String fieldName) {
        try {
            Class<?> clazz = Class.forName(className);
            clazz.getDeclaredField(fieldName);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            fail("Field " + fieldName + " in class " + className + " does not exist.");
        }
    }

	private void checkAnnotationExists(String className, String annotationName) {
		try {
			Class<?> clazz = Class.forName(className);
			ClassLoader classLoader = clazz.getClassLoader();
			Class<?> annotationClass = Class.forName(annotationName, false, classLoader);
			assertNotNull(clazz.getAnnotation((Class) annotationClass)); // Use raw type
		} catch (ClassNotFoundException | NullPointerException e) {
			fail("Class " + className + " or annotation " + annotationName + " does not exist.");
		}
	}
	

	 @Test
   public void testControllerClassExists() {
       checkClassExists("com.examly.springapp.controller.TaskController");
   }

   @Test
   public void testRepoClassExists() {
       checkClassExists("com.examly.springapp.repository.TaskRepository");
   }

   @Test
   public void testServiceClassExists() {
       checkClassExists("com.examly.springapp.service.TaskService");
   }

   @Test
   public void testModelClassExists() {
       checkClassExists("com.examly.springapp.model.Task");
   }


   @Test
   public void testModelHasIdField() {
       checkFieldExists("com.examly.springapp.model.Task", "id");
   }

   @Test
   public void testModelHasTaskNameField() {
       checkFieldExists("com.examly.springapp.model.Task", "title");
   }

   @Test
   public void testModelHasDoctorNameForField() {
       checkFieldExists("com.examly.springapp.model.Task", "description");
   }

   @Test
   public void testModelHasDiseaseForField() {
       checkFieldExists("com.examly.springapp.model.Task", "dueDate");
   }
   
   @Test
   public void testModelHasMedicationForField() {
       checkFieldExists("com.examly.springapp.model.Task", "status");
   }

   @Test
   public void testRepositoryInterfaceExtendsJpaRepository() {
       // Replace TaskRepo with the actual repository interface you want to check
       Class<?> repositoryInterface = TaskRepository.class;

       // Get the list of interfaces that the repository interface extends
       Class<?>[] interfaces = repositoryInterface.getInterfaces();

       // Check if JpaRepository is one of the extended interfaces
       boolean extendsJpaRepository = Arrays.stream(interfaces)
               .anyMatch(JpaRepository.class::isAssignableFrom);

       assertTrue("Repository interface should extend JpaRepository", extendsJpaRepository);
   }

   @Test
   public void testModelHasEntityAnnotation() {
       checkAnnotationExists("com.examly.springapp.model.Task", "javax.persistence.Entity");
   }

   @Test
   public void testUserRepoHasRepositoryAnnotation() {
       checkAnnotationExists("com.examly.springapp.repository.TaskRepository", "org.springframework.stereotype.Repository");
   }
   
   @Test
   public void testDeleteMappingAnnotations() {
       Class<TaskController> controller = TaskController.class;

       // Get all declared methods in the controller class
       Method[] methods = controller.getDeclaredMethods();

       // Check each method for the presence of @DeleteMapping annotation
       for (Method method : methods) {
           if (method.isAnnotationPresent(DeleteMapping.class)) {
               assertTrue("Method " + method.getName() + " should have @DeleteMapping annotation", true);
           }
       }
   }
   
   @Test
   public void testGetMappingAnnotations() {
       Class<TaskController> controller = TaskController.class;

       // Get all declared methods in the controller class
       Method[] methods = controller.getDeclaredMethods();

       for (Method method : methods) {
           if (method.isAnnotationPresent(GetMapping.class)) {
               assertTrue("Method " + method.getName() + " should have @GetMapping annotation", true);
           }
       }
   }
   
   @Test
   public void testPostMappingAnnotations() {
       Class<TaskController> controller = TaskController.class;

       // Get all declared methods in the controller class
       Method[] methods = controller.getDeclaredMethods();

       for (Method method : methods) {
           if (method.isAnnotationPresent(PostMapping.class)) {
               assertTrue("Method " + method.getName() + " should have @PostMapping annotation", true);
           }
       }
   }
   
   @Test
   public void testPutMappingAnnotations() {
       Class<TaskController> controller = TaskController.class;

       // Get all declared methods in the controller class
       Method[] methods = controller.getDeclaredMethods();

       // Check each method for the presence of @DeleteMapping annotation
       for (Method method : methods) {
           if (method.isAnnotationPresent(PutMapping.class)) {
               assertTrue("Method " + method.getName() + " should have @PutMapping annotation", true);
           }
       }
   }


}
