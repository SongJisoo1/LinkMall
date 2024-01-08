package com.example.demo.comment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.alarm.service.AlarmService;
import com.example.demo.comment.model.Comment;
import com.example.demo.comment.model.CommentMapper;
import com.example.demo.comment.model.Pager;

@Service
public class CommentService {

	@Autowired
	private CommentMapper mapper;
	
	@Autowired
	private AlarmService alarmService;
	
	//�깅�
	public void addComment (Comment c){
		mapper.cmtInsert(c);
		List<String> likedMembers = alarmService.getAllLikedMember(c.getProduct_id());
		alarmService.createdAlarm("COMMENT", c.getProduct_id(), likedMembers);
	}
	
	//���대��濡� 寃���
	public Comment getCommentByCId (int comment_id){
		return mapper.selectByComID(comment_id);
	}
	
	public List<Comment> getCommentByPId (int product_id){
		return mapper.selectByProID(product_id);
	}
	
	//由ъ�ㅽ��
	public ArrayList<Comment> getCommentAll (){
		return mapper.cmt_selectAll();
	}	
	
	//����
	public void updateComment (Comment c){
		mapper.cmt_update(c);
	}	
	
	//����
	public void delComment (int comment_id){
		mapper.cmt_delete(comment_id);
	}
	
	
	//댓글
	public List<Comment> getCommentLisT(Pager pager, int product_id){
		Long totalCount = mapper.getCommentTotal(product_id);
		pager.setRow();
		pager.setNum(totalCount);
		
		return mapper.getCommentLisT(pager);
	}
	

}
