package com.in28minutes.rest.webservices.restfulwebservices.helloWorld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@Autowired
	private MessageSource messageSource;
	//GET
	//URI /hello-world
	//method - returns "Hello World
	@RequestMapping(path="/hello-world",method=RequestMethod.GET)
	public String helloWorld()
	{
		return "Hello World";
	}

	//GET
	//URI /hello-world-bean
	//method - returns "Hello World
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean()
	{
		return new HelloWorldBean("Hello World Bean");
	}

	//GET with path variable
	//URI /hello-world-bean
	//method - returns "Hello World
	@GetMapping(path="/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name)
	{
		return new HelloWorldBean(String.format("Hello World, %s",name));			
	}
	
	@GetMapping(path="/hello-world-internationalized")
	public String helloWorldInternationalized(@RequestHeader(name="Accept-Language",required=false) Locale locale)
	{
		return messageSource.getMessage("good.morning.message",null,locale);			
	}
}
