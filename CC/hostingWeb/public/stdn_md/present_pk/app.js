//(function){
    var config ={
        apiKey: "AIzaSyBz6Xnh9FrV-a4lcl0vKEWg1yCErqf_8VE",
        authDomain: "attendance-system-9f194.firebaseapp.com",
        databaseURL: "https://attendance-system-9f194.firebaseio.com",
        projectId: "attendance-system-9f194",
        storageBucket: "attendance-system-9f194.appspot.com",
        messagingSenderId: "532136023104"
    };
    firebase.initializeApp(config);
    var firestore = firebase.firestore();
    
    const docRef = firestore.doc("presentAttend/Philipus");
    const outputHeader = document.querySelector("#attendanceOutput");
    // const outputHeader2 = document.querySelector("#attendanceOutput2");
    const inputTextField = document.querySelector("#latesAttendanceStatus");
    const inputTextField2 = document.querySelector("#latesAttendanceDateTime");
    const saveButton = document.querySelector("#saveButton");
    const saveButton2 = document.querySelector("#saveButton2");
    
    saveButton.addEventListener("click", function() {
        const textToSave = inputTextField.value;
        const textToSave2 = inputTextField2.value;
        console.log(" Save " + textToSave + " to Firestore ");
        console.log(" Save " + textToSave2 + " to Firestore ");
        docRef.set({
            AttendanceStatus: textToSave,
            DateTime: textToSave2
        }).then(function() {
            console.log(" Status Saved! ");
        }).catch(function (error) {
            console.log(" Got an error: ", error);
        });
    });
    
    getRealtimeUpdates = function() {
        docRef.onSnapshot(function (doc) {
            if (doc && doc.exists) {
                const myData = doc.data();
                outputHeader.innerText = "Attendance Status: " + myData.hotDoStatus;
            }
        });
    }
//})();