let lidgeldId = 0;

async function max_lidgeld_start() {
	reset_grid();
	section_height(5, 100, 0);
	await Refrech_HTML('/lidgeld/maxlidgeldknoppen', 'main_section_header');
	await Refrech_HTML('/lidgeld/maxlidgeld', 'main_section_main');
};

async function lidgeld_start() {
	reset_grid();
	section_height(0, 100, 0);
	await Refrech_HTML('/lidgeld/', 'main_section_main');
};

async function lidgeld_tabel_start() {
	reset_grid();
	section_height(0, 100, 0);
	await Refrech_HTML('/lidgeld/lidgeldTabel', 'main_section_main');
};

function lidgeldselect(id) {
	lidgeldId = id;
	let selection = document.querySelector('tr.active')
	if(selection) {
		selection.classList.remove('active');
	};
	document.querySelector('#ledenlidgeldtabel' + id).classList.add('active');
};

function lidgeldbyid(id) {
	console.log('Lidgeld: ' + id);
	let selection = document.querySelector('tr.active')
	if(selection) {
		selection.classList.remove('active');
	};
	document.getElementById('maxlidgeld' + id).classList.add('active');
}