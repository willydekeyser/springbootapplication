
async function agenda_start() {
	reset_grid();
	section_height(99);
	await Refrech_HTML('/agenda/agenda', '.main_section_A');
};

function mail_form() {
	let emailtext = "";
	let datum_vergadering = eerste_woensdag();
	let textfreak = document.getElementById("freak").value;
	let textfreaklesgever = document.getElementById("freaklesgever").value;
	let textfreaktobe = document.getElementById("freaktobe").value;
	let textfreaktobelesgever = document.getElementById("freaktobelesgever").value;
	let textinfo = document.getElementById("tekstblok").value;
    emailtext = "Beste lid,<br><br><br><h3>Planning voor " + datum_vergadering + "</h3>";
    if (textfreak === "") {
    	emailtext += "<b>Freaks & Freaks to Be: </b><br>" + textfreaktobe + " - " + textfreaktobelesgever + "<br><br><br>";
    } else {
    	emailtext += "<b>Freaks to Be: </b><br>" + textfreaktobe + " - " + textfreaktobelesgever + "<br><br><br>" + "<b>Freaks: </b><br>" + 
        textfreak + " - " + textfreaklesgever + "<br><br><br>";
    }
    emailtext += "<b>Info: </b><br>" + textinfo + "<br><br><br><p align='right'>Computerclub Format C<br>Kring Kristus Koning<br>Rerum Novarumplein<br>Gent</p><p>&nbsp;</p>" +
    "<p>Afmelden voor deze agenda? Klik dan op de volgende link&nbsp;&nbsp;" +
    "<a href='mailto:cfc.schatbewaarder@cformatc.be?subject=Computerclub FORMAT C afmelden agenda'>Afmelden</a>" +
    "<br>" + verzenddatum() + "</font></p>";
    document.getElementById("tekst").innerHTML = emailtext;
};

function maaktext() {
	
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
	return ["Zondag", "Maandag","Dinsdag","Woensdag","Donderdag","Vrijdag","Zaterdag"][dagindex];
};

function maandvanhetjaar(maandindex) {
	return ["Januari","Ferbuari","Maart","April","Mei","Juni","Juli", "Augustus","September","Oktober","November","December"][maandindex];
}