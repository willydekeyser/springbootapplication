/**
 * Leden aanpassen.
 */

/**
 * 
 * 
 * Nieuwe leden
 * 
 * 
 */

let lidModal;
let lidModalAchtergrond;
let lidModalForm;


function newLid() {
	console.log("New lid");
	let editModal = document.getElementById("editLidModal");
	if(typeof(editModal) == 'undefined' || editModal == null){
		let data = load_HTML('/leden/editLid')
		.then((data) => {
			document.getElementById("editLidModalHolder").innerHTML = data;
			setup_newLidModal();
		})
		.catch((error) => {
			console.error('FOUT: ' + error);
		});
	} else {
		setup_newLidModal();
	};
	
};

function setup_newLidModal() {
	lidModalAchtergrond = document.querySelector('#lidModalAchtergrond');
	lidModal = document.querySelector('#editLidModal');
	lidModalForm = document.getElementById('editLidModalForm');
	document.getElementById('modal-titel').innerHTML = 'New lid!';
		
	showNewLidModal(true);
};

function listener_newLid_close(event) {
	event.preventDefault;
	showNewLidModal(false);
};

function listener_newLid_submit() {
	let formData = new FormData(document.getElementById('editLidModalForm'));
	let data = post_Form('/leden/save_newLid', formData)
	.then((data) => {
		selectedLidId = data.id;
		selectedSoortId = data.soortenleden.id;
		showNewLidModal(false);
	
//		$('#select_leden').val(selectedSoortId);
//		$('#select_leden').trigger('change');
	})
	.catch((error) => {
		console.error('FOUT: ' + error);
	});
	return false;
};

function showNewLidModal(show) {
	if(show) {
		document.getElementById('editLidModalForm').addEventListener('submit', listener_newLid_submit, false);
		document.querySelector('.closeBtnX').addEventListener('click', listener_newLid_close, false);
		document.getElementById('sluiten').addEventListener('click', listener_newLid_close, false);
		lidModalAchtergrond.addEventListener('click', listener_newLid_close, false);
		lidModalAchtergrond.classList.add('show');
		lidModal.classList.add('on');
		lidModalAchtergrond.classList.remove('hide');
		lidModal.classList.remove('off');
	} else {
		document.getElementById('editLidModalForm').reset();
		document.getElementById('editLidModalForm').removeEventListener('submit', listener_newLid_submit, false);
		document.querySelector('.closeBtnX').removeEventListener('click', listener_newLid_close, false);
		document.getElementById('sluiten').removeEventListener('click', listener_newLid_close, false);
		lidModalAchtergrond.removeEventListener('click', listener_newLid_close, false);
		lidModalAchtergrond.classList.add('hide');
		lidModal.classList.add('off');
		lidModalAchtergrond.classList.remove('show');
		lidModal.classList.remove('on');
	}
};

/**
 * 
 * 
 * Update leden
 * 
 * 
 */

function updateLid(id) {
	console.log("Update lid: " + id);
	var editModal = document.getElementById("editLidModal");
	if(typeof(editModal) == 'undefined' || editModal == null){
		let data = load_HTML('/leden/editLid')
		.then((data) => {
			document.getElementById("editLidModalHolder").innerHTML = data;
			setup_updateLidModal();
			updateLid_Formvullen();
		})
		.catch((error) => {
			console.error('FOUT: ' + error);
		});
	} else {
		setup_updateLidModal();
		updateLid_Formvullen();
	};
};

function setup_updateLidModal() {
	change_naam = false;
	change_soort = false;
	$('#modal-titel').html('Update lid!');
	$('#editLidModal').one('shown.bs.modal', listener_newLid_focus);
	$('#editLidModal').one('hidden.bs.modal', listener_newLid_hidden);
	$('#editLidModal #voornaam').one('input', listener_change_naam);
	$('#editLidModal #familienaam').one('input', listener_change_naam);
	$('#editLidModal #soortlid').one('change', listener_change_soortlid);
	$('#editLidModalForm').one('submit', listener_updateLid_submit);
	$("#editLidModal").modal("show");
};

function updateLid_Formvullen(){
	console.log('ID: ' + leden_gegevens.voornaam + ' ' + leden_gegevens.familienaam);
	$('#editLidModalForm #id').val(leden_gegevens.id);
	$('#editLidModalForm #voornaam').val(leden_gegevens.voornaam);
	$('#editLidModalForm #familienaam').val(leden_gegevens.familienaam);
	$('#editLidModalForm #straat').val(leden_gegevens.straat);
	$('#editLidModalForm #nr').val(leden_gegevens.nr);
	$('#editLidModalForm #postnr').val(leden_gegevens.postnr);
	$('#editLidModalForm #gemeente').val(leden_gegevens.gemeente);
	$('#editLidModalForm #emailadres').val(leden_gegevens.emailadres);
	$('#editLidModalForm #webadres').val(leden_gegevens.webadres);
	$('#editLidModalForm #telefoonnummer').val(leden_gegevens.telefoonnummer);
	$('#editLidModalForm #gsmnummer').val(leden_gegevens.gsmnummer);
	$('#editLidModalForm #datumlidgeld').val(leden_gegevens.datumlidgeld);
	$('#editLidModalForm #soortlid').val(leden_gegevens.soortenleden.id);
	$('#editLidModalForm #ontvangmail').prop('checked', leden_gegevens.ontvangMail);
	$('#editLidModalForm #mailvlag').prop('checked', leden_gegevens.mailVlag);
};

function listener_change_naam() {
	change_naam = true;
};

function listener_change_soortlid() {
	change_soort = true;
};

function listener_updateLid_submit() {
	let formData = new FormData(document.getElementById('editLidModalForm'));
	let data = put_Form('/leden/save_updateLid', formData, change_soort, change_naam)
	.then((data) => {
		selectedLidId = data.id;
		selectedSoortId = data.soortenleden.id;
		$("#editLidModal").modal('toggle');
		if(change_soort) {
			$('#select_leden').val(selectedSoortId);
			$('#select_leden').trigger('change');
		} else if(change_naam) {
			leden_namenlijst_refrech();
			leden_gegevens_laden(data);
			leden_gegevens = data;
		} else {
			leden_gegevens_laden(data);
			leden_gegevens = data;
		}
		console.log('Update Lid: ' + selectedSoortId + ' - ' + selectedLidId + ' ' + data.voornaam + ' ' + data.familienaam);
	})
	.catch((error) => {
	console.log('FOUT: ' + error);
	});
	return false;
};

/**
 * 
 * 
 * Delete leden
 * 
 * 
 */

function deleteLid(id) {
	console.log("Delete lid: " + id);
	var deleteModal = document.getElementById("deleteLidModal");
	if(typeof(deleteModal) == 'undefined' || deleteModal == null){
		let data = load_HTML('/leden/deleteLid')
		.then((data) => {
			document.getElementById("deleteLidModalHolder").innerHTML = data;
			setup_deleteLidModal();
		})
		.catch((error) => {
			console.log('ERROR: ' + error);
		});
	} else {
		setup_deleteLidModal();
	};
};

function setup_deleteLidModal() {
	if(leden_gegevens.lidgelden.length == 0){
		document.getElementById('delete_modal_footer').hidden = false;
		document.getElementById('delete_modal_footer_error').hidden = true;
		document.getElementById('delete_form_body').hidden = false;
		document.getElementById('delete_form_body_error').hidden = true;
		document.getElementById('delete_id').value = leden_gegevens.id;
		document.getElementById('delete_naam').value = leden_gegevens.voornaam + ' ' + leden_gegevens.familienaam;
		$('#deleteLidModal').one('shown.bs.modal', listener_deleteLid_focus);
		$('#deleteLidModalForm').one('submit', listener_deleteLid_submit);
	} else {
		document.getElementById('delete_modal_footer').hidden = true;
		document.getElementById('delete_modal_footer_error').hidden = false;
		document.getElementById('delete_form_body').hidden = true;
		document.getElementById('delete_form_body_error').hidden = false;
		document.getElementById('delete_form_body_error_naam').innerHTML = leden_gegevens.voornaam + ' ' + leden_gegevens.familienaam;
	}
	document.getElementById('delete-modal-titel').innerHTML = 'Verwijder lid!';
	$("#deleteLidModal").modal("show");
};

function listener_deleteLid_focus() {
	$("#deleteLidModal #sluiten").focus();
};

function listener_deleteLid_submit() {
	let formData = new FormData(document.getElementById('deleteLidModalForm'));
	let data = delete_Form('/leden/save_deleteLid', formData)
	.then((data) => {
		selectedLidId = data;
		$("#deleteLidModal").modal('toggle');
		$('#select_leden').val(selectedSoortId);
		$('#select_leden').trigger('change');
		console.log('Delete Lid: ' + selectedSoortId + ' - ' + selectedLidId);
	})
	.catch((error) => {
	console.log('FOUT: ' + error);
	});
	return false;
};
