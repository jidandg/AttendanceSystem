//(function(){
    // Initialize Firebase
    // For Firebase JS SDK v7.20.0 and later, measurementId is optional
    var firebaseConfig = {
        apiKey: "AIzaSyCLHN2KVN0GtgsAyCkcGpjmcDE67BSIG2A",
        authDomain: "attendance-system-9f194.firebaseapp.com",
        databaseURL: "https://attendance-system-9f194-default-rtdb.asia-southeast1.firebasedatabase.app",
        projectId: "attendance-system-9f194",
        storageBucket: "attendance-system-9f194.appspot.com",
        messagingSenderId: "532136023104",
        appId: "1:532136023104:web:d8dd103df7fb77ee1efba5",
        measurementId: "G-REX0RF7770"
    };
    firebase.initializeApp(firebaseConfig);
    var firestore = firebase.firestore();

    const docRef = firestore.doc("samples/sandwichData");
    const outputHeader = document.querySelector("#hotDogOutput");
    const inputTextField = document.querySelector("#latestHotDogStatus");
    const saveButton = document.querySelector("#saveButton");

    saveButton.addEventListener("click", function() {
        const textToSave = inputTextField.value;
        console.log("I am going to save " + textToSave + " to Firestore");
        docRef.set({
            hotDogStatus: textToSave
        }).then(function(){
            console.log("Status saved!");
        }).catch(function(error){
            console.log("Got an error: ", error);
        });
    })


//})();