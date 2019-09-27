"use strict";

let kasboekId = 0;
let selectedId = 0;
let selectedJaar = 0;
let selectedRubriek = 0;
let Kasboek_gegevens = {
		id : 0,
		jaar : 0,
		rubriek : {
			id : 0,
			rubriek : ''
		},
		onschrijving : '',
		datum : 0,
		uitgaven : 0,
		inkomsten : 0
};
let change_jaar = false;
let change_rubriek = false;
let jaar = 0;
let rubriek = 0;
let pagina = 1;
let aantalPerPagina = 0;
let startAantal = 0;
let aantalrijen = 0;
let aantalPaginas = 0;

async function kasboek_start() {
	console.log('------------------------------------------------------------');
	console.log('Menu kasboek onclick');
	reset_grid();
	menu_height(3, 100);
	menu_main_width(300);
	await Refrech_HTML('/kasboek/', 'menu_main');
	kasboek_main_laden();
	kasboek_menu_listener();
};

async function kasboek_menu_refrech_select(menu_select_jaar, menu_select_rubriek) {
	console.log('kasboek_menu_refrech_select ' + menu_select_jaar + ' - ' + menu_select_rubriek)
	await Refrech_HTML('/kasboek/', 'menu_main');
	kasboek_menu_listener();
	document.getElementById(menu_select_jaar).dispatchEvent(new Event('click'));
	document.querySelector('#jaartal' + menu_select_jaar + menu_select_rubriek).dispatchEvent(new Event('click'));
}

async function kasboek_tabel_start() {
	console.log('------------------------------------------------------------');
	console.log('Menu kasboek tabel onclick');
	reset_grid();
	menu_height(3, 100);
	menu_main_width(300);
	await Refrech_HTML('/kasboek/kasboekHeader', 'main_section_header')
	kasboek_totalen_laden();
};

async function kasboekSelect(id) {
	kasboekId = id.getAttribute('id');
	const selection = document.querySelector('tr.active')
	if(selection) {
		selection.classList.remove('active');
	};
	id.classList.add('active');
	Kasboek_gegevens = await fetch_JSON('/kasboek/restcontroller/kasboekbyid/' + kasboekId);
};

function kasboek_menu_listener() {
	const menuLink = document.querySelectorAll('.kasboeklijst_click');
	menuLink.forEach(link => link.addEventListener('click', function() { 
		const menuOpenSelection = document.querySelector('.menuopen')
		if(menuOpenSelection) {
			menuOpenSelection.style.display = 'none';
			menuOpenSelection.classList.remove('menuopen');
		};
		const selection = document.querySelector('ol li.active')
		if(selection) {
			selection.classList.remove('active');
		};
		this.classList.add('active');
		const select = document.getElementById("jaartal" + this.id);
		if (select) {
			select.style.display = "block";
			select.classList.add('menuopen');
		}
		selectedJaar = this.getAttribute("jaar");
		selectedRubriek = this.getAttribute("rubriek");
		aantalPerPagina = Math.floor(document.getElementById('main_section_main').offsetHeight / 20) - 1;
		startAantal = 0;
		pagina = 1;
		Refrech_HTML('/kasboek/kasboekJaarRubriek/' + selectedJaar + '/' + selectedRubriek + '/' + aantalPerPagina + '/' + startAantal, 'main_section_main');
		kasboek_totalen_laden();
	}));
	
	const subMenuLink = document.querySelectorAll('.sub_kasboeklijst_click');
	subMenuLink.forEach(link => link.addEventListener('click', function(event) {
		event.stopImmediatePropagation()
		let selection = document.querySelector('ol li.active')
		if(selection) {
			selection.classList.remove('active');
		};
		this.classList.add('active');
		selectedJaar = this.getAttribute("jaar");
		selectedRubriek = this.getAttribute("rubriek");
		aantalPerPagina = Math.floor(document.getElementById('main_section_main').offsetHeight / 20) - 1;
		startAantal = 0;
		pagina = 1;
		kasboek_totalen_laden();
	}));
};

function kasboek_main_laden() {
	console.log('Start main laden');
	document.querySelector('#namenlijst_click #kasboek').classList.add('active');
	aantalPerPagina = Math.floor(document.getElementById('main_section_main').offsetHeight / 20) - 1;
	startAantal = 0;
	pagina = 1;
	selectedJaar = 0;
	selectedRubriek = 0;
	Refrech_HTML('/kasboek/kasboekHeader', 'main_section_header')
	kasboek_totalen_laden();
};

async function kasboek_totalen_laden() {
	console.log('Start totalen laden');
	await Refrech_HTML('/kasboek/kasboekJaarRubriek/' + selectedJaar + '/' + selectedRubriek + '/' + aantalPerPagina + '/' + startAantal, 'main_section_main');
	Kasboek_gegevens = "";
	const data = await fetch_JSON('/kasboek/restcontroller/kasboekTotalen/' + selectedJaar + '/' + selectedRubriek);
	if(data.Jaar == 0) {data.Jaar = 'Alle jaren';}
	if(data.Rubriek == '') {data.Rubriek = 'Alle rubrieken';}
	let html = ``;
	console.log('Totalen: ' + data.Jaar);
	document.getElementById('main_section_footer').innerHTML = `<div class="KasboekTotalen col-3"><p>Jaar: <b>${data.Jaar}</b></p>
	<p>Rubriek: <b>${data.Rubriek}</b></p> 
	<p>Inkomsten: <b>${getFormattedEuro(data.Totalen[0].Inkomsten)}</b> Uitgaven: <b>${getFormattedEuro(data.Totalen[0].Uitgaven)}</b> Totaal: <b><span style=${data.Totalen[0].Totaal < 0 ? "color:red" : "color:black"}>${getFormattedEuro(data.Totalen[0].Totaal)}</span></b></p></div>
	<div class="KasboekTotalen col-3"><p>Totaal:</p>
	<p>Inkomsten: <b>${getFormattedEuro(data.Totalen[1].Inkomsten)}</b> Uitgaven: <b>${getFormattedEuro(data.Totalen[1].Uitgaven)}</b> Totaal: <b><span style=${data.Totalen[1].Totaal < 0 ? "color:red" : "color:black"}>${getFormattedEuro(data.Totalen[1].Totaal)}</span></b></p></div>`;
	console.log('End totalen laden');
	aantalrijen = document.getElementById('aantalrijen').getAttribute('value');
	aantalPaginas = Math.ceil(aantalrijen / aantalPerPagina);
	const paginaInfo = document.getElementById('pagina_info');
	if(paginaInfo){paginaInfo.innerHTML = 'Pagina ' + pagina + ' - ' + aantalPaginas;}
};

async function kasboek_menu_refrech() {
	let html = ``;
	const data = await fetch_JSON('/kasboek/restcontroller/kasboekmenu');
	html += `<div><ol class="menu_lijst" id="namenlijst_click">
		<li class="menu_lijst_item kasboeklijst_click active" id="kasboek" jaar="0" rubriek="0">
			<span class="menu_lijst_item_tekst">Kasboek</span>
		</li>`;
	data.forEach((jaren, index) => {
		if (jaren.jaartal == jaar) {
			html += `<li class="menu_lijst_item kasboeklijst_click active" id="${jaren.jaartal}" jaar="${jaren.jaartal}" rubriek="0">
				<span class="menu_lijst_item_tekst">${jaren.jaartal}</span>
			<ol class="sub_menu_lijst menuopen" id="jaartal${jaren.jaartal}" style="display: block;">`;
		} else {
			html += `<li class="menu_lijst_item kasboeklijst_click" id="${jaren.jaartal}" jaar="${jaren.jaartal}" rubriek="0">
				<span class="menu_lijst_item_tekst">${jaren.jaartal}</span>
			<ol class="sub_menu_lijst" id="jaartal${jaren.jaartal}" style="display: none;">`;
		}
		const rubrieken = jaren.rubriek;
		rubrieken.forEach((rubriek, index) => {
			html += `<li class="sub_menu_lijst_item sub_kasboeklijst_click" id="jaartal${jaren.jaartal}${rubriek.id}" jaar="${jaren.jaartal}" rubriek="${rubriek.id}">${rubriek.rubriek}</li>`;
		})
		html += `</ol></li>`;
	});
	html += `</ol</div>`;
	document.getElementById('menu_main').innerHTML = html;
	kasboek_menu_listener();
};

function kasboek_tabel_refrech(data) {
	let html = ``;
	document.getElementById('kasboek_tabel_body').innerHTML = '';
	data.forEach((kasboek, index) => {
		html += `<tr id="${kasboek.id}" class="test" onclick="kasboekSelect(this)">
			<td class="test" style="width: 15px"> </td>
			<td class="test" style="width: 50px">${index + 1}</td>
			<td class="test" style="width: 50px">${kasboek.id}</td>
			<td class="test" style="width: 50px">${kasboek.jaartal}</td>
			<td class="test" style="width: 200px">${kasboek.rubriek.rubriek}</td>
			<td class="test">${kasboek.omschrijving}</id>
			<td class="test right" style="width: 80px">${getFormattedDate(kasboek.datum)}</td>
			<td class="test right" style="width: 80px">${getFormattedEuro(kasboek.uitgaven)}</td>
			<td class="test right" style="width: 80px">${getFormattedEuro(kasboek.inkomsten)}</td>
			<td class="test" style="width: 15px"> </td>`;
	});
	document.getElementById('kasboek_tabel_body').innerHTML =html;
	kasboek_totalen_laden();
	if (change_jaar || change_rubriek) {
		kasboek_menu_refrech();
	}
};

function eersteKasboek() {
	document.getElementById('eersteKasboek').disabled = true;
	setTimeout(function() {
		document.getElementById('eersteKasboek').disabled = false;
	}, 500);
	pagina = 1;
	startAantal = aantalPerPagina * (pagina - 1);
	document.getElementById('pagina_info').innerHTML = 'Pagina ' + pagina + ' - ' + aantalPaginas;
	Refrech_HTML('/kasboek/kasboekJaarRubriek/' + selectedJaar + '/' + selectedRubriek + '/' + aantalPerPagina + '/' + startAantal, 'main_section_main')
};

function vorigeKasboek() {
	document.getElementById('vorigeKasboek').disabled = true;
	setTimeout(function() {
		document.getElementById('vorigeKasboek').disabled = false;
	}, 500);
	if(pagina != 1) {
		pagina = pagina - 1;
	}
	startAantal = aantalPerPagina * (pagina - 1);
	document.getElementById('pagina_info').innerHTML = 'Pagina ' + pagina + ' - ' + aantalPaginas;
	Refrech_HTML('/kasboek/kasboekJaarRubriek/' + selectedJaar + '/' + selectedRubriek + '/' + aantalPerPagina + '/' + startAantal, 'main_section_main')
};
	
function volgendeKasboek() {
	document.getElementById('volgendeKasboek').disabled = true;
	setTimeout(function() {
		document.getElementById('volgendeKasboek').disabled = false;
	}, 500);
	if(pagina != aantalPaginas) {
		pagina = pagina + 1;
	}
	startAantal = aantalPerPagina * (pagina - 1);
	document.getElementById('pagina_info').innerHTML = 'Pagina ' + pagina + ' - ' + aantalPaginas;
	Refrech_HTML('/kasboek/kasboekJaarRubriek/' + selectedJaar + '/' + selectedRubriek + '/' + aantalPerPagina + '/' + startAantal, 'main_section_main')
};

function laatsteKasboek() {
	document.getElementById('laatsteKasboek').disabled = true;
	setTimeout(function() {
		document.getElementById('laatsteKasboek').disabled = false;
	}, 500);
	pagina = aantalPaginas;
	startAantal = aantalPerPagina * (pagina - 1);
	document.getElementById('pagina_info').innerHTML = 'Pagina ' + pagina + ' - ' + aantalPaginas;
	Refrech_HTML('/kasboek/kasboekJaarRubriek/' + selectedJaar + '/' + selectedRubriek + '/' + aantalPerPagina + '/' + startAantal, 'main_section_main')
};
