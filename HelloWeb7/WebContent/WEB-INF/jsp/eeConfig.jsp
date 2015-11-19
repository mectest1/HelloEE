<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EE Params Viewer&amp;Editor</title>
<style type="text/css">

	.editable-panel{
		border: 1px solid grey;
		background-color: #FFFFCC;
	}
</style>

</head>
<body>

<p>Config path:${configFile.name}</p>
<p>Config Content:</p>
<div class="editable-panel" contenteditable="true">
	${configFile.content}
</div>
</body>
</html>