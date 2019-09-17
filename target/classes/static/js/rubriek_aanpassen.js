/**
 * Kasboek rubrieken aanpassen
 */

/**
 * 
 * 
 * Nieuwe rubrieken
 * 
 * 
 */

function newRubriek() {
	console.log("New rubriek");
	let modal = document.querySelector('#editRubriekModal');
	if(modal == null){
		let data = load_HTML('/rubriek/editRubriek')
		.then((data) => {
			document.getElementById("editRubriekModalHolder").innerHTML = data;
			setup_newRubriekModal();
		})
		.catch((error) => {
			console.log('FOUT: ' + error);
		});
	} else {
		setup_newRubriekModal();
	};
};

let rubriekModal;
let rubriekModalAchtergrond;
let rubriekModalForm;

function setup_newRubriekModal() {
	rubriekModalAchtergrond = document.querySelector('#rubriekModalAchtergrond');
	rubriekModal = document.querySelector('#rubriekModal');
	rubriekLodalForm = document.getElementById('editRubriekModalForm');
	document.getElementById('modal-titel').innerHTML = 'Nieuwe rubriek';
	document.getElementById('modal-titel').classList.remove('text-danger');
	document.getElementById('form_body_error').style.display = 'none';
	document.querySelector('#rubriek').focus();
	document.getElementById('rubriek').readOnly = false;
	window.onkeyup = function (event) {
		if(event.keyCode == 27) {
			listener_newRubriek_close(event);
		}
	};
	showNewRubriekModal(true);
};

function listener_newRubriek_close(event) {
	event.preventDefault;
	document.getElementById('editRubriekModalForm').reset();
	showNewRubriekModal(false);
};

function listener_newRubriek_submit(event) {
	event.preventDefault();
	let formData = new FormData(document.getElementById('editRubriekModalForm'));
	let data = post_Form('/rubriek/save_newRubriek', formData)
	.then((data) => {
		showNewRubriekModal(false);
		rubriek_tabel_laden(data);
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	return false;
};

function showNewRubriekModal(show) {
	if(show) {
		document.getElementById('editRubriekModalForm').addEventListener('submit', listener_newRubriek_submit, false);
		document.querySelector('.closeBtnX').addEventListener('click', listener_newRubriek_close, false);
		document.getElementById('sluiten').addEventListener('click', listener_newRubriek_close, false);
		rubriekModalAchtergrond.addEventListener('click', listener_newRubriek_close, false);
		rubriekModalAchtergrond.classList.remove('hide');
		rubriekModal.classList.remove('off');
		rubriekModalAchtergrond.classList.add('show');
		rubriekModal.classList.add('on');
	} else {
		document.getElementById('editRubriekModalForm').removeEventListener('submit', listener_newRubriek_submit, false);
		document.querySelector('.closeBtnX').removeEventListener('click', listener_newRubriek_close, false);
		document.getElementById('sluiten').removeEventListener('click', listener_newRubriek_close, false);
		rubriekModalAchtergrond.removeEventListener('click', listener_newRubriek_close, false);
		rubriekModalAchtergrond.classList.remove('show');
		rubriekModal.classList.remove('on');
		rubriekModalAchtergrond.classList.add('hide');
		rubriekModal.classList.add('off');
	}
};

/**
 * 
 * 
 * Update rubrieken
 * 
 * 
 */

function updateRubriek() {
	console.log("Update rubriek");
	let modal = document.querySelector('#editRubriekModal');
	if(modal == null){
		let data = load_HTML('/rubriek/editRubriek')
		.then((data) => {
			document.getElementById("editRubriekModalHolder").innerHTML = data;
			setup_updateRubriekModal();
		})
		.catch((error) => {
			console.log('FOUT: ' + error);
		});
	} else {
		setup_updateRubriekModal();
		return false;
	};
};

function setup_updateRubriekModal() {
	rubriekModalAchtergrond = document.querySelector('#rubriekModalAchtergrond');
	rubriekModal = document.querySelector('#rubriekModal');
	rubriekLodalForm = document.getElementById('editRubriekModalForm');
	let actief_row = document.querySelector('tr.active');
	if (actief_row === undefined || actief_row === null) {
		document.getElementById('modal-titel').innerHTML = 'Je hebt geen selectie gemaakt!';
		document.getElementById('modal-titel').classList.add('text-danger');
		document.getElementById('editRubriek_save').style.visibility = 'hidden';
		document.getElementById('rubriek').readOnly = true;
	} else {
		document.getElementById('modal-titel').innerHTML = 'Update rubriek!';
		document.getElementById('modal-titel').classList.remove('text-danger');
		document.getElementById('editRubriek_save').style.visibility = 'visible';
		document.getElementById('id').value = Rubriek_gegevens.id;
		document.getElementById('rubriek').readOnly = false;
		document.getElementById('rubriek').value = Rubriek_gegevens.rubriek;
		document.querySelector('#rubriek').focus();
	};
	document.getElementById('form_body_error').style.display = 'none';
	document.getElementById('form_body').style.visibility = 'visible';
	window.onkeyup = function (event) {
		if(event.keyCode == 27) {
			listener_updateRubriek_close(event);
		}
	};
	showUpdateRubriekModal(true);
};

function listener_updateRubriek_close(event) {
	event.preventDefault;
	document.getElementById('editRubriekModalForm').reset();
	showUpdateRubriekModal(false);
};

function listener_updateRubriek_submit(event) {
	event.preventDefault();
	let formData = new FormData(document.getElementById('editRubriekModalForm'));
	let data = post_Form('/rubriek/save_updateRubriek', formData)
	.then((data) => {
		showUpdateRubriekModal(false);
		rubriek_tabel_laden(data);
	})
	.catch((error) => {
		console.error('FOUT: ' + error);
	});
	return false;
};

function showUpdateRubriekModal(show) {
	if(show) {
		document.getElementById('editRubriekModalForm').addEventListener('submit', listener_updateRubriek_submit, false);
		document.querySelector('.closeBtnX').addEventListener('click', listener_updateRubriek_close, false);
		document.getElementById('sluiten').addEventListener('click', listener_updateRubriek_close, false);
		rubriekModalAchtergrond.addEventListener('click', listener_updateRubriek_close, false);
		rubriekModalAchtergrond.classList.remove('hide');
		rubriekModal.classList.remove('off');
		rubriekModalAchtergrond.classList.add('show');
		rubriekModal.classList.add('on');
	} else {
		document.getElementById('editRubriekModalForm').removeEventListener('submit', listener_updateRubriek_submit, false);
		document.querySelector('.closeBtnX').removeEventListener('click', listener_updateRubriek_close, false);
		document.getElementById('sluiten').removeEventListener('click', listener_updateRubriek_close, false);
		rubriekModalAchtergrond.removeEventListener('click', listener_updateRubriek_close, false);
		rubriekModalAchtergrond.classList.remove('show');
		rubriekModal.classList.remove('on');
		rubriekModalAchtergrond.classList.add('hide');
		rubriekModal.classList.add('off');
	}
};

/**
 * 
 * 
 * Delete rubrieken
 * 
 * 
 */

function deleteRubriek() {
	console.log("Delete rubriek");
	let modal = document.querySelector('#editRubriekModal');
	if(modal == null){
		let data = load_HTML('/rubriek/editRubriek')
		.then((data) => {
			document.getElementById("editRubriekModalHolder").innerHTML = data;
			setup_deleteRubriekModal();
		})
		.catch((error) => {
			console.log('FOUT: ' + error);
		});
	} else {
		setup_deleteRubriekModal();
	};
};

async function setup_deleteRubriekModal() {
	var actief_row = $('tr.active');
	if (actief_row === undefined || actief_row.length == 0) {
		$('#editRubriekModal #modal-titel').html('Je hebt geen selectie gemaakt!');
		$('#editRubriek_save').prop('disabled', true);
		$('#editRubriek_save').hide();
	} else {
		let exist = await existRecord('/rubriek/existKasboekByRubriekId/' + Rubriek_gegevens.id);
		if(exist == 'true') {
			$('#editRubriek_save').hide();
			$('#form_body_error').show();
			$('#form_body').hide();
			$('#form_body_error_naam').html(Rubriek_gegevens.rubriek);
		}else {
			$('#editRubriek_save').show();
			$('#form_body_error').hide();
			$('#form_body').show();
		};
		$('#editRubriekModal #modal-titel').html('Delete rubriek!');
		$('#editRubriek_save').text('Delete rubriek');
		$('#editRubriekModalForm #id').val(Rubriek_gegevens.id);
		$('#editRubriekModalForm #rubriek').val(Rubriek_gegevens.rubriek);
		$('#editRubriekModalForm #rubriek').prop('readonly', true);
	};
	$('#editRubriekModal #modal-titel').addClass('text-danger');
	$('#editRubriekModal').one('shown.bs.modal', listener_deleteRubriek_focus);
	$('#editRubriekModal').one('hidden.bs.modal', listener_deleteRubriek_hidden);
	$('#editRubriekModalForm').one('submit', listener_deleteRubriek_submit);
	$("#editRubriekModal").modal("show");
};

function listener_deleteRubriek_focus() {
	$("input[name='rubriek']").focus();
};

function listener_deleteRubriek_hidden() {
	$('#editRubriekModalForm').unbind();
	$('#editRubriekModalForm').trigger('reset');
};

function listener_deleteRubriek_submit() {
	let formData = new FormData(document.getElementById('editRubriekModalForm'));
	let data = post_Form('/rubriek/save_deleteRubriek', formData)
	.then((data) => {
		$("#editRubriekModal").modal('toggle');
		rubriek_tabel_laden(data);
	})
	.catch((error) => {
		console.log('FOUT: ' + error);
	});
	return false;
};
