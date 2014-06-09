<!DOCTYPE html>
<html>
<head>

	<title>Accueil</title>
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
	<div id="wrap">
		<h1>Systeme De Recommandation D'Amis</h1>
		<div id='form_wrap'>
			
			<FORM METHOD="POST" ACTION="AnalyserMessageServlet">

		        <p id="formstatus"></p>
				<p></p>
				<label for="message">Votre Message : </label>
				<textarea  name="message" value="Message" id="msg" ></textarea>
				
				<input type="submit" name ="submit" value="Poster" />
			</form>
			
		</div>
		
	</div>
	
	
</body>
</html>