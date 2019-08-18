var Rubriek_gegevens = {
		id : 0,
		rubriek : ''
};

async function rubriek_start() {
	reset_grid();
	await Refrech_HTML('/rubriek/', 'main_section_main');
};

function rubriekSelect(row) {
	Rubriek_gegevens.id = row.getAttribute('id');
	Rubriek_gegevens.rubriek = row.getAttribute('rubriek');
	let selection = document.querySelector('tr.active');
	if (selection) {
		selection.classList.remove('active');
	}
	row.classList.add('active');
};

function rubriek_tabel_laden(data) {
	let html =``;
	data.forEach((rubriek, index) => {
		document.getElementById('rubriek_tabel_body').innerHTML = '';
		html += `<tr class="test" onclick="rubriekSelect(this)" id="${rubriek.id}" rubriek="${rubriek.rubriek}">
			<td id="rubriek_id" style="width: 50px" class="test">${rubriek.id}</td>
			<td id="rubriek_rubriek" class="test">${rubriek.rubriek}</td>
		</tr>`;
		
	});
	document.getElementById('rubriek_tabel_body').innerHTML = html;
	document.getElementById('aantal_rubrieken').innerHTML = data.length;
};
