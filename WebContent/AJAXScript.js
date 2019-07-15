/**
 * 
 */
var contactList;
const displayContact = () => {
	 fetch("http://localhost:8080/contacts").then(response => {
		if (response.ok) {
			return response.json();
		throw new Error("Request unaccepted");
		}
	}, newtworkError => {
		window.alert("Network Error!")
	}).then(jsonResponse => {
		let contactBody = document.getElementById("contactList");
		let body = "";
		let contactList = jsonResponse.contacts;
		
		contactList.forEach(contact => {	
			body += `<tr id = "${contact.ID}">`;
			body += `<td class = 'text-left pad'>`;
			body += contact.name;
			body += `</td>`;
			body += `<td class='text-right'>`;
			body += `<button class = 'btn btn-info margin' data-toggle = 'collapse' data-target = '#edit${contact.ID}'><span class = 'glyphicon glyphicon-pencil'></span></button>`;
			body += `<button class = 'btn btn-info' onclick = 'deleteContact("${contact.ID}")'><span class = 'glyphicon glyphicon-trash'></span></button>`;
			body += `</td>`;
			body += `</tr>`;
			body += `<div class = "panel-collapse collapse fade" id = "edit${contact.ID}">`;
			body += `<div class = "panel-body form-format">`;
			body += `<input type="text" name = "login" class="form-control" placeholder="Enter login" value = "${contact.login}" required>`;
			body += `<input type="text" name = "name" class="form-control" placeholder="Enter name" value = "${contact.name}" required>`;
			contact.additionalInformation.mailList.forEach(mailObject => {
				body += `<input type="email" name = "email" id="${mailObject.mailUUID}" class="form-control" placeholder="Enter Alternate Mail" value = "${mailObject.mail}" required>`;
			});
			contact.additionalInformation.phoneList.forEach(phoneObject => {
				body += `<input type="text" name = "mobile" id="${phoneObject.phoneUUID}" class="form-control" placeholder="Enter phone" value = "${phoneObject.phone}" required>`;
			});
			body += `<div id = "${contact.address.addressUUID}">`;
			body += `<input type="text" name = "house_no" class="form-control" placeholder="Enter House Number" value="${contact.address.houseNumber}" required>`;
			body += `<input type="text" name = "street_name" class="form-control" placeholder="Enter Street Name" value="${contact.address.streetName}" required>`;
			body += `<input type="text" name = "area" class="form-control" placeholder="Enter Area" value="${contact.address.area}" required>`;
			body += `<input type="text" name = "city" class="form-control" placeholder="Enter City" value="${contact.address.city}" required>`;
			body += `<input type="text" name = "state" class="form-control" placeholder="Enter State" value="${contact.address.state}" required>`;
			body += `<input type="text" name = "zipcode" class="form-control" placeholder="Enter ZipCode" value="${contact.address.zipCode}" required>`;
			body += `</div>`;
			body += `<div class="text-center">
				<button class="btn btn-primary btn-cancel" type="submit"
				data-toggle="collapse" data-target="#edit${contact.ID}" onclick="editContact('${contact.ID}')">Save</button>
				<button class="btn btn-default btn-cancel " type="reset"
				data-toggle="collapse" data-target="#edit${contact.ID}" onclick="clearChanges('${contact.ID}')">Cancel</button>
				</div>`;
			body += `</div>`;
			body += `</div>`;
		});
		contactBody.innerHTML += body;
	});
	
}

const addContact = () => {
  const login = document.getElementById("login").value;
  if(!checkMailFormat(login)){
	  alert("Enter valid mail");
	  clearInformation();
	  return;
  }
  const name = document.getElementById("name").value;
  if(!checkNameFormat(name)){
	  alert("Enter valid name");
	  clearInformation();
	  return;
  } 
  let phoneBody = document.getElementById("phoneBody").getElementsByTagName("input");
  let phoneArray = [];
  for(let counter = 0; counter<phoneBody.length;counter++) {
	  if(!checkPhoneFormat(phoneBody[counter].value)){
		  alert("Enter valid phone number");
		  clearInformation();
		  return;
	  }
	  phoneArray.push(phoneBody[counter].value);
  }
  const house_no = document.getElementById("house_no").value;
  if(!checkHouseNumberFormat(house_no)){
	  alert("Enter valid house number");
	  clearInformation();
	  return;
  } 
  const street_name = document.getElementById("street_name").value;
  if(!checkStreetNameFormat(street_name)){
	  alert("Enter valid street name");
	  clearInformation();
	  return;
  } 
  const area = document.getElementById("area").value;
  if(!checkNameFormat(area)){
	  alert("Enter valid area");
	  return;
  } 
  const city = document.getElementById("city").value;
  if(!checkNameFormat(city)){
	  alert("Enter valid city");
	  clearInformation();
	  return;
  } 
  const state = document.getElementById("state").value;
  if(!checkNameFormat(state)){
	  alert("Enter valid state");
	  clearInformation();
	  return;
  } 
  const zipcode = document.getElementById("zipcode").value;
  if(!checkZipCodeFormat(zipcode)){
	  alert("Enter valid zipcode");
	  clearInformation();
	  return;
  } 
  let mailArray = [];
  const mailBody = document.getElementById("mailBody").getElementsByTagName("input");
  for(let counter = 0; counter<mailBody.length;counter++) {
	  if(!checkMailFormat(mailBody[counter].value)){
		  alert("Enter valid e-mail");
		  clearInformation();
		  return;
	  }
	  mailArray.push(mailBody[counter].value);
  }
  let phoneList = [];
  phoneArray.forEach(phone => {
	  phoneList.push({'phone':phone});
  });
  let mailList = [];
  mailArray.forEach(mail => {
	  mailList.push({'mail':mail});
  });
  const data = JSON.stringify({
	  'login':login,
	  'name':name,
	  'additionalInformation':{
		  'phoneList':phoneList,
		  'mailList':mailList
	  },
	  'address':{
		  'houseNumber':house_no,
		  'streetName':street_name,
		  'area':area,
		  'city':city,
		  'state':state,
		  'zipCode':zipcode
	  }	  
  });
  fetch("http://localhost:8080/contacts",{
	  method: "POST",
	  body: data,
	  headers: {"Content-type": "application/json"}
  }).then(response => {
		if (response.ok) {
			return response.json();
		throw new Error("Request unaccepted");
		}
	}, newtworkError => {
		window.alert("Network Error!")
	}).then(response => {
		if(response == false){
			alert("Already Exist");
		}
		else {
			let contactBody = document.getElementById("contactList");
			let body = "";
			let contact = response;	
			body += `<tr id = "${contact.ID}">`;
			body += `<td class = 'text-left pad'>`;
			body += contact.name;
			body += `</td>`;
			body += `<td class='text-right'>`;
			body += `<button class = 'btn btn-info margin' data-toggle = 'collapse' data-target = '#edit${contact.ID}'><span class = 'glyphicon glyphicon-pencil'></span></button>`;
			body += `<button class = 'btn btn-info' onclick = 'deleteContact("${contact.ID}")'><span class = 'glyphicon glyphicon-trash'></span></button>`;
			body += `</td>`;
			body += `</tr>`;
			body += `<div class = "panel-collapse collapse fade" id = "edit${contact.ID}">`;
			body += `<div class = "panel-body form-format">`;
			body += `<input type="text" name = "login" class="form-control" placeholder="Enter login" value = "${contact.login}" required>`;
			body += `<input type="text" name = "name" class="form-control" placeholder="Enter name" value = "${contact.name}" required>`;
			contact.additionalInformation.mailList.forEach(mailObject => {
				body += `<input type="email" name = "email" id="${mailObject.mailUUID}" class="form-control" placeholder="Enter Alternate Mail" value = "${mailObject.mail}" required>`;
			});
			contact.additionalInformation.phoneList.forEach(phoneObject => {
				body += `<input type="text" name = "mobile" id="${phoneObject.phoneUUID}" class="form-control" placeholder="Enter phone" value = "${phoneObject.phone}" required>`;
			});
			body += `<div id = "${contact.address.addressUUID}">`;
			body += `<input type="text" name = "house_no" class="form-control" placeholder="Enter House Number" value="${contact.address.houseNumber}" required>`;
			body += `<input type="text" name = "street_name" class="form-control" placeholder="Enter Street Name" value="${contact.address.streetName}" required>`;
			body += `<input type="text" name = "area" class="form-control" placeholder="Enter Area" value="${contact.address.area}" required>`;
			body += `<input type="text" name = "city" class="form-control" placeholder="Enter City" value="${contact.address.city}" required>`;
			body += `<input type="text" name = "state" class="form-control" placeholder="Enter State" value="${contact.address.state}" required>`;
			body += `<input type="text" name = "zipcode" class="form-control" placeholder="Enter ZipCode" value="${contact.address.zipCode}" required>`;
			body += `</div>`;
			body += `<div class="text-center">
				<button class="btn btn-primary btn-cancel" type="submit"
				data-toggle="collapse" data-target="#edit${contact.ID}" onclick="editContact('${contact.ID}')">Save</button>
				<button class="btn btn-default btn-cancel " type="reset"
				data-toggle="collapse" data-target="#edit${contact.ID}" onclick="clearChanges('${contact.ID}')">Cancel</button>
				</div>`;
			body += `</div>`;
			body += `</div>`;
			contactBody.innerHTML += body;
		}	
		clearInformation();
	});
}


const deleteContact = id => {
	fetch("http://localhost:8080/contacts", {
		method: "DELETE",
		body: id
	}).then(response => {
		if (response.ok) {
			console.log('deleted successfully')
			return response.text();
		}
		throw new Error("Request unaccepted");
	}, newtworkError => {
		window.alert("Network Error!")
	}).then(response => {
		if(response === "true"){
			let row = document.getElementById(id);
			row.parentNode.removeChild(row);
		}
	});
	
}

const editContact = id => {
	
	let list = document.getElementById("edit"+id).getElementsByClassName("form-control");
	let body = {'ID': id}
	let phoneBody = []
	let mailBody = []
	let additionalInformation = {}
	let address = {}
	let change = false
	for(let i = 0; i < list.length; i++){
		let previous = list[i].defaultValue
		let current = list[i].value.trim()
		let name = list[i].getAttribute("name")
		if (current != previous) {
			change = true
			if(name=="name"){
				body['name'] = current;
				list[i].defaultValue = current
			}
			else if(name=="login"){
				body['login'] = current;
				list[i].defaultValue = current
			}
			else if(name=="email"){
				mailBody.push({'mailUUID':list[i].getAttribute('id'),'mail':current})
				list[i].defaultValue = current
			}
			else if(name=="mobile"){
				phoneBody.push({'phoneUUID':list[i].getAttribute('id'),'phone':current})
				list[i].defaultValue = current
			}
			else if(name=="house_no"){
				address['addressUUID'] = list[i].parentNode.getAttribute("id")
				address['houseNumber'] = current
				list[i].defaultValue = current
			}
			else if(name=="street_name"){
				address['addressUUID'] = list[i].parentNode.getAttribute("id")
				address['streetName'] = current
				list[i].defaultValue = current
			}
			else if(name=="area"){
				address['addressUUID'] = list[i].parentNode.getAttribute("id")
				address['area'] = current
				list[i].defaultValue = current
			}
			else if(name=="city"){
				address['addressUUID'] = list[i].parentNode.getAttribute("id")
				address['city'] = current
				list[i].defaultValue = current
			}
			else if(name=="state"){
				address['addressUUID'] = list[i].parentNode.getAttribute("id")
				address['state'] = current
				list[i].defaultValue = current
			}
			else if(name=="zipcode"){
				address['addressUUID'] = list[i].parentNode.getAttribute("id")
				address['zipCode'] = current
				list[i].defaultValue = current
			}				
		}
	}
	if(Object.keys(phoneBody).length >0)
		additionalInformation['phoneList'] = phoneBody
	if(Object.keys(mailBody).length >0)
		additionalInformation['mailList'] = mailBody
	if(Object.keys(additionalInformation).length >0)	
		body['additionalInformation'] = additionalInformation
	if(Object.keys(address).length > 0)
		body['address'] = address
	const data = JSON.stringify(body)
	fetch("http://localhost:8080/contacts",{
		method: "PUT",
		body: data,
		headers: {"Content-type": "application/json"}
	}).then(response => {
		if (response.ok) {
			console.log('updated successfully')
			return response.text();
		}
		throw new Error("Request unaccepted");
	}, newtworkError => {
		window.alert("Network Error!")
	}).then(response => {
		document.getElementById(id).getElementsByTagName('td')[0].innerHTML = list[1].value;
	})
}
const addEMailTextBox = () => {
	let parent = document.getElementById("mailBody");
	let input = document.createElement("input");
	input.setAttribute("type", "email");
	input.setAttribute("class", "form-control");
	input.setAttribute("placeholder","Enter another e-mail");
	parent.appendChild(input);
	
}
const addMobileTextBox = () => {
	let parent = document.getElementById("phoneBody");
	let input = document.createElement("input");
	input.setAttribute("type", "text");
	input.setAttribute("class", "form-control");
	input.setAttribute("placeholder","Enter another mobile");
	parent.appendChild(input);
}

const clearInformation = () => {
	document.getElementById("login").value = "";
	document.getElementById("name").value = "";
	document.getElementById("house_no").value = "";
	document.getElementById("street_name").value = "";
	document.getElementById("area").value = "";
	document.getElementById("city").value = "";
	document.getElementById("state").value = "";
	document.getElementById("zipcode").value = "";
	document.getElementById("phoneBody").innerHTML = `<input type = "text" class = "form-control" placeholder = "Enter mobile number" required>`;
	document.getElementById("mailBody").innerHTML = `<input type = "text" class = "form-control" placeholder = "Enter Alternate e-mail" required>`;
}

const clearChanges = id => {
	list = document.getElementById("edit"+id).getElementsByClassName("form-control");
	for(let i = 0; i < list.length; i++){
		list[i].value = list[i].defaultValue
	}
}

const checkNameFormat = name => {
	let nameTest = name.trim();
	nameTest = nameTest.replace(/-/g,"");
	nameTest = nameTest.replace(/_/g,"");
	if(nameTest==="")
		return false;
	if(/^[a-z _-]+$/i.test(nameTest))
		return true;
	return false;
}

const checkMailFormat = mail => {
	mail = mail.trim();
	if(mail.length === 0)
		return false;
	if(/^\w+([._]?\w+)*@\w+([._]?\w+)*(\.\w{2,3})+$/.test(mail))
		return true;
	return false;
}

const checkPhoneFormat = phone => {
	if(phone.length !== 10)
		return false;
	if(!isNaN(phone))
		return true;
	return false;
}

const checkZipCodeFormat = zipcode => {
	zipcode = zipcode.trim();
	if(!isNaN(zipcode) && zipcode.length === 6)
		return true;
	return false;
}

const checkHouseNumberFormat = houseNumber => {
	houseNumber = houseNumber.trim();
	if(houseNumber.length === 0)
		return false;
	if(/^\d{1,4}\w?([/]\d{1,4})?$/.test(houseNumber))
		return true;
	return false;
}

const checkStreetNameFormat = streetName => {
	streetName = streetName.trim();
	if(streetName.length === 0)
		return false;
	else if(/^[^\W_]+([ ][^\W_]+)*$/.test(streetName))
		return true;
	else
		return false;
}
