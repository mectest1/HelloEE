var CustomAudioController = function CustomAudioController(){
//				var audio = $("#fsw-song");
//				
//				var audioPlay = $("#audioPlay");
//				var audioPause = $("#audioPause");
//				var audioStop = $("#audioStop");
//				
//				var audioSeek = $("#audioSeek");
//				var audioLoaded = $("#audioLoaded");
//		
//				var audioDuration = $("#audioDuration");
//				var audioElapsed = $("#audioElapsed");
//				
//				var amountLoaded = 0;
	var isInitialized = false;
	
	var audio;

	var audioPlay;
	var audioPause;
	var audioStop;

	var audioSeek;
	var audioLoaded;

	var audioDuration;
	var audioElapsed;
	var amountLoaded;

	function init(){
		audio = $("#fsw-song");

		audioPlay = $("#audioPlay");
		audioPause = $("#audioPause");
		audioStop = $("#audioStop");

		audioSeek = $("#audioSeek");
		audioLoaded = $("#audioLoaded");

		audioDuration = $("#audioDuration");
		audioElapsed = $("#audioElapsed");

		amountLoaded = 0;
		//ref: https://developer.mozilla.org/en-US/docs/Web/Guide/Events/Media_events
		audio.addEventListener("loadedmetadata", setDuration);
//		audio.addEventListener("loadeddata", setDuration);
		audio.addEventListener("timeupdate", setElapsed);
		
		isInitialized = true;
	}

	function setDuration(event){
		audioDuration.firstChild && audioDuration.removeChild(audioDuration.firstChild);
		audioDuration.appendChild(document.createTextNode(timeFormatter(audio.duration)));
	}

	function setElapsed(event){
		audioElapsed.removeChild(audioElapsed.firstChild);
		audioElapsed.appendChild(document.createTextNode(timeFormatter(audio.currentTime)));
		amountLoaded = (audio.currentTime/audio.duration) * 100;
		audioLoaded.style.width = amountLoaded + 'px';
	}

	function playPause(){
		if(!isInitialized){
			init();
		}
		if(audio.paused){
			audio.play();
			audioPlay.className = 'hidden';
			audioPause.className = '';
		}else{
			audio.pause();
			audioPlay.className = '';
			audioPause.className = 'hidden';
		}
	}

	function playStop(){
		audio.pause();
		audio.currentTime = 0;
		audioPlay.className = '';
		audioPause.className = 'hidden';
	}

	function timeFormatter(seconds){
		function zeroPad(str){
			if(2 < str.length){
				return str;
			}
			for(var i = 0; i < (2 - str.length); ++i){
				str = "0" + str;
			}
			return str;
		}


		var minute = 60,
			hour = minute * 60,
			hStr = "",
			mStr = "",
			sStr = "";

		var h = Math.floor(seconds/hour);
		hStr = zeroPad(String(h));

		var m = Math.floor((seconds - (h * hour))/minute);
		mStr = zeroPad(String(m));

		var s = Math.floor((seconds - (h * hour)) - (m * minute));
		sStr = zeroPad(String(s));

		return (hStr + ":" + mStr + ":" + sStr);
	}

//	console.log("CustomAudioController.js loaded successfully");
//	init();
	return{
		playPause: playPause,
		playStop: playStop
	};
}();