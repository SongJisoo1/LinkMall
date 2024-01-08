package com.example.demo.comment.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
	
	//異�媛�
	void cmtInsert(Comment c);
	
	//list
	ArrayList<Comment> cmt_selectAll();
	
	List<Comment> selectByProID(int product_id);

	Comment selectByComID(int product_id);

	//����
	void cmt_update(Comment c);
	
	//����
	void cmt_delete(int comment_id);
	
	
	List<Comment> getCommentLisT(Pager pager);
	
	Long getCommentTotal(int productId);

}
