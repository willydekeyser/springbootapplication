let SoortenLeden_gegevens = {
		id: 0,
		soortleden: ""
};

/**
 *  Soorten leden.
 *  
 *  
 */


async function soortenleden_start() {
	reset_grid();
	await Refrech_HTML('/soortenleden/', 'main_section_main');
};

function soortenledenSelect(row) {
	SoortenLeden_gegevens.id = row.getAttribute('id');
	SoortenLeden_gegevens.soortleden = row.getAttribute('soortenleden');
	let selection = document.querySelector('tr.active')
	if(selection) {
		selection.classList.remove('active');
	};
	row.classList.add('active');
};

function soortenleden_tabel_laden(data) {
	let html =``;
	data.forEach((soort, index) => {
		document.getElementById('soortenleden_tabel_body').innerHTML = '';
		html += `<tr class="test" onclick="soortenledenSelect(this)" id="${soort.id}" soortenleden="${soort.soortenleden}">
			<td id="soortenleden_id" style="width: 50px" class="test">${soort.id}</td>
			<td id="soortenleden_soortenleden" class="test">${soort.soortenleden}</td>
		</tr>`;
		
	});
	document.getElementById('soortenleden_tabel_body').innerHTML = html;
	document.getElementById('aantal_soortenleden').innerHTML = data.length;
};

