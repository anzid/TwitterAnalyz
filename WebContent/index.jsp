<%@ page import="fr.lhous.Connexion" %>
<%@ page import="fr.lhous.FollowersComparator" %>
<%@ page import="fr.lhous.TwitterStats" %>
<%@ page import="java.util.List" %>
<%@ page import="twitter4j.Twitter" %>
<%@ page import="twitter4j.TwitterException" %>
<%@ page import="twitter4j.User" %>
<%@ page import="twitter4j.Status" %>
<%@ page import="java.util.stream.IntStream" %>

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
 String twAccountName = "cnn";
 List<User> followers = twitterStats.getTopTenFollowers(twAccountName, twitter);
 for(User user: followers){ %>

 <div>Le nombre de followers : <%=user.getFollowersCount() %></div>
 <div>          <%=user.getName()            %></div>
 <div><img alt="" src=" <%=user.getProfileImageURL()%>"></div>
<%} %>
<% List<Status> mostRetweetedTweets = twitterStats.MostRetweetedTweet("cnn", twitter);
//int numberOfRetweet = (int) twitterStats.NumberOfRetweetInDay(mostRetweetedTweets);
int numberOftweetsAndRetweets[][][] = new int[2][7][24]; 
List<Status> listOfTweets = twitterStats.getTweets("cnn",twitter);
numberOftweetsAndRetweets = twitterStats.NumberOfTweetsAndRetweets(listOfTweets, twitter);
for (Status status : mostRetweetedTweets){ %>
<div>tweet : <%=status.getText() %></div>
 <%} %>
 
<div style="width: 50%">
			<canvas id="canvas_tweetPerDay" height="450" width="600"></canvas>
		</div>
		<div style="width: 50%">
			<canvas id="canvas_tweetPerHour" height="450" width="600"></canvas>
		</div>
		<div style="width:30%">
			<div>
				<canvas id="canvas_retweetPerDay" height="450" width="600"></canvas>
			</div>
		</div>
		<div style="width:30%">
			<div>
				<canvas id="canvas_retweetPerHour" height="450" width="600"></canvas>
			</div>
		</div>
<div id="canvas-holder">
			<canvas id="canvas_retweetInTweets" width="300" height="300"/></canvas>
		</div>

<script>

	var barChartData = {
		labels : ["Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche"],
		datasets : [
			
			{
				fillColor : "rgba(220,220,220,0.5)",
				strokeColor : "rgba(220,220,220,0.8)",
				highlightFill: "rgba(220,220,220,0.75)",
				highlightStroke: "rgba(220,220,220,1)",
				data : [<%=twitterStats.NumberOfTweetInDay(twAccountName, twitter, numberOftweetsAndRetweets, 1)%>, 
						<%=twitterStats.NumberOfTweetInDay(twAccountName, twitter, numberOftweetsAndRetweets, 2)%>, 
						<%=twitterStats.NumberOfTweetInDay(twAccountName, twitter, numberOftweetsAndRetweets, 3)%>, 
						<%=twitterStats.NumberOfTweetInDay(twAccountName, twitter, numberOftweetsAndRetweets, 4)%>, 
						<%=twitterStats.NumberOfTweetInDay(twAccountName, twitter, numberOftweetsAndRetweets, 5)%>, 
						<%=twitterStats.NumberOfTweetInDay(twAccountName, twitter, numberOftweetsAndRetweets, 6)%>, 
						<%=twitterStats.NumberOfTweetInDay(twAccountName, twitter, numberOftweetsAndRetweets, 7)%>]
			},		
		]
	}
	
	var barChartData2 = {
			labels : ["0h","1h","2h","3h","4h","5h","6h","7h","8h","9h","10h","11h","12h",
			          "13h","14h","15h","16h","17h","18h","19h","20h","21h","22h","23h","24h"],
			datasets : [
				
				{
					fillColor : "rgba(220,220,220,0.5)",
					strokeColor : "rgba(220,220,220,0.8)",
					highlightFill: "rgba(220,220,220,0.75)",
					highlightStroke: "rgba(220,220,220,1)",
					data : [<%=numberOftweetsAndRetweets[0][3][0]%>, 
							<%=numberOftweetsAndRetweets[0][3][1]%>, 
							<%=numberOftweetsAndRetweets[0][3][2]%>, 
							<%=numberOftweetsAndRetweets[0][3][3]%>, 
							<%=numberOftweetsAndRetweets[0][3][4]%>, 
							<%=numberOftweetsAndRetweets[0][3][5]%>, 
							<%=numberOftweetsAndRetweets[0][3][6]%>,
							<%=numberOftweetsAndRetweets[0][3][7]%>, 
							<%=numberOftweetsAndRetweets[0][3][8]%>, 
							<%=numberOftweetsAndRetweets[0][3][9]%>, 
							<%=numberOftweetsAndRetweets[0][3][10]%>, 
							<%=numberOftweetsAndRetweets[0][3][11]%>, 
							<%=numberOftweetsAndRetweets[0][3][12]%>, 
							<%=numberOftweetsAndRetweets[0][3][13]%>,
							<%=numberOftweetsAndRetweets[0][3][14]%>, 
							<%=numberOftweetsAndRetweets[0][3][15]%>, 
							<%=numberOftweetsAndRetweets[0][3][16]%>, 
							<%=numberOftweetsAndRetweets[0][3][17]%>, 
							<%=numberOftweetsAndRetweets[0][3][18]%>,
							<%=numberOftweetsAndRetweets[0][3][19]%>, 
							<%=numberOftweetsAndRetweets[0][3][20]%>, 
							<%=numberOftweetsAndRetweets[0][3][21]%>, 
							<%=numberOftweetsAndRetweets[0][3][22]%>, 
							<%=numberOftweetsAndRetweets[0][3][23]%>						
							]
				},			
			]
		}
	
	var lineChartData = {
			labels : ["Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche"],
			datasets : [
				{
					label: "Nombre de retweet par jour",
					fillColor : "rgba(220,220,220,0.2)",
					strokeColor : "rgba(220,220,220,1)",
					pointColor : "rgba(220,220,220,1)",
					pointStrokeColor : "#fff",
					pointHighlightFill : "#fff",
					pointHighlightStroke : "rgba(220,220,220,1)",
					data : [<%=twitterStats.NumberOfRetweetInDay(twAccountName, twitter, numberOftweetsAndRetweets, 1)%>, 
							<%=twitterStats.NumberOfRetweetInDay(twAccountName, twitter, numberOftweetsAndRetweets, 2)%>, 
							<%=twitterStats.NumberOfRetweetInDay(twAccountName, twitter, numberOftweetsAndRetweets, 3)%>, 
							<%=twitterStats.NumberOfRetweetInDay(twAccountName, twitter, numberOftweetsAndRetweets, 4)%>, 
							<%=twitterStats.NumberOfRetweetInDay(twAccountName, twitter, numberOftweetsAndRetweets, 5)%>, 
							<%=twitterStats.NumberOfRetweetInDay(twAccountName, twitter, numberOftweetsAndRetweets, 6)%>, 
							<%=twitterStats.NumberOfRetweetInDay(twAccountName, twitter, numberOftweetsAndRetweets, 7)%>]
				},
			]
		}
	
	var lineChartData2 = {
			labels : ["0h","1h","2h","3h","4h","5h","6h","7h","8h","9h","10h","11h","12h",
			          "13h","14h","15h","16h","17h","18h","19h","20h","21h","22h","23h","24h"],
			datasets : [
				{
					label: "Nombre de retweet par heure",
					fillColor : "rgba(220,220,220,0.2)",
					strokeColor : "rgba(220,220,220,1)",
					pointColor : "rgba(220,220,220,1)",
					pointStrokeColor : "#fff",
					pointHighlightFill : "#fff",
					pointHighlightStroke : "rgba(220,220,220,1)",
					data : [<%=numberOftweetsAndRetweets[1][3][0]%>, 
							<%=numberOftweetsAndRetweets[1][3][1]%>, 
							<%=numberOftweetsAndRetweets[1][3][2]%>, 
							<%=numberOftweetsAndRetweets[1][3][3]%>, 
							<%=numberOftweetsAndRetweets[1][3][4]%>, 
							<%=numberOftweetsAndRetweets[1][3][5]%>, 
							<%=numberOftweetsAndRetweets[1][3][6]%>,
							<%=numberOftweetsAndRetweets[1][3][7]%>, 
							<%=numberOftweetsAndRetweets[1][3][8]%>, 
							<%=numberOftweetsAndRetweets[1][3][9]%>, 
							<%=numberOftweetsAndRetweets[1][3][10]%>, 
							<%=numberOftweetsAndRetweets[1][3][11]%>, 
							<%=numberOftweetsAndRetweets[1][3][12]%>, 
							<%=numberOftweetsAndRetweets[1][3][13]%>,
							<%=numberOftweetsAndRetweets[1][3][14]%>, 
							<%=numberOftweetsAndRetweets[1][3][15]%>, 
							<%=numberOftweetsAndRetweets[1][3][16]%>, 
							<%=numberOftweetsAndRetweets[1][3][17]%>, 
							<%=numberOftweetsAndRetweets[1][3][18]%>,
							<%=numberOftweetsAndRetweets[1][3][19]%>, 
							<%=numberOftweetsAndRetweets[1][3][20]%>, 
							<%=numberOftweetsAndRetweets[1][3][21]%>, 
							<%=numberOftweetsAndRetweets[1][3][22]%>, 
							<%=numberOftweetsAndRetweets[1][3][23]%>				
							]
				},
			]

		}
	
	var pieData = [
					{
						value: 11,
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
		var ctx1 = document.getElementById("canvas_tweetPerDay").getContext("2d");
		var ctx2 = document.getElementById("canvas_tweetPerHour").getContext("2d");
		var ctx3 = document.getElementById("canvas_retweetInTweets").getContext("2d");
		var ctx4 = document.getElementById("canvas_retweetPerDay").getContext("2d");
		var ctx5 = document.getElementById("canvas_retweetPerHour").getContext("2d");
		
		window.myPie = new Chart(ctx3).Pie(pieData);
		window.myBar = new Chart(ctx1).Bar(barChartData, {
			responsive : true
		});
		window.myBar = new Chart(ctx2).Bar(barChartData2, {
			responsive : true
		});
		window.myLine = new Chart(ctx4).Line(lineChartData, {
			responsive: true
		});
		window.myLine = new Chart(ctx5).Line(lineChartData2, {
			responsive: true
		});

	}

	</script>


</body>
</html>