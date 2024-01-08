package com.example.demo.comment.model;

import lombok.ToString;

@ToString
public class Comment {

	private int comment_id;
	private int member_id;
	private String nickname;
	private int product_id;
	private String content;

	public Comment() {
	}

	public Comment(int comment_id, int member_id, int product_id, String nickname, String content) {
		this.comment_id = comment_id;
		this.member_id = member_id;
		this.product_id = product_id;
		this.nickname = nickname;
		this.content = content;
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public int getMember_id() {
		return member_id;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Comment [comment_id=" + comment_id + ", member_id=" + member_id + ", nickname=" + nickname
				+ ", product_id=" + product_id + ", content=" + content + "]";
	}

}
