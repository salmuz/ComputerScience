<%@ page import="org.montp2.m1decol.ter.data.beans.Author" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html"%>
<html>
<head>

	<title>Resultats</title>
	<link rel="stylesheet" type="text/css" href="./styles.css" />

<style>
@font-face {
	font-family: 'YanoneKaffeesatzRegular';
	src: url('./fonts/yanonekaffeesatz-regular-webfont.eot');
	src: url('./fonts/yanonekaffeesatz-regular-webfont.eot?#iefix') format('embedded-opentype'),
	url('./fonts/yanonekaffeesatz-regular-webfont.woff') format('woff'),
	url('./fonts/yanonekaffeesatz-regular-webfont.ttf') format('truetype'),
	url('./fonts/yanonekaffeesatz-regular-webfont.svg#YanoneKaffeesatzRegular') format('svg');
	font-weight: normal;
	font-style: normal;
}
</style>
	
</head>
<body>
	<div id="wrap2">
		<h1>Resultats</h1>
		<div id='form_wrap2'>
			<dl>
                <% List<Author> authors = (List<Author>) session.getAttribute("LIST_USERS");
                   for (int i = 0; i < authors.size(); i++) {
                       Author a = authors.get(i);
                %>
                    <dt>
                        <img src="images/user.png" ALIGN="right" height="15%" width="20%">
                        <p><%=a.getPseudonymAut()%></p>
                    </dt>
                    <dd><%=a.getRankAut()%></dd>
                    <br/>
                    <a href="<%=a.getProfileLinkAut()%>">Visiter Profil"</a>
                    <br/>
                    <hr color="#b3aba1">
                <% } %>
			</dl> 
		</div>

		
	</div>
	
</body>
</html>