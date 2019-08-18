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
	document.oncontextmenu = (event) => {
		console.log("Context menu is uitgeschakeld");
		event.preventDefault();
	};
	section_height(5, 100, 20);
	menu_main_width(300);
	let promises = [Refrech_HTML('/start_menu_logo', 'header_logo'), 
						Refrech_HTML('/start_menu_menu', 'header_menu'), 
						Refrech_HTML('/start_main', 'main_section_main')];
	Promise.all(promises)
	.then(() => {
		start_menu();
	});	
	console.log("END main");
};

function time_out() {
	var sessionExpiry = Math.abs(getCookie('sessionExpiry'));
    var serverTime = Math.abs(getCookie('serverTime'));
    var localTime = (new Date()).getTime();
    
    //console.log("SessionExpiry: " + msToTime(sessionExpiry));
    //console.log("TimeOffset: " + msToTime(timeOffset));
    //console.log("LocalTime: " + msToTime(localTime));
    //console.log("Time out: " + msToTime(timeOut));
       
    timeOut = 0;
    if((sessionExpiry - serverTime) <= 0){
    	clearInterval(timeOutTimer);
    } else {
    	timeOut = sessionExpiry - localTime;
    }  
    
    document.getElementsByClassName('footer_section_A')[0].innerHTML = "Time out: " + msToTime(timeOut) + " - " + msToTime(serverTime) + " - " + msToTime(sessionExpiry) + " - " + msToTime(localTime);
    document.getElementById('timeOutTeller').innerHTML = msToTime(timeOut);
    
    if(timeOut < 30000 && timeOut > 29000 ) {
    	setup_timeOutModal();
    }
    
    if(timeOut < 2000 && timeOut > 10 ) {
    	window.open("/logout", "_self");
   	}

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

    return minutes + ":" + seconds;
    //return hours + ":" + minutes + ":" + seconds;
}

function start_menu() {
	var header_leden;
	var header_leden_tabel;
	var header_lidgeld;
	var header_lidgeld_tabel;
	var header_kasboek;
	var header_kasboek_tabel;
	var header_agenda;
	var header_soortenleden;
	var header_rubriek;
	var header_restcontroller;
	
	timeOutTimer = setInterval('time_out()', 1000);
	
	header_leden = document.getElementById('header_leden');
	if(header_leden != null) {
		header_leden.addEventListener('click', (event) => {
		event.preventDefault();
		leden_start()
		.then(() => {
			console.log('Leden geladen');
		})
		.catch((error) => {
			console.error('Error in leden laden: ' + error);
		});
	});
	};

	header_leden_tabel = document.getElementById('header_leden_tabel');
	if(header_leden_tabel != null) {
		header_leden_tabel.addEventListener('click', (event) => {
			event.preventDefault();
			leden_tabel_start()
			.then(() => {
				console.log('Leden_tabel geladen');
			})
			.catch((error) => {
				console.error('Error in leden_tabel laden: ' + error);
			});
		});
	};
	
	header_lidgeld = document.getElementById('header_lidgeld');
	if(header_lidgeld != null) {
		header_lidgeld.addEventListener('click', (event) => {
			event.preventDefault();
			max_lidgeld_start()
			.then(() => {
				console.log('Max_Lidgeld geladen');
			})
			.catch((error) => {
				console.error('Error in leden_tabel laden: ' + error);
			});
		});
	};	
	
	header_lidgeld_tabel = document.getElementById('header_lidgeld_tabel');
	if(header_lidgeld_tabel != null) {
		header_lidgeld_tabel.addEventListener('click', (event) => {
			event.preventDefault();
			lidgeld_tabel_start()
			.then(() => {
				console.log('Lidgeld_tabel geladen');
			})
			.catch((error) => {
				console.error('Error in leden_tabel laden: ' + error);
			});
		});
	};	
	
	header_kasboek = document.getElementById('header_kasboek');
	if(header_kasboek != null) {
		header_kasboek.addEventListener('click', (event) => {
			event.preventDefault();
			kasboek_start()
			.then(() => {
				console.log('Kasboek geladen');
			})
			.catch((error) => {
				console.error('Error in kasboek laden: ' + error);
			});
		});
	};	
	
	header_kasboek_tabel = document.getElementById('header_kasboek_tabel');
	if(header_kasboek_tabel != null) {
		header_kasboek_tabel.addEventListener('click', (event) => {
			event.preventDefault();
			kasboek_tabel_start()
			.then(() => {
				console.log('Kasboek_tabel geladen');
			})
			.catch((error) => {
				console.error('Error in kasboek_tabel laden: ' + error);
			});
		});
	};
	
	header_agenda = document.getElementById('header_agenda');
	if(header_agenda != null) {
		header_agenda.addEventListener('click', (event) => {
			event.preventDefault();
			agenda_start()
			.then(() => {
				console.log("Agenda geladen! ")
				maaktext();
				document.getElementById('agendaVersturenForm').addEventListener('submit', listener_agenda_submit);
				document.getElementById('agenda_voorbeeld_button').addEventListener('click', agenda_voorbeeld_onclick);
			})
			.catch((error) => {
				console.error('Error in agenda laden: ' + error);
			});
		});
	};
	
	header_soortenleden = document.getElementById('header_soortenleden');
	if(header_soortenleden != null) {
		header_soortenleden.addEventListener('click', (event) => {
			event.preventDefault();
			soortenleden_start()
			.then(() => {
				console.log('Soortenleden geladen');
			})
			.catch((error) => {
				console.error('Error in soortenleden laden: ' + error);
			});
		});
	};	
	
	header_rubriek = document.getElementById('header_rubriek');
	if(header_rubriek != null) {
		header_rubriek.addEventListener('click', (event) => {
			event.preventDefault();
			rubriek_start()
			.then(() => {
				console.log('Rubriek_tabel geladen');
			})
			.catch((error) => {
				console.error('Error in rubriek laden: ' + error);
			});
		});
	};	
	
	header_restcontroller = document.getElementById('header_restcontroller');
	if(header_restcontroller != null) {
		header_restcontroller.addEventListener('click', (event) => {
			event.preventDefault();
			restcontroller_start()
			.then(() => {
				console.log('Restcontroller geladen');
			})
			.catch((error) => {
				console.error('Error in restcontroller laden: ' + error);
			});
		});
	};	
	
	console.log('Menu header onclick geladen');
};

function start_login() {
	console.log("Login laden");
	reset_grid();
	menu_height(3);
	menu_main_width(300);
	Refrech_HTML('/login_main','main_section_main');
	return false;
	
};

function start_logout() {
	console.log("Logout laden");
	reset_grid();
	menu_height(10, 100);
	menu_main_width(300);
	let data = fetch_TEXT('/logout')
	.then((data) => {
		console.log('Logout: ' + data);
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	return false;
	
};

function initSessionMonitor() {
	window.addEventListener('keypress', function (ed, e) {
        console.log('Key press! ' + ed + ' - ' + e);
    });
	window.addEventListener('mousedown', function (ed, e) {
    	console.log('Mouse move! ' + ed + ' - ' + e);
    });
	window.addEventListener('keydown', function (ed, e) {
    	console.log('Key down! ' + ed + ' - ' + e);
    });
    
}
