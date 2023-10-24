
const form = document.getElementById('signupForm');
const firstName = document.getElementById('firstName');
const lastName = document.getElementById('lastName');
const email = document.getElementById('email');
const phone = document.getElementById('phone');
const password = document.getElementById('password');
const repassword = document.getElementById('rePassword');
const checkbox = document.getElementById('exampleCheckbox12');

const setError = (element, message, e) => {
    console.log(element)
    e.preventDefault();
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.error');

    errorDisplay.innerText = message;
    inputControl.classList.add('error');
    inputControl.classList.remove('success');
};
const setSuccess = element => {
    console.log(element)
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.error');
    errorDisplay.innerText = '';
    inputControl.classList.add('success');
    inputControl.classList.remove('error');
};
const isValidEmail = email => {
//const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    const re =/[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,3}/;
    return re.test(String(email).toLowerCase());
}

function validateInputs(e) {
    setSuccess(firstName);
    setSuccess(lastName);
    setSuccess(email);
    setSuccess(phone);
    setSuccess(password);
    setSuccess(repassword);

    const firstNameValue = firstName.value.trim();
    const lastNameValue = lastName.value.trim();
    const phoneValue = phone.value.trim();
    const emailValue = email.value.trim();
    const passwordValue = password.value.trim();
    const repasswordValue = repassword.value.trim();

    if(firstNameValue === '') {
        setError(firstName, 'Please enter first name', e);
        firstName.focus();
        return false;
    }
    else{
        setSuccess(firstName);
    }
    if(lastNameValue === '') {
        setError(lastName, 'Please enter last name', e);
        lastName.focus();
        return false;
    }
    else{
        setSuccess(lastName);
    }

    if(emailValue === '') {
        setError(email, 'Please enter email', e);
        email.focus();
        return false;
    } else if (!isValidEmail(emailValue)) {
        setError(email, 'Provide a valid email address', e);
        email.focus();
        return false;
    }
    else{
        setSuccess(email);
    }

    const phonenoRegex = /^\d{10}$/;
    if(phoneValue === ""){
        setError(phone, 'Enter phone number', e);
        phone.focus();
        return false;
    }
    else if(!phoneValue.match(phonenoRegex)){
        setError(phone, 'Enter valid number', e);
        phone.focus();
        return false;
    }
    else{
        setSuccess(phone);
    }

    const passwordRegex  = /^[a-zA-Z0-9!@#$%^&*]{4,16}$/;
    if(passwordValue === '') {
        setError(password, 'Please enter password', e);
        password.focus();
        return false;
    }
    else if(!passwordValue.match(passwordRegex)){
        setError(password, 'Min 4 characters', e);
        password.focus();
        return false;
    }
    else{
        setSuccess(password);
    }

    if(repasswordValue === '') {
        setError(repassword, 'Please confirm password', e);
        repassword.focus();
        return false;
    } else if(repasswordValue !== passwordValue){
        setError(repassword, 'Password incorrect. Not matching', e);
        repassword.focus();
        return false;
    }
    else{
        setSuccess(repassword);
    }

    if(!checkbox.checked){
        setError(checkbox.parentElement, 'Accept terms & condtions to continue', e);
        checkbox.focus();
        return false;
    }
    else{
        setSuccess(checkbox);
    }

    return true;
}

form.addEventListener('submit', function(e) {
// e.preventDefault()
    if(validateInputs(e)){
        console.log("VALIDATION Success");
    }
})