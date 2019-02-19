let agenda_progress_bar_teller_id;

async function agenda_start() {
	reset_grid();
	section_height(100);
	menu_main_width(0);
	await Refrech_HTML('/agenda/agenda', '.main_section_A');
};

function maaktext() {
	mail_form();
};

function mail_form() {
	let emailtext = "";
	let datum_vergadering = eerste_woensdag();
	let datum_verzenden = verzenddatum();
	document.getElementById("datum_vergadering").value = datum_vergadering;
	document.getElementById("datum_verzenden").value = datum_verzenden;
	let textfreak = document.getElementById("freak").value;
	let textfreaklesgever = document.getElementById("freaklesgever").value;
	let textfreaktobe = document.getElementById("freaktobe").value;
	let textfreaktobelesgever = document.getElementById("freaktobelesgever").value;
	let textinfo = document.getElementById("info").value;
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
	document.getElementById('verzenden').style.display = 'none';
	document.getElementById('agenda_voorbeeld_venster').style.display = 'none';
	document.getElementById('agenda_voorbeeld_button').style.display = 'none';
	document.getElementById('agenda_hulp_venster').style.display = 'block';
	document.getElementById('agenda_progress_bar').style.display = 'block';
	let form = $(this);
	console.log('AGENDA: ' + form);
	
	let data = post_Form('mail/post', form)
	.then((data) => {
		console.log('AGENDA: ' + data)
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	agenda_progress_bar_teller_id = setInterval(agenda_progress_bar,1000);
	return false;
};

async function agenda_progress_bar() {
	let teller = 0;
	let max_teller = 0;
	let response = await fetch_JSON('mail/test');
	teller = response.return_progress;
	max_teller = response.return_max;
	console.log('RESPONSE: ' + teller);
	if (teller == 10000) {
		clearTimeout(agenda_progress_bar_teller_id);
		document.getElementById('agenda_progress_bar_text').innerHTML = "Alle e-mail berichten verzonden.";
		document.getElementById('agenda_progress_bar_progressbar').value = 100;
	} else {
		document.getElementById('agenda_progress_bar_progressbar').value = (teller / max_teller) * 100;
		document.getElementById('agenda_progress_bar_text').innerHTML = teller == 1 ? teller + " e-mail bericht verzonden." : teller + " e-mail berichten verzonden.";
	}
	
};

function agenda_voorbeeld_onclick() {
	let div = document.getElementById('agenda_voorbeeld_venster');
	let knop = document.getElementById('agenda_voorbeeld_button');
	let agenda_hulp_venster = document.getElementById('agenda_hulp_venster');
	div.style.display = div.style.display == "none" ? "block" : "none";
	agenda_hulp_venster.style.display = div.style.display == "none" ? "block" : "none";
	knop.innerHTML = div.style.display == "none" ? "Open voorbeeld" : "Sluit voorbeeld";
};

function verzenddatum() {
	let date = new Date();
	return dagvandeweek(date.getDay()) + ", " + ((date.getDate() < 10) ? "0" + date.getDate() : date.getDate()) + "/" + maandvanhetjaar(date.getMonth()) +
	"/" + date.getFullYear() + " - " + ((date.getHours() < 10) ? "0" + date.getHours() : date.getHours()) + ":" +
	((date.getMinutes() < 10) ? "0" + date.getMinutes() : date.getMinutes()) + ":" + ((date.getSeconds() < 10) ? "0" + date.getSeconds() : date.getSeconds());
};

function eerste_woensdag() {
	let date = new Date();
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