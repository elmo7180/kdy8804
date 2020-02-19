package com.example.demo.vo;

import java.util.ArrayList;

public class ShowMatchesAction {
	
	public static void execute(ArrayList<MatchVO> matchVOList)
	{
		for(int i=0;i<matchVOList.size();i++)
			System.out.println(matchVOList.get(i).toString());
	}
}
