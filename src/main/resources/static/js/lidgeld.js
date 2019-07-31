let lidgeldId = 0;

async function max_lidgeld_start() {
	reset_grid();
	section_height(0, 100, 0);
	await Refrech_HTML('/lidgeld/maxlidgeld', 'main_section_main');
};

async function lidgeld_tabel_start() {
	reset_grid();
	section_height(0, 100, 0);
	await Refrech_HTML('/lidgeld/maxlidgeld', 'main_section_main');
};

function lidgeldselect(id) {
	lidgeldId = id;
	$('tr.active').removeClass('active');
	$('#lidgeld_tabel_body #' + id).addClass('active');
};

