package com.andrew.SRV;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.andrew.db.DBUtil;

public class TeamManagement {
	public static ArrayList<String> returnNames(ArrayList<Integer>list){
		ArrayList<String>Ranking = new ArrayList<String>();
		for (int i:list) {
			Connection connection = DBUtil.getConnection(); // establishes connection with database
			if(connection!=null) {
				try {
					String sql = "SELECT Team_Name FROM TEAM WHERE Team_ID="+i;
					PreparedStatement ps = connection.prepareStatement(sql);
					ResultSet rlt = ps.executeQuery(); 
					Ranking.add(rlt.getString("Team_Name"));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if(connection!=null) {
						DBUtil.closeConnection();
					}
				}	
			}
		}
		return Ranking;
	}
	
	public static ArrayList<ArrayList<Integer>> displayRanking() { // returns an arraylist of arraylists
		int id = DALManager.findCurrentGame();
		Connection connection = DBUtil.getConnection();
		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>(); // HashMap mapping TeamID to Points
		ArrayList<ArrayList<Integer>> leaderboard = new ArrayList<ArrayList<Integer>>();
		if(connection!=null) {
			try {
				String sql = "SELECT * FROM Game_Team_bridge WHERE Game_ID="+id;
				PreparedStatement ps;
				ps = connection.prepareStatement(sql);
				ResultSet rlt = ps.executeQuery(); 
				while(rlt.next()) {
					hm.put(rlt.getInt("Team_ID"),rlt.getInt("Team_points"));
				}
				ArrayList<Integer> temp = new ArrayList<Integer>(hm.keySet()); // temporary arry that holds the id of teams
				ArrayList<Integer> points = new ArrayList<Integer>(hm.values()); // array that holds the amount of points each team has
				ArrayList<Integer> teams = new ArrayList<Integer>(); // empty array
				Collections.sort(points); // points sorted in ascending order
				while (!temp.isEmpty()) { 
					for (int i=0;i<points.size();i++) {  // enclosed for loop to try match the teams with the points that is sorted
						for (int j=0; j<temp.size();j++){
							if (hm.get(temp.get(j))==points.get(i)) {
								teams.add(temp.get(j));
								temp.remove(j);
								break;
							}
						}
					}
				}
				leaderboard.add(teams);
				leaderboard.add(points);
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(connection!=null) {
					DBUtil.closeConnection();
				}
			}	
		}
		return leaderboard; // returns arraylist of arraylists with the first arrylist being the teams and second being points, both in 
							// ascending order of ranking 
	}

}
