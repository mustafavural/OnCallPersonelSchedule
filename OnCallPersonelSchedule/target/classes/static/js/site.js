var _year;                                      //alındı
var _month;                                     //alındı

var _pzt;                                       //alındı
var _sal;                                       //alındı
var _csb;                                       //alındı
var _psb;                                       //alındı
var _cum;                                       //alındı
var _cmt;                                       //alındı
var _paz;                                       //alındı

var _selectedMonthName;                         //alındı

var _onCallStuffCount;                          //alındı
var _offDayCount;                               //alındı

var _personelCount;                             //alındı

var _specifiedDayPointsArray = [];              //alındı

var _scheduleDays = [];                         //alındı
var _personelsNameArray = [];                   //alındı
var _personelsOffDaysArray = [];
var _personelsVolunteerDaysArray = [];
var _personelsScoresArray = [];
var _personelsOnCallCountsArray = [];

function getDaysInMonth(year, month) {
    return new Date(year, month, 0).getDate();
}

function getWeekDay(day, month, year) {
    var date = new Date(year, month - 1, day);
    var daysOfWeek = ["Pazar", "Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi"];
    return daysOfWeek[date.getDay()];
}

function getMonth() {
    var monthElement = document.getElementById("bottomSettingsMonth");
    _month = parseInt(monthElement.value);
    _selectedMonthName = monthElement.options[monthElement.selectedIndex].text;
    _year = parseInt(document.getElementById("bottomSettingsYear").value);

    _offDayCount = parseInt(document.getElementById("inputIstirahat").value) || 0;
    _onCallStuffCount = parseInt(document.getElementById("inputNobetci").value) || 0;

    _pzt = parseInt(document.getElementById("inputPzt").value) || 0;
    _sal = parseInt(document.getElementById("inputSal").value) || 0;
    _csb = parseInt(document.getElementById("inputCsb").value) || 0;
    _psb = parseInt(document.getElementById("inputPsb").value) || 0;
    _cum = parseInt(document.getElementById("inputCum").value) || 0;
    _cmt = parseInt(document.getElementById("inputCmt").value) || 0;
    _paz = parseInt(document.getElementById("inputPaz").value) || 0;


    var monthAndNames = document.getElementById("month");
    monthAndNames.innerHTML = "";
    for (var day = 1; day <= getDaysInMonth(_year, _month); day++) {
        var dayName = getWeekDay(day, _month, _year);

        var div = document.createElement("div");
        div.className = "row input-group mb-1";

        var label = document.createElement("label");
        label.id = "longDayString_" + day;
        label.className = "col-8 form-label";
        label.textContent = day + " " + _selectedMonthName + " " + dayName;
        _scheduleDays.push(label.textContent);

        var input = document.createElement("input");
        input.id = "SpecifiedDayPoint_" + day;
        input.type = "text";
        input.className = "col-3 form-control pad-text text-center";
        input.setAttribute("aria-label", "SpecifiedDayPoint");

        switch (dayName) {
            case "Pazartesi":
                input.value = _pzt;
                break;
            case "Salı":
                input.value = _sal;
                break;
            case "Çarşamba":
                input.value = _csb;
                break;
            case "Perşembe":
                input.value = _psb;
                break;
            case "Cuma":
                input.value = _cum;
                break;
            case "Cumartesi":
                input.value = _cmt;
                break;
            case "Pazar":
                input.value = _paz;
                break;
        }
        _specifiedDayPointsArray.push(input.value);
        div.appendChild(label);
        div.appendChild(input);
        monthAndNames.appendChild(div);
    }
}

function deleteNewName(nameId) {
    var row = document.getElementById(nameId);
    row.remove();
}

function btnAddStuffClick(fullName) {
    if (fullName) {
        var element = document.getElementById("newPersonelNames");

        var div = document.createElement("div");
        div.id = "newName_" + Date.now();
        div.className = "col-12 input-group borderradius";

        var span = document.createElement("span");
        span.className = "input-group-text bg-white borderradius w-80";
        span.textContent = fullName;

        var button = document.createElement("button");
        button.className = "btn btn-danger pad-0 borderradius w-20";
        button.textContent = "Sil";
        button.onclick = function () {
            deleteNewName(div.id);
        };

        _personelsNameArray.push(fullName);

        div.appendChild(span);
        div.appendChild(button);

        element.appendChild(div);
    }
}

function method1(context) {
    var lines = context.split('\n'); // Satırları ayırmak için '\n' kullanılıyor

    var div = document.getElementById("newPersonelNames");
    div.innerHTML = ""; // Mevcut listeyi temizle

    lines.forEach(function (line) {
        line = line.trim(); // Satırın başındaki ve sonundaki boşlukları temizle
        if (line) { // Satır boş değilse listeye ekle
            btnAddStuffClick(line);
        }
    });
}

function fileInputEvent(event) {
    var fileInput = document.getElementById("fileInput");
    var file = event.target.files[0]; // Kullanıcının seçtiği dosya
    if (file && file.type === "text/plain") {
        var reader = new FileReader(); // Dosyayı okumak için FileReader kullanılıyor

        reader.onload = function (e) {
            var content = e.target.result; // Dosyanın içeriği burada
            method1(content);
        };

        reader.readAsText(file); // Dosyayı metin olarak oku
        fileInput.value = "";
    } else {
        alert("Lütfen bir .txt dosyası seçin!");
    }
}

async function pasteFromClipboard() {
    try {
        const text = await navigator.clipboard.readText();
        method1(text);

    } catch (err) {
        console.error("Panodaki metni alma hatası:", err);
    }
}

function getNameHeader() {

    var element = document.getElementById("tblNamesHeader");
    element.innerHTML = "";

    var rowHeader = document.createElement("div");
    rowHeader.id = "tblScheduleHeaderRow";
    rowHeader.className = "row text-white text-center";

    var colFullName = document.createElement("div");
    colFullName.className = "col-4";
    colFullName.innerHTML = "Adı Soyadı";

    var colOffDays = document.createElement("div");
    colOffDays.className = "col-3";
    colOffDays.innerHTML = "İzinli Günler";

    var colVolunteerDays = document.createElement("div");
    colVolunteerDays.className = "col-3";
    colVolunteerDays.innerHTML = "Nöbet İstediği Günler";

    var colOnCallScore = document.createElement("div");
    colOnCallScore.className = "col-1";
    colOnCallScore.innerHTML = "Nöbet Puanı";

    var colOnCallCount = document.createElement("div");
    colOnCallCount.className = "col-1";
    colOnCallCount.innerHTML = "Nöbet Sayısı";

    rowHeader.appendChild(colFullName);
    rowHeader.appendChild(colOffDays);
    rowHeader.appendChild(colVolunteerDays);
    rowHeader.appendChild(colOnCallScore);
    rowHeader.appendChild(colOnCallCount);

    element.appendChild(rowHeader);
}

function CreateNewPersonelRow(newFullName) {

    var element = document.getElementById("tblNamesBody");

    var rowBody = document.createElement("div");
    rowBody.id = "tblScheduleBodyRow";
    rowBody.className = "row text-white";

    var divColName = document.createElement("div");
    divColName.className = "col-4";

    var btnColName = document.createElement("button");
    btnColName.className = "btn btn-outline-primary text-white";
    btnColName.style = "width:100%; height:100%;";
    btnColName.type = "button";
    btnColName.name = "FullName";
    btnColName.value = newFullName;
    btnColName.innerHTML = newFullName;

    var divColOffDay = document.createElement("div");
    divColOffDay.className = "col-3 text-center";

    var txtColOffDay = document.createElement("input");
    txtColOffDay.className = "form-control";
    txtColOffDay.type = "text";
    txtColOffDay.name = "OffDays";

    var divColVolunteerDay = document.createElement("div");
    divColVolunteerDay.className = "col-3 text-center";

    var txtColVolunteerDay = document.createElement("input");
    txtColVolunteerDay.className = "form-control";
    txtColVolunteerDay.type = "text";
    txtColVolunteerDay.name = "VolunteerDays";

    var divColScore = document.createElement("div");
    divColScore.className = "col-1 text-center";
    divColScore.innerHTML = "0";

    var divColCount = document.createElement("div");
    divColCount.className = "col-1 text-center";
    divColCount.innerHTML = "0";

    divColName.appendChild(btnColName);
    divColOffDay.appendChild(txtColOffDay);
    divColVolunteerDay.appendChild(txtColVolunteerDay);

    rowBody.appendChild(divColName);
    rowBody.appendChild(divColOffDay);
    rowBody.appendChild(divColVolunteerDay);
    rowBody.appendChild(divColScore);
    rowBody.appendChild(divColCount);
    element.appendChild(rowBody);

}

function getNameProject() {
    var element = document.getElementById("newPersonelNames");

    var spans = element.getElementsByClassName("input-group-text bg-white borderradius w-80");
    document.getElementById("tblNamesBody").innerHTML = "";

    _personelCount = spans.length;
    getNameHeader();
    var bodyelement = document.getElementById("tblNamesBody");
    bodyelement.innerHTML = "";
    for (var i = 0; i < spans.length; i++) {
        CreateNewPersonelRow(spans.item(i).textContent);
    }

}

function getScheduleHeader() {

    var tblHeader = document.getElementById("tblScheduleHeader");
    tblHeader.innerHTML = "";
    var rowHeader = document.createElement("div");
    rowHeader.id = "rowHeader";
    rowHeader.className = "row text-center";

    var colDateHeader = document.createElement("div");
    colDateHeader.id = "colScheduleDateHeader";
    colDateHeader.className = "col-2";
    colDateHeader.innerHTML = "Tarih";
    rowHeader.appendChild(colDateHeader);

    for (var i = 1; i <= _onCallStuffCount; i++) {
        var colStuffHeader = document.createElement("div");
        colStuffHeader.id = "colScheduleStuffHeader_" + i;
        colStuffHeader.className = "col";
        colStuffHeader.style = "width:" + (83 / _onCallStuffCount).toFixed(0) + "%;";
        colStuffHeader.innerHTML = "Kişi " + i;
        rowHeader.appendChild(colStuffHeader);
        tblHeader.appendChild(rowHeader);
    }
}

function getScheduleBody() {

    var tblBody = document.getElementById("tblScheduleBody");
    tblBody.innerHTML = "";

    for (var i = 0; i < _scheduleDays.length; i++) {
        var rowBody = document.createElement("div");
        rowBody.id = "tblScheduleBodyRow_" + (i + 1);
        rowBody.className = "row";

        var colDateBody = document.createElement("div");
        colDateBody.id = "colScheduleDateBody_" + (i + 1);
        colDateBody.className = "col-2 text-start";
        colDateBody.innerHTML = _scheduleDays[i];
        rowBody.appendChild(colDateBody);

        for (var j = 1; j <= _onCallStuffCount; j++) {
            var colStuffBody = document.createElement("div");
            colStuffBody.id = "colScheduleStuffBody_" + (i + 1) + "_" + j;
            colStuffBody.className = "col text-center";
            colStuffBody.style = "width:" + (83 / _onCallStuffCount).toFixed(0) + "%;";

            var colStuffSelect = document.createElement("select");
            colStuffSelect.id = "colScheduleStuffSelect_" + (i + 1) + "_" + j;
            colStuffSelect.className = "col form-select text-center";

            colStuffBody.appendChild(colStuffSelect);

            rowBody.appendChild(colStuffBody);
        }

        tblBody.appendChild(rowBody);
    }
}

function getScheduleProject() {
    getScheduleHeader();
    getScheduleBody();
}

function getYearRange() {
    var selectYearElement = document.getElementById("bottomSettingsYear");

    var startYear = new Date().getFullYear() - 4;
    for (var i = startYear; i <= startYear + 10; i++) {
        var option = document.createElement("option");
        option.value = i;
        option.text = i;
        if (_year === i) {
            option.selected = true;
        }
        selectYearElement.appendChild(option);
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const choiceArray = document.querySelectorAll(".choice_mmx");
    choiceArray.forEach((card) => {
        card.addEventListener("click", () => {
            choiceArray.forEach((element) => {
                element.classList.remove("expand_mmx", "unset_mmx");
                element.classList.add("small_mmx");
            });
            card.classList.remove("small_mmx");
            card.classList.add("expand_mmx");
        });
    });
    _year = new Date().getFullYear();
    getYearRange();
});