package com.example.demo.rest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.example.demo.vo.MatchVO;
import com.example.demo.vo.ParticipantVO;
import com.example.demo.vo.TeamStatsVO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class APIConnection {

	private static final String GET_EID_BY_NAME = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/";
	private static final String GET_MATCH_ID_BY_EID = "https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/";
	private static final String QUEUE_ID = "?queue=420"; // 2020-02-19 : Rank : 420
	private static final String END_INDEX = "&endIndex=10";
	private static final String GET_MATCHVO_BY_MATCHID=
			"https://kr.api.riotgames.com/lol/match/v4/matches/";
	
	private static JsonObject jsonObject;
	private static JsonArray jsonArray;
	private static InputStreamReader inputStreamReader;

	public static String GetEncryptedIdByName(String name) {
		String encryptedId = null;
		HttpURLConnection conn = null;
		
		try{
			name = URLEncoder.encode(name, "UTF-8");
			URL url = new URL(GET_EID_BY_NAME + name);
			conn = (HttpURLConnection) url.openConnection();
			HttpRequestManager.addHeaders(conn);
			inputStreamReader = new InputStreamReader(conn.getInputStream());
			jsonObject = (JsonObject) JsonParser.parseReader(inputStreamReader);
			encryptedId = jsonObject.get("accountId").getAsString();
			
		}catch(IOException e) {
			e.printStackTrace();
			encryptedId = "failed";
		}finally {
			HttpRequestManager.close(conn, inputStreamReader);
		}
		return encryptedId;
	}

	public static ArrayList<Long> getGameIdListByEid(String eId) {
		ArrayList<Long> gameIdList = new ArrayList<Long>();
		HttpURLConnection conn = null;
		
		try {
			eId = URLEncoder.encode(eId, "UTF-8");
			URL url = new URL(GET_MATCH_ID_BY_EID + eId + QUEUE_ID + END_INDEX);

			conn = (HttpURLConnection) url.openConnection();
			HttpRequestManager.addHeaders(conn);
			inputStreamReader = new InputStreamReader(conn.getInputStream());
			jsonObject = (JsonObject) JsonParser.parseReader(inputStreamReader);
			jsonArray = jsonObject.get("matches").getAsJsonArray();

			int size = jsonArray.size();

			for (int i = 0; i < size; i++) {
				long gameId = jsonArray.get(i).getAsJsonObject().get("gameId").getAsLong();
				gameIdList.add(gameId);
			}

			for (int i = 0; i < size; i++) {
				jsonArray.remove(size - i - 1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			HttpRequestManager.close(conn, inputStreamReader);
		}
		
		return gameIdList;
	}

	public static ArrayList<MatchVO> getMatchVOListByGameId(ArrayList<Long> gameIdList) {
		ArrayList<MatchVO> matchVOList = new ArrayList<MatchVO>();
		
		HttpURLConnection conn = null;
		try{
			MatchVO matchVO = new MatchVO();
			for(int i=0;i<gameIdList.size();i++) {
				URL url = new URL(GET_MATCHVO_BY_MATCHID+gameIdList.get(i));
				conn =(HttpURLConnection)url.openConnection();
				HttpRequestManager.addHeaders(conn);
				inputStreamReader = new InputStreamReader(conn.getInputStream());
				jsonObject = (JsonObject) JsonParser.parseReader(inputStreamReader);
				matchVO.setMatchId(jsonObject.get("gameId").getAsLong());
				matchVO.setGameDuration(jsonObject.get("gameDuration").getAsLong());
				
				
				JsonArray teamJsonList = jsonObject.get("teams").getAsJsonArray();
				ArrayList<TeamStatsVO> teamStatsVOList = getTSVOListByJson(teamJsonList); 
				matchVO.setTeamStatsVOList(teamStatsVOList);
				JsonArray pIdentArr = jsonObject.get("participantIdentities").getAsJsonArray();
				ArrayList<String> summonerNameList = getSummonerNameList(pIdentArr);
				
				JsonArray partyJsonList = jsonObject.get("participants").getAsJsonArray();
				ArrayList<ParticipantVO> participantVOList = getParticipantsByJson(partyJsonList,summonerNameList);		
				
				matchVO.setParticipantsVOList(participantVOList);
				matchVOList.add(matchVO);
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}finally {
			HttpRequestManager.close(conn, inputStreamReader);
		}
		//stack queue graph tree linked-list array 
		return matchVOList;
	}
	
	private static ArrayList<String> getSummonerNameList(JsonArray pIdentArr){
		ArrayList<String> summonerNameList = new ArrayList<String>();
		for(int i =0;i<pIdentArr.size();i++)
		{
			JsonObject pIdent = pIdentArr.get(i).getAsJsonObject();
			JsonObject playerJson = pIdent.get("player").getAsJsonObject();
			String summonerName = playerJson.get("summonerName").getAsString();
			summonerNameList.add(summonerName);
		}
		
		return summonerNameList;
	}
	private static ArrayList<TeamStatsVO> getTSVOListByJson(JsonArray jsonArray){
		ArrayList<TeamStatsVO> teamStatsVOList = new ArrayList<TeamStatsVO>();
		
		for(int i=0;i<jsonArray.size();i++)
		{
			JsonObject teamStatsObj = jsonArray.get(i).getAsJsonObject();
			TeamStatsVO teamStatsVO = new TeamStatsVO(); 
			teamStatsVO.setDragonKills(teamStatsObj.get("dragonKills").getAsInt());
			teamStatsVO.setBaronKills(teamStatsObj.get("baronKills").getAsInt());
			teamStatsVO.setInhibitorKills(teamStatsObj.get("towerKills").getAsInt());
			teamStatsVO.setInhibitorKills(teamStatsObj.get("inhibitorKills").getAsInt());
			teamStatsVOList.add(teamStatsVO);
		}
		
		return teamStatsVOList;
	}
	
	private static ArrayList<ParticipantVO> getParticipantsByJson(JsonArray partyJsonList,ArrayList<String> summonerNameList){
		ArrayList<ParticipantVO> participantVOList = new ArrayList<ParticipantVO>();
		
		for(int i=0;i<partyJsonList.size();i++)
		{
			JsonObject participantJson = partyJsonList.get(i).getAsJsonObject();
			JsonObject statsJson = participantJson.get("stats").getAsJsonObject();
			ParticipantVO participantVO = new ParticipantVO();
			JsonObject timelineJson = participantJson.get("timeline").getAsJsonObject();
			
			participantVO.setSummonerName(summonerNameList.get(i)); // encoding 변경
			System.out.println("summonerName: "+summonerNameList.get(i));
			participantVO.setAssists(statsJson.get("assists").getAsInt());
			participantVO.setChampionId(participantJson.get("championId").getAsInt());
			participantVO.setDeaths(statsJson.get("deaths").getAsInt());
			participantVO.setItem0(statsJson.get("item0").getAsInt());
			participantVO.setItem1(statsJson.get("item1").getAsInt());
			participantVO.setItem2(statsJson.get("item2").getAsInt());
			participantVO.setItem3(statsJson.get("item3").getAsInt());
			participantVO.setItem4(statsJson.get("item4").getAsInt());
			participantVO.setItem5(statsJson.get("item5").getAsInt());
			participantVO.setItem6(statsJson.get("item6").getAsInt());
			participantVO.setKills(statsJson.get("kills").getAsInt());
			participantVO.setLane(timelineJson.get("lane").getAsString());
			participantVO.setTotalHeal(statsJson.get("totalHeal").getAsInt());
			participantVO.setTotalDamageDealtToChampions(statsJson.get("totalDamageDealtToChampions").getAsInt());
			participantVO.setTotalDamageTaken(statsJson.get("totalDamageTaken").getAsInt());
			
			participantVOList.add(participantVO);
		}
		return participantVOList;
	}
}
