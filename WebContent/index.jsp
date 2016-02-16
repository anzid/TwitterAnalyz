<%@ page import="fr.lhous.Connexion" %>
<%@ page import="fr.lhous.FollowersComparator" %>
<%@ page import="fr.lhous.TwitterStats" %>
<%@ page import="java.util.List" %>
<%@ page import="twitter4j.Twitter" %>
<%@ page import="twitter4j.TwitterException" %>
<%@ page import="twitter4j.User" %>
<%@ page import="twitter4j.Status" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="Chart.js"></script>
<title>Insert title here</title>
</head>
<body>
 <%
 int[] data = {600,50,20,17,5,2,45}; 
 TwitterStats t = new TwitterStats(); 
 TwitterStats twitterStats = new TwitterStats();
 Twitter twitter = twitterStats.Connect();	
 List<User> followers = twitterStats.getFollowers("BFMTV", twitter);
 for(User user: followers){ %>

 <div>followers count : <%=user.getFollowersCount() %></div>
 <div>follower          <%=user.getName()            %></div>
 <div><img alt="" src=" <%=user.getProfileImageURL()%>"></div>
<%} %>
<% List<Status> mostRetweetedTweets = twitterStats.MostRetweetedTweet("rogerfederer", twitter);
int numberOfRetweet = (int) twitterStats.NumberOfRetweetInDay(mostRetweetedTweets);
for (Status status : mostRetweetedTweets){ %>
<div>tweet : <%=status.getText() %></div>
 <%} %>
 
<div style="width: 50%">
			<canvas id="canvas" height="450" width="600"></canvas>
		</div>
<div id="canvas-holder">
			<canvas id="chart-area" width="300" height="300"/></canvas>
		</div>

<script>
	var randomScalingFactor = function(){ return Math.round(Math.random()*100)};

	var barChartData = {
		labels : ["Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche"],
		datasets : [
			{
				fillColor : "rgba(220,220,220,0.5)",
				strokeColor : "rgba(220,220,220,0.8)",
				highlightFill: "rgba(220,220,220,0.75)",
				highlightStroke: "rgba(220,220,220,1)",
				data : [<%=data[0]%>, <%=data[1]%>, <%=data[2]%>, <%=data[3]%>, <%=data[4]%>, <%=data[5]%>, <%=data[6]%>]
			},
		]

	}
	
	var pieData = [
					{
						value: <%= numberOfRetweet%>,
						color:"#F7464A",
						highlight: "#FF5A5E",
						label: "Tweet"
					},
					{
						value: <%= mostRetweetedTweets.size()%>,
						color: "#46BFBD",
						highlight: "#5AD3D1",
						label: "Retweet"
					},


				];
	
	window.onload = function(){
		var ctx = document.getElementById("canvas").getContext("2d");
		var ctx = document.getElementById("chart-area").getContext("2d");
		window.myPie = new Chart(ctx).Pie(pieData);
		window.myBar = new Chart(ctx).Bar(barChartData, {
			responsive : true
		});

	}

	</script>


</body>
</html>