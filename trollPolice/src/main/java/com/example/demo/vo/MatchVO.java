package com.example.demo.vo;

import java.util.ArrayList;

public class MatchVO {
	
	private long matchId;
	private ArrayList<TeamStatsVO> teamStatsVOList;
	private ArrayList<ParticipantVO> participantVOList;
	private long gameDuration;
	
	
	public long getMatchId() {
		return matchId;
	}
	public void setMatchId(long matchId) {
		this.matchId = matchId;
	}
	public ArrayList<TeamStatsVO> getTeamStatsVOList() {
		return teamStatsVOList;
	}
	public void setTeamStatsVOList(ArrayList<TeamStatsVO> teamStatsVOList) {
		this.teamStatsVOList = teamStatsVOList;
	}
	public ArrayList<ParticipantVO> getParticipantsVOList() {
		return participantVOList;
	}
	public void setParticipantsVOList(ArrayList<ParticipantVO> participantVOList) {
		this.participantVOList = participantVOList;
	}
	public long getGameDuration() {
		return gameDuration;
	}
	public void setGameDuration(long gameDuration) {
		this.gameDuration = gameDuration;
	}
	
	@Override
	public String toString() {
		return "MatchVO [matchId=" + matchId + ", teamStatsVOList=" + teamStatsVOList.toString() + ", participantVOList="
				+ participantVOList.toString() + ", gameDuration=" + gameDuration + "]";
	}
	
	
}
