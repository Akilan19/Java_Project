package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Player;
import com.example.demo.repository.PlayerRepo;

@RestController
@RequestMapping("/player")
public class PlayerController {
	
	private PlayerRepo pr;
	public PlayerController(PlayerRepo pr)
	{
		this.pr = pr;
	}
	
	@PostMapping("/register")
	public void register(@RequestBody Player p)
	{
		pr.save(p);
	}

}
