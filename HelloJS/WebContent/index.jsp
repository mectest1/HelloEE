<%-- 
    Document   : index
    Created on : May 28, 2015, 3:45:40 PM
    Author     : MEC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to the playground of ES6</title>
		<link type="text/css" rel="stylesheet" href="css/style.css"/>
		<script type="text/javascript" src="js/Utils.js"></script>
		<script type="text/javascript">
			window.onload = function OnLoad(){
				Utils.LayoutUtils.wireHeaderWithContainer("h4", "section");
			}
		</script>
    </head>
    <body>
		<section>
	        <h1>Hello World!</h1>
			<p>It's simply a playground for ES6, and I will try to put some interesting 
			thoughts here.</p>
		</section>
		<section>
			<h4>Promise from ES6</h4>
			<p>Check some <a href="es6-promise.html">Promises from ES6</a>.</p>
		</section>
    </body>
</html>
