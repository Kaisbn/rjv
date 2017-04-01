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


const getDataFromSrv = (server) => {
    Axios.get("http://" + server + ":1664/status/game")
    .then((response) => {
        io.emit('serverDetail_' + server, {
            status: "up",
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
            "status": "up",
            "players": scores,
        };

        setTimeout(getDataFromSrv, 3500, server);
    })
    .catch(function (error) {
        //console.log(error);

        io.emit('serverDetail_' + server, {
            status: "down",
            data: {}
        });

        status[server] = { "status": "down" };
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
