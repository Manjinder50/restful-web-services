package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {

	@Autowired
	private UserDaoService service;
	//GET  /users
	//Retrieve all users
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return service.findAll();	
	}
	
	//Retrieve User(int id)
	//GET /users/{id}
	@GetMapping("/users/{id}")
	public Resource<User> retriveUser(@PathVariable("id") int id){
		User user = service.findOne(id);
		
		if(user==null){
			throw new UserNotFoundException("id-"+id);
		}
		//"all-users",SERVER_PATH + "/users"
		//HATEOAS
		Resource<User> resource = new Resource<User>(user);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		resource.add(linkTo.withRel("all-users"));
		return resource;
		
	}
	
	//input-details of the user
	//output-created and return the created URI
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user){
		User savedUser = service.save(user);
		//Return the status that the user is created
		//And set the URI of the created user into the 
		//response
		
		// /users/4- the ServletUriComponentBuilder provides the method
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
												  .path("/{id}").buildAndExpand(savedUser.getId())
												  .toUri();
		return ResponseEntity.created(location).build();	
	}
	
	//Delete User(int id)
		//GET /users/{id}
		@DeleteMapping("/users/{id}")
		public void deleteUser(@PathVariable("id") int id) throws UserNotFoundException{
			User user = service.deleteById(id);
			if(user==null){
				throw new UserNotFoundException("id-"+id);
			}
		
			
		}
	
}
