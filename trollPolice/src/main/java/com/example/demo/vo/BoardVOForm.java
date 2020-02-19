package com.example.demo.vo;

import java.util.Date;

public class BoardVOForm {
	private String email;
	private Date writeDate;
	private String content;
	private int discBoardId;
	private int readcount;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getDiscBoardId() {
		return discBoardId;
	}
	public void setDiscBoardId(int discBoardId) {
		this.discBoardId = discBoardId;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	
	
}
