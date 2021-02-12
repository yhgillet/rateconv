var rates = {
    kissRcRate: 0.7,
    kissRate: 0.7,
    kissRcCurve: 0.4,

    rfRate: 400,
    rfExpo: 50,
    rfAcro: 140,

    bfRcRate: 1,
    bfSuperRate: 0.7,
    bfExpo: 0
}

var calcKiss = function (rcCommand) {
    var a = Math.pow(rcCommand, 3) * rates.kissRcCurve + (1 - rates.kissRcCurve) * rcCommand
    return (200 * rates.kissRcRate * a) / (1 - rates.kissRate * rcCommand);
}

var calcBf = function (rcCommand) {
    var rcRate = rates.bfRcRate
    if (rcRate > 2.0) {
        rcRate = rcRate + (14.54 * (rcRate - 2.0));
    }
    var a = (Math.pow(rcCommand, 4) * rates.bfExpo + rcCommand * (1.0 - rates.bfExpo));
    return 200 * rcRate * a / (1 - rates.bfSuperRate * a);
}

var calcRf = function (rcCommand) {
    var returnValue = (1 + 0.01 * rates.rfExpo * (rcCommand * rcCommand - 1.0)) * rcCommand
    return returnValue * (rates.rfRate + (Math.abs(returnValue) * rates.rfRate * rates.rfAcro * 0.01))
}

google.charts.load('current', {'packages': ['corechart']});

google.charts.setOnLoadCallback(drawChart);


function drawChart() {
    var chart = new google.visualization.LineChart(document.getElementById('curve_chart'))
    var data = new google.visualization.DataTable();

    data.addColumn('number', 'Command');
    data.addColumn('number', 'Kiss');
    data.addColumn('number', 'BetaFlight');
    data.addColumn('number', 'RaceFlight');

    for (var i = 0; i <= 1; i += 0.01) {
        data.addRow([i, calcKiss(i), calcBf(i), calcRf(i)]);
    }
    data.addRow([1, calcKiss(i), calcBf(i), calcRf(i)]);

    var options = {
        // title: 'Rates',
        curveType: 'function',
        legend: {position: 'bottom'}
    };
    chart.draw(data, options);
}


var changeValues = function (rateParams) {
    rateParams.forEach(rateParam => {
        var input = document.getElementById(rateParam)
        input.value = rates[rateParam]
        input.onchange()
    })
}

var setupBinding = function (ids) {
    var copyThenDraw = function (from, to, inputId, draw) {
        return () => {
            to.value = from.value
            rates[inputId] = from.value
            drawChart()

        }
    }
    ids.forEach(inputId => {
        var input = document.getElementById(inputId);
        var slider = document.getElementById(inputId + "Slider")

        input.oninput = copyThenDraw(input, slider, inputId)
        input.onchange = copyThenDraw(input, slider, inputId)
        slider.onchange = copyThenDraw(slider, input, inputId)
        slider.oninput = copyThenDraw(slider, input, inputId)

    })


    var onConvert = function (btn, rate) {
        btn.onclick = () => alert("Convert from " + rate)
    }

    var convBf = document.getElementById("convBf");
    var convKiss = document.getElementById("convKiss");
    var convRf = document.getElementById("convRf");
    onConvert(convBf, "bf")
    onConvert(convKiss, "ki")
    onConvert(convRf, "rf")
}

function init() {

    var ids = ['bfRcRate',
        , 'bfRcRate',
        , 'bfSuperRate',
        , 'bfExpo',

        , 'kissRcRate',
        , 'kissRate',
        , 'kissRcCurve',

        , 'rfRate',
        , 'rfExpo',
        , 'rfAcro']
    setupBinding(ids);
}

document.addEventListener("DOMContentLoaded", init);