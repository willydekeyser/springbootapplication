
async function agenda_start() {
	reset_grid();
	section_height(99);
	await Refrech_HTML('/agenda/agenda', '.main_section_A');
};