$(function(){
    $("form[name='createaccount']").validate({
        rules:{
            firstname: "required",
            lastname: "required",
            username: "required",
            email: {
                required: true,
                email: true
            },
            telephone: "required",
            password: {
                required: true,
                minlength: 8,
                maxlength: 15

            }
        },
        messages: {
            firstname: "Please enter your first name",
            lastname: "Please enter your last name",
            username: "Please enter a user name",
            email: {
                required: "Please provide an email address",
                email: "Please enter a valid email address"
            },
            telephone: "Please provide telephone number",
            password: {
                required: "Please provide a password",
                minlength: "Please enter a password containing between 8 " +
                    "and 15 characters"
            }
        }

    });

    $("form[name='userlogin']").validate({
        rules:{
            username: "required",
            password: {
                required: true,
            }
        },
        messages: {
            username: "Please enter your user name",
            password: {
                required: "Please provide your password"
            }
        }

    });
});