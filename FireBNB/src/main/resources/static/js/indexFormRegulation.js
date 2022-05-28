const manageForms = Vue.createApp({
    data(){
        return{  
			firstNameInput: "",
            firstNameError: "",
			lastNameInput: "",
            lastNameError: "",
			emailInput: "",
            emailError: "",
			addressInput: "",
            addressError: "",
			cityInput: "",
            cityError: "",
			postalCodeInput: null,
            postalCodeError:"",
			countryInput: "",
            countryError:"",
			phoneNumberInput: null,
            phoneNumberError:"",
			passwordInput: "",
            passwordError:"",
			passwordConfirmInput: "",
            passwordConfirmError:"",
			pn:null,
        }
    },
    methods:{
        register: function(e){
           
			console.log("formControl");

            ///// First Name /////
            if(!this.firstNameInput == ""){
            	this.$refs.firstNameError.innerText="";
			}else{
				e.preventDefault();
				this.$refs.firstNameError.innerText="First Name cannot be empty";
			}
          	///// Last Name /////
            if(!this.lastNameInput == ""){
            	this.$refs.lastNameError.innerText="";
			}else{
				e.preventDefault();
				this.$refs.lastNameError.innerText="Last Name cannot be empty";
			}
			
			///// Email /////
            if(!this.emailInput == ""){
            	this.$refs.emailError.innerText="";
			}else{
				e.preventDefault();
				this.$refs.emailError.innerText="Email cannot be empty";
			}
			
			///// Address /////
            if(!this.addressInput == ""){
            	this.$refs.addressError.innerText="";
			}else{
				e.preventDefault();
				this.$refs.addressError.innerText="Address cannot be empty";
			}
			///// City /////
            if(!this.cityInput == ""){
            	this.$refs.cityError.innerText="";
			}else{
				e.preventDefault();
				this.$refs.cityError.innerText="City cannot be empty";
			}
			///// Zip Code /////
            if(!this.postalCodeInput == null){
            	this.$refs.postalCodeError.innerText="";
			}else{
				this.$refs.postalCodeError.innerText="Postal Code cannot be empty";
			}

			///// Country /////
            if(!this.countryInput == ""){
            	this.$refs.countryError.innerText="";
			}else{
				e.preventDefault();
				this.$refs.countryError.innerText="Country cannot be empty";
			}
			
			///// Phone Number /////
			phoneNumberInput = this.phoneNumberInput;
			const regexPhoneNumber = /^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/;
			if(this.phoneNumberInput == null){
				e.preventDefault();
				this.$refs.phoneNumberError.innerText="Phone Number cannot be empty";
			}
			else if(!this.phoneNumberInput == null || phoneNumberInput.match(regexPhoneNumber)){
				this.$refs.phoneNumberError.innerText="";
			}
//			if(phoneNumberInput.match(regexPhoneNumber)){
//				this.$refs.phoneNumberError.innerText="";
//			}
			
			///// Password /////
            if(!this.passwordInput == ""){
            	this.$refs.passwordError.innerText="";
			}else{
				e.preventDefault();
				this.$refs.passwordError.innerText="Password cannot be empty";
			}
			///// Password Confirm /////
			if(this.passwordConfirmInput == this.passwordInput){
            	this.$refs.passwordConfirmError.innerText="";
			}else{
				e.preventDefault();
				this.$refs.passwordConfirmError.innerText="Passwords are different";
			}
			
      
        }, 
    }
})


manageForms.mount('#indexPage')
