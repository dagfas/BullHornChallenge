package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeController {

    @Autowired
    MessageRepository messageRepository;

    @RequestMapping("/")
    public String listMessages(Model model){
        model.addAttribute("messages", messageRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String postForm(Model model){
        model.addAttribute("post", new Post());
        return "postForm";
    }

    @PostMapping ("/process")
    public String processForm(@Valid Post post, BindingResult result, @RequestParam("postedDate") String postedDate){
        if(result.hasErrors()){
            return "postForm";
        }

        Date date = new Date();

        try {
            date = new SimpleDateFormat("yyyy-MM-d").parse(postedDate);
        }

        catch(Exception e){
            e.printStackTrace();
        }

        post.setPostedDate(date);
        messageRepository.save(post);
        return "redirect:/";
    }

    @RequestMapping ("/content/{id}")
    public String showContent(@PathVariable("id") long id, Model model) {
        model.addAttribute("post", messageRepository.findById(id).get());
        return "display";
    }

    @RequestMapping("/update/{id}")
    public String updatePost(@PathVariable("id") long id, Model model) {
        model.addAttribute("post", messageRepository.findById(id).get());
        return "postForm";
    }

    @RequestMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") long id) {
        messageRepository.deleteById(id);
        return "redirect:/";
    }
}
