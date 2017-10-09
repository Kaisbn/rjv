# Dashboard

A simple dashboard to see server and player stats. It's build with AngualarJS
for the front and ExpressJS + SocketIO for the backend.

## How It Works

The backend fetch the data from all minecraft server every 5 seconds and
broadcast the new metrics to the clients through the websocket. There is one
websocket channel per server.

Every minutes, the backend saves the leaderboard as a simple JSON file.

## Requirements

One server with:
- a good CPU (since it's a NodeJS app, it's not multi-threaded)
- 2 GB of RAM (for 400 students, 2-3 websocket opens per students)
- a gigabit connection

## How-To Deploy

Using [PM2](http://pm2.keymetrics.io/) or Docker:

```console
$ docker stop creeps-dashboard-1 && docker rm creeps-dashboard-1
$ docker build -t creeps-dashboard .
$ docker run -d --name creeps-dashboard-1 -p 4242:1337 -v $(pwd)/servers.json:/usr/src/app/servers.json:ro --restart unless-stopped creeps-dashboard
```

## How-To Improves

- Use Redis or a real database to save the leaderboard and the servers metrics
- Change the leaderboards score computation (better than just `max(scores)`)
- Update the front-end to something like React/VueJS for performance
- Disconnect websocket on the client after X minutes of inactivity
