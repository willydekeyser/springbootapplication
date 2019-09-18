let temp = 0;
/**
 *  Soorten leden aanpassen.
 */

/**
 * 
 * 
 * Nieuwe soortenleden
 * 
 * 
 */

"use strict";

let soortenledenModal;
let soortenledenModalAchtergrond;
let soortenledenModalForm;


function newSoortenleden() {
	console.log("New Soortenleden");
	let modal = document.querySelector('#soortenledenModal');
	if(modal == null){
		let data = load_HTML('/soortenleden/editSoortenleden')
		.then((data) => {
			document.getElementById("editSoortenledenModalHolder").innerHTML = data;
			setup_newSoortenledenModal();
		})
		.catch((error) => {
			console.error('FOUT: ' + error);
		});
	} else {
		setup_newSoortenledenModal();
	};
};

function setup_newSoortenledenModal() {
	soortenledenModalAchtergrond = document.querySelector('#soortenledenModalAchtergrond');
	soortenledenModal = document.querySelector('#soortenledenModal');
	soortenledenModalForm = document.getElementById('editSoortenledenModalForm');
	document.getElementById('modal-titel').innerHTML = 'Nieuwe soortenleden';
	document.getElementById('modal-titel').classList.remove('text-danger');
	document.getElementById('form_body_error').style.display = 'none';
	document.querySelector('#soortenleden').focus();
	document.getElementById('soortenleden').readOnly = false;
	window.onkeyup = function (event) {
		if(event.keyCode == 27) {
			listener_newSoortenleden_close(event);
		}
	};
	showNewSoortenledenModal(true);
};

function listener_newSoortenleden_close(event) {
	event.preventDefault;
	showNewSoortenledenModal(false);
};

function listener_newSoortenleden_submit() {
	event.preventDefault();
	let formData = new FormData(document.getElementById('editSoortenledenModalForm'));
	let data = post_Form('/soortenleden/save_newSoortenleden', formData)
	.then((data) => {
		showNewSoortenledenModal(false);
		soortenleden_tabel_laden(data);
	})
	.catch((error) => {
		console.error('FOUT: ' + error);
	});
	return false;
};

function showNewSoortenledenModal(show) {
	if(show) {
		document.getElementById('editSoortenledenModalForm').addEventListener('submit', listener_newSoortenleden_submit, false);
		document.querySelector('.closeBtnX').addEventListener('click', listener_newSoortenleden_close, false);
		document.getElementById('sluiten').addEventListener('click', listener_newSoortenleden_close, false);
		soortenledenModalAchtergrond.addEventListener('click', listener_newSoortenleden_close, false);
		soortenledenModalAchtergrond.classList.add('show');
		soortenledenModal.classList.add('on');
		soortenledenModalAchtergrond.classList.remove('hide');
		soortenledenModal.classList.remove('off');
	} else {
		document.getElementById('editSoortenledenModalForm').reset();
		document.getElementById('editSoortenledenModalForm').removeEventListener('submit', listener_newSoortenleden_submit, false);
		document.querySelector('.closeBtnX').removeEventListener('click', listener_newSoortenleden_close, false);
		document.getElementById('sluiten').removeEventListener('click', listener_newSoortenleden_close, false);
		soortenledenModalAchtergrond.removeEventListener('click', listener_newSoortenleden_close, false);
		soortenledenModalAchtergrond.classList.add('hide');
		soortenledenModal.classList.add('off');
		soortenledenModalAchtergrond.classList.remove('show');
		soortenledenModal.classList.remove('on');
	}
};

/**
 * 
 * 
 * Update soortenleden
 * 
 * 
 */


function updateSoortenleden() {
	console.log("Update soortenleden");
	let modal = document.querySelector('#soortenledenModal');
	if(modal == null){
		let data = load_HTML('/soortenleden/editSoortenleden')
		.then((data) => {
			document.getElementById("editSoortenledenModalHolder").innerHTML = data;
			setup_updateSoortenledenModal();
		})
		.catch((error) => {
			console.error('FOUT: ' + error);
		});
	} else {
		setup_updateSoortenledenModal();
	};
};

function setup_updateSoortenledenModal() {
	soortenledenModalAchtergrond = document.querySelector('#soortenledenModalAchtergrond');
	soortenledenModal = document.querySelector('#soortenledenModal');
	soortenledenModalForm = document.getElementById('editSoortenledenModalForm');
	let actief_row = document.querySelector('tr.active');
	if (actief_row === undefined || actief_row === null) {
		document.getElementById('modal-titel').innerHTML = 'Je hebt geen selectie gemaakt!';
		document.getElementById('modal-titel').classList.add('text-danger');
		document.getElementById('editSoortenleden_save').style.visibility = 'hidden';
		document.getElementById('soortenleden').readOnly = true;
	} else {
		document.getElementById('modal-titel').innerHTML = 'Update soortenleden!';
		document.getElementById('modal-titel').classList.remove('text-danger');
		document.getElementById('editSoortenleden_save').style.visibility = 'visible';
		document.getElementById('id').value = SoortenLeden_gegevens.id;
		document.getElementById('soortenleden').readOnly = false;
		document.getElementById('soortenleden').value = SoortenLeden_gegevens.soortleden;
	};
	document.getElementById('form_body_error').style.display = 'none';
	document.getElementById('form_body').style.visibility = 'visible';
	document.querySelector('#soortenleden').focus();
	window.onkeyup = function (event) {
		if(event.keyCode == 27) {
			listener_updateSoortenleden_close(event);
		}
	};
	showUpdateSoortenledenModal(true);
};

function listener_updateSoortenleden_close(event) {
	event.preventDefault();
	showUpdateSoortenledenModal(false);
};

function listener_updateSoortenleden_submit() {
	event.preventDefault();
	let formData = new FormData(document.getElementById('editSoortenledenModalForm'));
	let data = put_Form('/soortenleden/save_updateSoortenleden', formData)
	.then((data) => {
		showUpdateSoortenledenModal(false);
		soortenleden_tabel_laden(data);
	})
	.catch((error) => {
		console.error('FOUT: ' + error);
	});
};

function showUpdateSoortenledenModal(show) {
	if(show) {
		document.getElementById('editSoortenledenModalForm').addEventListener('submit', listener_updateSoortenleden_submit, false);
		document.querySelector('.closeBtnX').addEventListener('click', listener_updateSoortenleden_close, false);
		document.getElementById('sluiten').addEventListener('click', listener_updateSoortenleden_close, false);
		soortenledenModalAchtergrond.addEventListener('click', listener_updateSoortenleden_close, false);
		soortenledenModalAchtergrond.classList.add('show');
		soortenledenModal.classList.add('on');
		soortenledenModalAchtergrond.classList.remove('hide');
		soortenledenModal.classList.remove('off');
	} else {
		document.getElementById('editSoortenledenModalForm').reset();
		document.getElementById('editSoortenledenModalForm').removeEventListener('submit', listener_updateSoortenleden_submit, false);
		document.querySelector('.closeBtnX').removeEventListener('click', listener_updateSoortenleden_close, false);
		document.getElementById('sluiten').removeEventListener('click', listener_updateSoortenleden_close, false);
		soortenledenModalAchtergrond.removeEventListener('click', listener_updateSoortenleden_close, false);
		soortenledenModalAchtergrond.classList.add('hide');
		soortenledenModal.classList.add('off');
		soortenledenModalAchtergrond.classList.remove('show');
		soortenledenModal.classList.remove('on');
	}
};

/**
 * 
 * 
 * Delete soortenleden
 * 
 * 
 */

function deleteSoortenleden() {
	console.log("Delete soortenleden");
	let modal = document.querySelector('#soortenledenModal');
	if(modal == null){
		let data = load_HTML('/soortenleden/editSoortenleden')
		.then((data) => {
			document.getElementById("editSoortenledenModalHolder").innerHTML = data;
			setup_deleteSoortenledenModal();
		})
		.catch((error) => {
			console.log('FOUT: ' + error);
		});
	} else {
		setup_deleteSoortenledenModal();
	};
};

async function setup_deleteSoortenledenModal() {
	soortenledenModalAchtergrond = document.querySelector('#soortenledenModalAchtergrond');
	soortenledenModal = document.querySelector('#soortenledenModal');
	soortenledenModalForm = document.getElementById('editSoortenledenModalForm');
	document.getElementById('form_body_error').style.display = 'none';
	let actief_row = document.querySelector('tr.active');
	if (actief_row === undefined || actief_row === null) {
		document.getElementById('modal-titel').innerHTML = 'Je hebt geen selectie gemaakt!';
		document.getElementById('modal-titel').classList.add('text-danger');
		document.getElementById('editSoortenleden_save').style.visibility = 'hidden';
		document.getElementById('soortenleden').readOnly = true;
	} else {
		let exist = await existRecord('/soortenleden/existLedenBySoortenledenId/' + SoortenLeden_gegevens.id);
		if(exist == 'true') {
			document.getElementById('editSoortenleden_save').style.visibility = 'hidden';
			document.getElementById('form_body_error').style.display = 'block';
			document.getElementById('form_body').style.visibility = 'hidden';
			document.getElementById('form_body_error_naam').innerHTML = SoortenLeden_gegevens.soortleden;
		}else {
			document.getElementById('editSoortenleden_save').style.visibility = 'visible';
			document.getElementById('form_body_error').style.display = 'none';
			document.getElementById('form_body').style.visibility = 'visible';
		};
		document.getElementById('modal-titel').innerHTML = 'Delete soortenleden!';	
		document.getElementById('editSoortenleden_save').innerHTML = 'Delete soortenleden';
		document.getElementById('id').value = SoortenLeden_gegevens.id;
		document.getElementById('soortenleden').value = SoortenLeden_gegevens.soortleden;
		document.getElementById('soortenleden').readOnly = true;
	};
	document.getElementById('modal-titel').classList.add('text-danger');
	document.querySelector('#soortenleden').focus();
	window.onkeyup = function (event) {
		if(event.keyCode == 27) {
			listener_updateSoortenleden_close(event);
		}
	};
	showDeleteSoortenledenModal(true);
};

function listener_deleteSoortenleden_close(event) {
	event.preventDefault();
	showDeleteSoortenledenModal(false);
};

function listener_deleteSoortenleden_submit() {
	event.preventDefault();
	let formData = new FormData(document.getElementById('editSoortenledenModalForm'));
	let data = delete_Form('/soortenleden/save_deleteSoortenleden', formData)
	.then((data) => {
		showDeleteSoortenledenModal(false);
		soortenleden_tabel_laden(data);
	})
	.catch((error) => {
		console.error('FOUT: ' + error);
	});
};

function showDeleteSoortenledenModal(show) {
	if(show) {
		document.getElementById('editSoortenledenModalForm').addEventListener('submit', listener_deleteSoortenleden_submit, false);
		document.querySelector('.closeBtnX').addEventListener('click', listener_deleteSoortenleden_close, false);
		document.getElementById('sluiten').addEventListener('click', listener_deleteSoortenleden_close, false);
		soortenledenModalAchtergrond.addEventListener('click', listener_deleteSoortenleden_close, false);
		soortenledenModalAchtergrond.classList.add('show');
		soortenledenModal.classList.add('on');
		soortenledenModalAchtergrond.classList.remove('hide');
		soortenledenModal.classList.remove('off');
	} else {
		document.getElementById('editSoortenledenModalForm').reset();
		document.getElementById('editSoortenledenModalForm').removeEventListener('submit', listener_deleteSoortenleden_submit, false);
		document.querySelector('.closeBtnX').removeEventListener('click', listener_deleteSoortenleden_close, false);
		document.getElementById('sluiten').removeEventListener('click', listener_deleteSoortenleden_close, false);
		soortenledenModalAchtergrond.removeEventListener('click', listener_deleteSoortenleden_close, false);
		soortenledenModalAchtergrond.classList.add('hide');
		soortenledenModal.classList.add('off');
		soortenledenModalAchtergrond.classList.remove('show');
		soortenledenModal.classList.remove('on');
	}
};
