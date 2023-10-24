const formAddress = document.getElementById('addressForm');
const address_line_1 = document.getElementById('address_line_1');
const address_line_2 = document.getElementById('address_line_2');
const city = document.getElementById('city');
const country = document.getElementById('country');
const pinCode = document.getElementById('pincode');


const setErrorAddress = (element, message, e) => {
    console.log(element)
    e.preventDefault();
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.error');

    errorDisplay.innerText = message;
    inputControl.classList.add('error');
    inputControl.classList.remove('success');
};
const setSuccessAddress = element => {
    console.log(element)
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.error');
    errorDisplay.innerText = '';
    inputControl.classList.add('success');
    inputControl.classList.remove('error');
};


function validateInputs(e) {
    setSuccessAddress(address_line_1);
    setSuccessAddress(address_line_2);
    setSuccessAddress(city);
    setSuccessAddress(country);
    setSuccessAddress(pinCode);


    const address_line_1_Value = address_line_1.value.trim();
    const address_line_2_Value = address_line_2.value.trim();
    const cityValue = city.value.trim();
    const countryValue = country.value.trim();
    const pinCodeValue = pinCode.value.trim();

    if(address_line_1_Value === '') {
        setErrorAddress(address_line_1, 'Please enter address line 1', e);
        address_line_1.focus();
        return false;
    }
    else{
        setSuccessAddress(address_line_1);
    }

    if(address_line_2_Value === '') {
        setErrorAddress(address_line_2, 'Please enter address line 2', e);
        address_line_2.focus();
        return false;
    }
    else{
        setSuccessAddress(address_line_2);
    }

    if(cityValue === '') {
        setErrorAddress(city, 'Please enter City', e);
        city.focus();
        return false;
    }
    else{
        setSuccessAddress(city);
    }

    if(countryValue === '') {
        setErrorAddress(country, 'Please enter Country', e);
        country.focus();
        return false;
    }
    else{
        setSuccessAddress(country);
    }



    const pinCodeRegex = /^\d{6}$/;
    if(pinCodeValue === ""){
        setErrorAddress(pinCode, 'Enter pinCode', e);
        pinCode.focus();
        return false;
    }
    else if(!pinCodeValue.match(pinCodeRegex)){
        setErrorAddress(pinCode, 'Enter valid pinCode', e);
        pinCode.focus();
        return false;
    }
    else{
        setSuccessAddress(pinCode);
    }


    return true;
}

formAddress.addEventListener('submit', function(e) {
    console.log("hello");


    if(!validateInputs(e)){
        e.preventDefault();
        console.log("VALIDATION Success");
    }
});