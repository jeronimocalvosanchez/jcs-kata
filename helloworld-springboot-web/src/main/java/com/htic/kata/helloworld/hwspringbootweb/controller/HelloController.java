package com.htic.kata.helloworld.hwspringbootweb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/")
	public String index() {
		return "App is up and running";
	}

	@GetMapping("/hello")
	public String hello() {
		return "Hello, World!";
	}

	@GetMapping("/bye")
	public String bye() {
		return "Bye, World!";
	}
}