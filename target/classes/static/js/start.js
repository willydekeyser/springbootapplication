/*
 * 
 * Start
 * 
 * 
 */

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

function start_menu() {
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
	Refrech_HTML('/login_main?logout','.main_section_A');
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
