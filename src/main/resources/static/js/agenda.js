let agenda_progress_bar_teller_id;
let datum_vergadering;

async function agenda_start() {
	reset_grid();
	section_height(0, 100, 0);
	menu_main_width(0);
	await Refrech_HTML('/agenda/agenda', 'main_section_main');
};

function maaktext() {
	mail_form();
};

function mail_form() {
	let emailtext = "";
	datum_vergadering = eerste_woensdag();
	let datum_verzenden = verzenddatum();
	document.getElementById("datum_vergadering").value = datum_vergadering;
	document.getElementById("datum_verzenden").value = datum_verzenden;
	const textfreak = document.getElementById("freak").value;
	const textfreaklesgever = document.getElementById("freaklesgever").value;
	const textfreaktobe = document.getElementById("freaktobe").value;
	const textfreaktobelesgever = document.getElementById("freaktobelesgever").value;
	const textinfo = document.getElementById("info").value;
    emailtext = "Beste lid,<br><br><br><h3>Planning voor " + datum_vergadering + "</h3>";
    if (textfreak === "") {
    	emailtext += "<b>Freaks & Freaks to Be: </b><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + textfreaktobe + " - " + textfreaktobelesgever + "<br/><br/><br/>";
    } else {
    	emailtext += "<b>Freaks to Be: </b><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + textfreaktobe + " - " + textfreaktobelesgever + "<br/><br/><br/><b>Freaks: </b><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
        textfreak + " - " + textfreaklesgever + "<br/><br/><br/>";
    }
    emailtext += "<b>Info: </b><br>" + textinfo + "<br><br><br><p align='right'>Computerclub Format C<br>Kring Kristus Koning<br>Rerum Novarumplein<br>Gent</p><p>&nbsp;</p>" +
    "<p>Afmelden voor deze agenda? Klik dan op de volgende link&nbsp;&nbsp;" +
    "<a href='mailto:cfc.schatbewaarder@cformatc.be?subject=Computerclub FORMAT C afmelden agenda'>Afmelden</a>" +
    "<br>" + datum_verzenden + "</font></p>";
    document.getElementById("tekst").innerHTML = emailtext;
};

function listener_agenda_submit(event) {
	event.preventDefault();
	document.getElementById('verzenden').style.display = 'none';
	document.getElementById('agenda_voorbeeld_venster').style.display = 'none';
	document.getElementById('agenda_voorbeeld_button').style.display = 'none';
	document.getElementById('agenda_hulp_venster').style.display = 'block';
	document.getElementById('agenda_progress_bar').style.display = 'block';
	const formData = new FormData(document.getElementById('agendaVersturenForm'));
	console.log('AGENDA: ' + formData);	
	let data = post_Form('mail/agendaVersturen', formData)
	.then((data) => {
		console.log('AGENDA: ' + data)
	})
	.catch((error) => {
		console.error('FOUT: ' + error);
	});
	agenda_progress_bar_teller_id = setInterval(agenda_progress_bar,1000);
	return false;
};

async function agenda_progress_bar() {
	const agenda_progress_bar = document.getElementById('agenda_progress_bar');
	if(agenda_progress_bar) {
		agenda_progress_bar.style.display = 'block';
	}
	let teller = 0;
	let max_teller = 0;
	let nog_teller = 0;
	const response = await fetch_JSON('mail/test');
	teller = response.return_progress;
	max_teller = response.return_max;
	nog_teller = max_teller - teller;
	//console.log('RESPONSE: ' + teller + " - " + nog_teller + " - " + max_teller);
	if (teller < 0) {
		clearTimeout(agenda_progress_bar_teller_id);
		const agenda_progress_bar_text = document.getElementById('agenda_progress_bar_text');
		if(agenda_progress_bar_text) {
			agenda_progress_bar_text.innerHTML = "Alle e-mail berichten verzonden.";
		}
		const agenda_progress_bar_progressbar = document.getElementById('agenda_progress_bar_progressbar');
		if(agenda_progress_bar_progressbar) {
			agenda_progress_bar_progressbar.value = 100;
		}
		const footer_section_B = document.getElementById('footer_section_B');
		if(footer_section_B) {
			footer_section_B.innerHTML = "Computerclub FORMAT C:";
		}
	} else {
		const agenda_progress_bar_progressbar = document.getElementById('agenda_progress_bar_progressbar');
		if(agenda_progress_bar_progressbar) {
			agenda_progress_bar_progressbar.value = (teller / max_teller) * 100;
		}
		const teller_tekst = teller == 1 ? teller 
				+ " e-mail bericht verzonden. - " + nog_teller + " nog te verzenden." : teller + " e-mail berichten verzonden. - " 
				+ nog_teller + " nog te verzenden."  ;
		const agenda_progress_bar_text = document.getElementById('agenda_progress_bar_text');
		if(agenda_progress_bar_text) {
			agenda_progress_bar_text.innerHTML = teller_tekst;
		}
		const footer_section_B = document.getElementById('footer_section_B');
		if(footer_section_B) {
			footer_section_B.innerHTML = "Computerclub FORMAT C: Planning voor " + datum_vergadering + " verzenden: " + teller_tekst;
		}
	}
	
};

function agenda_voorbeeld_onclick() {
	const div = document.getElementById('agenda_voorbeeld_venster');
	const knop = document.getElementById('agenda_voorbeeld_button');
	const agenda_hulp_venster = document.getElementById('agenda_hulp_venster');
	div.style.display = div.style.display == "none" ? "block" : "none";
	agenda_hulp_venster.style.display = div.style.display == "none" ? "block" : "none";
	knop.innerHTML = div.style.display == "none" ? "Open voorbeeld" : "Sluit voorbeeld";
};

function verzenddatum() {
	const date = new Date();
	return dagvandeweek(date.getDay()) + ", " + ((date.getDate() < 10) ? "0" + date.getDate() : date.getDate()) + "/" + maandvanhetjaar(date.getMonth()) +
	"/" + date.getFullYear() + " - " + ((date.getHours() < 10) ? "0" + date.getHours() : date.getHours()) + ":" +
	((date.getMinutes() < 10) ? "0" + date.getMinutes() : date.getMinutes()) + ":" + ((date.getSeconds() < 10) ? "0" + date.getSeconds() : date.getSeconds());
};

function eerste_woensdag() {
	const date = new Date();
	let dag = date.getDay();
	if (dag < 4) {
		dag = 3 - dag;
	} else {
		dag = 7 - (dag  - 3);
	};
	date.setDate(date.getDate() + dag);
	return dagvandeweek(date.getDay()) + ", " + date.getDate() + " - " + maandvanhetjaar(date.getMonth()) + " - " + date.getFullYear();
};

function dagvandeweek(dagindex) {
	return ["zondag", "maandag","dinsdag","woensdag","donderdag","vrijdag","zaterdag"][dagindex];
};

function maandvanhetjaar(maandindex) {
	return ["januari","februari","maart","april","mei","juni","juli", "augustus","september","oktober","november","december"][maandindex];
}