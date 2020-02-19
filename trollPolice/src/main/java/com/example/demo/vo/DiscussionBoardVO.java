package com.example.demo.vo;



public class DiscussionBoardVO extends BoardVOForm{
	private MatchVO matchVO;
	private int up;
	private int down;
	
	
	public MatchVO getMatchVO() {
		return matchVO;
	}
	public void setMatchVO(MatchVO matchVO) {
		this.matchVO = matchVO;
	}
	public int getUp() {
		return up;
	}
	public void setUp(int up) {
		this.up = up;
	}
	public int getDown() {
		return down;
	}
	public void setDown(int down) {
		this.down = down;
	}
	
}
