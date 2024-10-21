package com.AuctionWeb.AuctionWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatController {

    @GetMapping("/")
    public String index() {
        return "index"; // Ensure you have an index.html in your templates
    }

    @PostMapping("/createRoom")
    public String createRoom(@RequestParam String roomId, Model model) {
        model.addAttribute("roomId", roomId);
        return "redirect:/chat/" + roomId;
    }

    @GetMapping("/chat/{roomId}")
    public String chatRoom(@PathVariable String roomId, Model model) {
        model.addAttribute("roomId", roomId);
        return "chat";
    }
}
