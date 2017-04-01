// Load Deps
const Express = require("express");
const Axios = require("axios");
const Http = require("http");
const SocketIo = require("socket.io");

// Instanciate
const app = Express();
const server = Http.createServer(app);
const io = SocketIo(server);

const servers = require('./servers.json').targets;
var status = {};


app.use(Express.static('deps'));
app.use(Express.static('public'));


app.get("/", (req, res, next) => {
    res.sendFile(__dirname + "/index.html");
});

const fetchServersData = () => {
    io.emit('serverList', status);
    setTimeout(fetchServersData, 3000);
}

const padInt = (s) => {
    return (s.toString().length < 2 ? "0" : "") + s;
}

const dateToTime = (d) => {
    d = parseInt(d);
    var m = parseInt(d / 60);
    m = m < 0 ? 0 : m;
    return padInt(m) + ":" + padInt(d % 60);
}


const getDataFromSrv = (server) => {
    Axios.get("http://" + server + ":1664/status/game")
    .then((response) => {
        io.emit('serverDetail_' + server, {
            status: "online",
            data: response.data
        });

        var scores = [];
        Object.keys(response.data.scores).map(function(key, index) {
            scores.push({
                "name": key,
                "score": response.data.scores[key].score
            });
        });

        status[server] = {
            status: "online",
            players: scores,
        };

        var now = Date.now();
        var servStatus = "offline";

        if (response.data.endTime == 0) {
            status[server].status = "Starting in...";
            status[server].remaining = dateToTime((response.data.startTime - now) / 1000);
        } else if (response.data.startTime >= response.data.endTime) {
            status[server].status = "Restarting...";
            status[server].remaining = "Soon";
        } else {
            status[server].status = "Remaining time..";
            status[server].remaining = dateToTime((response.data.endTime - now) / 1000);
        }

        setTimeout(getDataFromSrv, 3500, server);
    })
    .catch(function (error) {
        //console.log(error);

        io.emit('serverDetail_' + server, {
            status: "offline",
            remaining: "",
            data: {}
        });

        status[server] = { "status": "offline" };
        setTimeout(getDataFromSrv, 3500, server);
    });;
}


servers.forEach ((s) => {
    app.get("/server/" + s, (req, res, next) => {
        res.sendFile(__dirname + "/detail.html");
    })
    getDataFromSrv(s);
});

fetchServersData();

// app.use(function(req, res) {
//     res.redirect('/');
// });

server.listen(1337);
