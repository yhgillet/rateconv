let environmentUrl = {
    prod: "https://1mo0hqggf4.execute-api.us-east-2.amazonaws.com/Prod/convert",
    dev: "http://localhost:8080/convert",
    dev_native: "http://localhost:3000/convert"
}

let env = new URL(window.location.href).searchParams.get("env") || 'prod'
let apiUrl = environmentUrl[env]

let rates = {
    kissRcRate: 0.7,
    kissRate: 0.7,
    kissRcCurve: 0.4,

    bfRcRate: 1,
    bfSuperRate: 0.7,
    bfExpo: 0,

    rfRate: 400,
    rfExpo: 50,
    rfAcro: 140,
}

let inputIds = [
    'kissRcRate',
    'kissRate',
    'kissRcCurve',
    'bfRcRate',
    'bfRcRate',
    'bfSuperRate',
    'bfExpo',
    'rfRate',
    'rfExpo',
    'rfAcro']


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


var refreshRates = () => {
    inputIds.forEach(inputId => {
        $("#" + inputId).val(rates[inputId]).change()
    })
}

var convertFrom = (rate) => {

    var data = {
        from: rate
    }
    switch (rate) {
        case "KISS": {
            data.rateParam1 = rates.kissRcRate
            data.rateParam2 = rates.kissRate
            data.rateParam3 = rates.kissRcCurve
            break
        }
        case "BF": {
            data.rateParam1 = rates.bfRcRate
            data.rateParam2 = rates.bfSuperRate
            data.rateParam3 = rates.bfExpo
            break
        }
        case "RF": {
            data.rateParam1 = rates.rfRate
            data.rateParam2 = rates.rfExpo
            data.rateParam3 = rates.rfRate
            break
        }
    }
    $.ajax({
        dataType: "json",
        type: 'POST',
        contentType: 'application/json',
        url: apiUrl,
        crossDomain: true,
        data: JSON.stringify(data),
        success: function (data) {
            handleConvertResult(data)
        }
    })

}

var handleConvertResult = (response) => {
    if (response.validationErrors) {
        alert(JSON.stringify(response.validationErrors))
        return;
    }
    response.result.forEach(result => {
        switch (result.type) {
            case "KISS": {
                rates.kissRate = Number(result.rate).toFixed(2)
                rates.kissRcCurve = Number(result.rcCurve).toFixed(2)
                rates.kissRcRate = Number(result.rcRate).toFixed(2)
                break;
            }
            case "BF": {
                rates.bfExpo = Number(result.expo).toFixed(2)
                rates.bfRcRate = Number(result.rcRate).toFixed(2)
                rates.bfSuperRate = Number(result.superRate).toFixed(2)
                break;
            }
            case "RF": {
                rates.rfAcro = Number(result.acrop).toFixed(0)
                rates.rfExpo = Number(result.expo).toFixed(0)
                rates.rfRate = Number(result.rate).toFixed(0)
                break;
            }
        }
    })
    refreshRates()

}

var init = () => {
    google.charts.load('current', {'packages': ['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    //bindings
    inputIds.forEach(inputId => {
        var input = $("#" + inputId);
        var slider = $("#" + inputId + "Slider")
        input.on('input change', function () {
            rates[inputId] = parseFloat(this.value, 10)
            slider.val(this.value).change()
        })
        slider.on('input', function () {
            input.val(this.value).change()
        })
    })

    $(".convert").click(function () {
        convertFrom($(this).data('rate'))
    })

    $(".input-field").on("change paste keyup", function () {
        drawChart();
    });
}

$(document).ready(init)