var timeoutID;
var timeout = 1000;

function setup() {
    document.getElementById("send").addEventListener("click", sendMessage, true);
    timeoutID = window.setTimeout(poller, timeout);
}

function sendMessage() {
    var request = new XMLHttpRequest();

    if (!request) {
        alert("Cannot create an XMLHTTP instance");
        return false;
    }

    var message = document.getElementById("messageContent").value;
    var author = document.getElementById("hidden").value;
    var a = [author, message];
    request.onreadystatechange = function() { handleMessage(request, a)};

    request.open("POST", "/new_message");
    request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    var data;
    data = "message=" + message + "&author" + author;
    request.send(data);
}

function handleMessage(request, message) {
    if (request.readyState === XMLHttpRequest.DONE) {
        if(request.status === 200) {
            addMessage(message);
            clearInput();
        } else
            alert("There was a problem with the post request");
    }
}

function poller() {
    var request = new XMLHttpRequest();

    if(!request) {
        alert("Cannot create an XMLHTTP instance");
        return false;
    }

    request.onreadystatechange = function() { handlePoll(request)};
    request.open("GET", "/messages_poll");
    request.send();
}

function handlePoll(request) {
    if(request.readyState === XMLHttpRequest.DONE) {
        if(request.status === 200) {
            var table = document.getElementById("messages");
            while(table.rows.length > 0)
                table.deleteRow(0);

            let room = document.getElementById("hiddenRoom").value;
            var rows = JSON.parse(request.responseText);
            for (var i = 0; i < rows[room].length; i++)
                addMessage(rows[room][i]);

            timeoutID = window.setTimeout(poller, timeout);
        } else
            alert("There was a problem with the poll request")
    }
}

function clearInput() {
    document.getElementById("messageContent").value = "";
}

function addMessage(message) {

    var table = document.getElementById("messages");
    let tempAuthor = document.getElementById("hidden").value;

    var authorRow = table.insertRow();
    if(message[0] === tempAuthor)
        authorRow.insertCell().colSpan = 2;

    var author = authorRow.insertCell();
    var info = document.createTextNode(message[0]);
    author.style.fontSize = "12px";
    author.appendChild(info);

    var messageRow = table.insertRow();
    if(message[0] === tempAuthor)
        messageRow.insertCell().colSpan=2;

    var messageText = messageRow.insertCell();
    var text = document.createTextNode(message[1]);
    messageText.style.fontSize = "15px";
    messageText.style.fontWeight = "bold";
    messageText.appendChild(text);
}

window.addEventListener("load", setup, true);