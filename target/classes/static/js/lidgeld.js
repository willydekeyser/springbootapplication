/**
 * 
 * 
 * lidgeld
 * 
 * 
 */

"use strict";

let lidgeldId = 0;
let lidgeldData;

async function max_lidgeld_start() {
	reset_grid();
	section_height(5, 100, 0);
	await Refrech_HTML('/lidgeld/maxlidgeldknoppen', 'main_section_header');
	await Refrech_HTML('/lidgeld/maxlidgeld', 'main_section_main');
	max_lidgeld_Tabel_laden();
	const newMaxLidgeld = document.getElementById('maxlidgeld_newlidgeld');
	if(newMaxLidgeld) {
		newMaxLidgeld.addEventListener('click', listener_new_max_lidgeld, false);
	}
	const emailMaxLidgeld = document.getElementById('maxlidgeld_emaillidgeld');
	if(emailMaxLidgeld) {
		emailMaxLidgeld.addEventListener('click', listener_email_max_lidgeld, false);
	}
	const briefMaxLidgeld = document.getElementById('maxlidgeld_brieflidgeld');
	if(briefMaxLidgeld) {
		briefMaxLidgeld.addEventListener('click', listener_brief_max_lidgeld, false);
	}
	const rapportwMaxLidgeld = document.getElementById('maxlidgeld_rapportlidgeld');
	if(rapportwMaxLidgeld) {
		rapportwMaxLidgeld.addEventListener('click', listener_rapport_max_lidgeld, false);
	}
};

async function max_lidgeld_Tabel_laden() {
	lidgeldData = await fetch_JSON('/lidgeld/restcontroller/maxlidgeld');
	maxlidgeld_laden(lidgeldData)
}

function maxlidgeld_laden(data) {
	let datumkleur;
	let datum = new Date();
	datum.setFullYear(datum.getFullYear() - 1);
	let html = ``;
	const tabelBody = document.getElementById('maxlidgeld_tabel_body');
	tabelBody.innerHTML = '';
	data.forEach((lidgeld, index) => {
		const lidgelddatum = new Date(lidgeld.datum);
		if(lidgelddatum < datum){
			datumkleur = 'red';
		} else {
			datumkleur = 'black';
		};
		html += `<tr class="test" id="maxlidgeld${lidgeld.id}" onclick="maxlidgeldselect(${lidgeld.id})">
					<td style="width: 15px" class="test"> </td>
					<td style="width: 10px" class="test">${index + 1}</td>
					<td class="test right" style="width: 15; color: ${datumkleur}">${getFormattedDate(lidgeld.datum)}</td>
					<td style="width: 15%" class="test right">${getFormattedEuro(lidgeld.bedrag)}</td>
					<td style="width: 60%" class="test">${lidgeld.leden.voornaam + ' ' + lidgeld.leden.familienaam}</td>
					<td style="width: 15px" class="test" > </td>
				</tr>`;
	});
	tabelBody.innerHTML = html;
}

async function lidgeld_start() {
	reset_grid();
	section_height(0, 100, 0);
	await Refrech_HTML('/lidgeld/', 'main_section_main');
};

async function lidgeld_tabel_start() {
	reset_grid();
	section_height(0, 100, 0);
	await Refrech_HTML('/lidgeld/lidgeldTabel', 'main_section_main');
	lidgeld_Tabel_laden();
};

async function lidgeld_Tabel_laden() {
	lidgeldData = await fetch_JSON('/lidgeld/restcontroller/all_lidgeld');
	maxlidgeld_laden(lidgeldData)
};

function lidgeldselect(id) {
	lidgeldId = id;
	const selection = document.querySelector('tr.active')
	if(selection) {
		selection.classList.remove('active');
	};
	document.querySelector('#ledenlidgeldtabel' + id).classList.add('active');
};

function maxlidgeldselect(id) {
	console.log('Lidgeld: ' + id);
	const selection = document.querySelector('tr.active')
	if(selection) {
		selection.classList.remove('active');
	};
	document.getElementById('maxlidgeld' + id).classList.add('active');
};

function listener_new_max_lidgeld() {
	maxLidgeld(lidgeldId);
};

function listener_email_max_lidgeld() {
	maxLidgeldEmail(lidgeldId);
};

function listener_brief_max_lidgeld() {
	console.log('Brief afprinten');
};

function listener_rapport_max_lidgeld() {
	console.log('Rapport opmaken');
};



