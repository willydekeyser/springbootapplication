let selectedLidId = 0;
let selectedSoortId = 0;
let aantal_leden = 0;
let change_soort = false;
let change_naam = false;
let leden_gegevens = "";

/**
 * 
 * 
 * Leden 
 * 
 * 
 */

async function leden_start() {
	
	console.log('------------------------------------------------------------');
	console.log('Menu leden onclick');
	reset_grid();
	menu_height(3);
	menu_main_width(300);
	section_height(0, 100, 0);
	selectedSoortId = 1;
	selectedLidId = 0;
	await Promise.all([
		Refrech_HTML('/leden/leden_menu/1', 'menu_header'),
		Refrech_HTML('/leden/namenlijst/1/0', 'menu_main')
	]);
	leden_change_soort();
	leden_namenlijst_geladen();
	if (aantal_leden == 0){
		document.getElementById('lid_main').html('<h1>Geen leden gevonden!</h1>');
	} else {
		leden_namenlijst_onclick()
		await Refrech_HTML('/leden/leden_main/' + selectedLidId, 'main_section_main');
		leden_gegevens_refrech();
	};	
	console.log("END Leden onclick - Soort ID = " + selectedSoortId + " - ID = " + selectedLidId);
	console.log('------------------------------------------------------------');
};

function leden_menu_geladen(soortId, lidid) {
	selectedSoortId = soortId;
	selectedLidId = lidId;
	console.log("Selected soort & lid: " + selectedSoortId + " - " + selectedLidId);
	leden_namenlijst(selectedSoortId, selectedLidId);
};

async function leden_namenlijst(soortId, lidId) {
	selectedSoortId = soortId;
	selectedLidId = lidId;
	await Refrech_HTML('/leden/namenlijst/' + soortId + '/' + lidId , 'menu_main');
	//await leden_namenlijst_refrech();
	leden_namenlijst_geladen();
	if (aantal_leden == 0){
		document.getElementById('geen_leden_gevonden').style.display = "block";
		let option = document.getElementById('select_leden');
		let text = option.options[option.selectedIndex].text;
		document.getElementById('geen_leden_gevonden_span').innerHTML = text;
		document.getElementById('leden_gevonden').style.display = "none";
	} else {
		document.getElementById('geen_leden_gevonden').style.display = "none";
		document.getElementById('leden_gevonden').style.display = "block";
		leden_namenlijst_onclick();
		await leden_gegevens_refrech();
	};
};

async function leden_gegevens_new() {
	await Refrech_HTML('/leden/leden_main/' + selectedLidId, '#lid_main');
};

async function leden_namenlijst_refrech() {
	console.log('Leden namenlijst refrech: ' + selectedSoortId);
	let data = await fetch_JSON('/leden/restcontroller/ledennamenlijstbyid/' + selectedSoortId);
	leden_namenlijst_laden(data);
};

async function leden_gegevens_refrech() {
	leden_gegevens = await fetch_JSON('/leden/restcontroller/ledenbyid/' + selectedLidId);
	await leden_gegevens_laden(leden_gegevens);
	await leden_lidgeld_laden(leden_gegevens.lidgelden);
};

async function leden_lidgeld_refrech() {
	console.log('Lidgeld refrech: ');
	let data = await fetch_JSON('/lidgeld/restcontroller/lidgeld/' + selectedLidId);
	leden_gegevens.lidgelden = data;
	leden_lidgeld_laden(data);
};

function leden_namenlijst_laden(data) {
	let html = ``;
	data.forEach((namenlijst, index) => {
		html += `<li id="${namenlijst.id}" class="list-group-item namenlijst ${namenlijst.id == selectedLidId ? `active` : ``}">${index + 1} - ${namenlijst.naam}</li>`;
	});
	document.getElementById('namenlijst_click').innerHTML = html;
};

function leden_gegevens_laden(data) {
	document.getElementById('main_id').innerhtml = 'Lid: ' + data.id;
	document.getElementById('id').innerHTML = data.id;
	document.getElementById('naam').innerHTML = data.voornaam + ' ' + data.familienaam;
	document.getElementById('adres').innerHTML = data.straat + ' ' + data.nr;
	document.getElementById('gemeente').innerHTML = data.postnr + ' ' + data.gemeente;
	document.getElementById('telefoon').innerHTML = data.telefoonnummer;
	document.getElementById('gsm').innerHTML = data.gsmnummer;
	document.getElementById('email').innerHTML = '<a href="mailto:' + data.emailadres + '">' + data.emailadres + '</a>';
	document.getElementById('website').innerHTML ='<a href="' + data.webadres + '" target="_blank">' + data.webadres + '</a>';
	document.getElementById('inschrijving').innerHTML = getFormattedDate(data.datumlidgeld);
	document.getElementById('soort').innerHTML = data.soortenleden.soortenleden;
	document.getElementById('ontvangmail').setAttribute('checked', data.ontvangMail);
	document.getElementById('mailvlag').setAttribute('checked', data.mailVlag);
	document.getElementById('leden_newlid').setAttribute('onclick', 'newLid();');
	document.getElementById('leden_updatelid').setAttribute('onclick', 'updateLid(' + data.id + ');');
	document.getElementById('leden_deletelid').setAttribute('onclick', 'deleteLid(' + data.id + ');');
	document.getElementById('lidgeld_newlidgeld').setAttribute('onclick', 'newLidgeld(' + data.id + ');');
	document.getElementById('lidgeld_updatelidgeld').setAttribute('onclick', 'updateLidgeld(' + data.id + ');');
	document.getElementById('lidgeld_deletelidgeld').setAttribute('onclick', 'deleteLidgeld(' + data.id + ');');
};

function leden_lidgeld_laden(data) {
	let html = ``;
	document.getElementById('lidgeld_tabel_body').innerHTML = '';
	data.forEach((lidgeld, index) => {
		html += `<tr class="test" onclick="lidgeldselect(${lidgeld.id})" id="${lidgeld.id}">
			<td style="width: 5%" class="right">${lidgeld.id}</td>
			<td style="width: 40%" class="right">${getFormattedDate(lidgeld.datum)}</td>
			<td style="width: 55%" class="right">${getFormattedEuro(lidgeld.bedrag)}</td>
			</tr>`;
	});
	document.getElementById('lidgeld_tabel_body').innerHTML = html;
};

function leden_namenlijst_geladen() {
	aantal_leden = document.querySelectorAll('#namenlijst_click li').length;
	if (aantal_leden == 0){
		return;
	};
	let selection = document.querySelector('#namenlijst_click #\\' + selectedLidId)
	if(selection){
		selectedLidId = 0;
	};
	if(selectedLidId != 0) {
		
		document.querySelector('#namenlijst_click li #\\' + selectedLidId).classList.add("active");
		let lijst = document.getElementsByClassName('menu_main')[0];
		console.log('lijst offset ' + lijst.offsetTop + ' - ' + lijst.scrollTopMax + ' - ' + lijst.offsetHeight  )
		let lijst_item = document.getElementsByClassName('active')[0];
		console.log('lijst item offsetTop ' + lijst_item.offsetTop)
		lijst.scrollTop = (lijst_item.offsetTop - 100);
	} else {
		document.querySelector('#namenlijst_click li').classList.add("active");
		selectedLidId = document.querySelector('#namenlijst_click li').getAttribute('id');
	};
};

function leden_namenlijst_onclick() {
	let menuLink = document.querySelectorAll("#namenlijst_click li");
	menuLink.forEach(link => link.addEventListener('click', function() {
		let selection = document.querySelector('ol li.active')
		if(selection) {
			selection.classList.remove('active');
		};
		this.classList.add('active');
		selectedLidId = this.getAttribute("id");
		console.log("Namenlijst select - Selected id: " + selectedLidId);
		leden_gegevens_refrech();
	}));
};

function leden_change_soort() {
	document.getElementById("select_leden").addEventListener('change', function(){
		let selectionSoort = document.getElementById('select_leden');
		selectedSoortId = selectionSoort.options[selectionSoort.selectedIndex].value;
		console.log('Change soort leden ' + selectedSoortId);
		selectedLidId = 0;
		leden_namenlijst(selectedSoortId, selectedLidId);
	});
};


/**
 * 
 * 
 * Leden tabel
 * 
 * 
 */

async function leden_tabel_start() {
	reset_grid();
	section_height(10, 80, 100);
	menu_main_width(10);
	await Refrech_HTML('/leden/leden_menu/1', 'main_section_header'),
	await Refrech_HTML('/leden/leden_tabel', 'main_section_footer');
	await Refrech_HTML('/leden/leden_tabel_ledenlijst/1', 'main_section_main');
	let ledenId = document.querySelector('#ledenTabel').rows[1].cells[1].getAttribute('id');
	document.getElementById('ledenTabel' + ledenId).classList.add('active');
	await Refrech_HTML('/leden/leden_tabel_ById/' + ledenId, 'main_section_footer');
	leden_tabel_change_soort();
};

function leden_tabel_change_soort() {
	document.getElementById("select_leden").addEventListener('change', function(){
		let selectionSoort = document.getElementById('select_leden');
		selectedSoortId = selectionSoort.options[selectionSoort.selectedIndex].value;
		console.log("Select soort leden: " + selectedSoortId);
		leden_tabel_ledenlijst(selectedSoortId, 0);
	});
};

async function leden_tabel_menu(soortid) {
	console.log("Leden tabel menu laden: " + soortid);
	let data = await load_HTML("/leden/leden_tabel_menu/" + soortid);
	document.getElementById('menu').innerHTML = data;
	document.addEventListener('contextmenu', (function(event){
		event.preventDefault();
	}));
};

async function leden_tabel_menu_geladen(id) {
	console.log("Leden tabel lijst laden: " + id);
	let data = await load_HTML("/leden/leden_tabel_ledenlijst/" + id);
	document.getElementById('main_section_main').innerHTML = data;
};

async function leden_tabel_ledenlijst(soortId, lidId) {
	selectedSoortId = soortId;
	selectedLidId = lidId;
	console.log("Leden ledenlijst laden: " + selectedSoortId + " - " + selectedLidId);
	let data = await load_HTML("/leden/leden_tabel_ledenlijst/" + selectedSoortId);
	document.getElementById('main_section_main').innerHTML = data;
	let rowCount = document.getElementById('ledenTabel').rows.length;
	if(rowCount > 1) {
		let ledenId = document.querySelector('#ledenTabel').rows[1].cells[1].getAttribute('id');
		document.getElementById('ledenTabel' + ledenId).classList.add('active');
		await Refrech_HTML('/leden/leden_tabel_ById/' + ledenId, 'main_section_footer');
	} else {
		document.getElementById('main_section_footer').innerHTML = '';
	}
	
};

function leden_tabel_ledenlijst_geladen() {
	leden_tabel_change_soort();
	var id = document.getElementById("leden_tabel_ledenlijst").rows[0].cells[0].getAttribute('id');
	ledenbyid(id);
	console.log("Leden menu & ledenlijst geladen: " + id);
};

function ledenbyid(id) {
	console.log("Leden detail: " + id);
	let selection = document.querySelector('#ledenTabel tr.active');
	if (selection) {
		selection.classList.remove('active');
	}
	document.getElementById('ledenTabel' + id).classList.add('active');
	Refrech_HTML('/leden/leden_tabel_ById/' + id, 'main_section_footer');
};

function ledenTabelLidgeldbyid(id) {
	console.log('Leden Tabel lidgeld ' + id);
	let selection = document.querySelector('#ledenTabelLidgeld tr.active');
	if (selection) {
		selection.classList.remove('active');
	}
	document.getElementById('ledenTabelLidgeld' + id).classList.add('active');
};

