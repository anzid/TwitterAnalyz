<%@ page import="fr.lhous.Marin" %>
<%@ page import="fr.lhous.Connexion" %>
<%@ page import="fr.lhous.FollowersComparator" %>
<%@ page import="fr.lhous.TwitterStats" %>
<%@ page import="java.util.List" %>
<%@ page import="twitter4j.Twitter" %>
<%@ page import="twitter4j.TwitterException" %>
<%@ page import="twitter4j.User" %>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
 <%
 TwitterStats t = new TwitterStats(); 
 TwitterStats twitterStats = new TwitterStats();
 Twitter twitter = twitterStats.Connect();	
 List<User> followers = twitterStats.getFollowers("rogerfederer", twitter);
	for(User user: followers){ %>

		 <div>first follower is <%=user.getName() %> </div>
		 <div><img alt="" src="<%=user.getProfileImageURL()%>"></div>
	 <%}
	
	System.out.println(followers.get(1).getProfileImageURL());

 %>

</body>
</html>