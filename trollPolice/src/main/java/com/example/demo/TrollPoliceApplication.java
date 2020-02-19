package com.example.demo;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.util.StopWatch;

import com.example.demo.rest.APIConnection;
import com.example.demo.vo.MatchVO;
import com.example.demo.vo.ShowMatchesAction;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class TrollPoliceApplication {

	public static void main(String[] args) throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		SpringApplication app = new SpringApplication(TrollPoliceApplication.class);
		app.setWebApplicationType(WebApplicationType.NONE);
		app.run(args);
		String eId = APIConnection.GetEncryptedIdByName("2011151003");
		
		System.out.println("debug :" + eId);
		ArrayList<Long> gameIdList = APIConnection.getGameIdListByEid(eId);

		for (int i = 0; i < 10; i++) {
			System.out.println("[gameId]" + gameIdList.get(i));
		}

		ArrayList<MatchVO> matchVOList = APIConnection.getMatchVOListByGameId(gameIdList);
		ShowMatchesAction.execute(matchVOList);
		System.out.println(stopWatch.getTotalTimeMillis());
		stopWatch.stop();
	}
}
