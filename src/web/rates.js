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

var calcKiss = rcCommand => {
    var a = Math.pow(rcCommand, 3) * rates.kissRcCurve + (1 - rates.kissRcCurve) * rcCommand
    return (200 * rates.kissRcRate * a) / (1 - rates.kissRate * rcCommand);
}

var calcBf = rcCommand => {
    var rcRate = rates.bfRcRate
    if (rcRate > 2.0) {
        rcRate = rcRate + (14.54 * (rcRate - 2.0));
    }
    var a = (Math.pow(rcCommand, 4) * rates.bfExpo + rcCommand * (1.0 - rates.bfExpo));
    return 200 * rcRate * a / (1 - rates.bfSuperRate * a)
}

var calcRf = rcCommand => {
    var returnValue = ((1 + 0.01 * rates.rfExpo * (rcCommand * rcCommand - 1.0)) * rcCommand)
    returnValue = (returnValue * (rates.rfRate + (Math.abs(returnValue) * rates.rfRate * rates.rfAcro * 0.01)))
    return returnValue
}


var drawChart = () => {
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
        title: 'Rates',
        curveType: 'function',
        legend: {position: 'bottom'}
    };
    chart.draw(data, options);
}

var convertFrom = (rate, param1, param2, param3) => {
    var url = 'https://44r0mjinz8.execute-api.us-east-2.amazonaws.com/Prod/rateconv'
    $.getJSON(url, {
            type: rate,
            p1: rates[param1],
            p2: rates[param2],
            p3: rates[param3],
        }, function (data) {
            alert("Result " + data)
        }
    )
}

var changeValues = (rateParams) => {
    rateParams.forEach(rateParam => {
        var input = document.getElementById(rateParam)
        input.value = rates[rateParam]
        input.onchange()
    })
}

var setupBinding = (ids) => {
    var copyThenDraw = (from, to, inputId) => {
        return () => {
            to.value = from.value
            rates[inputId] = parseFloat(from.value, 10)
            drawChart()
        }
    }
    ids.forEach(inputId => {
        var input = document.getElementById(inputId);
        var slider = document.getElementById(inputId + "Slider")

        input.oninput = input.onchange = copyThenDraw(input, slider, inputId)
        slider.onchange = slider.oninput = copyThenDraw(slider, input, inputId)

    })

    var convBf = document.getElementById("convBf");
    var convKiss = document.getElementById("convKiss");
    var convRf = document.getElementById("convRf");
    convBf.onclick = () => convertFrom("BF", "bfRcRate", "bfSuperRate", "bfExpo")
    convKiss.onclick = () => convertFrom("KISS", "kissRcRate", "kissRate", "kissRcCurve")
    convRf.onclick = () => convertFrom("RF", "rfRate", "rfExpo", "rfAcro")

}

var init = () => {
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

google.charts.load('current', {'packages': ['corechart']});
google.charts.setOnLoadCallback(drawChart);

document.addEventListener("DOMContentLoaded", init);