// Load Deps
const Express = require("express");
const Axios = require("axios");
const Http = require("http");
const SocketIo = require("socket.io");
const fs = require("fs");

// Instanciate
const app = Express();
const server = Http.createServer(app);
const io = SocketIo(server);

const servers = require('./data/servers.json').targets;
const students = require('./data/students.json');
var status = {};
var players = {};

if (fs.existsSync('./data/leaderboard.json')) {
    players = require('./data/leaderboard.json')
}

app.use(Express.static('public'));

app.get("/", (req, res, next) => {
    res.sendFile(__dirname + "/index.html");
});

app.get("/leaderboard", (req, res, next) => {
    res.sendFile(__dirname + "/leaderboard.html");
})

const saveLeaderboard = () => {
    var json = JSON.stringify(players);
    fs.writeFile('./data/leaderboard.json', json, 'utf8');

    setTimeout(saveLeaderboard, 5000);
}

const fetchServersData = () => {
    io.emit('serverList', status);
    setTimeout(fetchServersData, 3000);
}

const fetchLeaderboard = () => {
    io.emit('leaderboard', players);
    setTimeout(fetchLeaderboard, 5000);
}

const saveScore = (server, player, score) => {
    var regex = /^\[(\d+)\] (.*)$/

    if (player.match(regex)) {
        var result = player.match(regex);
        if (result[1] in students) {

            if (!players[students[result[1]]]
                || players[students[result[1]]].score <= score) {
                players[students[result[1]]] = {
                    pseudo: result[2],
                    score: score,
                    server: server,
                };
            }

            return result[2];
        } else {
            console.log("Unknown", result[2]);
        }
    }

    return player;
}


const getDataFromSrv = (server) => {
    Axios.get("http://" + server + ":1664/status/game")
    .then((response) => {

        var scoresSimplify = [];
        var scoresDetail = {};

        Object.keys(response.data.scores).forEach(function(player) {
            var name = saveScore(server, player, response.data.scores[player].score);

            scoresSimplify.push({
                "name": name,
                "score": response.data.scores[player].score
            });

            scoresDetail[name] = response.data.scores[player];
        });

        io.emit('serverDetail_' + server, {
            status: "ON",
            data: {
                pluginRunning: response.data.pluginRunning,
                gameRunning: response.data.gameRunning,
                startTime: response.data.startTime,
                endTime: response.data.endTime,
                scores: scoresDetail,
            },
        });

        status[server] = {
            status: "ON",
            message: "",
            remaining: "",
            players: scoresSimplify,
        };

        var now = Date.now();

        if (response.data.endTime == 0) {
            status[server].status = "ON";
            status[server].message = "Starting in...";
            status[server].remaining = response.data.startTime;
        } else if (response.data.endTime <= now) {
            status[server].status = "OFF";
            status[server].message = "Restarting...";
            status[server].remaining = "Soon";
        } else {
            status[server].status = "ON";
            status[server].message = "Remaining time..";
            status[server].remaining = response.data.endTime;
        }

        setTimeout(getDataFromSrv, 3500, server);
    })
    .catch(function (error) {
        //console.log(error);

        io.emit('serverDetail_' + server, {
            status: "OFF",
            remaining: "",
            data: {}
        });

        status[server] = { status: "OFF" };
        setTimeout(getDataFromSrv, 5000, server);
    });;
}


servers.forEach ((s) => {
    app.get("/server/" + s, (req, res, next) => {
        res.sendFile(__dirname + "/detail.html");
    })
    getDataFromSrv(s);
});

fetchServersData();
fetchLeaderboard();
saveLeaderboard();

app.get("*", (req, res, next) => {
    res.redirect('/');
});

server.listen(1337);
