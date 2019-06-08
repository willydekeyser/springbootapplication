/*
 * 
 * Start
 * 
 * 
 */

var timeOutTimer;
var timeOut = 0;

function start_main() {
	console.log("Start main");
	//initSessionMonitor();
	document.getElementById('div_script').innerHTML = '';
	$(document).on('contextmenu', (event) => {
		console.log("Context menu is uitgeschakeld");
		event.preventDefault();
	});
	section_height(80);
	menu_main_width(300);
	let promises = [Refrech_HTML('/start_menu_logo', '.header_logo'), 
						Refrech_HTML('/start_menu_menu', '.header_menu'), 
						Refrech_HTML('/start_main', '.main_section_A')];
	Promise.all(promises)
	.then(() => {
		start_menu();
	});	
	console.log("END main");
};

function time_out() {
	var sessionExpiry = Math.abs(getCookie('sessionExpiry'));
    var timeOffset = Math.abs(getCookie('serverTime'));
    var localTime = (new Date()).getTime();
    
    //console.log("SessionExpiry: " + msToTime(sessionExpiry));
    //console.log("TimeOffset: " + msToTime(timeOffset));
    //console.log("LocalTime: " + msToTime(localTime));
    //console.log("Time out: " + msToTime(timeOut));
       
    timeOut = 0;
    if((sessionExpiry - timeOffset) <= 0){
    //if((sessionExpiry - localTime) <= 0){
    	clearInterval(timeOutTimer);
    } else {
    	timeOut = sessionExpiry - localTime;
    }  
    
    document.getElementsByClassName('footer_section_A')[0].innerHTML = "Time out: " + msToTime(timeOut) + " - " + msToTime(timeOffset) + " - " + msToTime(sessionExpiry) + " - " + msToTime(localTime);
    document.getElementById('timeOutTeller').innerHTML = msToTime(timeOut);
    
    if(timeOut < 30000 && timeOut > 29000 ) {
    	setup_timeOutModal();
    }
    
    if(timeOut < 2000 && timeOut > 10 ) {
    	window.open("/logout", "_self");
   	}
    //console.log("Time out: " + timeOut);
}

function setup_timeOutModal() {
	$('#timeOutModal').one('hidden.bs.modal', listener_timeOut_hidden);
	$('#timeOutModal').modal('show');
	$('#timeOutModalForm').one('submit', listener_timeOut_submit);
};

function listener_timeOut_hidden() {
	if(timeOut < 30000){
		window.open("/logout", "_self");
	}
};


function listener_timeOut_submit() {
	timeOut = 30000;
	$("#timeOutModal").modal('toggle');
	let data = fetch_TEXT('/timeout')
	.then((data) => {
		console.log('Time out: ' + data);
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	return false;
};

function getCookie(name)
{
    var name = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++)
    {
        var c = ca[i].trim();
        if (c.indexOf(name)==0) return c.substring(name.length,c.length);
    }
    return "";
}

function msToTime(duration) {
    var milliseconds = parseInt((duration%1000))
        , seconds = parseInt((duration/1000)%60)
        , minutes = parseInt((duration/(1000*60))%60)
        , hours = parseInt((duration/(1000*60*60))%24);

    hours = (hours < 10) ? "0" + hours : hours;
    minutes = (minutes < 10) ? "0" + minutes : minutes;
    seconds = (seconds < 10) ? "0" + seconds : seconds;

    //return minutes + ":" + seconds;
    return hours + ":" + minutes + ":" + seconds;
}

function start_menu() {
	timeOutTimer = setInterval('time_out()', 1000);
	document.getElementById('header_leden').addEventListener('click', (event) => {
		event.preventDefault();
		leden_start()
		.then(() => {
			console.log('Leden geladen');
		})
		.catch((error) => {
			console.error('Error in leden laden: ' + error);
		});
	});
	document.getElementById('header_leden_tabel').addEventListener('click', (event) => {
		event.preventDefault();
		leden_tabel_start()
		.then(() => {
			console.log('Leden_tabel geladen');
		})
		.catch((error) => {
			console.error('Error in leden_tabel laden: ' + error);
		});
	});
	document.getElementById('header_lidgeld').addEventListener('click', (event) => {
		event.preventDefault();
		max_lidgeld_start()
		.then(() => {
			console.log('Max_Lidgeld geladen');
		})
		.catch((error) => {
			console.error('Error in leden_tabel laden: ' + error);
		});
	});
	document.getElementById('header_lidgeld_tabel').addEventListener('click', (event) => {
		event.preventDefault();
		max_lidgeld_start()
		.then(() => {
			console.log('Lidgeld_tabel geladen');
		})
		.catch((error) => {
			console.error('Error in leden_tabel laden: ' + error);
		});
	});
	document.getElementById('header_kasboek').addEventListener('click', (event) => {
		event.preventDefault();
		kasboek_start()
		.then(() => {
			console.log('Kasboek geladen');
		})
		.catch((error) => {
			console.error('Error in kasboek laden: ' + error);
		});
	});
	document.getElementById('header_kasboek_tabel').addEventListener('click', (event) => {
		event.preventDefault();
		max_lidgeld_start()
		.then(() => {
			console.log('Kasboek_tabel geladen');
		})
		.catch((error) => {
			console.error('Error in kasboek_tabel laden: ' + error);
		});
	});
	document.getElementById('header_agenda').addEventListener('click', (event) => {
		event.preventDefault();
		agenda_start()
		.then(() => {
			console.log('agenda geladen');
		})
		.catch((error) => {
			console.error('Error in agenda laden: ' + error);
		});
	});
	document.getElementById('header_soortenleden').addEventListener('click', (event) => {
		event.preventDefault();
		soortenleden_start()
		.then(() => {
			console.log('Soortenleden geladen');
		})
		.catch((error) => {
			console.error('Error in soortenleden laden: ' + error);
		});
	});
	document.getElementById('header_rubriek').addEventListener('click', (event) => {
		event.preventDefault();
		rubriek_start()
		.then(() => {
			console.log('Rubriek_tabel geladen');
		})
		.catch((error) => {
			console.error('Error in rubriek laden: ' + error);
		});
	});
	document.getElementById('header_restcontroller').addEventListener('click', (event) => {
		event.preventDefault();
		restcontroller_start()
		.then(() => {
			console.log('Restcontroller geladen');
		})
		.catch((error) => {
			console.error('Error in restcontroller laden: ' + error);
		});
	});
	console.log('Menu header onclick geladen');
};

function start_login() {
	console.log("Login laden");
	reset_grid();
	menu_height(3);
	menu_main_width(300);
	Refrech_HTML('/login_main','.main_section_A');
	return false;
	
};

function start_logout() {
	console.log("Logout laden");
	reset_grid();
	menu_height(3);
	menu_main_width(300);
	let data = fetch_TEXT('/logout')
	.then((data) => {
		console.log('Logout: ' + data);
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	//Refrech_HTML('/login_main?logout','.main_section_A');
	return false;
	
};

function initSessionMonitor() {
    $(document).bind('keypress.session', function (ed, e) {
        //console.log('Key press!');
    });
    $(document).bind('mousedown keydown', function (ed, e) {
    	//console.log('Mouse move!');
    });
    
}
