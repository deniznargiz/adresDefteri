var selectedPerson = null;
var addressesByPerson = {};
var phonesByPerson = {};

var insertPeopleForm    = $("#insertPersonForm");
var insertAddressesForm = $("#insertAddressForm");
var insertPhonesForm    = $("#insertPhoneForm");
var deletePeopleForm    = $("#deletePersonForm");
var updatePeopleForm    = $("#updatePersonForm");
var updateAddressesForm = $("#updateAddressForm");
var updatePhonesForm    = $("#updatePhoneForm");

var peopleBody = $("#kisiler");
var phoneBody = $("#telefonlar");
var addressBody = $("#adresler");

var nameSearch = $("#nameSearch");
var surnameSearch = $("#surnameSearch");
var addressSearch = $("#addressSearch");

var personTemplate = "<tr><td>{{kulaid}}</td><td>{{adi}}</td><td>{{soyadi}}</td><td>{{dogum}}</td><td>{{isyeri}}</td><td><a href=\"#\" onclick=\"details({{kulaid}});\">Gör</a></td></tr>";
var phoneTemplate = "<tr><td>{{kulaid}}</td><td>{{telid}}</td><td>{{telces}}</td><td>{{telno}}</td></tr>";
var addressTemplate = "<tr><td>{{kulaid}}</td><td>{{adresId}}</td><td>{{adresAd}}</td><td>{{sehir}}</td><td>{{ilce}}</td><td>{{mahalle}}</td><td>{{sokak}}</td><td>{{apart}}</td><td>{{daireNo}}</td></tr>";

var ajax = function(method, url, data, failureMessage, success) {
	$.ajax({
		type: method,
		url: url,
		data: data,
		dataType: "json",
		//contentType: "application/json",
		success: success,
		failure: function(data) {
			alert(failureMessage);
		}
	});
}

function addressDetails(personId, filterByName) {
	addressBody.html("");
	
	if (personId == null)
		return;
	addressesByPerson[personId].filter(function (address) {
		return filterByName == null || filterByName == address.adresAdi;
	}).forEach(function (address) {
		addressBody.append(addressTemplate.replace("{{kulaid}}", personId)
									.replace("{{adresId}}", address.adresID)
									.replace("{{adresAd}}",address.adresAdi)
									.replace("{{sehir}}",address.sehir)
									.replace("{{ilce}}", address.ilce)
									.replace("{{mahalle}}", address.mahalle)
									.replace("{{sokak}}", address.sokak)
									.replace("{{apart}}", address.apartman)
									.replace("{{daireNo}}", address.daireNo));
	});
}

function details(personId) {
	phoneBody.html("");
	
	phonesByPerson[personId].forEach(function (phone) {
		phoneBody.append(phoneTemplate.replace("{{kulaid}}", personId)
									.replace("{{telid}}", phone.telefonID)
									.replace("{{telces}}", phone.telefon_cesit)
									.replace("{{telno}}", phone.telefonNo));
	});
	
	selectedPerson = personId;
	addressDetails(personId, null);
}

var peopleSuccess = function (people) {
	peopleBody.html("");
	phoneBody.html("");
	addressBody.html("");
	selectedPerson = null;
	
	people.forEach(function (person) {
		peopleBody.append(personTemplate.replace("{{kulaid}}", person.kullaniciID)
								.replace("{{kulaid}}", person.kullaniciID).replace("{{adi}}", person.isim)
								.replace("{{soyadi}}",person.soyisim).replace("{{dogum}}",person.dogumTarihi)
								.replace("{{isyeri}}",person.isYeri));
								
		addressesByPerson[person.kullaniciID] = person.adresler;
		phonesByPerson[person.kullaniciID] = person.telefonlar;
	});
}
var addressesSuccess = function (addresses) {
	
	addressBody.html("");
	selectedPerson = null;
	
	addresses.forEach(function (address) {
		addressBody.append(addressTemplate.replace("{{kulaid}}", address.kullaniciID)
									.replace("{{adresId}}", address.adresID)
									.replace("{{adresAd}}",address.adresAdi)
									.replace("{{sehir}}",address.sehir)
									.replace("{{ilce}}", address.ilce)
									.replace("{{mahalle}}", address.mahalle)
									.replace("{{sokak}}", address.sokak)
									.replace("{{apart}}", address.apartman)
									.replace("{{daireNo}}", address.daireNo));
		addressesByPerson[address.kullaniciID] = address.adresler;				
	});
}

function getAllPerson() {
	ajax("GET", "/kullanicilar", null, "Kullanıcı aktarımında hata oluştu.", peopleSuccess);
}
function getAllAddress(){
	ajax("GET","/adresler",null,"Adres aktarımında hata oluştu.",addressesSuccess);
}		
function insertPerson() {
	ajax("POST", "/kullanici_ekle", insertPeopleForm.serialize(), "Kullanıcı eklerken hata oluştu.", function () {});
	insertPeopleForm[0].reset();
	setTimeout(getAllPerson, 1500);
}
function deletePerson() {
	ajax("DELETE", "/kullanici_sil?" + deletePeopleForm.serialize(), null, "Kullanıcı silerken hata oluştu.", function () {});
	deletePeopleForm[0].reset();
	setTimeout(getAllPerson, 1500);
} 
function insertAddress() {
	var id = $("#insertAddressID").val();
	if(id != null){
	ajax("POST", "/adres_ekle?kullaniciID=" + id , insertAddressesForm.serialize(), "Adres eklerken hata oluştu.", function () {});
	insertAddressesForm[0].reset();
	setTimeout(getAllPerson, 1500);
}
	
}	
function insertPhones() {
	ajax("POST", "/telefon_ekle", insertPhonesForm.serialize(), "Telefon eklerken hata oluştu.", function () {});
	insertPhonesForm[0].reset();
	setTimeout(getAllPerson, 1500);
}	

function updatePerson() {
	var id = $("#updatePersonId").val();
	if (id != null) {
		ajax("PUT","/kullanici_guncelle?kullaniciID=" + id , updatePeopleForm.serialize(), "Kullanıcı güncellerken hata oluştu.", function() {});
		updatePeopleForm[0].reset();
		setTimeout(getAllPerson,1500);
	}
}
function updatePhones(){	
	var idkullaniciID = $("#updatePhonekullaniciID").val();
	var idtelefonID = $("#updatePhonetelefonID").val();
	ajax("PUT","/telefon_guncelle?kullaniciID=" + idkullaniciID +"&telefonID=" + idtelefonID, updatePhonesForm.serialize() , null, "Telefon güncellenirken hata oluştu.", function() {});
	updatePhonesForm[0].reset();
	setTimeout(getAllPerson, 1500);
}		
function updateAddress(){
var idPerson = $("#updateAddressKullaniciID").val();
var idAddress = $("#updateAddressAdresID").val();
	ajax("PUT","/adres_guncelle?kullaniciID=" + idPerson + "&adresID=" + idAddress, updateAddressesForm.serialize(), "Adres güncellerken hata oluştu.", function() {});
	updateAddressesForm[0].reset();
	setTimeout(getAllPerson, 1500);
}

nameSearch.change(function () {
	var name = nameSearch.val();
	if (name.length == 0) {
		getAllPerson();
	} else {
		ajax("GET", "/kullanici?isim=" + name, "Kullanıcı aktarımında hata oluştu.", peopleSuccess)
	}
});

surnameSearch.change(function () {
	var surname = surnameSearch.val();
	if (surname.length == 0) {
		getAllPerson();
	} else {
		ajax("GET", "/kullanici?soyisim=" + surname, "Kullanıcı aktarımında hata oluştu.", peopleSuccess)
	}
});

addressSearch.change(function () {
	var address = addressSearch.val();
	if (address.length == 0) {
		addressDetails(selectedPerson, null);
	} else {
		addressDetails(selectedPerson, address);
	}
});

getAllPerson();		