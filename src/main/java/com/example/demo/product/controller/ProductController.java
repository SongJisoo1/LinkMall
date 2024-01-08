package com.example.demo.product.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.alarm.model.Alarm;
import com.example.demo.alarm.service.AlarmService;
import com.example.demo.comment.model.Comment;
import com.example.demo.comment.model.Pager;
import com.example.demo.comment.service.CommentService;
import com.example.demo.member.dto.LoginSessionValue;
import com.example.demo.product.dto.ProductCloseDto;
import com.example.demo.product.dto.ProductDeleteDto;
import com.example.demo.product.dto.ProductEditDto;
import com.example.demo.product.dto.ProductRegisterDto;
import com.example.demo.product.dto.ProductResponseDto;
import com.example.demo.product.model.Product;
import com.example.demo.product.model.ProductMapper;
import com.example.demo.product.service.ProductService;
import com.example.demo.utils.SessionConstant;
import com.example.demo.utils.dto.PagingResponse;
import com.example.demo.utils.dto.SearchDto;
import com.example.demo.utils.s3.S3UploadService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ProductController {
	
	private final ProductService productService;
	private final AlarmService alarmService;
	private final CommentService commentService;
	private final S3UploadService s3;
	private final ProductMapper mapper;
	
	@GetMapping("/")
	public String indexPage() {
		return "redirect:/products";
	}
	
	@GetMapping("/products/add")
	public String productAddForm() {
		return "product/productRegister";
	}
	
	@GetMapping("/products")
	public String getAllProducts(@ModelAttribute SearchDto params, HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		int memberId;
		
		if(session == null ||
		   (LoginSessionValue)session.getAttribute(SessionConstant.LOGIN_MEMBER) == null
		) {
			memberId = 0;
		} else {
			LoginSessionValue value = (LoginSessionValue)session.getAttribute(SessionConstant.LOGIN_MEMBER);
			memberId = value.getMemberId();
		}
		
		
		PagingResponse<ProductResponseDto> products = productService.getAllProducts(params, memberId);
		model.addAttribute("products", products);
		
		return "index";
	}
	
	@GetMapping("/products/search")
	public String getFilteredProducts(@ModelAttribute SearchDto params, HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		int memberId;
		
		if(session == null ||
		   (LoginSessionValue)session.getAttribute(SessionConstant.LOGIN_MEMBER) == null
		) {
			memberId = 0;
		} else {
			LoginSessionValue value = (LoginSessionValue)session.getAttribute(SessionConstant.LOGIN_MEMBER);
			memberId = value.getMemberId();
		}
		
		PagingResponse<ProductResponseDto> products = productService.getAllProducts(params, memberId);
		model.addAttribute("products", products);
		
		return "product/search";
		
	}
	
	@PostMapping("/products")
	@ResponseBody
	public void productRegister(@ModelAttribute ProductRegisterDto dto, HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		LoginSessionValue value = (LoginSessionValue)session.getAttribute(SessionConstant.LOGIN_MEMBER);

		productService.insertProduct(dto, value.getMemberId());
	}
	
	@GetMapping(value ="/products/test2")
	public ModelAndView getComment(
			@RequestParam(value = "product_id") int product_id,
			@RequestParam(value = "alarm_id", required = false) Integer alarmId,
			HttpServletRequest req,
			Pager pager 
	){
		
		ModelAndView mv = new ModelAndView();
		HttpSession session = req.getSession(false);
		int memberId;
		
		if(session == null ||
		   (LoginSessionValue)session.getAttribute(SessionConstant.LOGIN_MEMBER) == null
		) {
			memberId = 0;
		} else {
			LoginSessionValue value = (LoginSessionValue)session.getAttribute(SessionConstant.LOGIN_MEMBER);
			memberId = value.getMemberId();
			
			if(alarmId != null) {
				Alarm alarm = new Alarm();
				alarm.setAlarmId(alarmId);
				alarm.setIsRead(1);
				
				alarmService.updateReadstate(alarm);
			}
		}
		
		Product p = productService.getProductById(product_id, memberId);
		productService.updateViewCnt(product_id);
		
		mv.addObject("p", p);
		
		List<Comment> list = commentService.getCommentLisT(pager, product_id);
		
		mv.addObject("list",list);
		mv.addObject("pager",pager);
		
		mv.setViewName("product/detail");
		
		return mv;
	}
	
	@PostMapping("/products/edit")
	@ResponseBody
	public void editProduct(@ModelAttribute ProductEditDto dto) {
		productService.updateState(dto);
	}
	
	@DeleteMapping("/products/delete")
	@ResponseBody
	public void deleteProduct(@ModelAttribute ProductDeleteDto dto) {
		productService.deleteProduct(dto);
	}
	
	@PostMapping("/products/close")
	@ResponseBody
	public void productsClose(@ModelAttribute ProductCloseDto dto) {
		productService.productsClose(dto);
	}

}
