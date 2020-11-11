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
            telephone: {
                required: true,
                pattern: "^[0-9]{2}[-][0-9]{4}[-][0-9]{7}$"
            },
            password: {
                required: true,
                pattern: "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{7,15}$"
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
            telephone: {
                required: "Please provide telephone number",
                pattern: "Must be all digits and match xx-xxxx-xxxxxxx"
            },
            password: {
                required: "Please provide a password",
                pattern: "Password must be between 8 and 15 characters long and" +
                    "must contain at least one upper case letter, at least one " +
                    "lower case letter, and at least one digit"
            }
        }

    });
});
$(function(){
    $("form[name='login']").validate({
        rules:{
            username1: "required",
            password1: {
                required: true,
            }
        },
        messages: {
            username1: "Please enter your user name",
            password1: {
                required: "Please provide your password"
            }
        }

    });
});
$(function(){
    $("form[name='choosenumbers']").validate({
        rules:{
            numbers: {
                required: true,
                pattern: "([0-9]|[1-5][0-9]|60)"
            },
            numbers1: {
                required: true,
                pattern: "([0-9]|[1-5][0-9]|60)"
            },
            numbers2: {
                required: true,
                pattern: "([0-9]|[1-5][0-9]|60)"
            },
            numbers3: {
                required: true,
                pattern: "([0-9]|[1-5][0-9]|60)"
            },
            numbers4: {
                required: true,
                pattern: "([0-9]|[1-5][0-9]|60)"
            },
            numbers5: {
                required: true,
                pattern: "([0-9]|[1-5][0-9]|60)"
            },
        },
        messages: {
            numbers: {
                required: "Please enter a number",
                pattern: "Please enter a number between 0 and 60"
            },
            numbers2: {
                required: "Please enter a number",
                pattern: "Please enter a number between 0 and 60"
            },
            numbers3: {
                required: "Please enter a number",
                pattern: "Please enter a number between 0 and 60"
            },
            numbers4: {
                required: "Please enter a number",
                pattern: "Please enter a number between 0 and 60"
            },
            numbers5: {
                required: "Please enter a number",
                pattern: "Please enter a number between 0 and 60"
            },
            numbers6: {
                required: "Please enter a number",
                pattern: "Please enter a number between 0 and 60"
            }
        }

    });
});

