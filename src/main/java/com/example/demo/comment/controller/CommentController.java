package com.example.demo.comment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.comment.model.Comment;
import com.example.demo.comment.service.CommentService;

@Controller
@RequestMapping("/reply")
public class CommentController {

	@Autowired
	private CommentService service;
	
//	//��湲� 異�媛�
//	@RequestMapping("/add")
//	public String addcomment(Comment c) {
//		service.addComment(c);
//		System.out.println("add �ㅽ��");
//		return "redirect:/product/test2";
//	}
	
	//댓글 추가
	@RequestMapping(value="/add", produces = "text/plain;charset=UTF-8")
	public String addcomment(Comment c) {
		
		service.addComment(c);
		System.out.println("add 댓글");
		System.out.println(c);
		return "redirect:/products/test2?product_id="+c.getProduct_id();
	}
	
	//댓글 수정
	@GetMapping("/edit")
	public void editForm(@RequestParam("comment_id") int comment_id, Model model) {
		Comment c = service.getCommentByCId(comment_id);
		model.addAttribute("c", c);
	}
	
	
	@PostMapping(value="/edit", produces = "text/plain;charset=UTF-8")
	public String edit(Comment c) {
		service.updateComment(c);
		System.out.println("update 댓글");
		System.out.println(c);
		return "redirect:/product/test2?product_id="+c.getProduct_id();
	}
		
	//댓글 삭제
	@RequestMapping(value="/delete")
	public String delcomment(@RequestParam("comment_id") int comment_id) {
		service.delComment(comment_id);
		System.out.println("delete 댓글");
		return "redirect:/";
	}	
	
	
}
