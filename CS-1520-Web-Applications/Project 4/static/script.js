var timeoutID;
var timeout = 45000;

function setup() {
    document.getElementById("submitButton").addEventListener("click", sendCategory, true);
    document.getElementById("addPurchase").addEventListener("click", sendPurchase, true);
    n =  new Date();
    y = n.getFullYear();
    m = n.getMonth() + 1;
    d = n.getDate();
    document.getElementById("date").innerHTML = m + "/" + "01" + "/" + y + " - " + m + "/" + d + "/" + y;
    poller();
}

function makeRec(method, target, retCode, handlerAction, data) {
    var httpRequest = new XMLHttpRequest();

    if(!httpRequest) {
        alert('Cannot create an XMLHTTP instance');
        return false;
    }

    httpRequest.onreadystatechange = makeHandler(httpRequest, retCode, handlerAction);
    httpRequest.open(method, target);

    if(data) {
        httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        httpRequest.send(data);
    } else {
        httpRequest.send();
    }
}

function makeHandler(httpRequest, retCode, action) {
    console.log("making handler");
    function handler() {
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === retCode) {
                console.log("recieved response text: " + httpRequest.responseText);
                action(httpRequest.responseText);
            } else {
                alert("There was a problem with the request. Please refresh the page.");
            }
        }
    }
    return handler;
}

function poller() {
    makeRec("GET", "/cats", 200, repopulate);
}

function sendCategory() {
    window.clearTimeout(timeoutID);
    var newCat = document.getElementById("categoryName").value
    var limit = document.getElementById("limit").value
    var data;
    data = "cat=" + newCat + "&limit=" + limit;
    makeRec("POST", "/cats", 201, poller, data);
    document.getElementById("categoryName").value = "";
    document.getElementById("limit").value = "";
}

function sendPurchase() {
    window.clearTimeout(timeoutID);
    var newCost = document.getElementById("cost").value
    var newDescription = document.getElementById("description").value
    var newCat = document.getElementById("categoryElement").value
    var newDate = document.getElementById("setDate").value
    var data;
    data = "cost=" + newCost + "&description=" + newDescription + "&date=" + newDate + "&category=" + newCat;
    makeRec("POST", "/purchases", 201, poller, data);
    document.getElementById("cost").value = "";
    document.getElementById("description").value = "";
    document.getElementById("categoryElement").value = "";
    document.getElementById("setDate").value = "";
}

function deleteCategory(categoryID) {
    window.clearTimeout(timeoutID);
    makeRec("DELETE", "/cats/" + categoryID, 204, poller);
}

function addCell(row, text) {
    var newCell = row.insertCell();
    var newText= document.createTextNode(text);
    if(text === "overspent" || text.toString().includes("Uncategorized"))
        newCell.style.color = "#FF0000"
    newCell.appendChild(newText);
}

function repopulate(responseText) {
    console.log("repopulating categories table");
    var categories = JSON.parse(responseText);
    var table = document.getElementById("budgetTable");
    var newRow, newCell, newButton;
    while (table.rows.length > 0)
        table.deleteRow(0);

    newRow = table.insertRow();
    addCell(newRow, 'Name');
    addCell(newRow, 'Remaining');
    newCell = newRow.insertCell();

    for(var i = 0; i < categories['result'].length; i++) {
        if(categories['result'][i]['category_name'] !== "Uncategorized") {
            newRow = table.insertRow();
            addCell(newRow, categories['result'][i]['category_name']);

            if (categories['result'][i]['limit'] < 0)
                addCell(newRow, "overspent");
            else
                addCell(newRow, "$" + categories['result'][i]['limit']);

            newCell = newRow.insertCell();
            newButton = document.createElement("input");
            newButton.type = "button";
            newButton.value = "Delete";
            (function (_t) {
                newButton.addEventListener("click", function () {
                    deleteCategory(_t);
                });
            })(categories['result'][i]['category_id']);
            newCell.appendChild(newButton);

        }else {
            var newTable = document.getElementById("category");

            while (newTable.rows.length > 0)
                newTable.deleteRow(0);

            var newRow2 = newTable.insertRow();
            addCell(newRow2, "Uncategorized purchases: $" + categories['result'][i]['limit']);
        }
    }
    timeoutID = window.setTimeout(poller, timeout);
}

window.addEventListener("load", setup, true);