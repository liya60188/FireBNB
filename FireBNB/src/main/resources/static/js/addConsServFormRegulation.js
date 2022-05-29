const manageForms = Vue.createApp({
    data(){
        return{  
			nameInput: "",
            nameError: "",
			pn:null,
        }
    },
    methods:{

        addConstraint_Service: function(e){
           
			console.log("formControl");

            /////Name of the constraint/service/////
            if(!this.nameInput == ""){
            	this.$refs.nameError.innerText="";
			}else{
				e.preventDefault();
				this.$refs.nameError.innerText="Name cannot be empty";
			}
		
			
        }, 
    }
})


manageForms.mount('#addConsSerPage')